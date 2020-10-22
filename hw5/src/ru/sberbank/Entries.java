package ru.sberbank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Entries {
    TreeMap<LocalDateTime, ArrayList<Entry>> entries;
    private Entry lastEntry;

    public Entries() {
        this.entries = new TreeMap<>();
    }

    public void addEntry(Entry entry) {
        lastEntry = entry;
        ArrayList<Entry> transactionSet = entries.get(entry.getTime());
        if (transactionSet != null) {
            transactionSet.add(entry);
        } else {
            ArrayList<Entry> newTransactionSet = new ArrayList<>();
            newTransactionSet.add(entry);
            entries.put(entry.getTime(), newTransactionSet);
        }
    }

    public Collection<Entry> from(LocalDate date) {
        ArrayList<Entry> res = new ArrayList<>();
        LocalDateTime toKey = LocalDateTime.MAX;
        if(!date.equals(LocalDate.MAX)){
            toKey = date.atStartOfDay().plusDays(1);
        }
        entries.subMap(LocalDateTime.MIN, toKey).forEach((k, v) -> res.addAll(v));
        return res;
    }

    public Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        ArrayList<Entry> res = new ArrayList<>();
        LocalDateTime fromKey = from.atStartOfDay();
        LocalDateTime toKey = to.atStartOfDay();
        entries.subMap(fromKey, toKey).forEach((k, v) -> res.addAll(v));
        return res;
    }

    public Entry last() {
        return lastEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entries entries1 = (Entries) o;
        return entries.equals(entries1.entries) &&
                lastEntry.equals(entries1.lastEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entries, lastEntry);
    }
}
