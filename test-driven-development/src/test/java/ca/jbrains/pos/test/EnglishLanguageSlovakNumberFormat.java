package ca.jbrains.pos.test;

import java.util.Locale;

public class EnglishLanguageSlovakNumberFormat {
    public static String formatPrice(Price price) {
        return String.format(Locale.forLanguageTag("sk"), "%.2f\u00a0€", price.euro());
    }
}
