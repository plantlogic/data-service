package edu.csumb.spring19.capstone.models;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tractor")
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class TractorData{

    private Date workDate = new Date();
    private String workDone;
    private String fertilizer;
    private Float gallons;

    public Date getWorkDate() {
        return workDate;
    }
    public void setWorkDate(Date workDate){
        this.workDate = workDate;
    }
    public String getWorkDone() {
        return workDone;
    }
    public void setWorkDone(String workDone) {
        this.workDone = workDone;
    }
    public String getFertilizer(){
        return fertilizer;
    }
    public void setFertilizer(String fertilizer){
        this.fertilizer = fertilizer;
    }
    public Float getGallons() {
        return gallons;
    }
    public void setGallons(Float gallons) {
        this.gallons = gallons;
    }
}