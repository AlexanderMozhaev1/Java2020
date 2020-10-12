import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Map<Account, Long> beneficiaryCounter = getCounterBeneficiary(account);

        return beneficiaryCounter
                .keySet()
                .stream()
                .max(Comparator.comparing((k) -> beneficiaryCounter.get(k)))
                .get();
    }

    private Map<Account, Long> getCounterBeneficiary(Account account) {
        return transactionManager
                .findAllTransactionsByAccount(account)
                .stream()
                .filter(transaction -> transaction.getBeneficiary() != account)
                .collect(Collectors.groupingBy(Transaction::getBeneficiary, Collectors.counting()));
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        return transactionManager
                .findAllTransactionsByAccount(account)
                .stream()
                .filter(t -> t.isExecuted())
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}
