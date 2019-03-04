package edu.csumb.spring19.capstone.controllers;

import javax.validation.Valid;
import edu.csumb.spring19.capstone.models.TractorData;
import edu.csumb.spring19.capstone.repos.TractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TractorDataController{

    @Autowired
    private TractorRepository tractorRepository;

    @GetMapping("/tractor")
    public List<TractorData> getAllTractorData() {
        Sort sortByWorkDate = new Sort(Sort.Direction.DESC, "workDate");
        return tractorRepository.findAll(sortByWorkDate);
    }

    @PostMapping("/tractor")
    public TractorData createTractorData(@Valid @RequestBody TractorData tractorData) {
        return tractorRepository.save(tractorData);
    }

    @GetMapping("/tractor/{id}")
    public ResponseEntity<TractorData> getTractorDataByID(@PathVariable("id") String id) {
        return tractorRepository.findById(id)
            .map(tractorData -> ResponseEntity.ok().body(tractorData))
            .orElse(ResponseEntity.notFound().build());
    }
   
    @PutMapping("/tractor/{id}")
    public ResponseEntity<TractorData> updateTractorData(@PathVariable("id") String id, @Valid @RequestBody TractorData tractor) {
        return tractorRepository.findById(id)
            .map(tractorData -> {
                tractorData.setWorkDate(tractor.getWorkDate());
                tractorData.setWorkDone(tractor.getWorkDone());
                tractorData.setFertilizer(tractor.getFertilizer());
                tractorData.setGallons(tractor.getGallons());
                TractorData updatedTractorData = tractorRepository.save(tractorData);
                return ResponseEntity.ok().body(updatedTractorData);
            }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/tractors/{id}")
    public ResponseEntity<?> deleteTractorData(@PathVariable("id") String id){
        return tractorRepository.findById(id)
            .map(tractor -> {
                tractorRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }


    
    
}

