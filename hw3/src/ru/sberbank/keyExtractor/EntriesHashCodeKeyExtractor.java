package ru.sberbank.keyExtractor;

import ru.sberbank.Entries;

public class EntriesHashCodeKeyExtractor implements KeyExtractor<Integer, Entries> {
    @Override
    public Integer extract(Entries entries) {
        if(entries == null)return null;
        return entries.hashCode();
    }
}
