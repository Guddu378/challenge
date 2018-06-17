package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.AmountTransferException;
import com.db.awmd.challenge.exception.NegativeAmountTransferException;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;

  @Autowired
  private EmailNotificationService emailNotificationService;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }

  public void transferAmount(String senderAccId, String receiverAccId, BigDecimal amount) throws AmountTransferException {
    if(amount.compareTo(BigDecimal.ZERO) <= 0){
      throw new NegativeAmountTransferException("Negative amount can not be transferred");
    }

    boolean flag = this.accountsRepository.transferAmount(senderAccId,receiverAccId, amount);
    if(flag){
      Account sendAccount = this.getAccount(senderAccId);
      emailNotificationService.notifyAboutTransfer(sendAccount, "Your account is debited by " + amount + " and new balance is " + sendAccount.getBalance());
      Account receiverAccount = this.getAccount(receiverAccId);
      emailNotificationService.notifyAboutTransfer(receiverAccount, "Your account is credited by " + amount + " and new balance is " + receiverAccount.getBalance());
    }
  }
}
