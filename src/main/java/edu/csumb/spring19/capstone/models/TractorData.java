package edu.csumb.spring19.capstone.models;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tractor")
@JsonIgnoreProperties(ignoreUnknown = true, value= {"id"}, allowGetters = true)
public class TractorData{
    @Id
    private String id;

    private Date workDate = new Date();
    private String workDone;
    private String fertilizer;
    private Integer gallons;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
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
    public Integer getGallons() {
        return gallons;
    }
    public void setGallons(Integer gallons) {
        this.gallons = gallons;
    }
}