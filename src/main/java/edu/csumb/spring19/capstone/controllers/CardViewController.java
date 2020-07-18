package edu.csumb.spring19.capstone.controllers;

import edu.csumb.spring19.capstone.dto.RestDTO;
import edu.csumb.spring19.capstone.dto.RestData;
import edu.csumb.spring19.capstone.dto.RestFailure;
import edu.csumb.spring19.capstone.models.authentication.PLRole;
import edu.csumb.spring19.capstone.models.card.Card;
import edu.csumb.spring19.capstone.models.dbfilter.DbFilter;
import edu.csumb.spring19.capstone.models.dbfilter.DbFilterResponse;
import edu.csumb.spring19.capstone.repos.RanchRepository;
import edu.csumb.spring19.capstone.security.RanchAccess;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/view")
@PreAuthorize("hasAnyRole('DATA_VIEW', 'CONTRACTOR_VIEW', 'SHIPPER')")
public class CardViewController {
    @Autowired
    private RanchRepository ranchRepository;

    @Autowired
    private RanchAccess ranchAccess;

    @PostMapping("/ranchesFiltered")
    @ApiOperation(value = "Get all cards from the database, according to provided filter.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getAllRanchDataFiltered(@Valid @RequestBody DbFilter filter) {
        // Set defualt sort method
        Sort sort = Sort.by(Sort.Order.desc("lastUpdated"));
        // Use mongo sorting for provided filter if applicable
        if (!filter.getSort().equals("commodities")) {
            if (filter.getOrder().equals("asc")) {
                sort = Sort.by(Sort.Order.asc(filter.getSort()));
            } else {
                sort = Sort.by(Sort.Order.desc(filter.getSort()));
            }
        }
        // Build new ranch list which finds intersection between queried ranches and permitted ranches
        List<String> permitted = ranchAccess.getRanchList();
        ArrayList<String> ranches = new ArrayList<String>();
        for (int i = 0; i < permitted.size(); i++) {
            if (filter.getRanches().contains(permitted.get(i))) {
                ranches.add(permitted.get(i));
            }
        }
        // Get iterable over database cards sorted with given ranchlist
        Iterable<Card> cards = ranchRepository.findAllByRanchNameIsIn(ranches, sort);
        // Perform filter operation and return response
        return new RestData<DbFilterResponse>(filter.filter(cards));
    }

    @PostMapping("/shipperRanchesFiltered/{shipperId}")
    @ApiOperation(value = "Get all cards from the database containing the shipperID, according to provided filter.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getAllShipperRanchDataFiltered(@Valid @RequestBody DbFilter filter, @PathVariable("shipperId") String shipperId) {
        // Set defualt sort method
        Sort sort = Sort.by(Sort.Order.desc("lastUpdated"));
        // Use mongo sorting for provided filter if applicable
        if (!filter.getSort().equals("commodities")) {
            if (filter.getOrder().equals("asc")) {
                sort = Sort.by(Sort.Order.asc(filter.getSort()));
            } else {
                sort = Sort.by(Sort.Order.desc(filter.getSort()));
            }
        }
        // Get iterable over database cards sorted with given ranchlist
        Iterable<Card> cards = ranchRepository.findAllByRanchNameIsInAndShippersContaining(filter.getRanches(), shipperId, sort);
        // Perform filter operation and return response
        return new RestData<DbFilterResponse>(filter.filter(cards));
    }

    @GetMapping("/ranches")
    @ApiOperation(value = "Get all cards from the database.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getAllRanchData(@RequestParam(defaultValue = "true", required = false) Boolean openCards,
                                   @RequestParam(defaultValue = "true", required = false) Boolean closedCards) {
        RestDTO result = new RestFailure("You requested no cards, or don't have permission to access the requested cards.");
                                
        Sort sortByRanchName = Sort.by(Sort.Order.asc("fieldID"), Sort.Order.desc("lastUpdated"));
        System.out.println("Getting all ranches from default route");
        if (openCards && closedCards) {
            result = new RestData<>(ranchRepository.findAllByRanchNameIsIn(ranchAccess.getRanchList(), sortByRanchName));
        } else if (openCards) {
            result = new RestData<>(ranchRepository.findAllByIsClosedFalseAndRanchNameIsIn(ranchAccess.getRanchList(), sortByRanchName));
        } else if (closedCards) {
            result = new RestData<>(ranchRepository.findAllByIsClosedTrueAndRanchNameIsIn(ranchAccess.getRanchList(), sortByRanchName));
        } else {
            result = new RestFailure("You requested no cards.");
        }
        return result;
    }
    
    @GetMapping("/shipperRanches/{shipperId}")
    @ApiOperation(value = "Get all cards from the database which include the shipperID.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getShipperRanchData(@PathVariable("shipperId") String shipperId) {
        RestDTO result = new RestFailure("You requested no cards, or don't have permission to access the requested cards.");
        try {
            Sort sortByRanchName = Sort.by(Sort.Order.asc("fieldID"), Sort.Order.desc("lastUpdated"));
            result = new RestData<>(ranchRepository.findAllByShippersContaining(shipperId, sortByRanchName));
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    @GetMapping("/ranches/{id}")
    @ApiOperation(value = "Get card from the database by its ID.", authorizations = {@Authorization(value = "Bearer")})
    public RestDTO getRanchData(@PathVariable("id") String id) {
        Optional<Card> card = ranchRepository.findById(id);
        if (!card.isPresent()) {
            return new RestFailure("Card not found with id " + id );
        } else {
            PLRole[] roles = {PLRole.DATA_VIEW, PLRole.CONTRACTOR_VIEW, PLRole.SHIPPER};
            // LIMIT DATA SENT IF SHIPPER
            if (ranchAccess.cardExistsAndHasAnyPermissions(roles, true, card)) {
                return new RestData<>(card.get());
            } else {
                if (!ranchAccess.hasRole(PLRole.SHIPPER)) {
                    return new RestFailure("You don't have permission to access this card.");
                } else {
                    return new RestFailure("There was an error retrieving the card data.");
                }
            }
        }
    }
}

