package ca.jbrains.pos.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SellOneItemControllerTest {
    @Test
    void productFound() throws Exception {
        final Catalog catalog = Mockito.mock(Catalog.class);
        final Display display = Mockito.mock(Display.class);

        final Price matchingPrice = Price.cents(795);
        Mockito.when(catalog.findPrice_LegacyNullableVersion("::matching barcode::")).thenReturn(matchingPrice);

        new SellOneItemController(catalog, display).onBarcode("::matching barcode::");

        Mockito.verify(display).displayPrice(matchingPrice);
    }

    @Test
    void productNotFound() throws Exception {
        final Catalog catalog = Mockito.mock(Catalog.class);
        final Display display = Mockito.mock(Display.class);

        Mockito.when(catalog.findPrice_LegacyNullableVersion("::missing barcode::")).thenReturn(null);

        new SellOneItemController(catalog, display).onBarcode("::missing barcode::");

        Mockito.verify(display).displayProductNotFoundMessage("::missing barcode::");
    }

    @Test
    void emptyBarcode() throws Exception {
        final Catalog catalog = Mockito.mock(Catalog.class);
        final Display display = Mockito.mock(Display.class);

        new SellOneItemController(catalog, display).onBarcode("");

        Mockito.verify(display).displayScannedEmptyBarcodeMessage();
    }

    public interface Catalog {
        Price findPrice_LegacyNullableVersion(String barcode);
    }

    public interface Display {
        void displayPrice(Price price);

        void displayProductNotFoundMessage(String missingBarcode);

        void displayScannedEmptyBarcodeMessage();
    }

    public static class Price {
        public static Price cents(int centsValue) {
            return new Price();
        }

        @Override
        public String toString() {
            return "a Price";
        }
    }

    public static class SellOneItemController {
        private final Catalog catalog;
        private final Display display;

        public SellOneItemController(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.displayScannedEmptyBarcodeMessage();
                return;
            }
            
            final Price price = catalog.findPrice_LegacyNullableVersion(barcode);
            if (price == null) {
                display.displayProductNotFoundMessage(barcode);
            }
            else {
                display.displayPrice(price);
            }
        }
    }
}
