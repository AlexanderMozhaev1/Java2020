package ru.sberbank.account;

import ru.sberbank.Entry;

import java.time.LocalDate;
import java.util.Collection;

public interface Account {
    public double balanceOn(LocalDate date);
    public void addEntry(Entry entry);
    public Collection<Entry> history(LocalDate from, LocalDate to);
}
