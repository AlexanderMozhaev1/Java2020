package ru.sberbank;

import ru.sberbank.keyExtractor.KeyExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleEntitiesStorage<K, V>  implements BankEntitiesStorage<K, V> {
    private final Map<K, V> storage = new HashMap<>();
    private final KeyExtractor<K, V> keyExtractor;

    public SimpleEntitiesStorage(KeyExtractor<K, V> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public void save(V entity) {
        K key = keyExtractor.extract(entity);
        storage.put(key, entity);
    }

    @Override
    public void saveAll(List<V> entities) {
        for (V entity : entities) {
            K key = keyExtractor.extract(entity);
            storage.put(key, entity);
        }
    }

    @Override
    public V findByKey(K key) {
        return storage.get(key);
    }

    @Override
    public List<V> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteByKey(K key) {
        storage.remove(key);
    }

    @Override
    public void deleteAll(List<V> entities) {
        for (V entity : entities) {
            storage.remove(keyExtractor.extract(entity));
        }
    }
}
