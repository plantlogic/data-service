package edu.csumb.spring19.capstone.controllers;

import javax.validation.Valid;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.RanchData;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class RanchDataController{

    @Autowired
    private RanchRepository ranchRepository;

    @GetMapping("/view/ranches")
    public RestDTO getAllRanchData() {
        Sort sortByRanchName = new Sort(Sort.Direction.ASC, "ranchName");
        return new RestData<>(ranchRepository.findAll(sortByRanchName));
    }

    @GetMapping("/view/ranches/ranchName/{ranchName}")
    public RestDTO getRanchDataByRanchName(@PathVariable("ranchName") String ranchName) {
        return new RestData<>(ranchRepository.findByRanchName(ranchName));
    }

    @GetMapping("/view/ranches/ranchManager/{ranchManagerName}")
    public RestDTO getRanchDataByRanchManagerName(@PathVariable("ranchManagerName") String ranchManagerName) {
        return new RestData<>(ranchRepository.findByRanchManagerName(ranchManagerName));
    }
    
    @PostMapping("/entry/ranches")
    public RestDTO createRanchData(@Valid @RequestBody RanchData ranchData) {
        ranchRepository.save(ranchData);
        return new RestSuccess();
    }

    // TODO: Entry methods for adding tractor/irrigation data to an existing ranch

    @PutMapping("/edit/ranches/{id}")
    public RestDTO updateRanchData(@PathVariable("id") String id, @Valid @RequestBody RanchData ranch) {
        Optional<RestDTO> data = ranchRepository.findById(id)
              .map(ranchData -> {
                  ranchData.setRanchName(ranch.getRanchName());
                  ranchData.setRanchManagerName(ranch.getRanchManagerName());
                  ranchData.setIrrigationData(ranch.getIrrigationData());
                  ranchData.setTractorData(ranch.getTractorData());
                  ranchData.setLotNumber(ranch.getLotNumber());
                  ranchData.setTotalAcres(ranch.getTotalAcres());
                  ranchData.setCropYear(ranch.getCropYear());
                  ranchData.setCommodity(ranch.getCommodity());
                  ranchData.setVariety(ranch.getVariety());
                  ranchData.setCropAcres(ranch.getCropAcres());
                  ranchData.setBedCount(ranch.getBedCount());
                  ranchData.setSeedLotNumber(ranch.getSeedLotNumber());
                  ranchData.setBedType(ranch.getBedType());
                  ranchData.setLorsbanRate(ranch.getLorsbanRate());
                  ranchData.setDiaznonRate(ranch.getDiaznonRate());
                  ranchData.setKerbRate(ranch.getKerbRate());
                  ranchData.setDacthalRate(ranch.getDacthalRate());
                  ranchData.setWetDate(ranch.getWetDate());
                  ranchData.setThinDate(ranch.getWetDate());
                  ranchData.setHoeDate(ranch.getHoeDate());
                  ranchData.setHarvestDate(ranch.getHarvestDate());
                  ranchRepository.save(ranchData);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("Ranch ID not found."));
    }

    @DeleteMapping("/edit/ranches/{id}")
    public RestDTO deleteRanchData(@PathVariable("id") String id){
        Optional<RestDTO> data = ranchRepository.findById(id)
              .map(ranch -> {
                  ranchRepository.deleteById(id);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("Ranch ID not found."));
    }
}

