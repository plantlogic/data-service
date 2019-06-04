package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.repos.RanchRepository;
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
@PreAuthorize("hasRole('DATA_VIEW')")
public class CardViewController {
    @Autowired
    private RanchRepository ranchRepository;

    @GetMapping("/ranches")
    @ApiOperation(value = "Get all cards from the databse.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getAllRanchData(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards) {
        Sort sortByRanchName = Sort.by(Sort.Order.asc("fieldID"), Sort.Order.desc("lastUpdated"));
        if (openCards && closedCards) {
            return new RestData<>(ranchRepository.findAll(sortByRanchName));
        } else if (openCards) {
            return new RestData<>(ranchRepository.findAllByIsClosedFalse(sortByRanchName));
        } else if (closedCards) {
            return new RestData<>(ranchRepository.findAllByIsClosedTrue(sortByRanchName));
        } else {
            return new RestFailure("You requested no cards.");
        }
    }

    @GetMapping("/ranches/{id}")
    public RestDTO getRanchData(@PathVariable("id") String id) {
        Optional<RestDTO> data = ranchRepository.findById(id).map(RestData::new);
        return data.orElse(new RestFailure("card ID not found."));
    }

    @GetMapping("/ranches/ranchName/{ranchName}")
    public RestDTO getRanchDataByRanchName(@PathVariable("ranchName") String ranchName) {
        return new RestData<>(ranchRepository.findByRanchName(ranchName));
    }

    @GetMapping("/ranches/ranchManager/{ranchManagerName}")
    public RestDTO getRanchDataByRanchManagerName(@PathVariable("ranchManagerName") String ranchManagerName) {
        return new RestData<>(ranchRepository.findByRanchManagerName(ranchManagerName));
    }

    @GetMapping("ranches/fieldID/{fieldID}")
    public RestDTO getRanchDataByFieldID(@PathVariable("fieldID") Integer fieldID){
        return new RestData<>(ranchRepository.findByFieldID(fieldID));
    }
}

