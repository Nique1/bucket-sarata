package pl.sarata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BasketSplitter {

    private final Map<String, List<String>> deliveryOptions;

    public BasketSplitter(String absolutePathToConfigFile) {
        Map<String, List<String>> configMap = mapJsonToConfigMap(absolutePathToConfigFile);
        if (configMap.isEmpty()) {
            throw new IllegalArgumentException("Given config has no records");
        }
        this.deliveryOptions = configMap;
    }

    private Map<String, List<String>> mapJsonToConfigMap(String absolutePathToConfigFile) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> configMap;

        try {
            configMap = mapper.readValue(new FileReader(absolutePathToConfigFile), new TypeReference<>() {
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return configMap;
    }

    private Map<String, Integer> sortAvailableDeliveryOptionsDesc(Map<String, Integer> configMap) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(configMap.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        Map<String, Integer> sortedMap = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private Map<String, Integer> getAvailableDeliveryOptions(List<String> deliveryOptions) {
        Map<String, Integer> deliveryOptionsCount = new HashMap<>();
        for (String item : deliveryOptions) {
            List<String> options = this.deliveryOptions.get(item);
            if(options!= null){
                for (String deliveryOption : options) {
                    deliveryOptionsCount.put(deliveryOption, deliveryOptionsCount.getOrDefault(deliveryOption, 0) + 1);

                }

            }
        }
        sortAvailableDeliveryOptionsDesc(deliveryOptionsCount);
        return deliveryOptionsCount;

    }

    static void groupItems(Map<String, Integer> deliveryOptionCounts, Map<String, List<String>> groupedItems, String item, Map<String, List<String>> deliveryOptions) {
        boolean found = false;
        for (String option : deliveryOptionCounts.keySet()) {
            if (deliveryOptions.get(item).contains(option)) {
                if (!found) {
                    List<String> list = groupedItems.getOrDefault(option, new ArrayList<>());
                    list.add(item);
                    groupedItems.put(option, list);
                    found = true;
                } else {
                    Integer count = deliveryOptionCounts.get(option);
                    deliveryOptionCounts.put(option, count - 1);
                }
            }
        }
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String, Integer> deliveryOptionCounts = getAvailableDeliveryOptions(items);
        Map<String, List<String>> groupedItems = new HashMap<>();
        Set<String> assignedItems = new HashSet<>();

//        for (String item : items) {
//            groupItems(deliveryOptionCounts, groupedItems, item, this.deliveryOptions);
//        }
        for (String deliveryOption : deliveryOptionCounts.keySet()) {
            List<String> itemsForThisDelivery = new ArrayList<>();

            for (String item : items) {
                if (!assignedItems.contains(item) && deliveryOptions.get(item).contains(deliveryOption)) {
                    itemsForThisDelivery.add(item);
                    assignedItems.add(item);
                }
            }

            if (!itemsForThisDelivery.isEmpty()) {
                groupedItems.put(deliveryOption, itemsForThisDelivery);
            }
        }

        return groupedItems;
    }

}
