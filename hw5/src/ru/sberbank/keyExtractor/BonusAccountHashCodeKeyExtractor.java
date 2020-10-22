package ru.sberbank.keyExtractor;

import ru.sberbank.account.Account;
import ru.sberbank.account.BonusAccount;

public class BonusAccountHashCodeKeyExtractor implements KeyExtractor<Integer, BonusAccount> {
    @Override
    public Integer extract(BonusAccount bonusAccount) {
        if(bonusAccount == null) return null;
        return bonusAccount.hashCode();
    }
}
