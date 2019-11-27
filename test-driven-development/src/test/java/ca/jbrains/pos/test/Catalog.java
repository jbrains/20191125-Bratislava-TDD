package ca.jbrains.pos.test;

import io.vavr.control.Option;

public interface Catalog {
    Option<Price> findPrice(String barcode);
}
