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
@RequestMapping("/edit")
public class CardEditController {

    @Autowired
    private RanchRepository ranchRepository;

    @PutMapping("ranches/{id}")
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
        return data.orElse(new RestFailure("Card ID not found."));
    }

    @DeleteMapping("ranches/{id}")
    public RestDTO deleteRanchData(@PathVariable("id") String id){
        Optional<RestDTO> data = ranchRepository.findById(id)
              .map(ranch -> {
                  ranchRepository.deleteById(id);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("Card ID not found."));
    }
}

