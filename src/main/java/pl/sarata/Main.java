package pl.sarata;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        BasketSplitter basketSplitter = new BasketSplitter("src/main/java/pl/sarata/configFile/defaultConfig.json");

        Map<String, List<String>> split = basketSplitter.split(List.of("Steak (300g)",
                "Carrots (1kg)",
                "AA Battery (4 Pcs.)",
                "Espresso Machine",
                "Garden Chair"));
        System.out.println(split);

    }
}
