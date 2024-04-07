package pl.sarata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BasketSplitterTest {
    private final Map<String, List<String>> deliveryOptions;
    public BasketSplitterTest(String absolutePathToConfigFile){
        deliveryOptions = getStringListMap(absolutePathToConfigFile);
    }

    private Map<String, List<String>> getStringListMap(String absolutePathToConfigFile) {
        final Map<String, List<String>> deliveryOptions;
        Map<String, List<String>> configMap = mapJsonToConfigMap(absolutePathToConfigFile);
        if (configMap.isEmpty()) {
            throw new IllegalArgumentException("Given config has no records");
        }
        deliveryOptions = configMap;
        return deliveryOptions;
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
    public Map<String, List<String>> split(List<String> items) {

        Map<String, List<String>> deliveryGroups = new HashMap<>();
        Set<String> assignedItems = new HashSet<>();

        while (assignedItems.size() < items.size()) {
            String selectedDeliveryMethod = null;
            Set<String> selectedItems = new HashSet<>();

            for (String item : items) {
                if (assignedItems.contains(item)) continue;

                for (String deliveryMethod : deliveryOptions.get(item)) {
                    Set<String> tempSelectedItems = items.stream()
                            .filter(it -> !assignedItems.contains(it) && deliveryOptions.get(it).contains(deliveryMethod))
                            .collect(Collectors.toSet());

                    if (tempSelectedItems.size() > selectedItems.size()) {
                        selectedItems = tempSelectedItems;
                        selectedDeliveryMethod = deliveryMethod;
                    }
                }
            }

            if (selectedDeliveryMethod != null) {
                deliveryGroups.put(selectedDeliveryMethod, new ArrayList<>(selectedItems));
                assignedItems.addAll(selectedItems);
            }
        }

        return deliveryGroups;
    }
}
