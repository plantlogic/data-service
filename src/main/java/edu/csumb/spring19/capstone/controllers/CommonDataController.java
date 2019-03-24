package edu.csumb.spring19.capstone.controllers;

import javax.validation.Valid;
import edu.csumb.spring19.capstone.models.CommonData;
import edu.csumb.spring19.capstone.repos.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CommonDataController{

    @Autowired
    private CommonRepository commonRepository;

    @GetMapping("/view/common")
    public List<CommonData> getAllCommonData() {
        return commonRepository.findAll();
    }

    @PostMapping("/admin/common")
    public CommonData createCommonData(@Valid @RequestBody CommonData commonData) {
        return commonRepository.save(commonData);
    }

    @PutMapping({"/entry/common/{id}"})
    public ResponseEntity<CommonData> updateCommonData(@PathVariable("id") String id, @Valid @RequestBody CommonData common) {
        return commonRepository.findById(id)
            .map(commonData -> {
                commonData.setRanches(common.getRanches());
                commonData.setRanchManagers(common.getRanchManagers());
                commonData.setPostPlantFertilizers(common.getPostPlantFertilizers());
                commonData.setPrePlantFertilizers(common.getPrePlantFertilizers());
                commonData.setOrganicFertilizers(common.getOrganicFertilizers());
                commonData.setInsecticides(common.getInsecticides());
                commonData.setHerbicides(common.getHerbicides());
                CommonData updatedCommonData = commonRepository.save(commonData);
                return ResponseEntity.ok().body(updatedCommonData);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/admin/common")
    public ResponseEntity<?> deleteCommonData(@PathVariable("id") String id){
        return commonRepository.findById(id)
            .map(common -> {
                commonRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }


    
    
}

