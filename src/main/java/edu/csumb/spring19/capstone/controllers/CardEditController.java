package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.RanchData;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                  ranchData.setLastUpdated();
                  ranchData.setRanchName(ranch.getRanchName());
                  ranchData.setFieldID(ranch.getFieldID());
                  ranchData.setRanchManagerName(ranch.getRanchManagerName());
                  ranchData.setIrrigationData(ranch.getIrrigationData());
                  ranchData.setTractorData(ranch.getTractorData());
                  ranchData.setInitialData(ranch.getInitialData());
                  ranchData.setPreplantData(ranch.getPreplantData());
                  ranchData.setPlantingData(ranch.getPlantingData());
                  ranchData.setLotNumber(ranch.getLotNumber());
                  ranchData.setShipperID(ranch.getShipperID());
                  ranchData.setCropYear(ranch.getCropYear());
                  ranchData.setWetDate(ranch.getWetDate());
                  ranchData.setThinDate(ranch.getThinDate());
                  ranchData.setHoeDate(ranch.getHoeDate());
                  ranchData.setHarvestDate(ranch.getHarvestDate());
                  ranchRepository.save(ranchData);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("Card ID not found."));
    }

    @PutMapping("ranches/{id}/state")
    public RestDTO setRanchDataState(@PathVariable("id") String id, @RequestParam Boolean closed) {
        Optional<RestDTO> data = ranchRepository.findById(id)
              .map(ranchData -> {
                  ranchData.setLastUpdated();
                  ranchData.setIsClosed(closed);
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

