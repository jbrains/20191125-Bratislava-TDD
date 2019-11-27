package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DisplayMessagesToAWriterTest {
    private StringWriter canvas = new StringWriter();
    private final WriterDisplay writerDisplay = new WriterDisplay(canvas, new EnglishLanguageSlovakNumberFormat());

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

    // REFACTOR Move me to a generic text-processing library
    private static List<String> lines(String multilineText) {
        return Arrays.asList(multilineText.split("\\R"));
    }

    private static class WriterDisplay {
        private final PrintWriter out;
        private final SellOneItemControllerResponseMessageFormat sellOneItemControllerResponseMessageFormat;

        public WriterDisplay(StringWriter canvas, final SellOneItemControllerResponseMessageFormat sellOneItemControllerResponseMessageFormat) {
            out = new PrintWriter(canvas);
            this.sellOneItemControllerResponseMessageFormat = sellOneItemControllerResponseMessageFormat;
        }

        public void displayScannedEmptyBarcodeMessage() {
            out.println(sellOneItemControllerResponseMessageFormat.formatScannedEmptyBarcodeMessage());
        }

        public void displayProductNotFoundMessage(String barcodeNotFound) {
            out.println(sellOneItemControllerResponseMessageFormat.formatProductNotFoundMessage(barcodeNotFound));
        }

        public void displayPrice(Price price) {
            out.println(sellOneItemControllerResponseMessageFormat.formatPrice(price));
        }
    }
}
