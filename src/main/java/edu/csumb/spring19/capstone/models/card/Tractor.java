package edu.csumb.spring19.capstone.models.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

import javax.naming.LimitExceededException;

@Document(collection="tractor")
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class Tractor {
    private static final Integer MAX_LIST_SIZE = 2;
    private Date workDate = new Date();
    private String workDone;
    private String operator;
    private List<Chemical> chemicals;
    private List<Chemical> fertilizers;
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

    public void addChemical(Chemical chemical) throws LimitExceededException {
        if (this.chemicals.size() + 1 > MAX_LIST_SIZE)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE + " chemicals allowed.");
        this.chemicals.add(chemical);
    }

    public List<Chemical> getChemicals() {
        return chemicals;
    }

    public void setChemicals(List<Chemical> chemicals) throws LimitExceededException {
        if (chemicals.size() > MAX_LIST_SIZE)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE + " chemicals allowed.");
        this.chemicals = chemicals;
    }
    
    public void addFertilizer(Chemical fertilizer) throws LimitExceededException {
        if (fertilizers.size() + 1 > MAX_LIST_SIZE)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE + " fertilizers allowed.");
        this.fertilizers.add(fertilizer);
    }

    public List<Chemical> getFertilizers() {
        return fertilizers;
    }

    public void setFertilizers(List<Chemical> fertilizers) throws LimitExceededException {
        if (fertilizers.size() > MAX_LIST_SIZE)
            throw new LimitExceededException("Only " + MAX_LIST_SIZE + " fertilizers allowed.");
        this.fertilizers = fertilizers;
    }

    public String getTractorNumber() {
        return tractorNumber;
    }

    public void setTractorNumber(String tractorNumber) {
        this.tractorNumber = tractorNumber;
    }
}