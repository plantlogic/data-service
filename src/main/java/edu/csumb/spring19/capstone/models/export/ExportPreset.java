package edu.csumb.spring19.capstone.models.export;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.constraints.NotEmpty;

@Document(collection="exportPresets")
@JsonIgnoreProperties(ignoreUnknown = true, value= {"id"}, allowGetters = true)
public class ExportPreset {
 
    @Id
    private String id;

    @NotEmpty
    private String name;
    
    private Date dateCreated = new Date();

    // hashMap to manage what entries have dynamically sizeable sections
    private HashMap<String, HashMap<String, Boolean>> dynamic;

    // Lists which hold booleans for whether or not they will be showed in an export
    private List<ExportPresetListEntry> card;
    private List<ExportPresetListEntry> irrigationEntry;
    private List<ExportPresetListEntry> tractorEntry;
    private List<ExportPresetListEntry> commodities;
    private List<ExportPresetListEntry> preChemicals;
    private List<ExportPresetListEntry> irrigationEntryFertilizers;
    private List<ExportPresetListEntry> irrigationEntryChemicals;
    private List<ExportPresetListEntry> tractorEntryFertilizers;
    private List<ExportPresetListEntry> tractorEntryChemicals;
    private List<ExportPresetListEntry> preChemicalsFertilizer;
    private List<ExportPresetListEntry> preChemicalsChemical;
    private List<ExportPresetListEntry> thinCrews;
    private List<ExportPresetListEntry> hoeCrews;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public HashMap<String, HashMap<String, Boolean>> getDynamic() {
        return dynamic;
    }

    public void setDynamic(HashMap<String, HashMap<String, Boolean>> dynamic) {
        this.dynamic = dynamic;
    }

    public List<ExportPresetListEntry> getCard() {
        return card;
    }

    public void setCard(List<ExportPresetListEntry> card) {
        this.card = card;
    }

    public List<ExportPresetListEntry> getIrrigationEntry() {
        return irrigationEntry;
    }
    
    public void setIrrigationEntry(List<ExportPresetListEntry> irrigationEntry) {
        this.irrigationEntry = irrigationEntry;
    }

    public List<ExportPresetListEntry> getTractorEntry() {
        return tractorEntry;
    }

    public void setTractorEntry(List<ExportPresetListEntry> tractorEntry) {
        this.tractorEntry = tractorEntry;
    }

    public List<ExportPresetListEntry> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<ExportPresetListEntry> commodities) {
        this.commodities = commodities;
    }

    public List<ExportPresetListEntry> getThinCrews() {
        return thinCrews;
    }

    public void setThinCrews(List<ExportPresetListEntry> thinCrews) {
        this.thinCrews = thinCrews;
    }

    public List<ExportPresetListEntry> getHoeCrews() {
        return hoeCrews;
    }

    public void setHoeCrews(List<ExportPresetListEntry> hoeCrews) {
        this.hoeCrews = hoeCrews;
    }

    public List<ExportPresetListEntry> getPreChemicals() {
        return preChemicals;
    }

    public void setPreChemicals(List<ExportPresetListEntry> preChemicals) {
        this.preChemicals = preChemicals;
    }

    public List<ExportPresetListEntry> getIrrigationEntryFertilizers() {
        return irrigationEntryFertilizers;
    }

    public void setIrrigationEntryFertilizers(List<ExportPresetListEntry> irrigationEntryFertilizers) {
        this.irrigationEntryFertilizers = irrigationEntryFertilizers;
    }

    public List<ExportPresetListEntry> getIrrigationEntryChemicals() {
        return irrigationEntryChemicals;
    }

    public void setIrrigationEntryChemicals(List<ExportPresetListEntry> irrigationEntryChemicals) {
        this.irrigationEntryChemicals = irrigationEntryChemicals;
    }

    public List<ExportPresetListEntry> getTractorEntryFertilizers() {
        return tractorEntryFertilizers;
    }

    public void setTractorEntryFertilizers(List<ExportPresetListEntry> tractorEntryFertilizers) {
        this.tractorEntryFertilizers = tractorEntryFertilizers;
    }

    public List<ExportPresetListEntry> getTractorEntryChemicals() {
        return tractorEntryChemicals;
    }

    public void setTractorEntryChemicals(List<ExportPresetListEntry> tractorEntryChemicals) {
        this.tractorEntryChemicals = tractorEntryChemicals;
    }

    public List<ExportPresetListEntry> getPreChemicalsFertilizer() {
        return preChemicalsFertilizer;
    }

    public void setPreChemicalsFertilizer(List<ExportPresetListEntry> preChemicalsFertilizer) {
        this.preChemicalsFertilizer = preChemicalsFertilizer;
    }

    public List<ExportPresetListEntry> getPreChemicalsChemical() {
        return preChemicalsChemical;
    }

    public void setPreChemicalsChemical(List<ExportPresetListEntry> preChemicalsChemical) {
        this.preChemicalsChemical = preChemicalsChemical;
    }
}