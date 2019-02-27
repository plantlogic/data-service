package edu.csumb.spring19.capstone.ranch;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ranches")
@JsonIgnoreProperties(value= {"createdAt"}, allowGetters = true)
public class RanchData{

    @Id private String plotID;

    @Indexed(unique=true)
    private String ranchName;
    private List<String> commodities;
    private List<String> varieties;
    private Integer acres;
    private Integer lotNumber;
    
    public RanchData(){
        super();
    }
    
    public String getRanchName(){
        return ranchName;
    }
    public void setRanchName(String ranchName){
        this.ranchName = ranchName;
    }
    public List<String> getCommodities() {
        return commodities;
    }
    public void setCommodities(List<String> commodities){
        this.commodities = commodities;
    }
    public List<String> getVarieties(){
        return varieties;
    }
    public void setVarieties(List<String> varieties){
        this.varieties = varieties;
    }
    public Integer getAcres(){
        return acres;
    }
    public void setAcres(Integer acres){
        this.acres = acres;
    }
    public Integer getLotNumber(){
        return lotNumber;
    }
    public void setLotNumber(Integer lotNumber){
        this.lotNumber = lotNumber;
    }
    public String getPlotID(){
        return plotID;
    }
    public void setPlotID(String plotID){
        this.plotID = plotID;
    }
}
