package ru.sberbank;

import java.util.List;

public interface BankEntitiesStorage<K, V> {
    void save(V entity);

    void saveAll(List<V> entities);

    V findByKey(K key);

    List<V> findAll();

    void deleteByKey(K key);

    void deleteAll(List<V> entities);
}
