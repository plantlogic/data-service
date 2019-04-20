package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.common.CommonData;
import edu.csumb.spring19.capstone.repos.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class CommonDataController{

    @Autowired
    private CommonRepository commonRepository;

    @GetMapping("/admin/common")
    public RestDTO getAllCommonData() {
        return new RestData<>(commonRepository.findAll());
    }

    @PostMapping("/admin/common")
    public RestDTO createCommonData(@Valid @RequestBody CommonData commonData) {
        commonRepository.save(commonData);
        return new RestSuccess();
    }

    @PutMapping({"/admin/common/{id}"})
    public RestDTO updateCommonData(@PathVariable("id") String id, @Valid @RequestBody CommonData common) {
        Optional<RestDTO> data = commonRepository.findById(id)
              .map(commonData -> {
                  commonData.setRanches(common.getRanches());
                  commonData.setRanchManagers(common.getRanchManagers());
                  commonData.setPostPlantFertilizers(common.getPostPlantFertilizers());
                  commonData.setPrePlantFertilizers(common.getPrePlantFertilizers());
                  commonData.setOrganicFertilizers(common.getOrganicFertilizers());
                  commonData.setInsecticides(common.getInsecticides());
                  commonData.setHerbicides(common.getHerbicides());
                  commonRepository.save(commonData);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("ID not found."));
    }

    @DeleteMapping("/admin/common/{id}")
    public RestDTO deleteCommonData(@PathVariable("id") String id){
        Optional<RestDTO> data = commonRepository.findById(id)
              .map(common -> {
                  commonRepository.deleteById(id);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("ID not found."));
    }
}

