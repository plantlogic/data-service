package edu.csumb.spring19.capstone.models;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ranches")
@JsonIgnoreProperties(ignoreUnknown = true, value= {"id", "isManaged"}, allowGetters = true)
public class RanchData{
    @Id
    private String id;
    
    private List<IrrigationData> irrigationData;
    private List<TractorData> tractorData;

    private boolean isManaged = false;
    private String ranchName;
    private String ranchManagerName;
    private Integer lotNumber;
    private Integer totalAcres;

    @Max(9999)
    @Min(1000)
    private Integer cropYear;
    private List<String> commodity;
    private List<String> variety;
    private List<Integer> cropAcres;
    private List<Integer> bedCount;
    private List<Integer> seedLotNumber;

    private Integer bedType;
    private Float lorsbanRate;
    private Float diaznonRate;
    private Float kerbRate;
    private Float dacthalRate;

    private Date wetDate = new Date();
    private Date thinDate = new Date();
    private Date hoeDate = new Date();
    private Date harvestDate = new Date();
    
    public RanchData(){
        super();
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<IrrigationData> getIrrigationData() {
        return irrigationData;
    }
    public void setIrrigationData(List<IrrigationData> irrigationData) {
        this.irrigationData = irrigationData;
    }
    public List<TractorData> getTractorData() {
        return tractorData;
    }
    public void setTractorData(List<TractorData> tractorData) {
        this.tractorData = tractorData;
    }
    public boolean getIsManaged() {
        return isManaged;
    }
    public void setIsManaged(boolean isManaged) {
        this.isManaged = isManaged;
    }
    public String getRanchName(){
        return ranchName;
    }
    public void setRanchName(String ranchName){
        this.ranchName = ranchName;
    }
    public String getRanchManagerName() {
        return ranchManagerName;
    }
    public void setRanchManagerName(String ranchManagerName) {
        this.ranchManagerName = ranchManagerName;
    }
    public Integer getLotNumber() {
        return lotNumber;
    }
    public void setLotNumber(Integer lotNumber) {
        this.lotNumber = lotNumber;
    }
    public Integer getTotalAcres() {
        return totalAcres;
    }
    public void setTotalAcres(Integer totalAcres) {
        this.totalAcres = totalAcres;
    }
    public Integer getCropYear() {
        return cropYear;
    }
    public void setCropYear(Integer cropYear) {
        this.cropYear = cropYear;
    }
    public List<String> getCommodity() {
        return commodity;
    }
    public void setCommodity(List<String> commodity) {
        this.commodity = commodity;
    }
    public List<String> getVariety() {
        return variety;
    }
    public void setVariety(List<String> variety) {
        this.variety = variety;
    }
    public List<Integer> getCropAcres() {
        return cropAcres;
    }
    public void setCropAcres (List<Integer> cropAcres) {
        this.cropAcres = cropAcres;
    }
    public List<Integer> getBedCount() {
        return bedCount;
    }
    public void setBedCount(List<Integer> bedCount) {
        this.bedCount = bedCount;
    }
    public List<Integer> getSeedLotNumber() {
        return seedLotNumber;
    }
    public void setSeedLotNumber(List<Integer> seedLotNumber) {
        this.seedLotNumber = seedLotNumber;
    }
    public Integer getBedType() {
        return bedType;
    }
    public void setBedType(Integer bedType) {
        this.bedType = bedType;
    }
    public Float getLorsbanRate() {
        return lorsbanRate;
    }
    public void setLorsbanRate(Float lorsbanRate) {
        this.lorsbanRate = lorsbanRate;
    }
    public Float getDiaznonRate() {
        return diaznonRate;
    }
    public void setDiaznonRate(Float diaznonRate) {
        this.diaznonRate = diaznonRate;
    }
    public Float getKerbRate() {
        return kerbRate;
    }
    public void setKerbRate(Float kerbRate) {
        this.kerbRate = kerbRate;
    }
    public Float getDacthalRate() {
        return dacthalRate;
    }
    public void setDacthalRate(Float dacthalRate) {
        this.dacthalRate = dacthalRate;
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
    public Date getHarvestDate() {
        return harvestDate;
    }
    public void setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
    }
    
    
}
