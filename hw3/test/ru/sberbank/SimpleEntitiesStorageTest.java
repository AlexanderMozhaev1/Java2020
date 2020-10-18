package ru.sberbank;

import org.junit.jupiter.api.Test;
import ru.sberbank.account.DebitCard;
import ru.sberbank.keyExtractor.DebitCardHashCodeKeyExtractor;

import java.util.List;

import static com.sun.tools.javac.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleEntitiesStorageTest {

    @Test
    void save() {
        DebitCard debitCard = new DebitCard(1, new TransactionManager());
        DebitCardHashCodeKeyExtractor extractor = new DebitCardHashCodeKeyExtractor();
        SimpleEntitiesStorage<Integer, DebitCard> storage = new SimpleEntitiesStorage<>(extractor);
        storage.save(debitCard);
        assertEquals(storage.findByKey(extractor.extract(debitCard)), debitCard);
    }

    @Test
    void saveAll() {
        TransactionManager transactionManager = new TransactionManager();
        List<DebitCard> list = of(new DebitCard(1, transactionManager), new DebitCard(2, transactionManager));
        DebitCardHashCodeKeyExtractor extractor = new DebitCardHashCodeKeyExtractor();
        SimpleEntitiesStorage<Integer, DebitCard> storage = new SimpleEntitiesStorage<>(extractor);
        storage.saveAll(list);
        assertEquals(storage.findAll(), list);
    }

    @Test
    void findByKey() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        List<DebitCard> list = of(debitCard1, debitCard2);
        DebitCardHashCodeKeyExtractor extractor = new DebitCardHashCodeKeyExtractor();
        SimpleEntitiesStorage<Integer, DebitCard> storage = new SimpleEntitiesStorage<>(extractor);
        storage.saveAll(list);
        assertEquals(storage.findByKey(extractor.extract(debitCard1)), debitCard1);
    }

    @Test
    void findAll() {
        TransactionManager transactionManager = new TransactionManager();
        List<DebitCard> list = of(new DebitCard(1, transactionManager), new DebitCard(2, transactionManager));
        DebitCardHashCodeKeyExtractor extractor = new DebitCardHashCodeKeyExtractor();
        SimpleEntitiesStorage<Integer, DebitCard> storage = new SimpleEntitiesStorage<>(extractor);
        storage.saveAll(list);
        assertEquals(storage.findAll(), list);
    }

    @Test
    void deleteByKey() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        List<DebitCard> list = of(debitCard1, debitCard2);
        DebitCardHashCodeKeyExtractor extractor = new DebitCardHashCodeKeyExtractor();
        SimpleEntitiesStorage<Integer, DebitCard> storage = new SimpleEntitiesStorage<>(extractor);
        storage.saveAll(list);
        storage.deleteByKey(extractor.extract(debitCard1));
        assertEquals(storage.findAll(), of(debitCard2));
    }

    @Test
    void deleteAll() {
        TransactionManager transactionManager = new TransactionManager();
        DebitCard debitCard1 = new DebitCard(1, transactionManager);
        DebitCard debitCard2 = new DebitCard(2, transactionManager);
        DebitCard debitCard3 = new DebitCard(3, transactionManager);
        DebitCardHashCodeKeyExtractor extractor = new DebitCardHashCodeKeyExtractor();
        SimpleEntitiesStorage<Integer, DebitCard> storage = new SimpleEntitiesStorage<>(extractor);
        storage.saveAll(of(debitCard1, debitCard2, debitCard3));
        storage.deleteAll(of(debitCard1, debitCard2));
        assertEquals(storage.findAll(), of(debitCard3));
    }
}