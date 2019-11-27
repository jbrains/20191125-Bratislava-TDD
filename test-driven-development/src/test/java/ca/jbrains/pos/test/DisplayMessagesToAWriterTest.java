package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DisplayMessagesToAWriterTest {
    @Test
    void emptyBarcode() throws Exception {
        final StringWriter canvas = new StringWriter();
        new WriterDisplay(canvas).displayScannedEmptyBarcodeMessage();
        Assertions.assertEquals("Scanning error: empty barcode", canvas.toString());
    }

    private class WriterDisplay {

        private final PrintWriter out;

        public WriterDisplay(StringWriter canvas) {
            out = new PrintWriter(canvas);
        }

        public void displayScannedEmptyBarcodeMessage() {
            out.println("Scanning error: empty barcode");
        }
    }
}
