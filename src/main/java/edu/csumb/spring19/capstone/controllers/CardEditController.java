package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.dto.RestSuccess;
import edu.csumb.spring19.capstone.models.authentication.PLRole;
import edu.csumb.spring19.capstone.models.card.Card;
import edu.csumb.spring19.capstone.models.card.Comment;
import edu.csumb.spring19.capstone.models.card.ThinHoeCrew;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import edu.csumb.spring19.capstone.security.RanchAccess;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.LimitExceededException;
import javax.validation.Valid;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/edit")
public class CardEditController {
    @Autowired
    private RanchRepository ranchRepository;

    @Autowired
    private RanchAccess ranchAccess;

    @PutMapping("/updateCardsToThinHoeCrews")
    @ApiOperation(value = "Modify all cards in database to have Thin & Hoe Crews sections, replacing old thin & hoe data", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO updateCardsToThinHoeCrews() {
        if (ranchAccess.hasRole(PLRole.DATA_EDIT) && ranchAccess.hasRole(PLRole.APP_ADMIN)) {
            List<Card> cardList = ranchRepository.findAll();
            Iterator<Card> i = cardList.iterator();
            Integer numErrors = 0;
            Card temp;
            String machineTypeCrew = "machinelegacy";
            String handTypeCrew = "handlegacy";
            while (i.hasNext()) {
                temp = i.next();
                if (temp.getThinDate() != null) {
                    ThinHoeCrew thinEntry = new ThinHoeCrew();
                    thinEntry.setDate(temp.getThinDate());
                    thinEntry.setHoursWorked(0f);
                    thinEntry.setNumEmployees(0);
                    if (temp.getThinTypeString( )== "Hand") {
                        thinEntry.setCrew(handTypeCrew);
                    } else {
                        thinEntry.setCrew(machineTypeCrew);
                    }
                    try {
                        temp.addThinCrew(thinEntry);
                    } catch (LimitExceededException e) {
                        numErrors++;
                    }
                }
                temp.setThinDate(null);
                temp.setThinType(null);
                if (temp.getHoeDate() != null) {
                    ThinHoeCrew hoeEntry = new ThinHoeCrew();
                    hoeEntry.setDate(temp.getHoeDate());
                    hoeEntry.setHoursWorked(0f);
                    hoeEntry.setNumEmployees(0);
                    if (temp.getHoeTypeString() == "Hand") {
                        hoeEntry.setCrew(handTypeCrew);
                    } else {
                        hoeEntry.setCrew(machineTypeCrew);
                    }
                    try {
                        temp.addHoeCrew(hoeEntry);
                    } catch (LimitExceededException e) {
                        numErrors++;
                    }
                }
                temp.setHoeDate(null);
                temp.setHoeType(null);
                ranchRepository.save(temp);
            }
            System.out.println("Cards were updated, " + numErrors + " error(s) occured");
            return new RestSuccess();
        } else return new RestFailure("Failed to update all cards");
    }

    @PutMapping("/ranches/{id}")
    @ApiOperation(value = "Overwrite a card by it's ID.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO updateRanchData(@PathVariable("id") String id, @Valid @RequestBody Card ranch) {
        Optional<Card> card = ranchRepository.findById(id);
        PLRole[] dataRoles = {PLRole.DATA_VIEW, PLRole.DATA_EDIT};
        PLRole[] contractorRoles = {PLRole.CONTRACTOR_VIEW, PLRole.CONTRACTOR_EDIT};
        boolean allowed = ranchAccess.cardExistsAndHasAllPermissions(dataRoles, true, card)
                       || ranchAccess.cardExistsAndHasAllPermissions(contractorRoles, true, card);
        if (allowed) {
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
                card.get().setThinCrews(ranch.getThinCrews());
                card.get().setHoeCrews(ranch.getHoeCrews());
            } catch (LimitExceededException e) {
                return new RestFailure(e.getMessage());
            }

            card.get().setLotNumber(ranch.getLotNumber());
            card.get().setShippers(ranch.getShippers());
            card.get().setPlanterNumber(ranch.getPlanterNumber());
            card.get().setCropYear(ranch.getCropYear());
            card.get().setWetDate(ranch.getWetDate());
            card.get().setThinDate(ranch.getThinDate());
            card.get().setHoeDate(ranch.getHoeDate());
            card.get().setHarvestDate(ranch.getHarvestDate());
            card.get().setComments(ranch.getComments());
            card.get().setThinType(ranch.getThinType());
            card.get().setHoeType(ranch.getHoeType());
            ranchRepository.save(card.get());
            return new RestSuccess();
        } else return new RestFailure("Card ID not found, or you don't have permission to access this card.");
    }

