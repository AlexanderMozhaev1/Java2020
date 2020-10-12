import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void execute() {
        Transaction transaction = new Transaction(1, 100, null, null, false, false);
        Transaction transactionExecute = new Transaction(1, 100, null, null, true, false);
        assertEquals(transactionExecute, transaction.execute());
    }

    @Test
    void executeException() {
        Transaction transactionExecute = new Transaction(1, 100, null, null, true, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> transactionExecute.execute());
        assertEquals("Transaction was already executed", exception.getMessage());
    }

    @Test
    void rollback() {
        Transaction transaction = new Transaction(1, 100, null, null, false, false);
        Transaction transactionRollback = new Transaction(1, 100, null, null, false, true);
        assertEquals(transactionRollback, transaction.rollback());
    }

    @Test
    void rollbackException() {
        Transaction transactionRollback = new Transaction(1, 100, null, null, false, true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> transactionRollback.rollback());
        assertEquals("Transaction was already rolledBack", exception.getMessage());
    }
}