package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.authentication.PLRole;
import edu.csumb.spring19.capstone.models.export.ExportPreset;
import edu.csumb.spring19.capstone.repos.ExportPresetRepository;
import edu.csumb.spring19.capstone.security.RanchAccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/exportPresets")
@PreAuthorize("hasAnyRole('APP_ADMIN', 'DATA_VIEW')")
public class ExportPresetController {

    @Autowired
    private ExportPresetRepository exportPresetRepository;

    @Autowired
    private RanchAccess ranchAccess;

    @PostMapping("/add")
    @Operation(summary = "Create a new export preset.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO createExportPreset(@Valid @RequestBody ExportPreset preset) {
        if (ranchAccess.hasRole(PLRole.APP_ADMIN)) {
            if (Optional.of(preset).isPresent()) {
                preset.setDateCreated(new Date());
                exportPresetRepository.save(preset);
                return new RestSuccess();
            } else {
                return new RestFailure("There was an error saving the preset");
            }
        } else {
            return new RestFailure("You don't have permission to add export presets. App Admin permission required");
        }
    }

    @GetMapping("/view/{id}")
    @Operation(summary = "Get an export preset by its id from the database.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getExportPreset(@PathVariable("id") String id) {
        Optional<ExportPreset> preset = exportPresetRepository.findById(id);
        if (preset.isPresent()) return new RestData<>(preset);
        else return new RestFailure("No preset found with given id");
    }

    @GetMapping("/view")
    @Operation(summary = "Get all export presets from the database.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO getAllExportPresets() {
        return new RestData<>(exportPresetRepository.findAll());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Overwrite an export preset by its id in the database.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO updateExportPreset(@PathVariable("id") String id, @Valid @RequestBody ExportPreset updatedPreset) {
        Optional<ExportPreset> preset = exportPresetRepository.findById(id);
        if (ranchAccess.hasRole(PLRole.APP_ADMIN)) {
            if (preset.isPresent()) {
                // Update values
                preset.get().setCard(updatedPreset.getCard());
                preset.get().setCommodities(updatedPreset.getCommodities());
                preset.get().setDynamic(updatedPreset.getDynamic());
                preset.get().setIrrigationEntry(updatedPreset.getIrrigationEntry());
                preset.get().setIrrigationEntryChemicals(updatedPreset.getIrrigationEntryChemicals());
                preset.get().setIrrigationEntryFertilizers(updatedPreset.getIrrigationEntryFertilizers());
                preset.get().setName(updatedPreset.getName());
                preset.get().setPreChemicals(updatedPreset.getPreChemicals());
                preset.get().setPreChemicalsChemical(updatedPreset.getPreChemicalsChemical());
                preset.get().setPreChemicalsFertilizer(updatedPreset.getPreChemicalsFertilizer());
                preset.get().setTractorEntry(updatedPreset.getTractorEntry());
                preset.get().setTractorEntryFertilizers(updatedPreset.getTractorEntryFertilizers());
                preset.get().setTractorEntryChemicals(updatedPreset.getTractorEntryChemicals());
                preset.get().setHoeCrews(updatedPreset.getHoeCrews());
                preset.get().setThinCrews(updatedPreset.getThinCrews());
                exportPresetRepository.save(preset.get());
                return new RestSuccess();
            } else {
                return new RestFailure("No preset found with given id");
            }
        } else {
            return new RestFailure("You don't have permission to add export presets. App Admin permission required");
        }

    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a preset by its database id.", security = {@SecurityRequirement(name = "Bearer")})
    public RestDTO deleteExportPreset(@PathVariable("id") String id){
        if (ranchAccess.hasRole(PLRole.APP_ADMIN)) {
            Optional<RestDTO> data = exportPresetRepository.findById(id)
                  .map(ranch -> {
                      exportPresetRepository.deleteById(id);
                      return new RestSuccess();
                  });
            return data.orElse(new RestFailure("Preset ID not found."));
        } else return new RestFailure("Preset ID not found, or you don't have permission to perform this action.");
    }
}
