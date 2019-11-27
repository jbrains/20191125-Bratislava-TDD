package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormatOtherMessagesInEnglishTest {

    private final EnglishLanguageSlovakNumberFormat englishLanguageSlovakNumberFormat = new EnglishLanguageSlovakNumberFormat();

    @Test
    void emptyBarcode() throws Exception {
        Assertions.assertEquals(
                "Scanning error: empty barcode",
                englishLanguageSlovakNumberFormat.formatScannedEmptyBarcodeMessage());
    }
}
