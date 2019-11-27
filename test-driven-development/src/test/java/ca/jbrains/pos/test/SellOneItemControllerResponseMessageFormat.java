package ca.jbrains.pos.test;

public interface SellOneItemControllerResponseMessageFormat {
    String formatScannedEmptyBarcodeMessage();

    String formatProductNotFoundMessage(String barcodeNotFound);

    String formatPrice(Price price);
}
