import org.junit.jupiter.api.Test;
import pl.sarata.BasketSplitter;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BasketSplitterTest {

    @Test
    void shouldReturnGroupedBasket1() {
        BasketSplitter splitter = new BasketSplitter("src/test/java/pl/sarata/resource/config.json");
        List<String> bucket = Arrays.asList("Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Table Cloth 54x72 White",
                "Flower - Daisies",
                "Fond - Chocolate",
                "Cookies - Englishbay Wht");

        Map<String, List<String>> groupedItems = splitter.split(bucket);

        Map<String, List<String>> expected = Map.of(
                "Pick-up point", Arrays.asList("Fond - Chocolate"),
                "Courier", Arrays.asList("Flower - Daisies, Table Cloth 54x72 White, Cookies - Englishbay Wht, Tart - Raisin And Pecan, Cocoa Butter")
        );

       assertEquals(expected, groupedItems);
    }

    @Test
    void shouldReturnGroupedBasket2(){
        BasketSplitter splitter = new BasketSplitter("src/test/java/pl/sarata/resource/config.json");
        List<String> bucket = Arrays.asList("Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Table Cloth 54x72 White",
                "Flower - Daisies",
                "Fond - Chocolate",
                "Cookies - Englishbay Wht");

        Map<String, List<String>> groupedItems = splitter.split(bucket);

        Map<String, List<String>> expected = Map.of(
                "Pick-up point", Arrays.asList("Fond - Chocolate"),
                "Courier", Arrays.asList("Flower - Daisies, Table Cloth 54x72 White, Cookies - Englishbay Wht, Tart - Raisin And Pecan, Cocoa Butter")
        );

        assertEquals(expected, groupedItems);
    }

    @Test
    void split() {
    }

    @Test
    void shouldReturnTrueIfBasketIsEmpty(){
        BasketSplitter splitter = new BasketSplitter("src/test/java/pl/sarata/resource/config.json");
        List<String> items = new ArrayList<>();
        assertTrue(splitter.split(items).isEmpty());
    }
}