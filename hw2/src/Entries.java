import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Entries {
    TreeMap<LocalDateTime, ArrayList<Entry>> entries;
    private Entry lastEntry;

    public Entries() {
        this.entries = new TreeMap<>();
    }

    void addEntry(Entry entry) {
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

    Collection<Entry> from(LocalDate date) {
        ArrayList<Entry> res = new ArrayList<>();
        LocalDateTime fromKey = date.atStartOfDay();
        LocalDateTime toKey = fromKey.plusDays(1);
        entries.subMap(fromKey, toKey).forEach((k, v)-> res.addAll(v));
        return res;
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        ArrayList<Entry> res = new ArrayList<>();
        LocalDateTime fromKey = from.atStartOfDay();
        LocalDateTime toKey = to.atStartOfDay();
        entries.subMap(fromKey, toKey).forEach((k, v)-> res.addAll(v));
        return res;
    }

    Entry last() {
        return lastEntry;
    }
}
