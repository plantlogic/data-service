package edu.csumb.spring19.capstone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="irrigation")
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class IrrigationData{

    private Date workDate = new Date();
    private String method;
    private String fertilizer;
    private String chemical;
    private Float gallons;

    public Date getWorkDate() {
        return workDate;
    }
    public void setWorkDate(Date workDate){
        this.workDate = workDate;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getFertilizer(){
        return fertilizer;
    }
    public void setFertilizer(String fertilizer){
        this.fertilizer = fertilizer;
    }
    public String getChemical(){
        return chemical;
    }
    public void setChemical(String chemical){
        this.chemical=chemical;
    }
    public Float getGallons() {
        return gallons;
    }
    public void setGallons(Float gallons) {
        this.gallons = gallons;
    }
}