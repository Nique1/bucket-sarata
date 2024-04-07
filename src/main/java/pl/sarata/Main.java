package pl.sarata;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        BasketSplitterTest basketSplitterTest = new BasketSplitterTest("src/main/java/pl/sarata/configFile/config.json");
        Map<String, List<String>> splitIt = basketSplitterTest.split(List.of(
                "Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Table Cloth 54x72 White",
                "Flower - Daisies",
                "Fond - Chocolate",
                "Cookies - Englishbay Wht"));
        System.out.println(splitIt);

        Map<String, List<String>> splitIt2 = basketSplitterTest.split(List.of(
                "Fond - Chocolate",
                "Chocolate - Unsweetened",
                "Nut - Almond, Blanched, Whole",
                "Haggis",
                "Mushroom - Porcini Frozen",
                "Cake - Miini Cheesecake Cherry",
                "Sauce - Mint",
                "Longan",
                "Bag Clear 10 Lb",
                "Nantucket - Pomegranate Pear",
                "Puree - Strawberry",
                "Numi - Assorted Teas",
                "Apples - Spartan",
                "Garlic - Peeled",
                "Cabbage - Nappa",
                "Bagel - Whole White Sesame",
                "Tea - Apple Green Tea"));
        System.out.println(splitIt2);


    }
}
