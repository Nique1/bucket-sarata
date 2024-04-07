package pl.sarata;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterTesting {
    private static BasketSplitter basketSplitter;

    @BeforeAll
    public static void setUp() {
        basketSplitter = new BasketSplitter("src/test/java/pl/sarata/resource/config.json");
    }

    @Test
    void shouldReturnTrueIfBasketIsEmpty() {

        List<String> items = new ArrayList<>();
        assertTrue(basketSplitter.split(items).isEmpty());

    }


    @Test
    void shouldReturnGroupedBasketOne() {

        List<String> items = Arrays.asList("Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Table Cloth 54x72 White",
                "Flower - Daisies",
                "Fond - Chocolate",
                "Cookies - Englishbay Wht"
        );

        Map<String, List<String>> actualOutput = basketSplitter.split(items);

        Map<String, List<String>> expectedOutput = Map.of(
                "Courier", Arrays.asList("Flower - Daisies", "Table Cloth 54x72 White", "Cookies - Englishbay Wht", "Tart - Raisin And Pecan", "Cocoa Butter"),
                "Pick-up point", Arrays.asList("Fond - Chocolate"));

        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void shouldReturnGroupedBasketTwo() {

        List<String> items = Arrays.asList("Fond - Chocolate",
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
                "Tea - Apple Green Tea"
        );
        Map<String, List<String>> actualOutput = basketSplitter.split(items);

        Map<String, List<String>> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(
                "Express Collection", Arrays.asList(
                        "Chocolate - Unsweetened", "Fond - Chocolate",
                        "Haggis", "Longan", "Apples - Spartan", "Tea - Apple Green Tea",
                        "Nut - Almond, Blanched, Whole", "Bag Clear 10 Lb",
                        "Mushroom - Porcini Frozen", "Bagel - Whole White Sesame", "Cabbage - Nappa",
                        "Nantucket - Pomegranate Pear", "Puree - Strawberry"
                )
        );
        expectedOutput.put(
                "Same day delivery", Arrays.asList(
                        "Sauce - Mint", "Numi - Assorted Teas", "Garlic - Peeled"
                )
        );
        expectedOutput.put(
                "Courier", Arrays.asList(
                        "Cake - Miini Cheesecake Cherry"
                )
        );

        assertEquals(expectedOutput, actualOutput);

    }

}