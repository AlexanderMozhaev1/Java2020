package ru.sberbank;

import ru.sberbank.account.Account;
import ru.sberbank.keyExtractor.KeyExtractor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Map<Account, Long> beneficiaryCounter = getCounterBeneficiary(account);

        if (beneficiaryCounter.isEmpty()) return null;
        return beneficiaryCounter.keySet()
                .stream()
                .max(Comparator.comparing(beneficiaryCounter::get))
                .get();
    }

    private Map<Account, Long> getCounterBeneficiary(Account account) {
        return transactionManager.findAllTransactionsByAccount(account)
                .stream()
                .filter(transaction -> !transaction.getBeneficiary().equals(account))
                .collect(Collectors.groupingBy(Transaction::getBeneficiary, Collectors.counting()));
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        return transactionManager.findAllTransactionsByAccount(account)
                .stream()
                .filter(Transaction::isExecuted)
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public double overallBalanceOfAccounts(List<Account> accounts) {
        if (accounts == null || accounts.isEmpty()) return 0d;
        return accounts.stream()
                .map(a -> a.balanceOn(LocalDate.MAX))
                .reduce(Double::sum)
                .get();
    }

    public <K> Set<K> uniqueKeysOf(List<Account> accounts, KeyExtractor<K, Account> extractor) {
        return accounts.stream()
                .map(extractor::extract)
                .collect(Collectors.toSet());
    }

    public List<Account> accountsRangeFrom(List<Account> accounts, Account minAccount, Comparator<Account> comparator) {
        return accounts.stream()
                .filter(a -> comparator.compare(a, minAccount) > 0)
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
