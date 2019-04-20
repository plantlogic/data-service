package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.card.Card;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.LimitExceededException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/edit")
public class CardEditController {

    @Autowired
    private RanchRepository ranchRepository;

    @PutMapping("ranches/{id}")
    public RestDTO updateRanchData(@PathVariable("id") String id, @Valid @RequestBody Card ranch) {
        Optional<RestDTO> data = ranchRepository.findById(id)
              .map(card -> {
                  card.setLastUpdated();
                  card.setRanchName(ranch.getRanchName());
                  card.setFieldID(ranch.getFieldID());
                  card.setRanchManagerName(ranch.getRanchManagerName());

                  try {
                      card.setIrrigation(ranch.getIrrigation());
                      card.setTractor(ranch.getTractor());
                      card.setCommodities(ranch.getCommodities());
                      card.setPreChemicals(ranch.getPreChemicals());
                      card.setPostChemicals(ranch.getPostChemicals());
                  } catch (LimitExceededException e) {
                      return new RestFailure(e.getMessage());
                  }

                  card.setLotNumber(ranch.getLotNumber());
                  card.setShipperID(ranch.getShipperID());
                  card.setCropYear(ranch.getCropYear());
                  card.setWetDate(ranch.getWetDate());
                  card.setThinDate(ranch.getThinDate());
                  card.setHoeDate(ranch.getHoeDate());
                  card.setHarvestDate(ranch.getHarvestDate());
                  ranchRepository.save(card);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("card ID not found."));
    }

    @PutMapping("ranches/{id}/state")
    public RestDTO setRanchDataState(@PathVariable("id") String id, @RequestParam Boolean closed) {
        Optional<RestDTO> data = ranchRepository.findById(id)
              .map(card -> {
                  card.setLastUpdated();
                  card.setClosed(closed);
                  ranchRepository.save(card);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("card ID not found."));
    }

    @DeleteMapping("ranches/{id}")
    public RestDTO deleteRanchData(@PathVariable("id") String id){
        Optional<RestDTO> data = ranchRepository.findById(id)
              .map(ranch -> {
                  ranchRepository.deleteById(id);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("card ID not found."));
    }
}

