package edu.csumb.spring19.capstone.models.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommonInitBuilder {
    private ArrayList<CommonData> common = new ArrayList<>();

    public List<CommonData> build() {
        return common;
    }

    public CommonInitBuilder ranches() {
        common.add(new CommonData<List<String>>("ranches", new ArrayList<>()));
        return this;
    }

    public CommonInitBuilder fertilizers() {
        common.add(new CommonData<List<String>>("fertilizers", new ArrayList<>()));
        return this;
    }

    public CommonInitBuilder chemicals() {
        common.add(new CommonData<List<String>>("chemicals", new ArrayList<>()));
        return this;
    }

    public CommonInitBuilder tractorOperators() {
        common.add(new CommonData<List<String>>("tractorOperators", new ArrayList<>()));
        return this;
    }

    public CommonInitBuilder bedTypes() {
        common.add(new CommonData<List<Integer>>("bedTypes", new ArrayList<>()));
        return this;
    }

    public CommonInitBuilder bedCounts() {
        common.add(new CommonData<List<Integer>>("bedCounts", new ArrayList<>()));
        return this;
    }

    // Contains both commodities and the sublist of varieties
    public CommonInitBuilder commodities() {
        CommonData<HashMap<String, List<String>>> list = new CommonData<>("commodities", new HashMap<>());
        list.getValues().put("Lettuce", Arrays.asList("Variety 1", "Variety 2"));
        common.add(list);
        return this;
    }
}
