package ca.jbrains.pos.test;

import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    void productFound() throws Exception {
        final Price matchingPrice = Price.cents(795);

        final Catalog catalog = catalogWith("12345", matchingPrice);

        Assertions.assertEquals(Option.of(matchingPrice), catalog.findPrice("12345"));
    }

    protected abstract Catalog catalogWith(String barcode, Price matchingPrice);

    @Test
    void productNotFound() throws Exception {
        final Catalog catalog = catalogWithout("12345");
        
        Assertions.assertEquals(Option.none(), catalog.findPrice("12345"));
    }

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
