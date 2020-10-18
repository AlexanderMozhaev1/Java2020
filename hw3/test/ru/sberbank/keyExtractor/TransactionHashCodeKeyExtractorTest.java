package ru.sberbank.keyExtractor;

import org.junit.jupiter.api.Test;
import ru.sberbank.Transaction;
import ru.sberbank.TransactionManager;
import ru.sberbank.account.DebitCard;

import static org.junit.jupiter.api.Assertions.*;

class TransactionHashCodeKeyExtractorTest {

    @Test
    void extract() {
        TransactionHashCodeKeyExtractor extractor = new TransactionHashCodeKeyExtractor();
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        Transaction transaction = transactionManager.createTransaction(100, debitCard1, debitCard2);
        assertEquals(extractor.extract(transaction), transaction.hashCode());
    }
}