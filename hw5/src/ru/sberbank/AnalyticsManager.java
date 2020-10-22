package ru.sberbank;

import ru.sberbank.account.Account;
import ru.sberbank.account.DebitCard;
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
        return beneficiaryCounter.keySet()
                .stream()
                .max(Comparator.comparing(beneficiaryCounter::get))
                .orElse(null);
    }

    private Map<Account, Long> getCounterBeneficiary(Account account) {
        return transactionManager.findAllTransactionsByAccount(account)
                .stream()
                .filter(transaction -> !account.equals(transaction.getBeneficiary()))
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
        return accounts.stream()
                .map(a -> a.balanceOn(LocalDate.MAX))
                .reduce(Double::sum)
                .orElse(0d);
    }

    public <K, V extends Account, E extends KeyExtractor<K, V>> Set<K> uniqueKeysOf(List<V> accounts, E extractor) {
        return accounts.stream()
                .map(extractor::extract)
                .collect(Collectors.toSet());
    }

    public <E extends Account> List<Account> accountsRangeFrom(List<E> accounts, E minAccount, Comparator<? super Account> comparator) {
        return accounts.stream()
                .filter(a -> comparator.compare(a, minAccount) >= 0)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public Optional<Entry> maxExpenseAmountEntryWithinInterval(List<? extends Account> accounts, LocalDate from, LocalDate to) {
        return accounts.stream()
                .map(account -> maxExpenseAmountEntryFromAccountWithinInterval(from, to, account))
                .filter(Objects::nonNull)
                .max(Comparator.comparing(entry -> entry.getTransaction().getAmount()));
    }

    private Entry maxExpenseAmountEntryFromAccountWithinInterval(LocalDate from, LocalDate to, Account account) {
        return account.history(from, to).stream()
                .filter(entry -> account.equals(entry.getTransaction().getOriginator()))
                .max(Comparator.comparing(entry -> entry.getTransaction().getAmount()))
                .orElse(null);
    }
}
