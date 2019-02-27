package edu.csumb.spring19.capstone.controllers;

import javax.validation.Valid;
import edu.csumb.spring19.capstone.ranch.RanchData;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RanchDataController{

    @Autowired
    RanchRepository ranchRepository;

    @GetMapping("/ranches")
    public List<RanchData> getAllRanchData() {
        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
        return ranchRepository.findAll(sortByCreatedAtDesc);
    }

    @PostMapping("/ranches")
    public RanchData createRanchData(@Valid @RequestBody RanchData ranchData) {
        return ranchRepository.save(ranchData);
    }
    @GetMapping("/ranches/{plotID}")
    public ResponseEntity<RanchData> getRanchDataByID(@PathVariable("plotID") String plotID) {
        return ranchRepository.findById(plotID)
            .map(ranchData -> ResponseEntity.ok().body(ranchData))
            .orElse(ResponseEntity.notFound().build());
    }
   
    @PutMapping("/ranches/{id}")
    public ResponseEntity<RanchData> updateRanchData(@PathVariable("plotID") String plotID, @Valid @RequestBody RanchData ranch) {
        return ranchRepository.findById(plotID)
            .map(ranchData -> {
                ranchData.setAcres(ranch.getAcres());
                ranchData.setLotNumber(ranch.getLotNumber());
                ranchData.setRanchName(ranch.getRanchName());
                ranchData.setCommodities(ranch.getCommodities());
                ranchData.setVarieties(ranch.getVarieties());
                RanchData updatedRanchData = ranchRepository.save(ranchData);
                return ResponseEntity.ok().body(updatedRanchData);
            }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteRanchData(@PathVariable("plotID") String plotID){
        return ranchRepository.findById(plotID)
            .map(ranch -> {
                ranchRepository.deleteById(plotID);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }


    
    
}

