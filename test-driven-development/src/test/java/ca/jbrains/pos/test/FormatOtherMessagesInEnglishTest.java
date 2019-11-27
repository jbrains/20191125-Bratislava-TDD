package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormatOtherMessagesInEnglishTest {

    private final SellOneItemControllerResponseMessageFormat sellOneItemControllerResponseMessageFormat = new EnglishLanguageSlovakNumberFormat();

    @Test
    void emptyBarcode() throws Exception {
        Assertions.assertEquals(
                "Scanning error: empty barcode",
                sellOneItemControllerResponseMessageFormat.formatScannedEmptyBarcodeMessage());
    }

    @Test
    void productNotFound() throws Exception {
        Assertions.assertEquals(
                "Product not found: ::missing barcode::",
                sellOneItemControllerResponseMessageFormat.formatProductNotFoundMessage("::missing barcode::"));
    }
}
