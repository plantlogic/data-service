package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.models.authentication.PLRole;
import edu.csumb.spring19.capstone.models.card.Card;
import edu.csumb.spring19.capstone.models.card.Commodities;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/view")
@PreAuthorize("hasAnyRole('DATA_VIEW', 'CONTRACTOR_VIEW', 'SHIPPER', 'TH_VIEW')")
public class CardViewController {
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

    @GetMapping("/commodityAcreCount/{shipperId}")
    @Operation(summary = "Get the total number of acres covered by each commodity among all open cards with matching shipperId",
                  security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getShipperCommoditiesAcreCount(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards,
                                   @PathVariable("shipperId") String shipperId) {
                                       
        Iterable<Card> result = ranchRepository.findAllCommodityIdsByShipper(shipperId, false);
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
    public RestDTO getCommoditiesCardCount(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
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

    @GetMapping("/commodityCardCount/{shipperId}")
    @Operation(summary = "Get the total number of occurences where each commodity exists among all open cards with matching shipperId",
                  security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getShipperCommoditiesCardCount(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards,
                                   @PathVariable("shipperId") String shipperId) {

        Iterable<Card> result = ranchRepository.findAllCommodityIdsByShipper(shipperId, false);
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

    @GetMapping("/count/{shipperId}")
    @Operation(summary = "Get a count of all permitted cards in the database.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getCardCountByShipper(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards,
                                   @PathVariable("shipperId") String shipperId) {
        RestDTO result = new RestFailure("You requested no cards, or don't have permission to access the requested cards.");

        if (openCards && closedCards) {
            result = new RestData<>(ranchRepository.countAllByShippersContaining(shipperId));
        } else if (openCards) {
            result = new RestData<>(ranchRepository.countAllByShippersContaining(shipperId, false));
        } else if (closedCards) {
            result = new RestData<>(ranchRepository.countAllByShippersContaining(shipperId, true));
        } else {
            result = new RestFailure("You requested no cards.");
        }
        return result;
    }

    @GetMapping("/ranches")
    @Operation(summary = "Get all cards from the database.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getAllRanchData(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards) {
        RestDTO result = new RestFailure("You requested no cards, or don't have permission to access the requested cards.");
                                
        Sort sortByRanchName = Sort.by(Sort.Order.asc("fieldID"), Sort.Order.desc("lastUpdated"));
        if (openCards && closedCards) {
            result = new RestData<>(ranchRepository.findAllByRanchNameIsIn(ranchAccess.getRanchList(), sortByRanchName));
        } else if (openCards) {
            result = new RestData<>(ranchRepository.findAllByIsClosedFalseAndRanchNameIsIn(ranchAccess.getRanchList(), sortByRanchName));
        } else if (closedCards) {
            result = new RestData<>(ranchRepository.findAllByIsClosedTrueAndRanchNameIsIn(ranchAccess.getRanchList(), sortByRanchName));
        } else {
            result = new RestFailure("You requested no cards.");
        }
        return result;
    }

    @GetMapping("/ranches/{id}")
    @Operation(summary = "Get card from the database by its ID.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getRanchData(@PathVariable("id") String id) {
        Optional<Card> card = ranchRepository.findById(id);
        if (!card.isPresent()) {
            return new RestFailure("Card not found with id " + id );
        } else {
            PLRole[] roles = {PLRole.DATA_VIEW, PLRole.CONTRACTOR_VIEW, PLRole.SHIPPER, PLRole.TH_VIEW};
            // LIMIT DATA SENT IF SHIPPER
            if (ranchAccess.cardExistsAndHasAnyPermissions(roles, true, card)) {
                return new RestData<>(card.get());
            } else {
                if (!ranchAccess.hasRole(PLRole.SHIPPER)) {
                    return new RestFailure("You don't have permission to access this card.");
                } else {
                    return new RestFailure("There was an error retrieving the card data.");
                }
            }
        }
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
        Iterable<Card> cards = ranchRepository.findAllByRanchNameIsIn(ranches, sort);
        // Perform filter operation and return response
        return new RestData<DbFilterResponse>(filter.filter(cards));
    }

    @PostMapping("/ranchesFilteredTH")
    @Operation(summary = "Get all cards from the database, according to provided filter, for the Thin & Hoe Page.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getAllRanchDataFilteredTH(@Valid @RequestBody DbFilter filter) {
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

    @GetMapping("/recentlyHarvestedCount/{shipperId}")
    @Operation(summary = "Get count of cards harvested in last 6 months", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getShipperRecentlyHarvestedCount(@PathVariable("shipperId") String shipperId) {
        Calendar c = Calendar.getInstance();
        List<Integer> counts = Arrays.asList(0, 0, 0, 0, 0, 0);
        Date current = new Date();
        c.setTime(current);
        int currentMonth = c.get(Calendar.MONTH);
        // Set start day 5 months prior on the first of the month (Do not include the 6th month back)
        c.add(Calendar.MONTH, -5);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date start = c.getTime();
        Iterable<Card> recent = ranchRepository.findAllByShipperAndHarvestedBetween(shipperId, start, current);
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

    @GetMapping("/shipperRanches/{shipperId}")
    @Operation(summary = "Get all cards from the database which include the shipperID.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getShipperRanchData(@PathVariable("shipperId") String shipperId) {
        RestDTO result = new RestFailure("You requested no cards, or don't have permission to access the requested cards.");
        try {
            Sort sortByRanchName = Sort.by(Sort.Order.asc("fieldID"), Sort.Order.desc("lastUpdated"));
            result = new RestData<>(ranchRepository.findAllByShippersContaining(shipperId, sortByRanchName));
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    @PostMapping("/shipperRanchesFiltered/{shipperId}")
    @Operation(summary = "Get all cards from the database containing the shipperID, according to provided filter.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getAllShipperRanchDataFiltered(@Valid @RequestBody DbFilter filter, @PathVariable("shipperId") String shipperId) {
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
        // Get iterable over database cards sorted with given ranchlist
        Iterable<Card> cards = ranchRepository.findAllByRanchNameIsInAndShippersContaining(filter.getRanches(), shipperId, sort);
        // Perform filter operation and return response
        return new RestData<DbFilterResponse>(filter.filter(cards));
    }

    @GetMapping("/uniqueCommodityList")
    @Operation(summary = "Get a list of all unique commodities", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getUniqueCommodities(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards) {
        Iterable<Card> result;
        if (openCards && closedCards) {
            result = ranchRepository.findAllCommodityIds(ranchAccess.getRanchList());
        } else if (openCards) {
            result = ranchRepository.findAllCommodityIds(ranchAccess.getRanchList(), false);
        } else if (closedCards) {
            result = ranchRepository.findAllCommodityIds(ranchAccess.getRanchList(), true);
        } else {
            return new RestFailure("You requested no cards.");
        }

        ArrayList<String> commodityList = new ArrayList<String>();

        Iterator<Card> i = result.iterator();
        while(i.hasNext()) {
            for (Commodities c : i.next().getCommodities()) {
                if (!commodityList.contains(c.getCommodity())) {
                    commodityList.add(c.getCommodity());
                }
            }
        }
        return new RestData<>(commodityList);
    }

    @GetMapping("/uniqueRanchList")
    @Operation(summary = "Get a list of all unique ranches available", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getUniqueRanchList(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards) {
        Iterable<Card> result;
        if (openCards && closedCards) {
            result = ranchRepository.findAllRanchIds(ranchAccess.getRanchList());
        } else if (openCards) {
            result = ranchRepository.findAllRanchIds(ranchAccess.getRanchList(), false);
        } else if (closedCards) {
            result = ranchRepository.findAllRanchIds(ranchAccess.getRanchList(), true);
        } else {
            return new RestFailure("You requested no cards.");
        }

        ArrayList<String> ranchList = new ArrayList<String>();

        Iterator<Card> i = result.iterator();
        while(i.hasNext()) {
            String ranchId = i.next().getRanchName();
            if (!ranchList.contains(ranchId)) {
                ranchList.add(ranchId);
            }
        }
        return new RestData<>(ranchList);
    }
}
