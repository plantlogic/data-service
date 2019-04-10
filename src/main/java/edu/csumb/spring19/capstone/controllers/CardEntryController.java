package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.IrrigationData;
import edu.csumb.spring19.capstone.models.RanchData;
import edu.csumb.spring19.capstone.models.TractorData;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/entry")
public class CardEntryController {

    @Autowired
    private RanchRepository ranchRepository;
    
    @PostMapping("/ranches")
    public RestDTO createRanchData(@Valid @RequestBody RanchData ranchData) {
        ranchRepository.save(ranchData);
        return new RestSuccess();
    }

    @GetMapping("/ranches")
    public RestDTO getAllRanchData() {
        Sort sortByRanchName = new Sort(Sort.Direction.ASC, "ranchName");
        return new RestData<>(ranchRepository.findAllByIsClosed(false, sortByRanchName));
    }

    @GetMapping("/ranches/{id}")
    public RestDTO getRanchData(@PathVariable("id") String id) {
        Optional<RestDTO> data = ranchRepository.findById(id).map(RestData::new);
        return data.orElse(new RestFailure("Card ID not found."));
    }

    @PostMapping("/ranches/{id}/tractor")
    public RestDTO addTractorData(@PathVariable("id") String id, @RequestBody TractorData data) {
        Optional<RanchData> card = ranchRepository.findById(id);
        if (card.isPresent()) {
            card.get().addTractorData(data);
            card.get().setLastUpdated();
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("Card ID not found.");
    }

    @PostMapping("/ranches/{id}/irrigation")
    public RestDTO addTractorData(@PathVariable("id") String id, @RequestBody IrrigationData data) {
        Optional<RanchData> card = ranchRepository.findById(id);
        if (card.isPresent()) {
            card.get().addIrrigationData(data);
            card.get().setLastUpdated();
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("Card ID not found.");
    }

    @PutMapping("/ranches/{id}/close")
    public RestDTO closeCard(@PathVariable("id") String id, @RequestBody RanchData ranch) {
        Optional<RestDTO> data = ranchRepository.findById(id)
              .map(ranchData -> {
                  ranchData.setHarvestDate(ranch.getHarvestDate());
                  ranchData.setIsClosed(true);
                  ranchData.setLastUpdated();
                  ranchRepository.save(ranchData);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("Card ID not found."));
    }
}

