package com.example.DG.bank.system.service;


import com.example.DG.bank.system.Repo.AlertRepo;
import com.example.DG.bank.system.Repo.RuleRepo;
import com.example.DG.bank.system.Repo.TransactionGroupRepo;
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

    @Autowired
    private TransactionGroupRepo transactionGroupRepo;

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
        return switch (operation) {
            case "Less" -> '<';
            case "Greater" -> '>';
            case "Equal" -> '=';
            default -> '\0'; // Return null character or another special character for other cases
        };
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

    @Override
    public void applyAllRulesOnTransactions() {
        List<Rule> rules = FindAllRule();

        for (Rule rule : rules) {
            List<TransactionGroup> transactionGroups = groupTransactionsByRule(rule);

            if (transactionGroups.isEmpty()) {
                continue;
            }

            List<TransactionGroup> achievedGroups = filterAchievedRules(transactionGroups, rule);


            for (TransactionGroup group : achievedGroups) {
                System.out.println("achievedGroup: " + group);
                createAlert(group, rule, achievedGroups);
            }
        }
    }

    private List<TransactionGroup> groupTransactionsByRule(Rule rule) {
        if (rule.getRuleType() == RuleType.Customer) {
            return groupTransactionsByCustomer(rule);
        } else {
            return groupTransactionsByAccount(rule);
        }
    }

    private List<TransactionGroup> groupTransactionsByCustomer(Rule rule) {
        String transactionType = String.valueOf(rule.getTransactionType());
        String query = "SELECT t.account1.customer.id, " + rule.getAggregation() + "(t.amount) "
                + "FROM Transaction t "
                + "WHERE t.type = :transactionType "
                + "GROUP BY t.account1.customer.id";

        return executeQueryAndMapToTransactionGroup(query, transactionType, "customer");
    }

    private List<TransactionGroup> groupTransactionsByAccount(Rule rule) {
        String transactionType = String.valueOf(rule.getTransactionType());
        String query = "SELECT t.account1.id, " + rule.getAggregation() + "(t.amount) "
                + "FROM Transaction t "
                + "WHERE t.type = :transactionType "
                + "GROUP BY t.account1.id";

        return executeQueryAndMapToTransactionGroup(query, transactionType, "account");
    }

    private List<TransactionGroup> executeQueryAndMapToTransactionGroup(String query, String transactionType, String scope) {
        List<TransactionGroup> transactionGroups = new ArrayList<>();

        List<Object[]> results = entityManager.createQuery(query, Object[].class)
                .setParameter("transactionType", transactionType)
                .getResultList();

        for (Object[] result : results) {
            long id = (long) result[0];
            long aggregatedAmount = (long) result[1];

            TransactionGroup group = createTransactionGroup(scope, id, aggregatedAmount , transactionType);
            transactionGroupRepo.save(group);
            transactionGroups.add(group);
        }

        return transactionGroups;
    }

    private TransactionGroup createTransactionGroup(String scope, long id, long aggregatedAmount , String transactionType) {
        if ("customer".equals(scope)) {
            return new TransactionGroup("customer", id, 0L, aggregatedAmount, transactionType);
        } else {
            return new TransactionGroup("account", 0L, id, aggregatedAmount, transactionType);
        }
    }

    private List<TransactionGroup> filterAchievedRules(List<TransactionGroup> transactionGroups, Rule rule) {
        return transactionGroups.stream()
                .filter(group -> checkRuleAchievement(group, rule))
                .collect(Collectors.toList());
    }
    private boolean checkRuleAchievement(TransactionGroup group, Rule rule) {
        long aggregatedAmount = group.getAggregatedAmount();
        long ruleAmount = rule.getAmount();
        char operation = mapOperation(String.valueOf(rule.getOperation()));

        return switch (operation) {
            case '<' -> aggregatedAmount < ruleAmount;
            case '>' -> aggregatedAmount > ruleAmount;
            case '=' -> aggregatedAmount == ruleAmount;
            default -> false;
        };
    }


    private void createAlert(TransactionGroup group, Rule rule, List<TransactionGroup> achievedGroups) {
        String description;
        LocalDate currentDate = LocalDate.now();

        if ("customer".equals(group.getScope())) {
            description = "Customer " + group.getCustomerId() + " has achieved Rule: " + rule.getId();
        } else {
            description = "Account " + group.getAccountId() + " has achieved Rule: " + rule.getId();
        }

        Alert existingAlert = getExistingAlert(group.getScope(), group.getCustomerId(), group.getAccountId(), rule.getId());

        if (existingAlert != null) {
            // Update existing alert with new description and aggregated amount
            existingAlert.setDescription(description);
            existingAlert.setTransactionGroups(achievedGroups);
            alertRepo.save(existingAlert);
        } else {
            Alert newAlert = new Alert();
            newAlert.setDescription(description);
            newAlert.setRule(rule.getId());
            newAlert.setTransactionGroups(achievedGroups);
            newAlert.setDate(currentDate);

            if ("customer".equalsIgnoreCase(group.getScope())) {
                Customer customer = customerService.findCustomerById(group.getCustomerId());
                newAlert.setCustomer(customer);
                customer.getAlerts().add(newAlert);
                customerService.addCustomer(customer);
            } else if ("account".equalsIgnoreCase(group.getScope())) {
                Account account = accountService.findAccountById(group.getAccountId());
                newAlert.setAccount(account);
                account.getAlerts().add(newAlert);
                accountService.addAccount(account);
            }

            alertRepo.save(newAlert);
        }
    }


    private Alert getExistingAlert(String type,long customerid,long accid, long ruleID){
        if ("customer".equalsIgnoreCase(type)) {
            Customer customer = customerService.findCustomerById(customerid);
            if (customer != null) {
                for (Alert alert : customer.getAlerts()) {
                    if (alert.getRule() == ruleID) {
                        return alert;
                    }
                }
            }
        } else if ("account".equalsIgnoreCase(type)) {
            Account account = accountService.findAccountById(accid);
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

}
