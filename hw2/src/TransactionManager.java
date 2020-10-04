import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionManager {
    private HashMap<Account, ArrayList<Transaction>> beneficiaryStorage;
    private HashMap<Account, ArrayList<Transaction>> originatorStorage;

    public TransactionManager() {
        beneficiaryStorage = new HashMap<>();
        originatorStorage = new HashMap<>();
    }

    /**
     * Creates and stores transactions
     *
     * @param amount
     * @param originator
     * @param beneficiary
     * @return created Transaction
     */
    public Transaction createTransaction(double amount,
                                         Account originator,
                                         Account beneficiary) {
        Transaction transaction = new Transaction((long)(Math.random() * Long.MAX_VALUE), amount,
                originator, beneficiary, false, false);
        LocalDateTime localDateTime = LocalDateTime.now();
        addTransaction(originator, transaction, originatorStorage, localDateTime);
        addTransaction(beneficiary, transaction, beneficiaryStorage, localDateTime);
        return transaction;
    }

    private void addTransaction(Account account,
                                Transaction transaction,
                                HashMap<Account, ArrayList<Transaction>> storage,
                                LocalDateTime localDateTime) {
        if(account != null) {
            account.addEntry(new Entry(account, transaction, transaction.getAmount(), localDateTime));
            ArrayList<Transaction> transactionList = storage.get(account);
            if (transactionList != null) {
                transactionList.add(transaction);
            } else {
                ArrayList<Transaction> newTransactionList = new ArrayList<>();
                newTransactionList.add(transaction);
                storage.put(account, newTransactionList);
            }
        }
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        ArrayList<Transaction> listAllTransactionsByAccount = new ArrayList<>();
        listAllTransactionsByAccount.addAll(originatorStorage.get(account));
        listAllTransactionsByAccount.addAll(beneficiaryStorage.get(account));
        return listAllTransactionsByAccount;
    }


    public void rollbackTransaction(Transaction transaction) {
        Transaction transactionRollback = transaction.rollback();
        LocalDateTime localDateTime = LocalDateTime.now();
        addTransaction(transaction.getOriginator(), transactionRollback, originatorStorage, localDateTime);
        addTransaction(transaction.getBeneficiary(), transactionRollback, beneficiaryStorage, localDateTime);
    }

    public void executeTransaction(Transaction transaction) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Transaction transactionExecute = transaction.execute();
        addTransaction(transaction.getOriginator(), transactionExecute, originatorStorage, localDateTime);
        addTransaction(transaction.getBeneficiary(), transactionExecute, beneficiaryStorage, localDateTime);
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Account resAccount = null;
        Long max = 0l;
        Map<Account, Long> beneficiaryCounter = beneficiaryStorage
                .get(account)
                .stream()
                .collect(Collectors.groupingBy(Transaction::getBeneficiary, Collectors.counting()));

        for(Map.Entry<Account, Long> item : beneficiaryCounter.entrySet()){
            if(item.getValue() > max){
                max = item.getValue();
                resAccount = item.getKey();
            }
        }

        return resAccount;
    }
}
