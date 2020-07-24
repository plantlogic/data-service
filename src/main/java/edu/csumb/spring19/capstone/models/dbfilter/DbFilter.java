package edu.csumb.spring19.capstone.models.dbfilter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.csumb.spring19.capstone.models.card.Card;
import edu.csumb.spring19.capstone.models.card.Commodities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class DbFilter {
    private String sort;
    private String order;
    private String fieldID;
    private String lotNumber;
    private List<String> ranches;
    private boolean isAllRanches = false;
    private List<String> commodities;
    private List<String> allCommoditiesOrdered;
    private boolean isAllCommodities = false;
    // Inclusive
    private int start;
    // Exclusive
    private int stop;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFieldID() {
        return fieldID;
    }

    public void setFieldID(String fieldID) {
        this.fieldID = fieldID;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public List<String> getRanches() {
        return this.ranches;
    }

    public void setRanches(List<String> ranches) {
        this.ranches = ranches;
    }

    public List<String> getCommodities() {
        return this.commodities;
    }

    public void setCommodities(List<String> commodities) {
        this.commodities = commodities;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public boolean getIsAllRanches() {
        return this.isAllRanches;
    }

    public void setIsAllRanches(boolean isAllRanches) {
        this.isAllRanches = isAllRanches;
    }

    public boolean getIsAllCommodities() {
        return this.isAllCommodities;
    }

    public void setIsAllCommodites(boolean isAllCommodities) {
        this.isAllCommodities = isAllCommodities;
    }

    public List<String> getAllCommoditiesOrdered() {
        return this.allCommoditiesOrdered;
    }

    public void setAllCommoditiesOrdered(List<String> allCommoditiesOrdered) {
        this.allCommoditiesOrdered = allCommoditiesOrdered;
    }

    public DbFilterResponse filter(Iterable<Card> cards) {
        ArrayList<Card> cardList = new ArrayList<Card>();

        // Filter down by checking for matching/similar commodities, fieldIDs, and lotNumbers
        Iterator<Card> i = cards.iterator();
        Card temp;
        boolean matchingCommodity;
        boolean matchingfieldID;
        boolean matchingLotNumber;
        while (i.hasNext()) {
            matchingCommodity = false;
            matchingfieldID = false;
            matchingLotNumber = false;
            temp = i.next();
            if (this.getIsAllCommodities()) {
                matchingCommodity = true;
            } else if (temp.getCommodities().size() != 0) {
                for (Commodities c : temp.getCommodities()) {
                    // If any of the card's commodities are found, add it
                    if (this.getCommodities().contains(c.getCommodity())) { matchingCommodity = true; }
                }
            } 
            if (Optional.ofNullable(temp.getFieldID()).isPresent()) {
                if (Integer.toString(temp.getFieldID()).contains(this.getFieldID().toLowerCase()) || this.getFieldID().equals("")) {
                    matchingfieldID = true;
                }
            } else if (this.getFieldID().equals("")) {
                matchingfieldID = true;
            }
            if (Optional.ofNullable(temp.getLotNumber()).isPresent()) {
                if (temp.getLotNumber().toLowerCase().contains(this.getLotNumber().toLowerCase()) || this.getLotNumber().equals("")) {
                    matchingLotNumber = true;
                }
            } else if (this.getLotNumber().equals("")) {
                matchingLotNumber = true;
            }
            if (matchingCommodity && matchingfieldID && matchingLotNumber) {
                cardList.add(temp);
            }
        }

        // Return only the elements from start to stop index (What happens if none?)
        // Check for indexing errors
        int difference = Math.abs(this.getStop() - this.getStart());
        if (this.getStart() < 0) { this.setStart(0); }
        if (this.getStop() > cardList.size()) { this.setStop(cardList.size()); }
        if (this.getStart() > this.getStop()) {
            if ((this.getStop() - difference) < 0) {
                this.setStart(0);
            } else {
                this.setStart(this.getStop() - difference);
            }
        }

        // Sort by commodities if necessary
        if (this.getSort().equalsIgnoreCase("commodities")) {
            List<Card> cardListOrderedByCommodity = this.orderCardsByCommodities(cardList);
            return new DbFilterResponse(cardListOrderedByCommodity.subList(this.getStart(), this.getStop()), cardListOrderedByCommodity.size());
        } else {
            return new DbFilterResponse(cardList.subList(this.getStart(), this.getStop()), cardList.size());
        }
    }

    public List<Card> orderCardsByCommodities(ArrayList<Card> unordered) {
        // Create a list that contains the "order keys" for each existing combination of commodities found in the filtered cards.
        ArrayList<String> orderKeys = new ArrayList<String>();
        // Create/add to the hashmap that contains (orderKey : [list of card ids with this order key])
        HashMap<String,ArrayList<Card>> m = new HashMap<String,ArrayList<Card>>();
        /*
            Getting "order key":
            A card may have commodities ["Carrot", "Onion"], although the values will be their db ids; e.g. ["e1232f", "e1363g"]
            The entry added to the list will replace the ids with the index in which they occured in the full ordered list of commodity ids supplied
            e.g [2 , 6]
            These values are converted to a string, padded with zeros, sorted in ascending order, and concatenated into a single string. This is the "order key"
            e.g "00020006"
        */
        for (Card c : unordered) {
            String orderKey = "";
            ArrayList<String> indices = new ArrayList<String>();
            for (Commodities c2: c.getCommodities()) {
                String index = "0000" + String.valueOf(this.getAllCommoditiesOrdered().indexOf(c2.getCommodity()));
                String formatted = index.substring(index.length() - 4);
                indices.add(formatted);
            }
            indices.sort(Comparator.comparing(String::toString));
            for (String s: indices) {
                orderKey += s;
            }
            if (!orderKeys.contains(orderKey)) {
                orderKeys.add(orderKey);
            }
            if (!m.containsKey(orderKey)) {
                m.put(orderKey, new ArrayList<Card>(Arrays.asList(c)));
            } else {
                ArrayList<Card> updated = m.get(orderKey);
                updated.add(c);
                m.put(orderKey, updated);
            }
        }

        // Sort the orderkeys alphabetically (padded numbers)
        if (this.getOrder().equalsIgnoreCase("asc")) {
            orderKeys.sort(Comparator.comparing(String::toString));
        } else {
            orderKeys.sort(Collections.reverseOrder());
        }

        ArrayList<Card> cardListOrderedByCommodity = new ArrayList<Card>();

        for (String orderKey: orderKeys) {
            for (Card c : m.get(orderKey)) {
                cardListOrderedByCommodity.add(c);
            }
        }
        return cardListOrderedByCommodity;
    }
}
