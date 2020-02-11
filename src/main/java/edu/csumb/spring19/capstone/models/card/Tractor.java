package edu.csumb.spring19.capstone.models.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="tractor")
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class Tractor {
    private Date workDate = new Date();
    private String workDone;
    private String operator;
    private Chemical chemical;
    private Chemical fertilizer;
    private String tractorNumber;

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
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

    public String getTractorNumber() {
        return tractorNumber;
    }

    public void setTractorNumber(String tractorNumber) {
        this.tractorNumber = tractorNumber;
    }
}