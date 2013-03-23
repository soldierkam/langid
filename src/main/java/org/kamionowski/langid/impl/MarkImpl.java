package org.kamionowski.langid.impl;

import org.kamionowski.langid.Mark;

/**
 * User: soldier
 * Date: 23.03.13
 * Time: 13:37
 */
class MarkImpl implements Mark, Comparable<MarkImpl> {

    private final String lang;
    private final double value;

    public MarkImpl(String lang, double value) {
        this.lang = lang;
        this.value = value;
    }

    @Override
    public String lang() {
        return lang;
    }

    @Override
    public double value() {
        return value;
    }

    @Override
    public int compareTo(MarkImpl o) {
        return Double.compare(o.value, this.value);
    }
}
