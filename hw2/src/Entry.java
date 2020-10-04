import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The record of allocating the amount to the account
 * Amount can be either positive or negative depending on originator or beneficiary
 */
public class Entry {
    private final Account account;
    private final Transaction transaction;
    private final double amount;
    private final LocalDateTime time;

    public Entry(Account account, Transaction transaction, double amount, LocalDateTime time) {
        if (amount == 0) {
            throw new IllegalArgumentException("Amount equals zero");
        }
        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.time = time;
    }
    public Transaction getTransaction() { return transaction; }
    public LocalDateTime getTime() {
        return time;
    }
}

