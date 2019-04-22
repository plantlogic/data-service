package edu.csumb.spring19.capstone.models.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="chemicals")
@JsonIgnoreProperties(allowGetters = true)
public class Chemicals {
    private Date date = new Date();
    private Chemical chemical;
    private Chemical fertilizer;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}