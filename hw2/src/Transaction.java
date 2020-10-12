import java.util.Objects;

public class Transaction {
    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private final boolean executed;
    private final boolean rolledBack;

    public Transaction(long id, double amount, Account originator, Account beneficiary, boolean executed, boolean rolledBack) {
        if (amount == 0) {
            throw new IllegalArgumentException("Amount equals zero");
        }
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = executed;
        this.rolledBack = rolledBack;
    }

    /**
     * Adding entries to both accounts
     *
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() {
        if (executed) {
            throw new IllegalArgumentException("Transaction was already executed");
        }
        return new Transaction(id, amount, originator, beneficiary, true, rolledBack);
    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     *
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() {
        if (rolledBack) {
            throw new IllegalArgumentException("Transaction was already rolledBack");
        }
        return new Transaction(id, amount, originator, beneficiary, executed, true);
    }

    public double getAmount() {
        return amount;
    }

    public Account getOriginator() {
        return originator;
    }

    public Account getBeneficiary() {
        return beneficiary;
    }

    public boolean isExecuted() {
        return executed;
    }

    public boolean isRolledBack() {
        return rolledBack;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                Double.compare(that.amount, amount) == 0 &&
                executed == that.executed &&
                rolledBack == that.rolledBack &&
                Objects.equals(originator, that.originator) &&
                Objects.equals(beneficiary, that.beneficiary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, originator, beneficiary, executed, rolledBack);
    }


}
