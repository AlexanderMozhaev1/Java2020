package ru.sberbank.account;

import org.junit.jupiter.api.Test;
import ru.sberbank.Transaction;
import ru.sberbank.TransactionManager;
import ru.sberbank.Entry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DebitCardTest {

    @Test
    void withdrawOverdraft() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        debitCard1.addCash(100);
        assertFalse(debitCard1.withdraw(150, debitCard2));
        assertEquals(debitCard1.balanceOn(LocalDate.now()), 100);
        assertEquals(debitCard2.balanceOn(LocalDate.now()), 0);
    }

    @Test
    void withdrawNullBalance() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        assertFalse(debitCard1.withdraw(150, debitCard2));
        assertEquals(debitCard1.balanceOn(LocalDate.now()), 0);
        assertEquals(debitCard2.balanceOn(LocalDate.now()), 0);
    }


    @Test
    void withdraw() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        debitCard1.addCash(100);
        assertTrue(debitCard1.withdraw(100, debitCard2));
        assertEquals(debitCard1.balanceOn(LocalDate.now()), 0);
        assertEquals(debitCard2.balanceOn(LocalDate.now()), 100);
    }

    @Test
    void withdrawCashOverdraft() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        debitCard1.addCash(100);
        assertFalse(debitCard1.withdrawCash(150));
        assertEquals(debitCard1.balanceOn(LocalDate.now()), 100);
    }

    @Test
    void withdrawCashNullBalance() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        assertFalse(debitCard1.withdrawCash(150));
        assertEquals(debitCard1.balanceOn(LocalDate.now()), 0);
    }


    @Test
    void withdrawCash() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        debitCard1.addCash(100);
        assertTrue(debitCard1.withdrawCash(100));
        assertEquals(debitCard1.balanceOn(LocalDate.now()), 0);
    }

    @Test
    void addCash() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard = new DebitCard(1, transactionManager);
        assertFalse(debitCard.addCash(0));
        assertFalse(debitCard.addCash(-100));
        assertTrue(debitCard.addCash(100));
        assertEquals(debitCard.balanceOn(LocalDate.now()), 100);
    }

    @Test
    void history() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        Transaction transaction1 = new Transaction(1, 100,
                debitCard1, null, false, false);

        Entry entry1 = new Entry(debitCard1, transaction1, 100, LocalDateTime.now());
        Entry entry2 = new Entry(debitCard1, transaction1, 100, LocalDateTime.now().plusDays(1));
        Entry entry3 = new Entry(debitCard1, transaction1, 100, LocalDateTime.now().plusDays(2));
        Entry entry4 = new Entry(debitCard1, transaction1, 100, LocalDateTime.now().plusDays(3));
        debitCard1.addEntry(entry1);
        debitCard1.addEntry(entry2);
        debitCard1.addEntry(entry3);
        debitCard1.addEntry(entry4);

        ArrayList<Entry> entries = new ArrayList<>();
        assertEquals(entries, debitCard1.history(LocalDate.MIN, LocalDate.now()));
        entries.add(entry1);
        assertEquals(entries, debitCard1.history(LocalDate.now(), LocalDate.now().plusDays(1)));
        entries.add(entry2);
        assertEquals(entries, debitCard1.history(LocalDate.now(), LocalDate.now().plusDays(2)));
        entries.add(entry3);
        assertEquals(entries, debitCard1.history(LocalDate.now(), LocalDate.now().plusDays(3)));
        entries.add(entry4);

        assertEquals(entries, debitCard1.history(LocalDate.MIN, LocalDate.MAX));
        assertEquals(entries, debitCard1.history(LocalDate.now(), LocalDate.MAX));

        entries = new ArrayList<>();
        entries.add(entry2);
        entries.add(entry3);
        assertEquals(entries, debitCard1.history(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
    }

    @Test
    void balanceOn() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard = new DebitCard(1, transactionManager);
        assertEquals(debitCard.balanceOn(LocalDate.now()), 0);
        debitCard.addCash(100);
        assertEquals(debitCard.balanceOn(LocalDate.now()), 100);
    }

    @Test
    void rollbackLastTransaction() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        debitCard1.addCash(100);
        assertTrue(debitCard1.withdraw(100, debitCard2));
        assertEquals(debitCard1.balanceOn(LocalDate.now()), 0);
        assertEquals(debitCard2.balanceOn(LocalDate.now()), 100);
        debitCard1.rollbackLastTransaction();
        assertEquals(debitCard1.balanceOn(LocalDate.now()), 100);
        assertEquals(debitCard2.balanceOn(LocalDate.now()), 0);
    }

    @Test
    void addEntry() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard = new DebitCard(1, transactionManager);
        Transaction transaction = new Transaction(1, 100,
                debitCard, null, false, false);
        Entry entry = new Entry(debitCard, transaction, 100, LocalDateTime.now());
        debitCard.addEntry(entry);
        assertTrue(debitCard.history(LocalDate.MIN, LocalDate.MAX).contains(entry));
    }
}