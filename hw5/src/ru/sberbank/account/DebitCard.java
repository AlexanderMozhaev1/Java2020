package ru.sberbank.account;

import ru.sberbank.Entries;
import ru.sberbank.Entry;
import ru.sberbank.Transaction;
import ru.sberbank.TransactionManager;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

public class DebitCard implements Account {
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;

    public DebitCard(long id, TransactionManager transactionManager) {
        this.id = id;
        this.transactionManager = transactionManager;
        this.entries = new Entries();
    }

    /**
     * Withdraws money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount, Account account) {
        double currentBalance = balanceOn(LocalDate.now());
        if (amount > 0 && currentBalance - amount >= 0) {
            Transaction transaction = transactionManager.createTransaction(amount, this, account);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     * Withdraws cash money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdrawCash(double amount) {
        double currentBalance = balanceOn(LocalDate.now());
        if (amount > 0 && currentBalance - amount >= 0) {
            Transaction transaction = transactionManager.createTransaction(amount, this, null);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     * Adds cash money to account. <b>Should use TransactionManager to manage transactions</b>
     * manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean addCash(double amount) {
        if (amount > 0) {
            Transaction transaction = transactionManager.createTransaction(amount, null, this);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    public Collection<Entry> history(LocalDate from, LocalDate to) {
        return entries.betweenDates(from, to);
    }

    /**
     * Calculates balance on the accounting entries basis
     *
     * @param date
     * @return balance
     */
    public double balanceOn(LocalDate date) {
        double balance = 0d;
        for (Entry entry : entries.from(date)) {
            balance += getBalanceTransaction(entry.getTransaction());
        }
        return balance;
    }

    private double getBalanceTransaction(Transaction transaction) {
        if (transaction.isExecuted()) {
            if ((transaction.isRolledBack() && this.equals(transaction.getBeneficiary())) ||
                    (transaction.isExecuted() && this.equals(transaction.getOriginator()) && !transaction.isRolledBack())) {
                return -transaction.getAmount();
            }
            return transaction.getAmount();
        }
        return 0d;
    }


    /**
     * Finds the last transaction of the account and rollbacks it
     */
    public void rollbackLastTransaction() {
        transactionManager.rollbackTransaction(entries.last().getTransaction());
    }

    public void addEntry(Entry entry) {
        entries.addEntry(entry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DebitCard debitCard = (DebitCard) o;
        return id == debitCard.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
