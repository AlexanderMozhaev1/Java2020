package ru.sberbank.keyExtractor;

import ru.sberbank.account.Account;

public interface KeyExtractor<K, V> {
    K extract(V entity);
}
