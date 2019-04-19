package edu.csumb.spring19.capstone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="tractor")
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class TractorData{

    private Date workDate = new Date();
    private String workDone;
    private String operator;
    private String fertilizer;
    private String chemical;
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
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
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