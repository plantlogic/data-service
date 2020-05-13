package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.authentication.PLRole;
import edu.csumb.spring19.capstone.models.card.Card;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import edu.csumb.spring19.capstone.security.RanchAccess;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.naming.LimitExceededException;
import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/view")
@PreAuthorize("hasAnyRole('DATA_VIEW', 'CONTRACTOR_VIEW', 'SHIPPER')")
public class CardViewController {
    @Autowired
    private RanchRepository ranchRepository;

    @Autowired
    private RanchAccess ranchAccess;

    @GetMapping("/ranches")
    @ApiOperation(value = "Get all cards from the database.", authorizations = {@Authorization(value = "Bearer")})
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

    // DELETE
    @PutMapping("/ranches/{id}")
    @ApiOperation(value = "Overwrite a card by it's ID.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO updateRanchData(@PathVariable("id") String id, @Valid @RequestBody Card ranch) {
        Optional<Card> card = ranchRepository.findById(id);
        card.get().setLastUpdated();
        card.get().setRanchName(ranch.getRanchName());
        card.get().setFieldID(ranch.getFieldID());
        card.get().setRanchManagerName(ranch.getRanchManagerName());

        try {
            card.get().setIrrigation(ranch.getIrrigation());
            card.get().setTractor(ranch.getTractor());
            card.get().setCommodities(ranch.getCommodities());
            card.get().setPreChemicals(ranch.getPreChemicals());
            card.get().setPostChemicals(ranch.getPostChemicals());
        } catch (LimitExceededException e) {
            return new RestFailure(e.getMessage());
        }

        card.get().setLotNumber(ranch.getLotNumber());
        card.get().setShippers(ranch.getShippers());
        card.get().setPlanterNumber(ranch.getPlanterNumber());
        card.get().setCropYear(ranch.getCropYear());
        card.get().setWetDate(ranch.getWetDate());
        card.get().setThinDate(ranch.getThinDate());
        card.get().setHoeDate(ranch.getHoeDate());
        card.get().setHarvestDate(ranch.getHarvestDate());
        card.get().setComments(ranch.getComments());
        card.get().setThinType(ranch.getThinType());
        card.get().setHoeType(ranch.getHoeType());
        ranchRepository.save(card.get());
        return new RestSuccess();
    }

    @GetMapping("/shipperRanches/{shipperId}")
    @ApiOperation(value = "Get all cards from the database which include the shipperID.", authorizations = {@Authorization(value = "Bearer")})
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

    @GetMapping("/ranches/{id}")
    @ApiOperation(value = "Get card from the database by its ID.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getRanchData(@PathVariable("id") String id) {
        Optional<Card> card = ranchRepository.findById(id);
        if (!card.isPresent()) {
            return new RestFailure("Card not found with id " + id );
        } else {
            PLRole[] roles = {PLRole.DATA_VIEW, PLRole.CONTRACTOR_VIEW, PLRole.SHIPPER};
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
}

