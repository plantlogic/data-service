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
    
    @PostMapping("/ranches")
    @ApiOperation(value = "Create a new card.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO createRanchData(@Valid @RequestBody Card card) {
        ranchRepository.save(card);
        return new RestSuccess();
    }

    @GetMapping("/ranches")
    @ApiOperation(value = "Get all cards the user is allowed access to view.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getAllRanchData() {
        Sort sortByRanchName = Sort.by(Sort.Order.asc("fieldID"), Sort.Order.desc("lastUpdated"));
        return new RestData<>(ranchRepository.findAllByIsClosedFalse(sortByRanchName));
    }

    @GetMapping("/ranches/{id}")
    @ApiOperation(value = "Get a specific card by its ID.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getRanchData(@PathVariable("id") String id) {
        Optional<RestDTO> data = ranchRepository.findById(id).map(RestData::new);
        return data.orElse(new RestFailure("card ID not found."));
    }

    @PostMapping("/ranches/{id}/tractor")
    @ApiOperation(value = "Add tractor data to a card.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO addTractorData(@PathVariable("id") String id, @RequestBody Tractor data) {
        Optional<Card> card = ranchRepository.findById(id);
        if (card.isPresent()) {
            try {
                card.get().addTractor(data);
            } catch (LimitExceededException e) {
                return new RestFailure(e.getMessage());
            }

            card.get().setLastUpdated();
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("card ID not found.");
    }

    @PostMapping("/ranches/{id}/irrigation")
    @ApiOperation(value = "Add Irrigation data to a card.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO addIrrigationData(@PathVariable("id") String id, @RequestBody Irrigation data) {
        Optional<Card> card = ranchRepository.findById(id);
        if (card.isPresent()) {
            try {
                card.get().addIrrigation(data);
            } catch (LimitExceededException e) {
                return new RestFailure(e.getMessage());
            }

            card.get().setLastUpdated();
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("card ID not found.");
    }

    @PostMapping("/ranches/{id}/chemical")
    @ApiOperation(value = "Add chemical data to a card.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO addChemicalData(@PathVariable("id") String id, @RequestBody Chemicals data) {
        Optional<Card> card = ranchRepository.findById(id);
        if (card.isPresent()) {
            try {
                card.get().addPostChemicals(data);
            } catch (LimitExceededException e) {
                return new RestFailure(e.getMessage());
            }

            card.get().setLastUpdated();
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("card ID not found.");
    }

    @PutMapping("/ranches/{id}/close")
    @ApiOperation(value = "Close card when completed.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO closeCard(@PathVariable("id") String id, @RequestBody Card ranch) {
        Optional<RestDTO> data = ranchRepository.findById(id)
              .map(card -> {
                  card.setHarvestDate(ranch.getHarvestDate());
                  card.setClosed(true);
                  card.setLastUpdated();
                  ranchRepository.save(card);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("card ID not found."));
    }
}

