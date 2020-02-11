package edu.csumb.spring19.capstone.models.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="irrigation")
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class Irrigation {
    private Date workDate = new Date();
    private String method;
    private Chemical chemical;
    private Chemical fertilizer;
    private String irrigator;


    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Chemical getChemical() {
        return chemical;
    }

    public void setChemical(Chemical chemical) {
        this.chemical = chemical;
    }

    public Chemical getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(Chemical fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getIrrigator() {
        return irrigator;
    }

    public void setIrrigator(String irrigator) {
        this.irrigator = irrigator;
    }
}