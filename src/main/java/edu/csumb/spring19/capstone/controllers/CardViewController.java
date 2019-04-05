package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.RanchData;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/view")
public class CardViewController {

    @Autowired
    private RanchRepository ranchRepository;

    @GetMapping("/ranches")
    public RestDTO getAllRanchData() {
        Sort sortByRanchName = new Sort(Sort.Direction.ASC, "ranchName");
        return new RestData<>(ranchRepository.findAll(sortByRanchName));
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

