package edu.csumb.spring19.capstone.models.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.naming.LimitExceededException;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Document(collection="ranches")
@JsonIgnoreProperties(ignoreUnknown = true, value= {"id"}, allowGetters = true)
public class Card {
    private static final Integer MAX_LIST_SIZE_BIG = 12;
    private static final Integer MAX_LIST_SIZE_SMALL = 3;

    @Id
    private String id;
    private Boolean isClosed = false;
    private Date dateCreated = new Date();
    private Date lastUpdated = new Date();

    private List<Irrigation> irrigation;
    private List<Tractor> tractor;
    private List<Commodities> commodities;
    private List<Chemicals> preChemicals;
    private List<Chemicals> postChemicals;

    @NotEmpty
    private String ranchName;
    private Integer fieldID;
    private String ranchManagerName;
    private String lotNumber;
    private List<String> shippers;
    private String planterNumber;
    private List<Comment> comments;

    private Date wetDate;
    private Date thinDate;
    private Date hoeDate;
    private Date harvestDate;

    private WorkType thinType;
    private WorkType hoeType;

    private List<ThinHoeCrew> thinCrews;
    private List<ThinHoeCrew> hoeCrews;

    private Integer cropYear = Calendar.getInstance().get(Calendar.YEAR);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated() {
        this.lastUpdated = new Date();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Irrigation> getIrrigation() {
        return irrigation;
    }

    public void addIrrigation(Irrigation irrigation) throws LimitExceededException {
        if (this.irrigation.size() + 1 > MAX_LIST_SIZE_BIG)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE_BIG + " irrigation entries allowed.");
        this.irrigation.add(irrigation);
    }

    public void setIrrigation(List<Irrigation> irrigation) throws LimitExceededException {
        if (irrigation.size() > MAX_LIST_SIZE_BIG)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE_BIG + " irrigation entries allowed.");
        this.irrigation = irrigation;
    }

    public List<Tractor> getTractor() {
        return tractor;
    }

    public void addTractor(Tractor tractor) throws LimitExceededException {
        if (this.tractor.size() + 1 > MAX_LIST_SIZE_BIG)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE_BIG + " tractor entries allowed.");
        this.tractor.add(tractor);
    }

    public void setTractor(List<Tractor> tractor) throws LimitExceededException {
        if (tractor.size() > MAX_LIST_SIZE_BIG)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE_BIG + " tractor entries allowed.");
        this.tractor = tractor;
    }

    public List<Commodities> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<Commodities> commodities) throws LimitExceededException {
        if (commodities.size() > MAX_LIST_SIZE_SMALL)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE_SMALL + " commodities allowed.");
        this.commodities = commodities;
    }

    public List<Chemicals> getPreChemicals() {
        return preChemicals;
    }

    public void setPreChemicals(List<Chemicals> preChemicals) throws LimitExceededException {
        if (preChemicals.size() > MAX_LIST_SIZE_SMALL)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE_SMALL + " pre-plant chemicals allowed.");
        this.preChemicals = preChemicals;
    }

    public List<Chemicals> getPostChemicals() {
        return postChemicals;
    }

    public void addPostChemicals(Chemicals postChemicals) throws LimitExceededException {
        if (this.postChemicals.size() + 1 > MAX_LIST_SIZE_BIG)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE_BIG + " post-plant chemicals allowed.");
        this.postChemicals.add(postChemicals);
    }

    public void setPostChemicals(List<Chemicals> postChemicals) throws LimitExceededException {
        if (postChemicals.size() > MAX_LIST_SIZE_BIG)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE_BIG + " post-plant chemicals allowed.");
        this.postChemicals = postChemicals;
    }

    public String getRanchName() {
        return ranchName;
    }

    public void setRanchName(String ranchName) {
        this.ranchName = ranchName;
    }

    public Integer getFieldID() {
        return fieldID;
    }

    public void setFieldID(Integer fieldID) {
        this.fieldID = fieldID;
    }

    public String getRanchManagerName() {
        return ranchManagerName;
    }

    public void setRanchManagerName(String ranchManagerName) {
        this.ranchManagerName = ranchManagerName;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public void addShipper(String shipperID) {
        this.shippers.add(shipperID);
    }

    public List<String> getShippers() {
        return shippers;
    }

    public void setShippers(List<String> shippers) {
        this.shippers = shippers;
    }

    public String getPlanterNumber() {
        return planterNumber;
    }

    public void setPlanterNumber(String planterNumber) {
        this.planterNumber = planterNumber;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
    
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getWetDate() {
        return wetDate;
    }

    public void setWetDate(Date wetDate) {
        this.wetDate = wetDate;
    }

    public Date getThinDate() {
        return thinDate;
    }

    public void setThinDate(Date thinDate) {
        this.thinDate = thinDate;
    }

    public Date getHoeDate() {
        return hoeDate;
    }

    public void setHoeDate(Date hoeDate) {
        this.hoeDate = hoeDate;
    }

    public List<ThinHoeCrew> getThinCrews() {
        return thinCrews;
    }

    public void setThinCrews(List<ThinHoeCrew> thinCrews) throws LimitExceededException {
        if (thinCrews.size() > 1)
            throw new LimitExceededException("Only 1 Thin Crew entry is allowed per card.");
        this.thinCrews = thinCrews;
    }

    public void addThinCrew(ThinHoeCrew thinCrew) throws LimitExceededException {
        if (this.thinCrews == null) {
            this.thinCrews = new ArrayList<ThinHoeCrew>();
        }
        if (this.thinCrews.size() >= 1)
            throw new LimitExceededException("Only 1 Thin Crew entry is allowed per card.");
        this.thinCrews.add(thinCrew);
    }

    public List<ThinHoeCrew> getHoeCrews() {
        return hoeCrews;
    }

    public void setHoeCrews(List<ThinHoeCrew> hoeCrews) throws LimitExceededException {
        if (hoeCrews.size() > 3)
            throw new LimitExceededException("Only 3 Hoe Crew entries are allowed per card.");
        this.hoeCrews = hoeCrews;
    }

    public void addHoeCrew(ThinHoeCrew hoeCrew) throws LimitExceededException {
        if (this.hoeCrews == null) {
            this.hoeCrews = new ArrayList<ThinHoeCrew>();
        }
        if (this.hoeCrews.size() >= 3)
            throw new LimitExceededException("Only 3 Hoe Crew entries are allowed per card.");
        this.hoeCrews.add(hoeCrew);
    }

    public Date getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
    }

    public Integer getCropYear() {
        return cropYear;
    }

    public void setCropYear(Integer cropYear) {
        this.cropYear = cropYear;
    }

    public WorkType getThinType() {
        return thinType;
    }

    public String getThinTypeString() {
        if (this.thinType != null) {
            return thinType.toString();
        } else {
            return "";
        }
    }

    public void setThinType(WorkType thinType) {
        this.thinType = thinType;
    }

    public WorkType getHoeType() {
        return hoeType;
    }

    public void setHoeType(WorkType hoeType) {
        this.hoeType = hoeType;
    }

    public String getHoeTypeString() {
        if (this.hoeType != null) {
            return hoeType.toString();
        } else {
            return "";
        }
    }
}
enum WorkType {
    Hand, Machine;
}