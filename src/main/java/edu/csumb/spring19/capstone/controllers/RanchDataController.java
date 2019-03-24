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
    //May need to add one more function to getRanchDataByRanchManager, better allowed for Data_Viewer 

    @PostMapping("/entry/ranches")
    public RanchData createRanchData(@Valid @RequestBody RanchData ranchData) {
        return ranchRepository.save(ranchData);
    }

    @PutMapping("/edit/ranches/{id}")
    public ResponseEntity<RanchData> updateRanchData(@PathVariable("id") String id, @Valid @RequestBody RanchData ranch) {
        return ranchRepository.findById(id)
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

