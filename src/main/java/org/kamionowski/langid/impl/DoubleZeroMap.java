package org.kamionowski.langid.impl;

import java.util.Collection;

/**
 * User: soldier
 * Date: 23.03.13
 * Time: 00:48
 */
class DoubleZeroMap<K> extends ZeroMap<K, Double> {
    public DoubleZeroMap() {
    }

    public DoubleZeroMap(Collection<K> keys) {
        super(keys);
    }

    @Override
    protected Double zero() {
        return 0.0;
    }


    @Override
    protected Double one() {
        return 1.0;
    }

    @Override
    protected Double add(Double aDouble, Double x) {
        return aDouble + x;
    }
}
