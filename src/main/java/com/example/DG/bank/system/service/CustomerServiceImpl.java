package com.example.DG.bank.system.service;

import com.example.DG.bank.system.Repo.CustomerRepo;
import com.example.DG.bank.system.dto.DepositRequest;
import com.example.DG.bank.system.dto.TransferRequest;
import com.example.DG.bank.system.exception.UserNotFoundException;
import com.example.DG.bank.system.model.Account;
import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.model.Transaction;
import com.example.DG.bank.system.model.enums.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

//    @Autowired
//    private TransactionService transactionService;
//    @Autowired
//    private AccountService accountService;
    private CustomerRepo customerRepo;

    @Autowired
    CustomerServiceImpl(CustomerRepo customerRepo)
    {
        this.customerRepo = customerRepo;
    }
    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public List<Customer> FindAllCustomer() {
        return customerRepo.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepo.deleteById(id);
    }

    @Override
    public Customer findCustomerById(long id) {
        return customerRepo.findCustomerById(id).orElseThrow(() -> new UserNotFoundException("Customer By Id " + id + "is not Found"));
    }

    @Override
    public long findTotalCustomers() {
        return customerRepo.count();
    }

//    @Override
//    public Transaction tranfer(TransferRequest transferRequest) {
//        //accountA
//        LocalDate currentDate = LocalDate.now();
//        String desc = null;
//        String status = null ;
//        Transaction trx = new Transaction();
//        Account account_A = accountService.findAccountById(transferRequest.getAccountA().getId());
//        Account account_B = accountService.findAccountById(transferRequest.getAccountB().getId());
//
//        if (account_A.getBalance() <= 0) {
//            desc = "this transaction cannot be done as your balance is :  " + account_A.getBalance();
//            status = "Failed";
//
//        } else if (account_A.getBalance() < transferRequest.getAmount()) {
//            desc = "this transaction cannot be done as your balance is :  " + account_A.getBalance() + "    less than   " + transferRequest.getAmount();
//            status = "Failed";
//        } else if (account_B != null) {
//            account_A.setBalance(account_A.getBalance() - transferRequest.getAmount());
//            account_B.setBalance(account_B.getBalance() + transferRequest.getAmount());
//            desc = "Transaction successfully completed from Account A (ID: " + account_A.getId() + ") to Account B (ID: " + account_B.getId() + ") by transferring an amount of " + transferRequest.getAmount() + ".";
//            status = "Successful";
//        } else if (account_B == null) {
//            account_A.setBalance(account_A.getBalance() - transferRequest.getAmount());
//            status = "Successful";
//            desc = "Transaction successfully completed from Account A (ID: " + account_A.getId() + ") to Account B (ID: " + account_B.getId() + ") by transferring an amount of " + transferRequest.getAmount() + ".";
//        }
//        trx.setStatus(status);
//        trx.setDescription(desc);
//        trx.setAmount(transferRequest.getAmount());
//        trx.setType("transfer");
//        trx.setAccount(account_A.getId());
//         trx.setAnother_account(account_B.getId());
//
//        trx.setDate(currentDate);
//        transactionService.addTransaction(trx);
//
//
//        return trx;
//
//    }
//
//    @Override
//    public Transaction deposite(DepositRequest depositRequest) {
//        LocalDate currentDate = LocalDate.now();
//        String desc = null;
//        String status  = null;
//        Transaction trx = new Transaction();
//        Account account_A = accountService.findAccountById(depositRequest.getAccountA().getId());
//        if(account_A != null)
//        {
//            status = "Successful";
//            account_A.setBalance(account_A.getBalance() + depositRequest.getAmount());
//            desc = "Deposit successfully completed. Your funds have been added to your account.";
//        }
//        else{
//            desc = "Account is not exist in system";
//            status = "Failed";
//        }
//        trx.setStatus(status);
//        trx.setDescription(desc);
//        trx.setAmount(depositRequest.getAmount());
//        trx.setType("deposite");
//        trx.setAccount(account_A.getId());
//        trx.setAnother_account(null);
//        trx.setDate(currentDate);
//        transactionService.addTransaction(trx);
//        return trx;
//    }
//
//    @Override
//    public Transaction withdraw(DepositRequest depositRequest) {
//        LocalDate currentDate = LocalDate.now();
//        String desc = null;
//        String status = null;
//        Transaction trx = new Transaction();
//        Account account_A = accountService.findAccountById(depositRequest.getAccountA().getId());
//        if(account_A != null)
//        {
//            if(account_A.getBalance() >= depositRequest.getAmount() )
//            {
//                status = "Successful";
//                account_A.setBalance(account_A.getBalance() - depositRequest.getAmount());
//                desc = "Withdrawal successfully processed. Your requested amount has been deducted from your account.";
//            }
//           else{
//                status = "Failed";
//                desc = "Insufficient funds. Your account balance is not sufficient to complete this withdrawal request. Please make sure you have enough funds in your account to proceed.";
//            }
//
//        }
//        else{
//            desc = "Account is not exist in system";
//            status = "Failed";
//        }
//        trx.setStatus(status);
//        trx.setDescription(desc);
//        trx.setAmount(depositRequest.getAmount());
//        trx.setType("withdraw");
//        trx.setAccount(account_A.getId());
//        trx.setAnother_account(null);
//        trx.setDate(currentDate);
//        transactionService.addTransaction(trx);
//        return trx;
//    }

//    @Override
//    public Customer getCustomerByAccountId(long id) {
//       Customer customer =  customerRepo.findCustomerByAccountId(id);
//        return customer;
//    }

}
