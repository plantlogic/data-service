package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
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

@RestController
@CrossOrigin("*")
@RequestMapping("/view")
@PreAuthorize("hasAnyRole('DATA_VIEW', 'DATA_ENTRY')")
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

        if (ranchAccess.hasRole(PLRole.DATA_VIEW)) {
            if (openCards && closedCards) {
                result = new RestData<>(ranchRepository.findAll(sortByRanchName));
            } else if (openCards) {
                result = new RestData<>(ranchRepository.findAllByIsClosedFalse(sortByRanchName));
            } else if (closedCards) {
                result = new RestData<>(ranchRepository.findAllByIsClosedTrue(sortByRanchName));
            } else {
                result = new RestFailure("You requested no cards.");
            }
        } else if (ranchAccess.hasRole(PLRole.DATA_ENTRY)) {
            result = new RestData<>(ranchRepository.findAllByIsClosedFalseAndRanchNameIsIn(ranchAccess.getRanchList(), sortByRanchName));
        }

        return result;
    }

    @GetMapping("/ranches/{id}")
    @ApiOperation(value = "Get card from the database by its ID.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getRanchData(@PathVariable("id") String id) {
        Optional<Card> card = ranchRepository.findById(id);

        if (ranchAccess.cardExistsAndViewAllowed(card)) return new RestData<>(card.get());
        else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
    }
}

