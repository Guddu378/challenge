package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.AmountTransferException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.db.awmd.challenge.exception.LowBalanceException;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

  private final Map<String, Account> accounts = new ConcurrentHashMap<>();

  @Override
  public void createAccount(Account account) throws DuplicateAccountIdException {
    Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
    if (previousAccount != null) {
      throw new DuplicateAccountIdException(
        "Account id " + account.getAccountId() + " already exists!");
    }
  }

  @Override
  public Account getAccount(String accountId) {
    return accounts.get(accountId);
  }

  @Override
  public void clearAccounts() {
    accounts.clear();
  }

  @Override
  public boolean transferAmount(String senderAccId, String receiverAccId, BigDecimal amount) throws AmountTransferException {
      boolean flag = false;
      ReadWriteLock lock = new ReentrantReadWriteLock();
      lock.writeLock().lock();
      try {
          Account senderAccount = accounts.get(senderAccId);
          BigDecimal balance = senderAccount.getBalance().subtract(amount);
          if( balance.compareTo(BigDecimal.ZERO)  < 0){
              throw new LowBalanceException("Balance is not sufficient");
          }
          senderAccount.setBalance(balance);
          Account receiverAccount = accounts.get(receiverAccId);
          receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
          flag = true;
      }finally {
          lock.writeLock().unlock();
      }
      return flag;
  }
}
