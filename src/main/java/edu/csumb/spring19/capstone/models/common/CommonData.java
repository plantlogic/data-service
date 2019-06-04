package edu.csumb.spring19.capstone.models.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="common")
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class CommonData<E> {
    @Id
    private String key;
    private E values;

    public CommonData(String key, E values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public E getValues() {
        return values;
    }

    public void setValues(E values) {
        this.values = values;
    }
}