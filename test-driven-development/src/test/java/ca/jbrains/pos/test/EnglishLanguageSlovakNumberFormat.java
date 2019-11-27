package ca.jbrains.pos.test;

import java.util.Locale;

public class EnglishLanguageSlovakNumberFormat implements SellOneItemControllerResponseMessageFormat {
    @Override
    public String formatScannedEmptyBarcodeMessage() {
        return "Scanning error: empty barcode";
    }

    @Override
    public String formatProductNotFoundMessage(String barcodeNotFound) {
        return String.format("Product not found: %s", barcodeNotFound);
    }

    @Override
    public String formatPrice(Price price) {
        return String.format(Locale.forLanguageTag("sk"), "%.2f\u00a0€", price.euro());
    }
}