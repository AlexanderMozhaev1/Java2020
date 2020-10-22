package ru.sberbank.keyExtractor;

import ru.sberbank.Entry;
import ru.sberbank.account.Account;

public class EntryHashCodeKeyExtractor implements KeyExtractor<Integer, Entry> {
    @Override
    public Integer extract(Entry entry) {
        if(entry == null)return null;
        return entry.hashCode();
    }
}
