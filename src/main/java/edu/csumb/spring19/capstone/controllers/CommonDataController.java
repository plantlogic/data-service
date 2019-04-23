package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.common.CommonData;
import edu.csumb.spring19.capstone.models.common.CommonInitService;
import edu.csumb.spring19.capstone.repos.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class CommonDataController{
    @Value("${ALLOW_COMMON_RESET:false}")
    private Boolean allowReset;

    @Autowired
    private CommonInitService commonInitService;
    @Autowired
    private CommonRepository commonRepository;

    @GetMapping("/common/{id}")
    public RestDTO getCommonData(@PathVariable("id") String id) {
        Optional<CommonData> data = commonRepository.findById(id);
        if (data.isPresent()) return new RestData<>(data);
        else return new RestFailure("Key not found.");
    }

    @GetMapping("/common")
    public RestDTO getAllCommonData() {
        return new RestData<>(commonRepository.findAll());
    }

    @PutMapping({"/admin/common"})
    public RestDTO updateCommonData(@RequestBody CommonData common) {
        Optional<RestDTO> data = commonRepository.findById(common.getKey())
              .map(commonData -> {
                  commonRepository.save(common);
                  return new RestSuccess();
              });
        return data.orElse(new RestFailure("Key not found."));
    }

    @DeleteMapping({"/admin/reset"})
    public RestDTO resetCommonData() {
        if (allowReset) {
            commonRepository.deleteAll();
            commonInitService.initDatabase();
            return new RestSuccess();
        } else return new RestFailure("This functionality is disabled.");
    }
}

