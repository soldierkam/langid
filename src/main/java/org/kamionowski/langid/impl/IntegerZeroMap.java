package org.kamionowski.langid.impl;

import java.util.Collection;

/**
 * User: soldier
 * Date: 23.03.13
 * Time: 00:49
 */
class IntegerZeroMap<K> extends ZeroMap<K, Integer> {

    public IntegerZeroMap() {
    }

    public IntegerZeroMap(Collection<K> keys) {
        super(keys);
    }

    @Override
    protected Integer zero() {
        return 0;
    }

    @Override
    protected Integer one() {
        return 1;
    }

    @Override
    protected Integer add(Integer aDouble, Integer x) {
        return aDouble + x;
    }
}
