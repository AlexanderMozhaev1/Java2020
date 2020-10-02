import java.util.ArrayList;
import java.util.Collection;

public class TransactionManager {
    /**
     * Creates and stores transactions
     * @param amount
     * @param originator
     * @param beneficiary
     * @return created Transaction
     */
    public Transaction createTransaction(double amount,
                                         Account originator,
                                         Account beneficiary) {
        return new Transaction(1, amount, originator, beneficiary, false, false);
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        return new ArrayList<>();
        // write your code here
    }


    public void rollbackTransaction(Transaction transaction) {
        transaction.rollback();
    }

    public void executeTransaction(Transaction transaction) {
        transaction.execute();
    }
}
