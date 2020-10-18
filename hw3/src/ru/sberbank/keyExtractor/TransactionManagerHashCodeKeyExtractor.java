package ru.sberbank.keyExtractor;

import ru.sberbank.TransactionManager;

public class TransactionManagerHashCodeKeyExtractor implements KeyExtractor<Integer, TransactionManager>{
    @Override
    public Integer extract(TransactionManager transactionManager) {
        if(transactionManager == null)return null;
        return transactionManager.hashCode();
    }
}
