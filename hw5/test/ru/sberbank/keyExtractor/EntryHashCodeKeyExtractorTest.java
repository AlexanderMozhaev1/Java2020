package ru.sberbank.keyExtractor;

import org.junit.jupiter.api.Test;
import ru.sberbank.Entry;
import ru.sberbank.Transaction;
import ru.sberbank.TransactionManager;
import ru.sberbank.account.DebitCard;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EntryHashCodeKeyExtractorTest {

    @Test
    void extract() {
        EntryHashCodeKeyExtractor extractor = new EntryHashCodeKeyExtractor();
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        Transaction transaction = transactionManager.createTransaction(100, debitCard1, debitCard2);
        Entry entry = new Entry(debitCard1, transaction, 100, LocalDateTime.now());
        assertEquals(extractor.extract(entry), entry.hashCode());
    }
}