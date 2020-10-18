package ru.sberbank;

import org.junit.jupiter.api.Test;
import ru.sberbank.account.DebitCard;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionManagerTest {

    @Test
    void createTransaction() {
        double amount = 100d;
        TransactionManager transactionManager = new TransactionManager();
        DebitCard originator = new DebitCard(1, transactionManager);
        DebitCard beneficiar = new DebitCard(2, transactionManager);

        Transaction transaction = transactionManager.createTransaction(amount, originator, beneficiar);
        assertEquals(new Transaction(transaction.getId(), amount, originator, beneficiar, false, false), transaction);
    }

    @Test
    void findAllTransactionsByAccount() {
        double amount = 100d;
        TransactionManager transactionManager = new TransactionManager();
        DebitCard originator = new DebitCard(1, transactionManager);
        DebitCard beneficiar = new DebitCard(2, transactionManager);

        Transaction transaction = transactionManager.createTransaction(amount, originator, beneficiar);
        Collection<Transaction> transactionsOriginator = transactionManager.findAllTransactionsByAccount(originator);
        Collection<Transaction> transactionsBeneficiar = transactionManager.findAllTransactionsByAccount(beneficiar);
        for (Transaction t : transactionsOriginator) {
            assertEquals(t, transaction);
        }
        for (Transaction t : transactionsBeneficiar) {
            assertEquals(t, transaction);
        }
    }

    @Test
    void rollbackTransaction() {
        double amount = 100d;
        TransactionManager transactionManager = new TransactionManager();
        DebitCard originator = new DebitCard(1, transactionManager);
        DebitCard beneficiar = new DebitCard(2, transactionManager);

        Transaction transaction = transactionManager.createTransaction(amount, originator, beneficiar);
        Transaction transactionRollback = transaction.rollback();
        transactionManager.rollbackTransaction(transaction);
        Transaction expected = findTransaction(transactionManager, originator, transactionRollback);
        assertEquals(expected, transaction.rollback());
        expected = findTransaction(transactionManager, beneficiar, transactionRollback);
        assertEquals(expected, transactionRollback);
    }

    @Test
    void executeTransaction() {
        double amount = 100d;
        TransactionManager transactionManager = new TransactionManager();
        DebitCard originator = new DebitCard(1, transactionManager);
        DebitCard beneficiar = new DebitCard(2, transactionManager);

        Transaction transaction = transactionManager.createTransaction(amount, originator, beneficiar);
        Transaction transactionExecute = transaction.execute();
        transactionManager.executeTransaction(transaction);
        Transaction expected = findTransaction(transactionManager, originator, transactionExecute);
        assertEquals(expected, transaction.execute());
        expected = findTransaction(transactionManager, beneficiar, transactionExecute);
        assertEquals(expected, transactionExecute);
    }

    private Transaction findTransaction(TransactionManager transactionManager, DebitCard debitCard, Transaction transactionRollback) {
        return transactionManager.findAllTransactionsByAccount(debitCard)
                .stream()
                .filter(t -> t.equals(transactionRollback))
                .findFirst()
                .get();
    }
}