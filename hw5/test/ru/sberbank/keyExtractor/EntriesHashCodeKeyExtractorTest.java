package ru.sberbank.keyExtractor;

import org.junit.jupiter.api.Test;
import ru.sberbank.Entries;

import static org.junit.jupiter.api.Assertions.*;

class EntriesHashCodeKeyExtractorTest {

    @Test
    void extract() {
        EntriesHashCodeKeyExtractor extractor = new EntriesHashCodeKeyExtractor();
        Entries entries = new Entries();
        assertEquals(extractor.extract(entries), entries.hashCode());
    }
}