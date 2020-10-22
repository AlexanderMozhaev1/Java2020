package ru.sberbank.account;

import ru.sberbank.Entry;
import ru.sberbank.Transaction;
import ru.sberbank.TransactionManager;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

public class BonusAccount implements Account {

    private DebitCard debitCard;
    private final double percent;

    public BonusAccount(long id, TransactionManager transactionManager, double percent) {
        this.percent = percent;
        debitCard = new DebitCard(id, transactionManager);
    }

    @Override
    public double balanceOn(LocalDate date) {
        return debitCard.balanceOn(LocalDate.now());
    }

    @Override
    public void addEntry(Entry entry) {
        debitCard.addEntry(entry);
    }

    @Override
    public Collection<Entry> history(LocalDate from, LocalDate to) {
        return debitCard.history(from, to);
    }

    public boolean addCash(double amount) {
        return debitCard.addCash(amount);
    }

    public boolean withdrawCash(double amount) {
        return debitCard.withdrawCash(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BonusAccount that = (BonusAccount) o;
        return Double.compare(that.percent, percent) == 0 &&
                debitCard.equals(that.debitCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debitCard, percent);
    }

    public boolean withdraw(double amount, Account account){
        if(debitCard.withdraw(amount, account)){
            debitCard.addCash(amount * percent);
            return true;
        }
        return false;
    }
}
