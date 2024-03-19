package com.example.DG.bank.system.service;


import com.example.DG.bank.system.Repo.AlertRepo;
import com.example.DG.bank.system.Repo.RuleRepo;
import com.example.DG.bank.system.Repo.TransactionRepo;
import com.example.DG.bank.system.model.*;
import com.example.DG.bank.system.model.enums.RuleType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RulesServiceImpl implements RuleService{

    @PersistenceContext
    private EntityManager entityManager;
    private RuleRepo ruleRepo;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AlertRepo alertRepo;

//    @Autowired
//    private TransactionService transactionService;

    @Autowired
    RulesServiceImpl(RuleRepo ruleRepo)
    {
        this.ruleRepo = ruleRepo;
    }

    @Override
    public Rule addRule(Rule rule) {
        return ruleRepo.save(rule);
    }

    @Override
    public List<Rule> FindAllRule() {
        return ruleRepo.findAll();
    }

    @Override
    public Rule updateRule(Rule rule) {
        return ruleRepo.save(rule);
    }

    @Override
    public void deleteRule(long id) {
        ruleRepo.deleteById(id);
    }

    @Override
    public List<Transaction> findAllByCustomerId(long customerId) {

        return transactionRepo.findAllByCustomerId(customerId);
    }

    public Map<Long, List<Transaction>> getAllCustomerTransactions() {
        List<Customer> customers = customerService.FindAllCustomer();
        Map<Long, List<Transaction>> customerTransactionsMap = new HashMap<>();

        for (Customer customer : customers) {
            long customerId = customer.getId();
            List<Transaction> transactions = findAllByCustomerId(customerId);
            customerTransactionsMap.put(customerId, transactions);
        }

        return customerTransactionsMap;
    }
    @Override
    public List<Transaction> getTransactionsByType(String type) {
        return transactionRepo.findAllByType(type);
    }
    private static char mapOperation(String operation) {
        switch (operation) {
            case "Less":
                return '<';
            case "Greater":
                return '>';
            case "Equal":
                return '=';
            default:
                return '\0'; // Return null character or another special character for other cases
        }
    }


    @Override
    public Boolean ExecuteRule(Rule rule,Transaction transaction)
    {
        boolean ruleisAchieved = false;

        long id ;
        String description;
        List<Transaction> transactions;
        String trxType;
        String dynamicQuery;
        String type ;

        if(rule.getRuleType()== RuleType.Customer) {

            id = transaction.getAccount1().getCustomer().getId();
            description = "Customer " + id + " has achieved Rule: " + rule.getId();
            trxType = String.valueOf(rule.getTransactionType());
            dynamicQuery = "select " + rule.getAggregation() + "(t.amount) FROM Transaction t Where t.account1.customer.id = :id AND t.type = :type AND t.date = :dateui";
            transactions = getTransactionsByCustomerId(id, trxType, rule.getDate());
            type = "customer";
        }
        else {
            id = transaction.getAccount1().getId();
            description = "Account " + id + " has achieved Rule: " + rule.getId();
            trxType = String.valueOf(rule.getTransactionType());
            dynamicQuery = "select " + rule.getAggregation() + "(t.amount) FROM Transaction t Where t.account1.id = :id AND t.type = :type AND t.date = :dateui";
            transactions = getTransactionsByAccountId(id,trxType, rule.getDate());
            type = "account";
        }
            if (transactions.isEmpty()) {
                // The query returned a single null element; exit the function
                return false;
            }


                long result = executeDynamicQuery(dynamicQuery, id, trxType, rule.getDate());
//                entityManager.close();


                String operation = String.valueOf(rule.getOperation());

                System.out.println("resOfQuery::\n"+result);
                char mappedOperation = mapOperation(operation);

                if (mappedOperation == '<') {
                    ruleisAchieved = result < rule.getAmount();
                } else if (mappedOperation == '>') {
                    ruleisAchieved = result > rule.getAmount();
                } else if (mappedOperation == '=') {
                    ruleisAchieved = result == rule.getAmount();
                }

                System.out.println("the Status of the Rule: "+ruleisAchieved);
                if(ruleisAchieved)
                {
                    Alert existingAlert = getExistingAlert(type,id, rule.getId());
                    if (existingAlert != null && !transactions.isEmpty()) {
                        existingAlert.setTransactions(transactions);
                        alertRepo.save(existingAlert);
                        return false;
                    }
                    System.out.println("the rule is achieved : )");
                    CreateAndSaveAlert(type,id, transactions, description, rule.getId());
                }
                return true;

    }

    @Override
    public long findTotalAlerts() {
        return alertRepo.count();
    }



    private List<Transaction> getTransactionsByCustomerId(long customerId, String trxType, LocalDate date) {
        String trxs = "SELECT t FROM Transaction t Where t.account1.customer.id = :id AND t.type = :type AND t.date = :dateui";
        return entityManager.createQuery(trxs, Transaction.class)
                .setParameter("id", customerId)
                .setParameter("type", trxType.toString().toLowerCase())
                .setParameter("dateui", date != null ? date : LocalDate.now())
                .getResultList();
    }
    private List<Transaction> getTransactionsByAccountId(long accountId, String trxType, LocalDate date) {
        String trxs = "SELECT t FROM Transaction t Where t.account1.id = :id AND t.type = :type AND t.date = :dateui";
        return entityManager.createQuery(trxs, Transaction.class)
                .setParameter("id", accountId)
                .setParameter("type", trxType.toString().toLowerCase())
                .setParameter("dateui", date != null ? date : LocalDate.now())
                .getResultList();
    }
    private long executeDynamicQuery(String dynamicQuery, long id, String type, LocalDate date) {
        return (long) entityManager.createQuery(dynamicQuery)
                .setParameter("id", id)
                .setParameter("type", type)
                .setParameter("dateui", date != null ? date : LocalDate.now())
                .getSingleResult();
    }
//    @Override
//    public Boolean Rule1(long id) {
//        Customer customer = customerService.findCustomerById(id);
//        if (customer.getAppliedRules().contains("Rule1")) {
//            Alert existingAlert = getExistingAlertForCustomer(customer, "Rule1");
//            List<Transaction> withdrawTransactions = transactionRepo.findAllByAccount1_Customer_IdAndTypeAndDate(id, "withdraw",LocalDate.now());
//            existingAlert.setTransactions(withdrawTransactions);
//            alertRepo.save(existingAlert);
//        }
//        else
//        {
//            List<Transaction> withdrawTransactions = transactionRepo.findAllByAccount1_Customer_IdAndTypeAndDate(id, "withdraw",LocalDate.now());
//            if (withdrawTransactions.size() >  4) {
//                customer.getAppliedRules().add("Rule1");
//                String description = "Customer " + id + " has exceeded the times (4) for 'withdraw' transactions.";
//                CreateAndSaveAlert(customer, withdrawTransactions, description, "Rule1");
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public Boolean Rule2(long id) {
//        Customer customer = customerService.findCustomerById(id);
//
//        // Check if Rule1 has already been applied
//        if (customer.getRules().contains("Rule2")) {
//            // Check if there's an existing alert for Rule1
//            Alert existingAlert = getExistingAlertForCustomer(customer, "Rule2");
//            List<Transaction> withdrawTransactions = transactionRepo.findAllByAccount1_Customer_IdAndTypeAndDate(id, "withdraw",LocalDate.now());
//            existingAlert.setTransactions(withdrawTransactions);
//            alertRepo.save(existingAlert);
//        }
//        else
//        {
//            long amount = transactionRepo.sumAmountByCustomerIdAndType(id,"withdraw",LocalDate.now());
//            if(amount >= 1000)
//            {
//
//                List<Transaction> withdrawTransactions = transactionRepo.findAllByAccount1_Customer_IdAndTypeAndDate(id, "withdraw",LocalDate.now());
//                customer.getRules().add("Rule2");
//                String description = "Customer " + id + " has exceeded the Amount (1000) for 'withdraw' transactions .";
//                CreateAndSaveAlert(customer, withdrawTransactions, description,"Rule2");
//                return true;
//            }
//
//        }
//        return false;
//
//    }


    private void CreateAndSaveAlert(String type,long id,List<Transaction> trxs,String description,long ruleID)
    {
        LocalDate currentDate = LocalDate.now();
        if ("customer".equalsIgnoreCase(type)) {
            Customer customer = customerService.findCustomerById(id);
            Alert alert = new Alert();
            alert.setDescription(description);
            alert.setRule(ruleID);
            alert.setCustomer(customer);
            alert.setTransactions(trxs);
            alert.setDate(currentDate);

            for (Transaction trx : trxs) {
                trx.getAlerts().add(alert);
            }
            ;
            alertRepo.save(alert);
        } else if ("account".equalsIgnoreCase(type)) {
            Account account = accountService.findAccountById(id);
            Alert alert = new Alert();
            alert.setDescription(description);
            alert.setRule(ruleID);
            alert.setAccount(account);
            alert.setTransactions(trxs);

            for (Transaction trx : trxs) {
                trx.getAlerts().add(alert);
            };
            alertRepo.save(alert);
        }
    }


private Alert getExistingAlert(String type,long id, long ruleID){
    if ("customer".equalsIgnoreCase(type)) {
        Customer customer = customerService.findCustomerById(id);
        if (customer != null) {
            for (Alert alert : customer.getAlerts()) {
                if (alert.getRule() == ruleID) {
                    return alert;
                }
            }
        }
    } else if ("account".equalsIgnoreCase(type)) {
        Account account = accountService.findAccountById(id);
        if (account != null) {
            for (Alert alert : account.getAlerts()) {
                if (alert.getRule() == ruleID) {
                    return alert;
                }
            }
        }
    }
    return null;
    }

    @Override
    public Map<String, Long> getAlertsCountsByDate(String dateType) {
        List<Alert> alerts = alertRepo.findAll();
        Map<String, Long> alertCount = new HashMap<>();
        if ("Daily".equalsIgnoreCase(dateType)) {
            System.out.println("iam in Daily  Alert :)");
            alertCount = alerts.stream()
                    .collect(Collectors.groupingBy(
                            transaction -> transaction.getDate().toString(),
                            Collectors.counting()
                    ));
        } else if ("Monthly".equalsIgnoreCase(dateType)) {
            System.out.println("iam in Monthly Alert :)");
            alertCount = alerts.stream()
                    .collect(Collectors.groupingBy(
                            transaction -> transaction.getDate().toString().substring(0, 7), // Extract year and month (e.g., "2023-10")
                            Collectors.counting()
                    ));
        } else if ("Yearly".equalsIgnoreCase(dateType)) {
            System.out.println("iam in Yearly Alert :)");
            alertCount = alerts.stream()
                    .collect(Collectors.groupingBy(
                            transaction -> transaction.getDate().toString().substring(0, 4), // Extract year and month (e.g., "2023-10")
                            Collectors.counting()
                    ));
        }

        Map<String, Long> sortedAlertCounts = alertCount.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));



        System.out.println("Alerts date :" + sortedAlertCounts);


        return sortedAlertCounts;
    }

}
