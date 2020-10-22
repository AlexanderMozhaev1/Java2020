package ru.sberbank.account;

import org.junit.jupiter.api.Test;
import ru.sberbank.Entry;
import ru.sberbank.Transaction;
import ru.sberbank.TransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BonusAccountTest {

    @Test
    void balanceOn() {
        TransactionManager transactionManager = new TransactionManager();
        BonusAccount bonusAccount = new BonusAccount(1, transactionManager, 0.1);
        assertEquals(bonusAccount.balanceOn(LocalDate.now()), 0);
        bonusAccount.addCash(100);
        assertEquals(bonusAccount.balanceOn(LocalDate.now()), 100);
    }

    @Test
    void addEntry() {
        TransactionManager transactionManager = new TransactionManager();
        BonusAccount bonusAccount = new BonusAccount(1, transactionManager, 0.1);
        Transaction transaction = new Transaction(1, 100,
                bonusAccount, null, true, false);
        Entry entry = new Entry(bonusAccount, transaction, 100, LocalDateTime.now());
        bonusAccount.addEntry(entry);
        assertEquals(bonusAccount.balanceOn(LocalDate.MAX), 100);
    }

    @Test
    void withdrawOverdraft() {
        TransactionManager transactionManager = new TransactionManager();
        BonusAccount bonusAccount1 = new BonusAccount(1, transactionManager, 0.1);
        BonusAccount bonusAccount2 = new BonusAccount(2, transactionManager, 0.1);
        bonusAccount1.addCash(100);
        assertFalse(bonusAccount1.withdraw(150, bonusAccount2));
        assertEquals(bonusAccount1.balanceOn(LocalDate.now()), 100);
        assertEquals(bonusAccount2.balanceOn(LocalDate.now()), 0);
    }

    @Test
    void withdrawNullBalance() {
        TransactionManager transactionManager = new TransactionManager();
        BonusAccount bonusAccount1 = new BonusAccount(1, transactionManager, 0.1);
        BonusAccount bonusAccount2 = new BonusAccount(2, transactionManager, 0.1);
        assertFalse(bonusAccount1.withdraw(150, bonusAccount2));
        assertEquals(bonusAccount1.balanceOn(LocalDate.now()), 0);
        assertEquals(bonusAccount2.balanceOn(LocalDate.now()), 0);
    }


    @Test
    void withdraw() {
        TransactionManager transactionManager = new TransactionManager();
        BonusAccount bonusAccount1 = new BonusAccount(1, transactionManager, 0.1);
        BonusAccount bonusAccount2 = new BonusAccount(2, transactionManager, 0.1);
        bonusAccount1.addCash(100);
        assertTrue(bonusAccount1.withdraw(100, bonusAccount2));
        assertEquals(bonusAccount1.balanceOn(LocalDate.now()), 10);
        assertEquals(bonusAccount2.balanceOn(LocalDate.now()), 100);
    }

    @Test
    void history() {
        TransactionManager transactionManager = new TransactionManager();
        BonusAccount bonusAccount = new BonusAccount(1, transactionManager, 0.1);
        Transaction transaction1 = new Transaction(1, 100,
                bonusAccount, null, false, false);

        Entry entry1 = new Entry(bonusAccount, transaction1, 100, LocalDateTime.now());
        Entry entry2 = new Entry(bonusAccount, transaction1, 100, LocalDateTime.now().plusDays(1));
        Entry entry3 = new Entry(bonusAccount, transaction1, 100, LocalDateTime.now().plusDays(2));
        Entry entry4 = new Entry(bonusAccount, transaction1, 100, LocalDateTime.now().plusDays(3));
        bonusAccount.addEntry(entry1);
        bonusAccount.addEntry(entry2);
        bonusAccount.addEntry(entry3);
        bonusAccount.addEntry(entry4);

        ArrayList<Entry> entries = new ArrayList<>();
        assertEquals(entries, bonusAccount.history(LocalDate.MIN, LocalDate.now()));
        entries.add(entry1);
        assertEquals(entries, bonusAccount.history(LocalDate.now(), LocalDate.now().plusDays(1)));
        entries.add(entry2);
        assertEquals(entries, bonusAccount.history(LocalDate.now(), LocalDate.now().plusDays(2)));
        entries.add(entry3);
        assertEquals(entries, bonusAccount.history(LocalDate.now(), LocalDate.now().plusDays(3)));
        entries.add(entry4);

        assertEquals(entries, bonusAccount.history(LocalDate.MIN, LocalDate.MAX));
        assertEquals(entries, bonusAccount.history(LocalDate.now(), LocalDate.MAX));

        entries = new ArrayList<>();
        entries.add(entry2);
        entries.add(entry3);
        assertEquals(entries, bonusAccount.history(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
    }
}