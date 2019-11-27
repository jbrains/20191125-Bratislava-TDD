package ca.jbrains.pos.test;

import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {

    @Override
    protected Catalog catalogWith(final String barcode, Price matchingPrice) {
        return new InMemoryCatalog(
                new HashMap<>() {{
                    put(String.format("not %s", barcode), Price.cents(0));
                    put(String.format("certainly not %s", barcode), Price.cents(0));
                    put(String.format("definitely not %s, I swear it", barcode), Price.cents(0));
                    put(barcode, matchingPrice);
                }}
        );
    }

    @Override
    protected Catalog catalogWithout(String barcodeToAvoid) {
        return new InMemoryCatalog(
                new HashMap<>() {{
                    put(String.format("not %s", barcodeToAvoid), Price.cents(0));
                    put(String.format("certainly not %s", barcodeToAvoid), Price.cents(0));
                    put(String.format("definitely not %s, I swear it", barcodeToAvoid), Price.cents(0));
                }}
        );
    }

    private static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Option<Price> findPrice(String barcode) {
            return Option.of(pricesByBarcode.get(barcode));
        }
    }
}
