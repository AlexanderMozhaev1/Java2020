package ru.sberbank;

import org.junit.jupiter.api.Test;
import ru.sberbank.account.Account;
import ru.sberbank.account.DebitCard;
import ru.sberbank.keyExtractor.DebitCardHashCodeKeyExtractor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.sun.tools.javac.util.List.of;
import static org.junit.jupiter.api.Assertions.*;

class AnalyticsManagerTest {

    @Test
    void mostFrequentBeneficiaryOfAccount() {
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        DebitCard debitCard3 = new DebitCard(3, transactionManager);
        Transaction transaction1 = transactionManager.createTransaction(1000, debitCard1, debitCard2);
        Transaction transaction2 = transactionManager.createTransaction(100, debitCard1, debitCard3);
        Transaction transaction3 = transactionManager.createTransaction(200, debitCard1, debitCard3);
        transactionManager.executeTransaction(transaction1);
        transactionManager.executeTransaction(transaction2);
        transactionManager.executeTransaction(transaction3);

        assertEquals(analyticsManager.mostFrequentBeneficiaryOfAccount(debitCard1), debitCard3);
    }

    @Test
    void topTenExpensivePurchases() {
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        debitCard1.addCash(11);
        debitCard1.addCash(2);
        debitCard1.addCash(3);
        debitCard1.addCash(4);
        debitCard1.addCash(5);
        debitCard1.addCash(6);
        debitCard1.addCash(7);
        debitCard1.addCash(8);
        debitCard1.addCash(9);
        debitCard1.addCash(10);
        debitCard1.addCash(1);

        Collection<Transaction> top = analyticsManager.topTenExpensivePurchases(debitCard1);
        double amount = 11d;
        for(Transaction t : top){
            assertEquals(t.getAmount(), amount);
            amount--;
        }
    }

    @Test
    void overallBalanceOfAccounts() {
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        DebitCard debitCard3 = new DebitCard(3, transactionManager);
        debitCard1.addCash(100);
        debitCard2.addCash(200);
        debitCard3.addCash(300);
        assertEquals(analyticsManager.overallBalanceOfAccounts(of(debitCard1, debitCard2, debitCard3)), 600);
    }

    @Test
    void uniqueKeysOf() {
        DebitCardHashCodeKeyExtractor extractor = new DebitCardHashCodeKeyExtractor();
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        DebitCard debitCard3 = new DebitCard(3, transactionManager);
        List<DebitCard> list = of(debitCard1, debitCard2, debitCard3);
        Set<Integer> set = analyticsManager.uniqueKeysOf(list, extractor);
        Set<Integer> actual = new HashSet<>(of(debitCard1.hashCode(), debitCard2.hashCode(), debitCard3.hashCode()));
        assertEquals(set, actual);
    }

    @Test
    void accountsRangeFrom() {
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        DebitCard debitCard3 = new DebitCard(3, transactionManager);
        debitCard1.addCash(300);
        debitCard2.addCash(500);
        debitCard3.addCash(400);
        Comparator<Account> comparing = Comparator.comparing(account -> account.balanceOn(LocalDate.MAX));
        List<DebitCard> accounts = new ArrayList<>(of(debitCard1, debitCard2, debitCard3));

        assertEquals(analyticsManager.accountsRangeFrom(accounts, debitCard1, comparing)
                , new ArrayList<>(of(debitCard1, debitCard3, debitCard2)));
        assertEquals(analyticsManager.accountsRangeFrom(accounts, debitCard2, comparing)
                , new ArrayList<>(of(debitCard2)));
        assertEquals(analyticsManager.accountsRangeFrom(accounts, debitCard3, comparing)
                , new ArrayList<>(of(debitCard3, debitCard2)));
    }

    @Test
    void maxExpenseAmountEntryWithinIntervalEmpti() {
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        DebitCard debitCard3 = new DebitCard(3, transactionManager);
        debitCard1.addCash(300);
        debitCard2.addCash(200);
        debitCard3.addCash(100);
        Optional<Entry> entry = analyticsManager.maxExpenseAmountEntryWithinInterval(
                of(debitCard1, debitCard2, debitCard3), LocalDate.MIN, LocalDate.MAX);
        assertFalse(entry.isPresent());
    }

    @Test
    void maxExpenseAmountEntryWithinInterval() {
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard originator = new DebitCard(1, transactionManager);
        DebitCard beneficiary = new DebitCard(2, transactionManager);
        originator.addCash(1500);
        Transaction transaction1 = new Transaction(1, 600, originator, beneficiary, true, false);
        Transaction transaction2 = new Transaction(1, 200, originator, beneficiary, true, false);
        Transaction transaction3 = new Transaction(1, 300, originator, beneficiary, true, false);
        Transaction transaction4 = new Transaction(1, 400, originator, beneficiary, true, false);
        Entry entry1 = new Entry(originator, transaction1, transaction1.getAmount(), LocalDateTime.now());
        Entry entry2 = new Entry(originator, transaction2, transaction2.getAmount(), LocalDateTime.now().plusDays(1));
        Entry entry3 = new Entry(originator, transaction3, transaction3.getAmount(), LocalDateTime.now().plusDays(2));
        Entry entry4 = new Entry(originator, transaction4, transaction4.getAmount(), LocalDateTime.now().plusDays(3));
        originator.addEntry(entry1);
        originator.addEntry(entry2);
        originator.addEntry(entry3);
        originator.addEntry(entry4);

        Optional<Entry> expected1 = analyticsManager.maxExpenseAmountEntryWithinInterval(of(originator, beneficiary),
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));
        Optional<Entry> expected2 = analyticsManager.maxExpenseAmountEntryWithinInterval(of(originator, beneficiary),
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        Optional<Entry> expected3 = analyticsManager.maxExpenseAmountEntryWithinInterval(of(originator, beneficiary),
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
        Optional<Entry> expected4 = analyticsManager.maxExpenseAmountEntryWithinInterval(of(originator, beneficiary),
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(4));
        Optional<Entry> expected5 = analyticsManager.maxExpenseAmountEntryWithinInterval(of(originator, beneficiary),
                LocalDate.now(), LocalDate.now().plusDays(1));

        assertFalse(expected1.isPresent());
        assertEquals(expected2.get().getTransaction().getAmount(), 200);
        assertEquals(expected3.get().getTransaction().getAmount(), 300);
        assertEquals(expected4.get().getTransaction().getAmount(), 400);
        assertEquals(expected5.get().getTransaction().getAmount(), 600);
    }
}