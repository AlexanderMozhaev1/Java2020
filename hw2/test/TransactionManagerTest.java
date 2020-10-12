import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerTest {

    @Test
    void createTransaction() {
        double amount = 100d;
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(1, transactionManager);
        Account beneficiar = new Account(2, transactionManager);

        Transaction transaction = transactionManager.createTransaction(amount, originator, beneficiar);
        assertEquals(new Transaction(transaction.getId(), amount, originator, beneficiar, false, false), transaction);
    }

    @Test
    void findAllTransactionsByAccount() {
        double amount = 100d;
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(1, transactionManager);
        Account beneficiar = new Account(2, transactionManager);

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
        Account originator = new Account(1, transactionManager);
        Account beneficiar = new Account(2, transactionManager);

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
        Account originator = new Account(1, transactionManager);
        Account beneficiar = new Account(2, transactionManager);

        Transaction transaction = transactionManager.createTransaction(amount, originator, beneficiar);
        Transaction transactionExecute = transaction.execute();
        transactionManager.rollbackTransaction(transaction);
        Transaction expected = findTransaction(transactionManager, originator, transactionExecute);
        assertEquals(expected, transaction.rollback());
        expected = findTransaction(transactionManager, beneficiar, transactionExecute);
        assertEquals(expected, transactionExecute);
    }

    private Transaction findTransaction(TransactionManager transactionManager, Account account, Transaction transactionRollback) {
        return transactionManager.findAllTransactionsByAccount(account)
                .stream()
                .filter(t -> t.equals(transactionRollback))
                .findFirst()
                .get();
    }
}