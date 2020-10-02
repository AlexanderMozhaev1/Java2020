import javafx.collections.transformation.SortedList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.SortedMap;

public class Entries {
    PriorityQueue<Entry> entries;

    public Entries() {
        this.entries = new PriorityQueue<>((e1, e2)-> e1.getTime().compareTo(e2.getTime()));
    }

    void addEntry(Entry entry) {
        entries.add(entry);
    }

    Collection<Entry> from(LocalDate date) {
        entries.fi
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        // write your code here
    }

    Entry last() {
        // write your code here
    }
}
