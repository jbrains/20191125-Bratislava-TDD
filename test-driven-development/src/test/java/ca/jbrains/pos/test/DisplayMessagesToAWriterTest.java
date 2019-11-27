package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class DisplayMessagesToAWriterTest {

    private StringWriter canvas = new StringWriter();
    private final WriterDisplay writerDisplay = new WriterDisplay(canvas);

    @Test
    void emptyBarcode() throws Exception {
        writerDisplay.displayScannedEmptyBarcodeMessage();

        Assertions.assertEquals(
                Arrays.asList("Scanning error: empty barcode"),
                lines(canvas.toString()));
    }

    @Test
    void productNotFound() throws Exception {
        writerDisplay.displayProductNotFoundMessage("::missing barcode::");

        Assertions.assertEquals(
                Arrays.asList("Product not found: ::missing barcode::"),
                lines(canvas.toString()));
    }

    @Test
    void price() throws Exception {
        writerDisplay.displayPrice(Price.cents(1795));

        Assertions.assertEquals(
                Arrays.asList("17,95\u00a0€"),
                lines(canvas.toString()));
    }

    @Test
    void severalMessages() throws Exception {
        Stream.of("::barcode 1::", "::barcode 2::", "::barcode 3::").forEach(
                writerDisplay::displayProductNotFoundMessage
        );
        writerDisplay.displayScannedEmptyBarcodeMessage();
        writerDisplay.displayPrice(Price.cents(2499));

        Assertions.assertEquals(
                Arrays.asList(
                        "Product not found: ::barcode 1::",
                        "Product not found: ::barcode 2::",
                        "Product not found: ::barcode 3::",
                        "Scanning error: empty barcode",
                        "24,99\u00a0€"
                ),
                lines(canvas.toString())
        );
    }

    public static class FormatPriceTest {
        @Test
        void happyPath() throws Exception {
            Assertions.assertEquals("14,97\u00a0€", WriterDisplay.formatPrice(Price.cents(1497)));
        }
    }
    // REFACTOR Move me to a generic text-processing library
    private static List<String> lines(String multilineText) {
        return Arrays.asList(multilineText.split("\\R"));
    }

    private static class WriterDisplay {

        private final PrintWriter out;

        public WriterDisplay(StringWriter canvas) {
            out = new PrintWriter(canvas);
        }

        public void displayScannedEmptyBarcodeMessage() {
            out.println("Scanning error: empty barcode");
        }

        public void displayProductNotFoundMessage(String barcodeNotFound) {
            out.println(String.format("Product not found: %s", barcodeNotFound));
        }

        public void displayPrice(Price price) {
            out.println(formatPrice(price));
        }

        public static String formatPrice(Price price) {
            return String.format(Locale.forLanguageTag("sk"), "%.2f\u00a0€", price.euro());
        }
    }
}
