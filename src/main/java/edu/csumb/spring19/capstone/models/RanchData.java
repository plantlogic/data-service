package edu.csumb.spring19.capstone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Document(collection="ranches")
@JsonIgnoreProperties(ignoreUnknown = true, value= {"id","fieldID","ranchManagerName"}, allowGetters = true)
public class RanchData{
    @Id
    private String id;
    
    private List<IrrigationData> irrigationData;
    private List<TractorData> tractorData;

    @NotEmpty
    private String ranchName;
    private Integer fieldID;
    private String ranchManagerName;
    private String lotNumber;
    private Float totalAcres;

    @Max(9999)
    @Min(1000)
    private Integer cropYear;
    @NotEmpty
    private List<String> commodity;
    private List<String> variety;
    private List<Float> cropAcres;
    private List<Integer> bedCount;
    private List<Integer> seedLotNumber;

    private Integer bedType;
    private Float lorsbanRate;
    private Float diaznonRate;
    private Float kerbRate;
    private Float dacthalRate;

    private Date wetDate;
    private Date thinDate;
    private Date hoeDate;
    private Date harvestDate;
    
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
    public Integer getFieldID() {
        return fieldID;
    }
    public void setFieldID(Integer fieldID) {
        this.fieldID = fieldID;
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
    public String getLotNumber() {
        return lotNumber;
    }
    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }
    public Float getTotalAcres() {
        return totalAcres;
    }
    public void setTotalAcres(Float totalAcres) {
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
    public List<Float> getCropAcres() {
        return cropAcres;
    }
    public void setCropAcres (List<Float> cropAcres) {
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
    public void addTractorData(TractorData data) {
        tractorData.add(data);
    }
    public void addIrrigationData(IrrigationData data) {
        irrigationData.add(data);
    }
}
