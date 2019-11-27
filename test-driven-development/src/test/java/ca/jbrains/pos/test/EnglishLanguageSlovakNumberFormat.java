package ca.jbrains.pos.test;

import java.util.Locale;

public class EnglishLanguageSlovakNumberFormat {
    public String formatScannedEmptyBarcodeMessage() {
        return "Scanning error: empty barcode";
    }

    public String formatProductNotFoundMessage(String barcodeNotFound) {
        return String.format("Product not found: %s", barcodeNotFound);
    }

    public String formatPrice(Price price) {
        return String.format(Locale.forLanguageTag("sk"), "%.2f\u00a0€", price.euro());
    }
}