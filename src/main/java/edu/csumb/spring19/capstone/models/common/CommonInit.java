package edu.csumb.spring19.capstone.models.common;

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CommonInit {
    private ArrayList<CommonData> common = new ArrayList<>();

    public List<CommonData> build() {
        return common;
    }

    public CommonInit ranches() {
        common.add(new CommonData<String>("ranches"));
        return this;
    }

    public CommonInit fertilizers() {
        common.add(new CommonData<String>("fertilizers"));
        return this;
    }

    public CommonInit chemicals() {
        common.add(new CommonData<String>("chemicals"));
        return this;
    }

    public CommonInit tractorOperators() {
        common.add(new CommonData<String>("tractorOperators"));
        return this;
    }

    public CommonInit bedTypes() {
        common.add(new CommonData<String>("bedTypes"));
        return this;
    }

    public CommonInit bedCounts() {
        common.add(new CommonData<String>("bedCounts"));
        return this;
    }

    // Contains both commodities and the sublist of varieties
    public CommonInit commodities() {
        common.add(new CommonData<Pair<String, List<String>>>("commodities"));
        return this;
    }
}
