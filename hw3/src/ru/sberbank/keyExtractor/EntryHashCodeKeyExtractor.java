package ru.sberbank.keyExtractor;

import ru.sberbank.Entry;

public class EntryHashCodeKeyExtractor implements KeyExtractor<Integer, Entry> {
    @Override
    public Integer extract(Entry entry) {
        if(entry == null)return null;
        return entry.hashCode();
    }
}
