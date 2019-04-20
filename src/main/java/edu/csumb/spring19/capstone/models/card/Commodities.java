package edu.csumb.spring19.capstone.models.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="commodities")
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class Commodities {
    private String commodity;
    private Float cropAcres;
    private Integer bedType;
    private Integer bedCount;
    private Integer seedLotNumber;
    private String variety;

    public String getCommodity() {
        return commodity;
    }
    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }
    public Float getCropAcres() {
        return cropAcres;
    }
    public void setCropAcres(Float cropAcres) {
        this.cropAcres = cropAcres;
    }
    public Integer getBedType() {
        return bedType;
    }
    public void setBedType(Integer bedType) {
        this.bedType = bedType;
    }
    public Integer getBedCount() {
        return bedCount;
    }
    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }
    public Integer getSeedLotNumber() {
        return seedLotNumber;
    }
    public void setSeedLotNumber(Integer seedLotNumber) {
        this.seedLotNumber = seedLotNumber;
    }
    public String getVariety() {
        return variety;
    }
    public void setVariety(String variety) {
        this.variety = variety;
    }
}