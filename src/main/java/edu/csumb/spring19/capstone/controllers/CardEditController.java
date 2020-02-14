package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.authentication.PLRole;
import edu.csumb.spring19.capstone.models.card.Card;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import edu.csumb.spring19.capstone.security.RanchAccess;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.LimitExceededException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/edit")
@PreAuthorize("hasRole('DATA_EDIT')")
public class CardEditController {
    @Autowired
    private RanchRepository ranchRepository;

    @Autowired
    private RanchAccess ranchAccess;

    @PutMapping("/ranches/{id}")
    @ApiOperation(value = "Overwrite a card by it's ID.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO updateRanchData(@PathVariable("id") String id, @Valid @RequestBody Card ranch) {
        Optional<Card> card = ranchRepository.findById(id);
        if (ranchAccess.cardExistsAndViewAllowed(card)) {
            card.get().setLastUpdated();
            card.get().setRanchName(ranch.getRanchName());
            card.get().setFieldID(ranch.getFieldID());
            card.get().setRanchManagerName(ranch.getRanchManagerName());

            try {
                card.get().setIrrigation(ranch.getIrrigation());
                card.get().setTractor(ranch.getTractor());
                card.get().setCommodities(ranch.getCommodities());
                card.get().setPreChemicals(ranch.getPreChemicals());
                card.get().setPostChemicals(ranch.getPostChemicals());
            } catch (LimitExceededException e) {
                return new RestFailure(e.getMessage());
            }

            card.get().setLotNumber(ranch.getLotNumber());
            card.get().setShipperID(ranch.getShipperID());
            card.get().setPlanterNumber(ranch.getPlanterNumber());
            card.get().setCropYear(ranch.getCropYear());
            card.get().setWetDate(ranch.getWetDate());
            card.get().setThinDate(ranch.getThinDate());
            card.get().setHoeDate(ranch.getHoeDate());
            card.get().setHarvestDate(ranch.getHarvestDate());
            card.get().setComment(ranch.getComment());
            card.get().setThinType(ranch.getThinType());
            card.get().setHoeType(ranch.getHoeType());
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
    }

    @PutMapping("/ranches/{id}/state")
    @ApiOperation(value = "Toggle the state of a card between closed/open.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO setRanchDataState(@PathVariable("id") String id, @RequestParam Boolean closed) {
        if (ranchAccess.hasRole(PLRole.DATA_VIEW)) {
            Optional<RestDTO> data = ranchRepository.findById(id)
                  .map(card -> {
                      card.setLastUpdated();
                      card.setClosed(closed);
                      ranchRepository.save(card);
                      return new RestSuccess();
                  });
            return data.orElse(new RestFailure("Card ID not found."));
        } else return new RestFailure("Card ID not found, or you don't have permission to perform this action.");
    }

    @DeleteMapping("/ranches/{id}")
    @ApiOperation(value = "Delete a card.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO deleteRanchData(@PathVariable("id") String id){
        if (ranchAccess.hasRole(PLRole.DATA_VIEW)) {
            Optional<RestDTO> data = ranchRepository.findById(id)
                  .map(ranch -> {
                      ranchRepository.deleteById(id);
                      return new RestSuccess();
                  });
            return data.orElse(new RestFailure("Card ID not found."));
        } else return new RestFailure("Card ID not found, or you don't have permission to perform this action.");
    }
}

