package org.kamionowski.langid.impl;

import org.kamionowski.langid.Model;
import org.kamionowski.langid.ModelReader;

import java.io.IOException;

/**
 * User: soldier
 * Date: 22.03.13
 * Time: 23:36
 */
class DefaultModelReader implements ModelReader {
    @Override
    public Model read(String country) throws IOException {
        return new ModelImpl(this.getClass().getResourceAsStream(country + "-3grams.txt"));
    }
}
