package edu.csumb.spring19.capstone.models.common;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.bson.types.ObjectId;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class CommonDataValue<E> {
    private String id;
    private E value;

    public CommonDataValue() {
        this(new ObjectId().toString(), null);
    }

    public CommonDataValue(E value) {
        this(new ObjectId().toString(), value);
    }

    public CommonDataValue(String id, E value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}