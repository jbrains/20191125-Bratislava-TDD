package ca.jbrains.pos.test;

import net.jqwik.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormatPriceTest {
    @Data
    Iterable<Tuple.Tuple2<Price, String>> examples() {
        return Table.of(
                Tuple.of(
                        Price.cents(1497), "14,97"
                )
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
