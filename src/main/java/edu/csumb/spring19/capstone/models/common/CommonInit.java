package edu.csumb.spring19.capstone.models.common;

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommonInit {
    private ArrayList<CommonData> common = new ArrayList<>();

    public List<CommonData> build() {
        return common;
    }

    public CommonInit ranches() {
        common.add(new CommonData<List<String>>("ranches", new ArrayList<>()));
        return this;
    }

    public CommonInit fertilizers() {
        common.add(new CommonData<List<String>>("fertilizers", new ArrayList<>()));
        return this;
    }

    public CommonInit chemicals() {
        common.add(new CommonData<List<String>>("chemicals", new ArrayList<>()));
        return this;
    }

    public CommonInit tractorOperators() {
        common.add(new CommonData<List<String>>("tractorOperators", new ArrayList<>()));
        return this;
    }

    public CommonInit bedTypes() {
        common.add(new CommonData<List<String>>("bedTypes", new ArrayList<>()));
        return this;
    }

    public CommonInit bedCounts() {
        common.add(new CommonData<List<String>>("bedCounts", new ArrayList<>()));
        return this;
    }

    // Contains both commodities and the sublist of varieties
    public CommonInit commodities() {
        CommonData<HashMap<String, List<String>>> list = new CommonData<>("commodities", new HashMap<>());
        list.getValues().put("Lettuce", Arrays.asList("Variety 1", "Variety 2"));
        common.add(list);
        return this;
    }
}
