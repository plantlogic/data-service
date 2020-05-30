package edu.csumb.spring19.capstone.models.export;

public class ExportPresetListEntry {
    private String key;
    private Boolean value;
    private String display;

    public ExportPresetListEntry(String key, Boolean value, String display) {
        this.key = key;
        this.value = value;
        this.display = display;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}