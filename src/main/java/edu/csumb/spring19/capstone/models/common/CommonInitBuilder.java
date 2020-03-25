package edu.csumb.spring19.capstone.models.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;

public class CommonInitBuilder {
    private ArrayList<CommonData> common = new ArrayList<>();

    public List<CommonData> build() {
        return common;
    }

    public CommonInitBuilder ranches() {
        // CommonDataValue<String> cdv1 = new CommonDataValue<String>((new ObjectId()).toString(), "Baillie");
        // CommonDataValue<String> cdv2 = new CommonDataValue<String>((new ObjectId()).toString(), "Spreckles");
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("ranches", new ArrayList<>());
        common.add(list);
        return this;
    }

    public CommonInitBuilder fertilizers() {
        // CommonDataValue<String> cdv1 = new CommonDataValue<String>((new ObjectId()).toString(), "0-0-20");
        // CommonDataValue<String> cdv2 = new CommonDataValue<String>((new ObjectId()).toString(), "6-16-0 (LIQ)");
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("fertilizers", new ArrayList<>());
        common.add(list);
        return this;
    }

    public CommonInitBuilder chemicals() {
        // CommonDataValue<String> cdv1 = new CommonDataValue<String>((new ObjectId()).toString(), "Kerb");
        // CommonDataValue<String> cdv2 = new CommonDataValue<String>((new ObjectId()).toString(), "RoundUp");
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("chemicals", new ArrayList<>());
        common.add(list);
        return this;
    }

    public CommonInitBuilder chemicalRateUnits() {
        // CommonDataValue<String> cdv1 = new CommonDataValue<String>((new ObjectId()).toString(), "Gallons");
        // CommonDataValue<String> cdv2 = new CommonDataValue<String>((new ObjectId()).toString(), "Pounds");
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("chemicalRateUnits", new ArrayList<>());
        common.add(list);
        return this;
    }

    public CommonInitBuilder irrigationMethod() {
        // CommonDataValue<String> cdv1 = new CommonDataValue<String>((new ObjectId()).toString(), "Drip");
        // CommonDataValue<String> cdv2 = new CommonDataValue<String>((new ObjectId()).toString(), "Furrow");
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("irrigationMethod", new ArrayList<>());
        common.add(list);
        return this;
    }

    public CommonInitBuilder irrigators() {
        // CommonDataValue<String> cdv1 = new CommonDataValue<String>((new ObjectId()).toString(), "John Smith");
        // CommonDataValue<String> cdv2 = new CommonDataValue<String>((new ObjectId()).toString(), "Jane Doe");
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("irrigators", new ArrayList<>());
        common.add(list);
        return this;
    }

    public CommonInitBuilder tractorOperators() {
        // CommonDataValue<String> cdv1 = new CommonDataValue<String>((new ObjectId()).toString(), "Dave Smith");
        // CommonDataValue<String> cdv2 = new CommonDataValue<String>((new ObjectId()).toString(), "Erica Doe");
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("tractorOperators", new ArrayList<>());
        common.add(list);
        return this;
    }

    public CommonInitBuilder tractorWork() {
        // CommonDataValue<String> cdv1 = new CommonDataValue<String>((new ObjectId()).toString(), "Initial Setup");
        // CommonDataValue<String> cdv2 = new CommonDataValue<String>((new ObjectId()).toString(), "Cultivate");
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("tractorWork", new ArrayList<>());
        common.add(list);
        return this;
    }

    public CommonInitBuilder bedTypes() {
        // CommonDataValue<String> cdv1 = new CommonDataValue<String>((new ObjectId()).toString(), "20-20");
        // CommonDataValue<String> cdv2 = new CommonDataValue<String>((new ObjectId()).toString(), "40");
        CommonData<List<CommonDataValue<String>>> list = new CommonData<>("bedTypes", new ArrayList<>());
        common.add(list);
        return this;
    }

    public CommonInitBuilder commodities() {
        // HashMap<String, List<String>> innerHash = new HashMap<String, List<String>>();
        // innerHash.put("Lettuce", Arrays.asList("Variety 1", "Variety 2"));
        // CommonDataValue<HashMap<String, List<String>>> cdv1 = new CommonDataValue<HashMap<String, List<String>>>((new ObjectId()).toString(), innerHash);
        
        // HashMap<String, List<String>> innerHash2 = new HashMap<String, List<String>>();
        // innerHash2.put("Cabbage", Arrays.asList("Variety 1", "Variety 2"));
        // CommonDataValue<HashMap<String, List<String>>> cdv2 = new CommonDataValue<HashMap<String, List<String>>>((new ObjectId()).toString(), innerHash2);
        
        CommonData<List<CommonDataValue<HashMap<String, List<String>>>>> list = new CommonData<>("commodities", new ArrayList<>());
        common.add(list);
        return this;
    }
}
