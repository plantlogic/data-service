package edu.csumb.spring19.capstone.models;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="planting")
@JsonIgnoreProperties(allowGetters = true)

public class PlantingData{

    private String fertilizer;
    private String chemical;
    private String amount;
    private Date date;

    public String getFertilizer() {
        return fertilizer;
    }
    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }
    public String getChemical(){
        return chemical;
    }
    public void setChemical(String chemical){
        this.chemical=chemical;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    
}