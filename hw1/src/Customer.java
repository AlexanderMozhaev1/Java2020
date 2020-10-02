public class Customer {
    private final String name;
    private final String lastName;
    private Account account;

    public Customer(String name, String lastName) {
        if (name == null ||
                lastName == null ||
                name.trim().isEmpty() ||
                lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty name or lastName");
        }
        this.name = name;
        this.lastName = lastName;
    }

    public Customer(String name, String lastName, Account account) {
        this(name, lastName);
        this.account = account;
    }


    /**
     * Opens account for a customer (creates Account and sets it to field "account").
     * Customer can't have greater than one opened account.
     *
     * @param accountId id of the account
     * @return true if account hasn't already created, otherwise returns false and prints "Customer fullName() already has the active account"
     */
    public boolean openAccount(long accountId) {
        if (account != null) {
            System.out.println("Customer " + fullName() + " already has the active account");
            return false;
        }
        account = new Account(accountId);
        return true;
    }

    /**
     * Opens account for a customer (creates Account and sets it to field "account").
     * Customer can't have greater than one opened account.
     *
     * @param account
     * @return true if account hasn't already created, otherwise returns false and prints "Customer fullName() already has the active account"
     */
    public boolean openAccount(Account account) {
        if (this.account != null) {
            System.out.println("Customer " + fullName() + " already has the active account");
            return false;
        }
        this.account = account;
        return true;
    }

    /**
     * Closes account. Sets account to null.
     *
     * @return false if account is already null and prints "Customer fullName() has no active account to close", otherwise sets account to null and returns true
     */
    public boolean closeAccount() {
        if (account == null) {
            System.out.println("Customer " + fullName() + " has no active account to close");
            return false;
        }
        account = null;
        return true;
    }

    /**
     * Formatted full name of the customer
     *
     * @return concatenated form of name and lastName, e.g. "John Goodman"
     */
    public String fullName() {
        return name + " " + lastName;
    }

    /**
     * Delegates withdraw to Account class
     *
     * @param amount
     * @return false if account is null and prints "Customer fullName() has no active account", otherwise returns the result of Account's withdraw method
     */
    public boolean withdrawFromCurrentAccount(double amount) {
        if (account == null) {
            System.out.println("Customer " + fullName() + " has  no active account");
            return false;
        }
        return account.withdraw(amount);
    }

    /**
     * Delegates adding money to Account class
     *
     * @param amount
     * @return false if account is null and prints "Customer fullName() has no active account", otherwise returns the result of Account's add method
     */
    public boolean addMoneyToCurrentAccount(double amount) {
        if (account == null) {
            System.out.println("Customer " + fullName() + " has  no active account");
            return false;
        }
        return account.add(amount);
    }
}
