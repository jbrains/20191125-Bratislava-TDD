package ca.jbrains.pos.test;

import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    void productFound() throws Exception {
        final SellOneItemControllerTest.Price matchingPrice = SellOneItemControllerTest.Price.cents(795);
        Assertions.assertEquals(Option.of(matchingPrice), new InMemoryCatalog(
                new HashMap<>() {{
                    put("12345", matchingPrice);
                }}
        ).findPrice("12345"));
    }

    private static class InMemoryCatalog {
        private final Map<String, SellOneItemControllerTest.Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, SellOneItemControllerTest.Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Option<SellOneItemControllerTest.Price> findPrice(String barcode) {
            return Option.of(pricesByBarcode.get(barcode));
        }
    }
}
