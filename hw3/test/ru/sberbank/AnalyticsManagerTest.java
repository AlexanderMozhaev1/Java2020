package ru.sberbank;

import org.junit.jupiter.api.Test;
import ru.sberbank.AnalyticsManager;
import ru.sberbank.account.DebitCard;

import java.util.Collection;

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
}