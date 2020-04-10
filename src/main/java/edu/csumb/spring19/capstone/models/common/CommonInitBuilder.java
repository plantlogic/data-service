package edu.csumb.spring19.capstone.models.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonInitBuilder {
    private ArrayList<CommonData> common = new ArrayList<>();

    public List<CommonData> build() {
        return common;
    }
    public CommonInitBuilder bedTypes() {
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("bedTypes", new ArrayList<>());
        common.add(list);
        return this;
    }
    public CommonInitBuilder chemicalRateUnits() {
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("chemicalRateUnits", new ArrayList<>());
        common.add(list);
        return this;
    }
    public CommonInitBuilder chemicals() {
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("chemicals", new ArrayList<>());
        common.add(list);
        return this;
    }
    public CommonInitBuilder commodities() {
        CommonData<List<CommonDataValue<HashMap<String, List<String>>>>> list = new CommonData<>("commodities", new ArrayList<>());
        common.add(list);
        return this;
    }
    public CommonInitBuilder fertilizers() {
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("fertilizers", new ArrayList<>());
        common.add(list);
        return this;
    }
    public CommonInitBuilder irrigationMethod() {
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("irrigationMethod", new ArrayList<>());
        common.add(list);
        return this;
    }
    public CommonInitBuilder irrigators() {
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("irrigators", new ArrayList<>());
        common.add(list);
        return this;
    }
    public CommonInitBuilder ranches() {
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("ranches", new ArrayList<>());
        common.add(list);
        return this;
    }
    public CommonInitBuilder shippers() {
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("shippers", new ArrayList<>());
        common.add(list);
        return this;
    }
    public CommonInitBuilder tractorOperators() {
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("tractorOperators", new ArrayList<>());
        common.add(list);
        return this;
    }
    public CommonInitBuilder tractorWork() {
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("tractorWork", new ArrayList<>());
        common.add(list);
        return this;
    }
}
