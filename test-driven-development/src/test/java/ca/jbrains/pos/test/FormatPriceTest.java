package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormatPriceTest {
    @Test
    void happyPath() throws Exception {
        Assertions.assertEquals("14,97\u00a0€", EnglishLanguageSlovakNumberFormat.formatPrice(Price.cents(1497)));
    }
}
