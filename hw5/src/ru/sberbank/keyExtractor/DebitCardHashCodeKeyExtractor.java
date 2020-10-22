package ru.sberbank.keyExtractor;

import ru.sberbank.account.Account;
import ru.sberbank.account.DebitCard;

public class DebitCardHashCodeKeyExtractor implements KeyExtractor<Integer, DebitCard> {
    @Override
    public Integer extract(DebitCard debitCard) {
        if (debitCard == null) return null;
        return debitCard.hashCode();
    }
}
