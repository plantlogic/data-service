package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.authentication.PLRole;
import edu.csumb.spring19.capstone.models.card.Card;
import edu.csumb.spring19.capstone.models.card.Chemicals;
import edu.csumb.spring19.capstone.models.card.Comment;
import edu.csumb.spring19.capstone.models.card.Commodities;
import edu.csumb.spring19.capstone.models.card.Irrigation;
import edu.csumb.spring19.capstone.models.card.Tractor;
import edu.csumb.spring19.capstone.models.dbfilter.DbFilter;
import edu.csumb.spring19.capstone.models.dbfilter.DbFilterResponse;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import edu.csumb.spring19.capstone.security.RanchAccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.LimitExceededException;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/entry")
@PreAuthorize("hasAnyRole('DATA_ENTRY','IRRIGATOR')")
public class CardEntryController {

    @Autowired
    private RanchRepository ranchRepository;

    @Autowired
    private RanchAccess ranchAccess;
    
    @GetMapping("/commodityAcreCount")
    @Operation(summary = "Get the total number of acres covered by each commodity among all open cards", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getCommoditiesAcreCount(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards) {
                                       
        Iterable<Card> result = ranchRepository.findAllCommodityIds(ranchAccess.getRanchList(), false);
        HashMap<String, Float> counts = new HashMap<String, Float>();
        Iterator<Card> i = result.iterator();
        while(i.hasNext()) {
            for (Commodities c : i.next().getCommodities()) {
                Float acres = c.getCropAcres();
                if (acres == null) {
                    acres = 0f;
                }
                if (counts.containsKey(c.getCommodity())) {
                    counts.put(c.getCommodity(), counts.get(c.getCommodity()) + acres);
                } else {
                    counts.put(c.getCommodity(), acres);
                }
            }
        }
        return new RestData<>(counts);
    }

    @GetMapping("/commodityCardCount")
    @Operation(summary = "Get the total number of occurences where each commodity exists among all open cards", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getCardCommoditiesCount(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards) {

        Iterable<Card> result = ranchRepository.findAllCommodityIds(ranchAccess.getRanchList(), false);
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        Iterator<Card> i = result.iterator();
        while(i.hasNext()) {
            for (Commodities c : i.next().getCommodities()) {
                if (counts.containsKey(c.getCommodity())) {
                    counts.put(c.getCommodity(), counts.get(c.getCommodity()) + 1);
                } else {
                    counts.put(c.getCommodity(), 0);
                }
            }
        }
        return new RestData<>(counts);
    }

    @GetMapping("/count")
    @Operation(summary = "Get a count of all permitted cards in the database.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getCardCount(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards) {
        RestDTO result = new RestFailure("You requested no cards, or don't have permission to access the requested cards.");

        if (openCards && closedCards) {
            result = new RestData<>(ranchRepository.countAllByRanchNameIsIn(ranchAccess.getRanchList()));
        } else if (openCards) {
            result = new RestData<>(ranchRepository.countAllByRanchNameIsIn(ranchAccess.getRanchList(), false));
        } else if (closedCards) {
            result = new RestData<>(ranchRepository.countAllByRanchNameIsIn(ranchAccess.getRanchList(), true));
        } else {
            result = new RestFailure("You requested no cards.");
        }
        return result;
    }

    @GetMapping("/ranches")
    @Operation(summary = "Get all cards the user is allowed access to view.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getAllRanchData() {
        Sort sortByRanchName = Sort.by(Sort.Order.asc("fieldID"), Sort.Order.desc("lastUpdated"));
        return new RestData<>(ranchRepository.findAllByIsClosedFalseAndRanchNameIsIn(ranchAccess.getRanchList(), sortByRanchName));
    }

    @PostMapping("/ranches")
    @Operation(summary = "Create a new card.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO createRanchData(@Valid @RequestBody Card card) {
        if (ranchAccess.hasRole(PLRole.DATA_ENTRY) || ranchAccess.hasRole(PLRole.DATA_VIEW)) {
            if (ranchAccess.cardExistsAndViewAllowed(Optional.of(card))) {
                card.setDateCreated(new Date());
                ranchRepository.save(card);
                return new RestSuccess();
            } else return new RestFailure("There was an error saving the card. You may not be allowed to save to that ranch.");
        } else return new RestFailure("You don't have permission to create a card.");
    }

    @GetMapping("/ranches/{id}")
    @Operation(summary = "Get a specific card by its ID.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getRanchData(@PathVariable("id") String id) {
        Optional<Card> card = ranchRepository.findById(id);

        if (ranchAccess.cardExistsAndViewAllowed(card)) return new RestData<>(card.get());
        else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
    }

    @PostMapping("/ranches/{id}/chemical")
    @Operation(summary = "Add chemical data to a card.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO addChemicalData(@PathVariable("id") String id, @RequestBody Chemicals data) {
        Optional<Card> card = ranchRepository.findById(id);
        if (ranchAccess.hasRole(PLRole.DATA_ENTRY) || ranchAccess.hasRole(PLRole.DATA_VIEW)) {
            if (ranchAccess.cardExistsAndViewAllowed(card)) {
                try {
                    card.get().addPostChemicals(data);
                } catch (LimitExceededException e) {
                    return new RestFailure(e.getMessage());
                }
                card.get().setLastUpdated();
                ranchRepository.save(card.get());
                return new RestSuccess();
            } else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
        } else return new RestFailure("You don't have permission to add chemical data to this card.");
    }

    @PutMapping("/ranches/{id}/close")
    @Operation(summary = "Close card when completed.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO closeCard(@PathVariable("id") String id, @RequestBody Card ranch) {
        Optional<Card> card = ranchRepository.findById(id);
        if (ranchAccess.hasRole(PLRole.DATA_ENTRY) || ranchAccess.hasRole(PLRole.DATA_VIEW)) {
            if (ranchAccess.cardExistsAndViewAllowed(card)) {
                card.get().setHarvestDate(ranch.getHarvestDate());
                card.get().setClosed(true);
                card.get().setLastUpdated();
                ranchRepository.save(card.get());
                return new RestSuccess();
            } else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
        } else return new RestFailure("You don't have permission to close this card.");
    }

    @PostMapping("/ranches/{id}/irrigation")
    @Operation(summary = "Add Irrigation data to a card.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO addIrrigationData(@PathVariable("id") String id, @RequestBody Irrigation data) {
        Optional<Card> card = ranchRepository.findById(id);
        if (ranchAccess.cardExistsAndViewAllowed(card)) {
            try {
                card.get().addIrrigation(data);
            } catch (LimitExceededException e) {
                return new RestFailure(e.getMessage());
            }
            card.get().setLastUpdated();
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
    }

    @PutMapping("/ranches/{id}/setComments")
    @Operation(summary = "Sets a card comments", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO setComments(@PathVariable("id") String id, @Valid @RequestBody List<Comment> comments) {
        Optional<RestDTO> data = ranchRepository.findById(id)
              .map(card -> {
                  card.setLastUpdated();
                  card.setComments(comments);
                  ranchRepository.save(card);
                  return new RestSuccess();
              });
              return data.orElse(new RestFailure("Card ID not found."));
    }

    @PostMapping("/ranches/{id}/tractor")
    @Operation(summary = "Add tractor data to a card.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO addTractorData(@PathVariable("id") String id, @RequestBody Tractor data) {
        Optional<Card> card = ranchRepository.findById(id);
        if (ranchAccess.hasRole(PLRole.DATA_ENTRY) || ranchAccess.hasRole(PLRole.DATA_VIEW)) {
            if (ranchAccess.cardExistsAndViewAllowed(card)) {
                try {
                    card.get().addTractor(data);
                } catch (LimitExceededException e) {
                    return new RestFailure(e.getMessage());
                }
                card.get().setLastUpdated();
                ranchRepository.save(card.get());
                return new RestSuccess();
            } else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
        } else return new RestFailure("You don't have permission to add tractor data to this card.");
    }

    @PostMapping("/ranches/{id}/wetDate")
    @Operation(summary = "Set the wet date of a card.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO addWetDate(@PathVariable("id") String id, @RequestBody Date wetDate) {
        Optional<Card> card = ranchRepository.findById(id);
        if (ranchAccess.hasRole(PLRole.DATA_ENTRY) || ranchAccess.hasRole(PLRole.DATA_VIEW)) {
            if (ranchAccess.cardExistsAndViewAllowed(card)) {
                card.get().setWetDate(wetDate);    
                card.get().setLastUpdated();
                ranchRepository.save(card.get());
                return new RestSuccess();
            } else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
        } else return new RestFailure("You don't have permission to set the wet, thin, or hoe date on this card.");
    }

    @PostMapping("/ranchesFiltered")
    @Operation(summary = "Get all cards from the database, according to provided filter.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getAllRanchDataFiltered(@Valid @RequestBody DbFilter filter) {
        // Set defualt sort method
        Sort sort = Sort.by(Sort.Order.desc("lastUpdated"));
        // Use mongo sorting for provided filter if applicable
        if (!filter.getSort().equals("commodities")) {
            if (filter.getOrder().equals("asc")) {
                sort = Sort.by(Sort.Order.asc(filter.getSort()));
            } else {
                sort = Sort.by(Sort.Order.desc(filter.getSort()));
            }
        }
        // Build new ranch list which finds intersection between queried ranches and permitted ranches
        List<String> permitted = ranchAccess.getRanchList();
        ArrayList<String> ranches = new ArrayList<String>();
        for (int i = 0; i < permitted.size(); i++) {
            if (filter.getRanches().contains(permitted.get(i))) {
                ranches.add(permitted.get(i));
            }
        }
        // Get iterable over database cards sorted with given ranchlist
        Iterable<Card> cards = ranchRepository.findAllByIsClosedFalseAndRanchNameIsIn(ranches, sort);
        // Perform filter operation and return response
        return new RestData<DbFilterResponse>(filter.filter(cards));
    }

    @GetMapping("/recentlyHarvestedCount")
    @Operation(summary = "Get count of cards harvested in last 6 months", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getRecentlyHarvestedCount() {
        Calendar c = Calendar.getInstance();
        List<Integer> counts = Arrays.asList(0, 0, 0, 0, 0, 0);
        Date current = new Date();
        c.setTime(current);
        int currentMonth = c.get(Calendar.MONTH);
        // Set start day 5 months prior on the first of the month (Do not include the 6th month back)
        c.add(Calendar.MONTH, -5);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date start = c.getTime();
        Iterable<Card> recent = ranchRepository.findAllHarvestedBetween(start, current);
        Iterator<Card> i = recent.iterator();
        while(i.hasNext()) {
            Date temp = i.next().getHarvestDate();
            if (temp != null) {
                c.setTime(temp);
                int index = 5 - (((currentMonth + 12) - c.get(Calendar.MONTH)) % 12);
                counts.set(index, counts.get(index)+1);
            }
        }
        return new RestData<>(counts);
    }
}
