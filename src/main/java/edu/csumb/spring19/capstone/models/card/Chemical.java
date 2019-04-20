package edu.csumb.spring19.capstone.models.card;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="chemical")
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class Chemical {
    private String name;
    private Float rate;
    private ChemicalUnit unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public ChemicalUnit getUnit() {
        return unit;
    }

    public void setUnit(ChemicalUnit unit) {
        this.unit = unit;
    }
}

enum ChemicalUnit {
    Gallons, Pounds;
}
