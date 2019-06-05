package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.card.Card;
import edu.csumb.spring19.capstone.models.card.Chemicals;
import edu.csumb.spring19.capstone.models.card.Irrigation;
import edu.csumb.spring19.capstone.models.card.Tractor;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import edu.csumb.spring19.capstone.security.RanchAccess;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.LimitExceededException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/entry")
@PreAuthorize("hasRole('DATA_ENTRY')")
public class CardEntryController {

    @Autowired
    private RanchRepository ranchRepository;

    @Autowired
    private RanchAccess ranchAccess;
    
    @PostMapping("/ranches")
    @ApiOperation(value = "Create a new card.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO createRanchData(@Valid @RequestBody Card card) {
        if (ranchAccess.cardViewAccessAllowed(Optional.of(card))) {
            ranchRepository.save(card);
            return new RestSuccess();
        } else return new RestFailure("There was an error saving the card. You may not be allowed to save to that ranch.");
    }

    @GetMapping("/ranches")
    @ApiOperation(value = "Get all cards the user is allowed access to view.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getAllRanchData() {
        Sort sortByRanchName = Sort.by(Sort.Order.asc("fieldID"), Sort.Order.desc("lastUpdated"));
        return new RestData<>(ranchRepository.findAllByIsClosedFalseAndRanchNameIsIn(ranchAccess.getRanchList(), sortByRanchName));
    }

    @GetMapping("/ranches/{id}")
    @ApiOperation(value = "Get a specific card by its ID.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getRanchData(@PathVariable("id") String id) {
        Optional<Card> card = ranchRepository.findById(id);

        if (ranchAccess.cardViewAccessAllowed(card)) return new RestData<>(card.get());
        else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
    }

    @PostMapping("/ranches/{id}/tractor")
    @ApiOperation(value = "Add tractor data to a card.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO addTractorData(@PathVariable("id") String id, @RequestBody Tractor data) {
        Optional<Card> card = ranchRepository.findById(id);
        if (ranchAccess.cardViewAccessAllowed(card)) {
            try {
                card.get().addTractor(data);
            } catch (LimitExceededException e) {
                return new RestFailure(e.getMessage());
            }

            card.get().setLastUpdated();
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
    }

    @PostMapping("/ranches/{id}/irrigation")
    @ApiOperation(value = "Add Irrigation data to a card.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO addIrrigationData(@PathVariable("id") String id, @RequestBody Irrigation data) {
        Optional<Card> card = ranchRepository.findById(id);
        if (ranchAccess.cardViewAccessAllowed(card)) {
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

    @PostMapping("/ranches/{id}/chemical")
    @ApiOperation(value = "Add chemical data to a card.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO addChemicalData(@PathVariable("id") String id, @RequestBody Chemicals data) {
        Optional<Card> card = ranchRepository.findById(id);
        if (ranchAccess.cardViewAccessAllowed(card)) {
            try {
                card.get().addPostChemicals(data);
            } catch (LimitExceededException e) {
                return new RestFailure(e.getMessage());
            }

            card.get().setLastUpdated();
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
    }

    @PutMapping("/ranches/{id}/close")
    @ApiOperation(value = "Close card when completed.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO closeCard(@PathVariable("id") String id, @RequestBody Card ranch) {
        Optional<Card> card = ranchRepository.findById(id);

        if (ranchAccess.cardViewAccessAllowed(card)) {
            card.get().setHarvestDate(ranch.getHarvestDate());
            card.get().setClosed(true);
            card.get().setLastUpdated();
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
    }




}

