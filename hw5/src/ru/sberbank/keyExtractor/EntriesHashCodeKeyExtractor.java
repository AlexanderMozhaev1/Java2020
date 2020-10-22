package ru.sberbank.keyExtractor;

import ru.sberbank.Entries;
import ru.sberbank.account.Account;

public class EntriesHashCodeKeyExtractor implements KeyExtractor<Integer, Entries> {
    @Override
    public Integer extract(Entries entries) {
        if(entries == null)return null;
        return entries.hashCode();
    }
}
