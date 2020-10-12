import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class EntriesTest {

    @Test
    void addEntry() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        Transaction transaction1 = transactionManager.createTransaction(100, account1, account2);
        Transaction transaction2 = transactionManager.createTransaction(100, account2, account1);
        Entry entry1 = new Entry(account1, transaction1, 100, LocalDateTime.now());
        Entry entry2 = new Entry(account2, transaction2, 100, LocalDateTime.now());
        Entries entries = new Entries();
        Collection<Entry> actual = new ArrayList<>();
        assertEquals(entries.from(LocalDate.now().MAX), actual);
        entries.addEntry(entry1);
        actual.add(entry1);
        assertEquals(entries.from(LocalDate.now().MAX), actual);
        entries.addEntry(entry2);
        actual.add(entry2);
        assertEquals(entries.from(LocalDate.now().MAX), actual);
    }

    @Test
    void from() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        Transaction transaction1 = new Transaction(1, 100,
                account1, null, false, false);
        Entries entries = new Entries();

        Entry entry1 = new Entry(account1, transaction1, 100, LocalDateTime.now());
        Entry entry2 = new Entry(account1, transaction1, 100, LocalDateTime.now().plusDays(1));
        Entry entry3 = new Entry(account1, transaction1, 100, LocalDateTime.now().plusDays(2));

        Collection<Entry> actual = new ArrayList<>();
        assertEquals(entries.from(LocalDate.now()), actual);
        entries.addEntry(entry1);
        actual.add(entry1);
        assertEquals(entries.from(LocalDate.now()), actual);
        entries.addEntry(entry2);
        actual.add(entry2);
        assertEquals(entries.from(LocalDate.now().plusDays(1)), actual);
        entries.addEntry(entry3);
        actual.add(entry3);
        assertEquals(entries.from(LocalDate.now().plusDays(2)), actual);
        assertEquals(entries.from(LocalDate.MAX), actual);
    }

    @Test
    void betweenDates() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        Transaction transaction1 = new Transaction(1, 100,
                account1, null, false, false);
        Entries entries = new Entries();

        Entry entry1 = new Entry(account1, transaction1, 100, LocalDateTime.now());
        Entry entry2 = new Entry(account1, transaction1, 100, LocalDateTime.now().plusDays(1));
        Entry entry3 = new Entry(account1, transaction1, 100, LocalDateTime.now().plusDays(2));
        Entry entry4 = new Entry(account1, transaction1, 100, LocalDateTime.now().plusDays(3));

        Collection<Entry> actual = new ArrayList<>();
        assertEquals(entries.betweenDates(LocalDate.MIN, LocalDate.now()), actual);
        entries.addEntry(entry1);
        actual.add(entry1);
        assertEquals(entries.betweenDates(LocalDate.now(), LocalDate.now().plusDays(1)), actual);
        entries.addEntry(entry2);
        actual.add(entry2);
        assertEquals(entries.betweenDates(LocalDate.now(), LocalDate.now().plusDays(2)), actual);
        entries.addEntry(entry3);
        actual.add(entry3);
        assertEquals(entries.betweenDates(LocalDate.now(), LocalDate.now().plusDays(3)), actual);
        entries.addEntry(entry4);
        actual.add(entry4);

        assertEquals(entries.betweenDates(LocalDate.MIN, LocalDate.MAX), actual);
        assertEquals(entries.betweenDates(LocalDate.now(), LocalDate.MAX), actual);

        entries = new Entries();
        actual = new ArrayList<>();
        entries.addEntry(entry2);
        entries.addEntry(entry3);
        actual.add(entry2);
        actual.add(entry3);
        assertEquals(entries.betweenDates(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)), actual);
    }

    @Test
    void last() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        Transaction transaction1 = transactionManager.createTransaction(100, account1, account2);
        Transaction transaction2 = transactionManager.createTransaction(100, account2, account1);
        Entry entry1 = new Entry(account1, transaction1, 100, LocalDateTime.now());
        Entry entry2 = new Entry(account2, transaction2, 100, LocalDateTime.now());
        Entries entries = new Entries();
        entries.addEntry(entry1);
        assertEquals(entries.last(), entry1);
        entries.addEntry(entry2);
        assertEquals(entries.last(), entry2);
    }
}