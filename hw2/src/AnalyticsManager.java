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
        Account resAccount = null;
        Long max = 0l;
        Map<Account, Long> beneficiaryCounter = transactionManager
                .findAllTransactionsByAccount(account)
                .stream()
                .filter(transaction -> transaction.getBeneficiary() != account)
                .collect(Collectors.groupingBy(
                        Transaction::getBeneficiary, Collectors.counting()));

        for(Map.Entry<Account, Long> item : beneficiaryCounter.entrySet()){
            if(item.getValue() > max){
                max = item.getValue();
                resAccount = item.getKey();
            }
        }
        return resAccount;
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        return transactionManager
                .findAllTransactionsByAccount(account)
                .stream()
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }
}
