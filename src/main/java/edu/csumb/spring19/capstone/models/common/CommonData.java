package edu.csumb.spring19.capstone.models.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="common")
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class CommonData<E> {
    @Id
    private String key;
    private List<E> values;

    public CommonData(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<E> getValues() {
        return values;
    }

    public void setValues(List<E> values) {
        this.values = values;
    }
}