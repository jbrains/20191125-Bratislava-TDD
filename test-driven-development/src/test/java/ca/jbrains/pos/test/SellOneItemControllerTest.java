package ca.jbrains.pos.test;

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SellOneItemControllerTest {
    @Test
    void productFound() throws Exception {
        final Catalog catalog = Mockito.mock(Catalog.class);
        final SellOneItemControllerResponseMessageFormat sellOneItemControllerResponseMessageFormat = Mockito.mock(SellOneItemControllerResponseMessageFormat.class);

        final Price matchingPrice = Price.cents(795);
        Mockito.when(catalog.findPrice("::matching barcode::")).thenReturn(Option.of(matchingPrice));

        new SellOneItemController(catalog, sellOneItemControllerResponseMessageFormat).onBarcode("::matching barcode::");

        Mockito.verify(sellOneItemControllerResponseMessageFormat).formatPrice(matchingPrice);
    }

    @Test
    void productNotFound() throws Exception {
        final Catalog catalog = Mockito.mock(Catalog.class);
        final SellOneItemControllerResponseMessageFormat sellOneItemControllerResponseMessageFormat = Mockito.mock(SellOneItemControllerResponseMessageFormat.class);

        Mockito.when(catalog.findPrice("::missing barcode::")).thenReturn(Option.none());

        new SellOneItemController(catalog, sellOneItemControllerResponseMessageFormat).onBarcode("::missing barcode::");

        Mockito.verify(sellOneItemControllerResponseMessageFormat).formatProductNotFoundMessage("::missing barcode::");
    }

    @Test
    void emptyBarcode() throws Exception {
        final Catalog catalog = Mockito.mock(Catalog.class);
        final SellOneItemControllerResponseMessageFormat sellOneItemControllerResponseMessageFormat = Mockito.mock(SellOneItemControllerResponseMessageFormat.class);

        new SellOneItemController(catalog, sellOneItemControllerResponseMessageFormat).onBarcode("");

        Mockito.verify(sellOneItemControllerResponseMessageFormat).formatScannedEmptyBarcodeMessage();
    }

    public static class SellOneItemController {
        private final Catalog catalog;
        private final SellOneItemControllerResponseMessageFormat sellOneItemControllerResponseMessageFormat;

        public SellOneItemController(Catalog catalog, SellOneItemControllerResponseMessageFormat sellOneItemControllerResponseMessageFormat) {
            this.catalog = catalog;
            this.sellOneItemControllerResponseMessageFormat = sellOneItemControllerResponseMessageFormat;
        }

        public String onBarcode(String barcode) {
            final String responseMessage;
            if ("".equals(barcode)) {
                responseMessage = sellOneItemControllerResponseMessageFormat.formatScannedEmptyBarcodeMessage();
            }
            else {
                final Option<Price> price = catalog.findPrice(barcode);
                if (price.isEmpty()) {
                    responseMessage = sellOneItemControllerResponseMessageFormat.formatProductNotFoundMessage(barcode);
                }
                else {
                    responseMessage = sellOneItemControllerResponseMessageFormat.formatPrice(price.get());
                }
            }
            return responseMessage;
        }
    }
}
