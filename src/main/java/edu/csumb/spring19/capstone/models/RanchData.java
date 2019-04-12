package edu.csumb.spring19.capstone.models;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ranches")
@JsonIgnoreProperties(ignoreUnknown = true, value= {"id","fieldID","ranchManagerName"}, allowGetters = true)
public class RanchData{
    @Id
    private String id;
    
    private List<IrrigationData> irrigationData;
    private List<TractorData> tractorData;
    private List<InitialData> initialData;
    private List<PreplantData> preplantData;
    private List<PlantingData> plantingData;

    @NotEmpty
    private String ranchName;
    private Integer fieldID;
    private String ranchManagerName;
    private String lotNumber;
    private String shipperID;

    private Date wetDate = new Date();
    private Date thinDate = new Date();
    private Date hoeDate = new Date();
    private Date harvestDate = new Date();

    private Integer cropYear;

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
    public List<InitialData> getInitialData() {
        return initialData;
    }
    public void setInitialData(List<InitialData> initialData) {
        this.initialData = initialData;
    }
    public List<PreplantData> getPreplantData() {
        return preplantData;
    }
    public void setPreplantData(List<PreplantData> preplantData) {
        this.preplantData = preplantData;
    }
    public List<PlantingData> getPlantingData() {
        return plantingData;
    }
    public void setPlantingData(List<PlantingData> plantingData) {
        this.plantingData = plantingData;
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
    public String getShipperID() {
        return shipperID;
    }
    public void setShipperID(String shipperID) {
        this.shipperID = shipperID;
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
    public Integer getCropYear() {
        return cropYear;
    }
    public void setCropYear(Integer cropYear) {
        this.cropYear = cropYear;
    }

}
