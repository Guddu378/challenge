package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.AmountTransferException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.LowBalanceException;
import com.db.awmd.challenge.exception.NegativeAmountTransferException;
import com.db.awmd.challenge.service.AccountsService;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsServiceTest {

  @Autowired
  private AccountsService accountsService;

  @Test
  public void addAccount() throws Exception {
    Account account = new Account("Id-123");
    account.setBalance(new BigDecimal(1000));
    this.accountsService.createAccount(account);
    assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(account);
  }

  @Test
  public void addAccount_failsOnDuplicateId() throws Exception {
    String uniqueId = "Id-" + System.currentTimeMillis();
    Account account = new Account(uniqueId);
    this.accountsService.createAccount(account);

    try {
      this.accountsService.createAccount(account);
      fail("Should have failed when adding duplicate account");
    } catch (DuplicateAccountIdException ex) {
      assertThat(ex.getMessage()).isEqualTo("Account id " + uniqueId + " already exists!");
    }
  }

  @Test
  public void transferAmount(){

      /*Create first account */
      Account firstAccount = new Account("Id-120");
      firstAccount.setBalance(new BigDecimal(1000));
      this.accountsService.createAccount(firstAccount);

      /*Create second account */
      Account secondAccount = new Account("Id-125");
      secondAccount.setBalance(new BigDecimal(2000));
      this.accountsService.createAccount(secondAccount);

      BigDecimal amountToTransfer = new BigDecimal(500);

      try {
          //call to transfer the amount
          this.accountsService.transferAmount(firstAccount.getAccountId(), secondAccount.getAccountId(), amountToTransfer);
      }catch (AmountTransferException ex){
          assertThat(ex.getMessage()).isEqualTo("There is issue while transfer the amount");
      }

      assertEquals(this.accountsService.getAccount("Id-120").getBalance(), new BigDecimal(500));
      assertEquals(this.accountsService.getAccount("Id-125").getBalance(), new BigDecimal(2500));

  }

  @Test
  public void transferAmount_failsOnLowBalance(){

      /*Create first account */
      Account firstAccount = new Account("Id-10");
      firstAccount.setBalance(new BigDecimal(1000));
      this.accountsService.createAccount(firstAccount);

      /*Create second account */
      Account secondAccount = new Account("Id-11");
      secondAccount.setBalance(new BigDecimal(2000));
      this.accountsService.createAccount(secondAccount);

      BigDecimal amountToTransfer = new BigDecimal(5000);

      try {
          //call to transfer the amount
          this.accountsService.transferAmount(firstAccount.getAccountId(), secondAccount.getAccountId(), amountToTransfer);
          fail("There is no sufficient balance to transfer on the sender account");
      }catch (LowBalanceException ex){
          assertThat(ex.getMessage()).isEqualTo("Balance is not sufficient");
      }
  }

    @Test
    public void transferAmount_failsOnNegativeBalanceTransfer(){

        /*Create first account */
        Account firstAccount = new Account("Id-13");
        firstAccount.setBalance(new BigDecimal(1000));
        this.accountsService.createAccount(firstAccount);

        /*Create second account */
        Account secondAccount = new Account("Id-14");
        secondAccount.setBalance(new BigDecimal(2000));
        this.accountsService.createAccount(secondAccount);

        BigDecimal amountToTransfer = new BigDecimal(-500);

        try {
            //call to transfer the amount
            this.accountsService.transferAmount(firstAccount.getAccountId(), secondAccount.getAccountId(), amountToTransfer);
            fail("Negative amount can not be transferred");
        }catch (NegativeAmountTransferException ex){
            assertThat(ex.getMessage()).isEqualTo("Negative amount can not be transferred");
        }
    }

}
