import com.sun.jdi.Value;

import java.lang.reflect.Executable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        HashMap<Account, ArrayList<Transaction>> beneficiaryStorage = new HashMap<>();

        TransactionManager transactionManager = new TransactionManager();
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        Account account3 = new Account(3, transactionManager);
        Account account4 = new Account(4, transactionManager);

        Transaction transaction1 = new Transaction(1, 100, account1, account2, false, true);
        Transaction transaction2 = new Transaction(1, 150, account1, account3, true, false);
        Transaction transaction3 = new Transaction(1, 200, account1, account4, false, true);
        Transaction transaction4 = new Transaction(1, 250, account1, account2, true, false);
        Transaction transaction5 = new Transaction(1, 300, account1, account3, false, true);
        Transaction transaction6 = new Transaction(1, 350, account1, account4, true, false);
        Transaction transaction7 = new Transaction(1, 400, account1, account3, false, true);
        Transaction transaction8 = new Transaction(1, 450, account1, account2, true, false);
        Transaction transaction9 = new Transaction(1, 500, account1, account4, false, true);
        Transaction transaction10 = new Transaction(1, 550, account1, account4, true, false);
        Transaction transaction11 = new Transaction(1, 600, account1, account2, false, true);
        Transaction transaction12 = new Transaction(1, 650, account1, account3, true, false);

        ArrayList<Transaction> transactionArrayList1 = new ArrayList<>();

        transactionArrayList1.add(transaction1);
        transactionArrayList1.add(transaction2);
        transactionArrayList1.add(transaction3);
        transactionArrayList1.add(transaction4);
        transactionArrayList1.add(transaction5);
        transactionArrayList1.add(transaction6);
        transactionArrayList1.add(transaction7);
        transactionArrayList1.add(transaction8);
        transactionArrayList1.add(transaction9);
        transactionArrayList1.add(transaction10);
        transactionArrayList1.add(transaction11);
        transactionArrayList1.add(transaction12);

        beneficiaryStorage.put(account1, transactionArrayList1);

        Map<Account, Long> beneficiaryCounter = beneficiaryStorage
                .get(account1)
                .stream()
                .collect(Collectors.groupingBy(Transaction::getBeneficiary, Collectors.counting()));

        for(Map.Entry<Account, Long> item : beneficiaryCounter.entrySet()){

            System.out.println(item.getKey() + " - " + item.getValue());
        }

        System.out.println((long)(Math.random() * Long.MAX_VALUE));

    }

}
