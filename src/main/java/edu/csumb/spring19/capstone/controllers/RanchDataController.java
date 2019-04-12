package edu.csumb.spring19.capstone.controllers;

import javax.validation.Valid;
import edu.csumb.spring19.capstone.models.RanchData;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
public class RanchDataController{

    @Autowired
    private RanchRepository ranchRepository;

    @GetMapping("/view/ranches")
    public List<RanchData> getAllRanchData() {
        Sort sortByRanchName = new Sort(Sort.Direction.ASC, "ranchName");
        return ranchRepository.findAll(sortByRanchName);
    }

    @GetMapping("/view/ranches/{ranchName}")
    public List<RanchData> getRanchDataByRanchName(@PathVariable("ranchName") String ranchName) {
        return ranchRepository.findByRanchName(ranchName);
    }

    @GetMapping("/view/ranches/{ranchManagerName}")
    public List<RanchData> getRanchDataByRanchManagerName(@PathVariable("ranchManagerName") String ranchManagerName) {
        return ranchRepository.findByRanchManagerName(ranchManagerName);
    }
    
    @GetMapping("/view/ranches/{fieldID}")
    public List<RanchData> getRanchDataByFieldID(@PathVariable("fieldID") Integer fieldID){
        return ranchRepository.findByFieldID(fieldID);
    }
    
    @PostMapping("/entry/ranches")
    public RanchData createRanchData(@Valid @RequestBody RanchData ranchData) {
        return ranchRepository.save(ranchData);
    }

    @PutMapping("/edit/ranches/{id}")
    public ResponseEntity<RanchData> updateRanchData(@PathVariable("id") String id, @Valid @RequestBody RanchData ranch) {
        return ranchRepository.findById(id)
            .map(ranchData -> {
                ranchData.setIrrigationData(ranch.getIrrigationData());
                ranchData.setTractorData(ranch.getTractorData());
                ranchData.setInitialData(ranch.getInitialData());
                ranchData.setPreplantData(ranch.getPreplantData());
                ranchData.setPlantingData(ranch.getPlantingData());
                ranchData.setRanchName(ranch.getRanchName());
                ranchData.setRanchManagerName(ranch.getRanchManagerName());
                ranchData.setLotNumber(ranch.getLotNumber());
                ranchData.setLotNumber(ranch.getLotNumber());
                ranchData.setCropYear(ranch.getCropYear());
                ranchData.setWetDate(ranch.getWetDate());
                ranchData.setThinDate(ranch.getWetDate());
                ranchData.setHoeDate(ranch.getHoeDate());
                ranchData.setHarvestDate(ranch.getHarvestDate());
                RanchData updatedRanchData = ranchRepository.save(ranchData);
                return ResponseEntity.ok().body(updatedRanchData);
            }).orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/admin/ranches/{id}")
    public ResponseEntity<RanchData> updateRanchDataAdmin(@PathVariable("id") String id, @Valid @RequestBody RanchData ranch) {
        return ranchRepository.findById(id)
            .map(ranchData -> {
                ranchData.setIrrigationData(ranch.getIrrigationData());
                ranchData.setTractorData(ranch.getTractorData());
                ranchData.setInitialData(ranch.getInitialData());
                ranchData.setPreplantData(ranch.getPreplantData());
                ranchData.setPlantingData(ranch.getPlantingData());
                ranchData.setRanchName(ranch.getRanchName());
                ranchData.setFieldID(ranch.getFieldID());
                ranchData.setRanchManagerName(ranch.getRanchManagerName());
                ranchData.setLotNumber(ranch.getLotNumber());
                ranchData.setShipperID(ranch.getShipperID());
                ranchData.setWetDate(ranch.getWetDate());
                ranchData.setThinDate(ranch.getWetDate());
                ranchData.setHoeDate(ranch.getHoeDate());
                ranchData.setHarvestDate(ranch.getHarvestDate());
                ranchData.setCropYear(ranch.getCropYear());
                RanchData updatedRanchData = ranchRepository.save(ranchData);
                return ResponseEntity.ok().body(updatedRanchData);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/edit/ranches/{id}")
    public ResponseEntity<?> deleteRanchData(@PathVariable("id") String id){
        return ranchRepository.findById(id)
            .map(ranch -> {
                ranchRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }


    
    
}

