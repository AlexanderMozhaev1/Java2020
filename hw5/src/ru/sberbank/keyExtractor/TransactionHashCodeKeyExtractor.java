package ru.sberbank.keyExtractor;

import ru.sberbank.Transaction;
import ru.sberbank.account.Account;

public class TransactionHashCodeKeyExtractor implements KeyExtractor<Integer, Transaction> {
    @Override
    public Integer extract(Transaction transaction) {
        if(transaction == null)return null;
        return transaction.hashCode();
    }
}
