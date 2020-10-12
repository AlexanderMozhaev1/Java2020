import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsManagerTest {

    @Test
    void mostFrequentBeneficiaryOfAccount() {
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        Account account3 = new Account(3, transactionManager);
        Transaction transaction1 = transactionManager.createTransaction(1000, account1, account2);
        Transaction transaction2 = transactionManager.createTransaction(100, account1, account3);
        Transaction transaction3 = transactionManager.createTransaction(200, account1, account3);
        transactionManager.executeTransaction(transaction1);
        transactionManager.executeTransaction(transaction2);
        transactionManager.executeTransaction(transaction3);

        assertEquals(analyticsManager.mostFrequentBeneficiaryOfAccount(account1), account3);
    }

    @Test
    void topTenExpensivePurchases() {
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        Account account1 = new Account(1, transactionManager);
        account1.addCash(11);
        account1.addCash(2);
        account1.addCash(3);
        account1.addCash(4);
        account1.addCash(5);
        account1.addCash(6);
        account1.addCash(7);
        account1.addCash(8);
        account1.addCash(9);
        account1.addCash(10);
        account1.addCash(1);

        Collection<Transaction> top = analyticsManager.topTenExpensivePurchases(account1);
        double amount = 11d;
        for(Transaction t : top){
            assertEquals(t.getAmount(), amount);
            amount--;
        }
    }
}