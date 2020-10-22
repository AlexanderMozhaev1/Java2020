package ru.sberbank.keyExtractor;

import org.junit.jupiter.api.Test;
import ru.sberbank.TransactionManager;
import ru.sberbank.account.DebitCard;

import static org.junit.jupiter.api.Assertions.*;

class DebitCardHashCodeKeyExtractorTest {

    @Test
    void extract() {
        DebitCardHashCodeKeyExtractor extractor = new DebitCardHashCodeKeyExtractor();
        DebitCard debitCard = new DebitCard(1, new TransactionManager());
        assertEquals(extractor.extract(debitCard), debitCard.hashCode());
    }
}