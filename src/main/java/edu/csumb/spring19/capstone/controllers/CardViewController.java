package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/view")
public class CardViewController {

    @Autowired
    private RanchRepository ranchRepository;

    @GetMapping("/ranches")
    public RestDTO getAllRanchData(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards) {
        Sort sortByRanchName = new Sort(Sort.Direction.DESC, "lastUpdated");
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
        return data.orElse(new RestFailure("Card ID not found."));
    }

    @GetMapping("/ranches/ranchName/{ranchName}")
    public RestDTO getRanchDataByRanchName(@PathVariable("ranchName") String ranchName) {
        return new RestData<>(ranchRepository.findByRanchName(ranchName));
    }

    @GetMapping("/ranches/ranchManager/{ranchManagerName}")
    public RestDTO getRanchDataByRanchManagerName(@PathVariable("ranchManagerName") String ranchManagerName) {
        return new RestData<>(ranchRepository.findByRanchManagerName(ranchManagerName));
    }
}

