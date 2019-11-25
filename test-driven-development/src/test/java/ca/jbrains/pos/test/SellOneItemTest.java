package ca.jbrains.pos.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SellOneItemTest {
    @Test
    void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<String, String>() {{
            put("12345", "7,95\u00A0€");
            put("23456", "12,50\u00A0€");
        }});

        sale.onBarcode("12345");

        Assertions.assertEquals("7,95\u00A0€", display.getText());
    }

    @Test
    void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<String, String>() {{
            put("12345", "7,95\u00A0€");
            put("23456", "12,50\u00A0€");
        }});

        sale.onBarcode("23456");

        Assertions.assertEquals("12,50\u00A0€", display.getText());
    }

    @Test
    void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<String, String>() {{
            put("12345", "7,95\u00A0€");
            put("23456", "12,50\u00A0€");
        }});

        sale.onBarcode("99999");

        Assertions.assertEquals("Product not found: 99999", display.getText());
    }

    @Test
    void emptyBarcode() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<String, String>() {{
            put("12345", "7,95\u00A0€");
            put("23456", "12,50\u00A0€");
        }});

        sale.onBarcode("");

        Assertions.assertEquals("Scanning error: empty barcode", display.getText());
    }

    public static class Sale {
        private Display display;
        private final Map<String, String> pricesByBarcode;

        public Sale(Display display, final Map<String, String> pricesByBarcode) {
            this.display = display;
            this.pricesByBarcode = pricesByBarcode;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode))
                display.setText("Scanning error: empty barcode");
            else {
                if (pricesByBarcode.containsKey(barcode))
                    display.setText(pricesByBarcode.get(barcode));
                else
                    display.setText(String.format("Product not found: %s", barcode));
            }
        }
    }

    public static class Display {
        private String text;

        public String getText() {
            return text;
        }

        private void setText(final String text) {
            this.text = text;
        }
    }
}
