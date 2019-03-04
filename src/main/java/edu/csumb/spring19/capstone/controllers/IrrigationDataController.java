package edu.csumb.spring19.capstone.controllers;

import javax.validation.Valid;
import edu.csumb.spring19.capstone.models.IrrigationData;
import edu.csumb.spring19.capstone.repos.IrrigationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class IrrigationDataController{

    @Autowired
    IrrigationRepository irrigationRepository;

    @GetMapping("/irrigation")
    public List<IrrigationData> getAllIrrigationData() {
        Sort sortByWorkDate = new Sort(Sort.Direction.DESC, "workDate");
        return irrigationRepository.findAll(sortByWorkDate);
    }

    @PostMapping("/irrigation")
    public IrrigationData createIrrigationData(@Valid @RequestBody IrrigationData irrigationData) {
        return irrigationRepository.save(irrigationData);
    }

    @GetMapping("/irrigation/{id}")
    public ResponseEntity<IrrigationData> getIrrigationDataByID(@PathVariable("id") String id) {
        return irrigationRepository.findById(id)
            .map(irrigationData -> ResponseEntity.ok().body(irrigationData))
            .orElse(ResponseEntity.notFound().build());
    }
   
    @PutMapping("/irrigation/{id}")
    public ResponseEntity<IrrigationData> updateIrrigationData(@PathVariable("id") String id, @Valid @RequestBody IrrigationData irrigation) {
        return irrigationRepository.findById(id)
            .map(irrigationData -> {
                irrigationData.setWorkDate(irrigation.getWorkDate());
                irrigationData.setMethod(irrigation.getMethod());
                irrigationData.setFertilizer(irrigation.getFertilizer());
                irrigationData.setGallons(irrigation.getGallons());
                IrrigationData updatedIrrigationData = irrigationRepository.save(irrigationData);
                return ResponseEntity.ok().body(updatedIrrigationData);
            }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/irrigation/{id}")
    public ResponseEntity<?> deleteIrrigationData(@PathVariable("id") String id){
        return irrigationRepository.findById(id)
            .map(irrigation -> {
                irrigationRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }


    
    
}

