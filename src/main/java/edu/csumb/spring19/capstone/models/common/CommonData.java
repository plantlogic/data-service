package edu.csumb.spring19.capstone.models.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="common")
@JsonIgnoreProperties(ignoreUnknown = true, value= {"id"}, allowGetters = true)
public class CommonData{
    @Id
    private String id;

    private List<String> ranches;
    private List<String> ranchManagers;
    private List<String> postPlantFertilizers;
    private List<String> prePlantFertilizers;
    private List<String> organicFertilizers;
    private List<String> insecticides;
    private List<String> herbicides;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<String> getRanches() {
        return ranches;
    }

    public List<String> getHerbicides() {
        return herbicides;
    }

    public void setHerbicides(List<String> herbicides) {
        this.herbicides = herbicides;
    }

    public List<String> getInsecticides() {
        return insecticides;
    }

    public void setInsecticides(List<String> insecticides) {
        this.insecticides = insecticides;
    }

    public List<String> getOrganicFertilizers() {
        return organicFertilizers;
    }

    public void setOrganicFertilizers(List<String> organicFertilizers) {
        this.organicFertilizers = organicFertilizers;
    }

    public List<String> getPrePlantFertilizers() {
        return prePlantFertilizers;
    }

    public void setPrePlantFertilizers(List<String> prePlantFertilizers) {
        this.prePlantFertilizers = prePlantFertilizers;
    }

    public List<String> getPostPlantFertilizers() {
        return postPlantFertilizers;
    }

    public void setPostPlantFertilizers(List<String> postPlantFertilizers) {
        this.postPlantFertilizers = postPlantFertilizers;
    }

    public List<String> getRanchManagers() {
        return ranchManagers;
    }

    public void setRanchManagers(List<String> ranchManagers) {
        this.ranchManagers = ranchManagers;
    }

    public void setRanches(List<String> ranches) {
        this.ranches = ranches;
    }


}