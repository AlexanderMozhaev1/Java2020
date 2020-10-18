package ru.sberbank.keyExtractor;

import org.junit.jupiter.api.Test;
import ru.sberbank.TransactionManager;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerHashCodeKeyExtractorTest {

    @Test
    void extract() {
        TransactionManagerHashCodeKeyExtractor extractor = new TransactionManagerHashCodeKeyExtractor();
        TransactionManager transactionManager = new TransactionManager();
        assertEquals(extractor.extract(transactionManager), transactionManager.hashCode());
    }
}