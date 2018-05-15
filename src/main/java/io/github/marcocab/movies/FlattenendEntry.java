package io.github.marcocab.movies;

import java.util.Map;

final class FlattenendEntry<K, V> implements Map.Entry<K, V> {
    private final K key;
    private V value;

    public FlattenendEntry(K key, V value) {
	this.key = key;
	this.value = value;
    }

    @Override
    public K getKey() {
	return key;
    }

    @Override
    public V getValue() {
	return value;
    }

    @Override
    public V setValue(V value) {
	V old = this.value;
	this.value = value;
	return old;
    }

    @Override
    public String toString() {
	return "MyEntry [key=" + key + ", value=" + value + "]";
    }

}