package ru.sberbank.keyExtractor;

public interface KeyExtractor<K, V> {
    K extract(V entity);
}