    @DeleteMapping("/ranches/{id}")
    @ApiOperation(value = "Delete a card.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO deleteRanchData(@PathVariable("id") String id){
        if (ranchAccess.hasRole(PLRole.DATA_EDIT) || ranchAccess.hasRole(PLRole.CONTRACTOR_EDIT)) {
            Optional<RestDTO> data = ranchRepository.findById(id)
                  .map(ranch -> {
                      ranchRepository.deleteById(id);
                      return new RestSuccess();
                  });
            return data.orElse(new RestFailure("Card ID not found."));
        } else return new RestFailure("Card ID not found, or you don't have permission to perform this action.");
    }

    @PutMapping("/ranches/{id}/setComments")
    @ApiOperation(value = "Adds a comment to a card", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO setComments(@PathVariable("id") String id, @Valid @RequestBody List<Comment> comments) {
        if (ranchAccess.hasRole(PLRole.DATA_VIEW) || ranchAccess.hasRole(PLRole.CONTRACTOR_VIEW)
        || ranchAccess.hasRole(PLRole.SHIPPER)) {
            Optional<RestDTO> data = ranchRepository.findById(id)
                  .map(card -> {
                      card.setLastUpdated();
                      card.setComments(comments);
                      ranchRepository.save(card);
                      return new RestSuccess();
                  });
            return data.orElse(new RestFailure("Card ID not found."));
        } else return new RestFailure("Card ID not found, or you don't have permission to perform this action.");
    }

    @PutMapping("/ranches/{id}/setThinning")
    @ApiOperation(value = "Set the card's thinning entries", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO setThinning(@PathVariable("id") String id, @Valid @RequestBody List<ThinHoeCrew> thinCrews) {
        if (ranchAccess.hasRole(PLRole.TH_EDIT)) {
            Optional<RestDTO> data = ranchRepository.findById(id)
                  .map(card -> {
                      card.setLastUpdated();
                        try {
                            card.setThinCrews(thinCrews);
                        } catch (LimitExceededException e) {
                            return new RestFailure("Max number of thinning entries has been exceeded.");
                        }
                      ranchRepository.save(card);
                      return new RestSuccess();
                  });
            return data.orElse(new RestFailure("Card ID not found."));
        } else return new RestFailure("Card ID not found, or you don't have permission to perform this action.");
    }

    @PutMapping("/ranches/{id}/setHoeing")
    @ApiOperation(value = "Set the card's hoeing entries", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO setHoeing(@PathVariable("id") String id, @Valid @RequestBody List<ThinHoeCrew> hoeCrews) {
        if (ranchAccess.hasRole(PLRole.TH_EDIT)) {
            Optional<RestDTO> data = ranchRepository.findById(id)
                  .map(card -> {
                      card.setLastUpdated();
                        try {
                            card.setHoeCrews(hoeCrews);
                        } catch (LimitExceededException e) {
                            return new RestFailure("Max number of hoeing entries has been exceeded.");
                        }
                      ranchRepository.save(card);
                      return new RestSuccess();
                  });
            return data.orElse(new RestFailure("Card ID not found."));
        } else return new RestFailure("Card ID not found, or you don't have permission to perform this action.");
    }

    @PutMapping("/ranches/{id}/state")
    @ApiOperation(value = "Toggle the state of a card between closed/open.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO setRanchDataState(@PathVariable("id") String id, @RequestParam Boolean closed) {
        if (ranchAccess.hasRole(PLRole.DATA_EDIT) || ranchAccess.hasRole(PLRole.CONTRACTOR_EDIT)) {
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
}
