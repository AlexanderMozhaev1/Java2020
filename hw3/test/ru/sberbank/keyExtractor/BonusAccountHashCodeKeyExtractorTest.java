package ru.sberbank.keyExtractor;

import org.junit.jupiter.api.Test;
import ru.sberbank.TransactionManager;
import ru.sberbank.account.BonusAccount;

import static org.junit.jupiter.api.Assertions.*;

class BonusAccountHashCodeKeyExtractorTest {

    @Test
    void extract() {
        BonusAccountHashCodeKeyExtractor bonusAccountHashCodeKeyExtractor = new BonusAccountHashCodeKeyExtractor();
        BonusAccount bonusAccount = new BonusAccount(1, new TransactionManager(), 0.1);
        assertEquals(bonusAccountHashCodeKeyExtractor.extract(bonusAccount), bonusAccount.hashCode());
    }
}