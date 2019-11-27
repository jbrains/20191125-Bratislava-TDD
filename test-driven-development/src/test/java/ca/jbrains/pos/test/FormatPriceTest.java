package ca.jbrains.pos.test;

import net.jqwik.api.*;
import net.jqwik.api.Tuple.Tuple2;
import org.junit.jupiter.api.Assertions;

public class FormatPriceTest {
    @Data
    Iterable<Tuple2<Price, String>> examples() {
        return Table.of(
                Tuple.of(Price.cents(1497), "14,97"),
                Tuple.of(Price.cents(0), "0,00"),
                Tuple.of(Price.cents(1), "0,01"),
                Tuple.of(Price.cents(40), "0,40"),
                Tuple.of(Price.cents(68), "0,68"),
                Tuple.of(Price.cents(8237611), "82376,11")
        );
    }

    @Property
    @FromData("examples")
    void happyPath(@ForAll Price price, @ForAll String expectedFormat) throws Exception {
        Assertions.assertEquals(
                String.format("%s\u00a0€", expectedFormat),
                EnglishLanguageSlovakNumberFormat.formatPrice(price));
    }
}
