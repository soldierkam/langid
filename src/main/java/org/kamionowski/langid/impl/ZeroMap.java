package org.kamionowski.langid.impl;

import java.util.Collection;
import java.util.HashMap;

/**
 * User: soldier
 * Date: 23.03.13
 * Time: 00:28
 */
abstract class ZeroMap<K, V> extends HashMap<K, V> {

    protected ZeroMap() {
    }

    public ZeroMap(Collection<K> keys) {
        V zero = zero();
        for (K k : keys) {
            this.put(k, zero());
        }
    }

    protected abstract V zero();

    protected abstract V one();

    protected abstract V add(V v, V x);

    public void inc(K key, V step) {
        put(key, add(get(key), step));
    }

    public void inc(K key) {
        inc(key, one());
    }

    @Override
    public V get(Object key) {
        if (!this.containsKey(key)) {
            this.put((K) key, zero());
        }
        return super.get(key);
    }

    public V valuesSum() {
        V res = zero();
        for (V v : values()) {
            res = add(v, res);
        }
        return res;
    }
}
