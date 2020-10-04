import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void withdrawOverdraft() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        account1.addCash(100);
        assertFalse(account1.withdraw(150, account2));
        assertEquals(account1.balanceOn(LocalDate.now()), 100);
        assertEquals(account2.balanceOn(LocalDate.now()), 0);
    }

    @Test
    void withdrawNullBalance() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        assertFalse(account1.withdraw(150, account2));
        assertEquals(account1.balanceOn(LocalDate.now()), 0);
        assertEquals(account2.balanceOn(LocalDate.now()), 0);
    }


    @Test
    void withdraw() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        account1.addCash(100);
        assertTrue(account1.withdraw(100, account2));
        assertEquals(account1.balanceOn(LocalDate.now()), 0);
        assertEquals(account2.balanceOn(LocalDate.now()), 100);
    }

    @Test
    void withdrawCashOverdraft() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        account1.addCash(100);
        assertFalse(account1.withdrawCash(150));
        assertEquals(account1.balanceOn(LocalDate.now()), 100);
    }

    @Test
    void withdrawCashNullBalance() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        assertFalse(account1.withdrawCash(150));
        assertEquals(account1.balanceOn(LocalDate.now()), 0);
    }


    @Test
    void withdrawCash() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        account1.addCash(100);
        assertTrue(account1.withdrawCash(100));
        assertEquals(account1.balanceOn(LocalDate.now()), 0);
    }
    @Test
    void addCash() {
        TransactionManager transactionManager = new TransactionManager();
        Account account = new Account(1, transactionManager);
        assertFalse(account.addCash(0));
        assertFalse(account.addCash(-100));
        assertTrue(account.addCash(100));
        assertEquals(account.balanceOn(LocalDate.now()), 100);
    }

    @Test
    void history() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        Transaction transaction1 = new Transaction(1, 100,
                account1, null, false, false);

        Entry entry1 = new Entry(account1, transaction1, 100, LocalDateTime.now());
        Entry entry2 = new Entry(account1, transaction1, 100, LocalDateTime.now().plusDays(1));
        Entry entry3 = new Entry(account1, transaction1, 100, LocalDateTime.now().plusDays(2));
        Entry entry4 = new Entry(account1, transaction1, 100, LocalDateTime.now().plusDays(3));
        account1.addEntry(entry1);
        account1.addEntry(entry2);
        account1.addEntry(entry3);
        account1.addEntry(entry4);

        ArrayList<Entry> entries = new ArrayList<>();
        assertEquals(entries, account1.history(LocalDate.MIN, LocalDate.now()));
        entries.add(entry1);
        assertEquals(entries, account1.history(LocalDate.now(), LocalDate.now().plusDays(1)));
        entries.add(entry2);
        assertEquals(entries, account1.history(LocalDate.now(), LocalDate.now().plusDays(2)));
        entries.add(entry3);
        assertEquals(entries, account1.history(LocalDate.now(), LocalDate.now().plusDays(3)));
        entries.add(entry4);

        assertEquals(entries, account1.history(LocalDate.MIN, LocalDate.MAX));
        assertEquals(entries, account1.history(LocalDate.now(), LocalDate.MAX));

        entries = new ArrayList<>();
        entries.add(entry2);
        entries.add(entry3);
        assertEquals(entries, account1.history(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
    }

    @Test
    void balanceOn() {
        TransactionManager transactionManager = new TransactionManager();
        Account account = new Account(1, transactionManager);
        assertEquals(account.balanceOn(LocalDate.now()), 0);
        account.addCash(100);
        assertEquals(account.balanceOn(LocalDate.now()), 100);
    }

    @Test
    void rollbackLastTransaction() {
        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        account1.addCash(100);
        assertTrue(account1.withdraw(100, account2));
        assertEquals(account1.balanceOn(LocalDate.now()), 0);
        assertEquals(account2.balanceOn(LocalDate.now()), 100);
        account1.rollbackLastTransaction();
        assertEquals(account1.balanceOn(LocalDate.now()), 100);
        assertEquals(account2.balanceOn(LocalDate.now()), 0);
    }
}