import com.example.bank.dto.AccountDto;
import com.example.bank.feign.FeignNotification;
import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import com.example.bank.service.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private FeignNotification feignNotification;

    @Mock
    private KafkaTemplate<String, String> kafkaProducer;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void testCreateAccount() {
        AccountDto accountDto = new AccountDto();
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(100));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto createdAccount = accountService.createAccount(accountDto);

        assertNotNull(createdAccount);
    }

    @Test
    public void testGetAccountById() {
        Long accountId = 1L;
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Optional<AccountDto> retrievedAccount = accountService.getAccountById(accountId);

        assertTrue(retrievedAccount.isPresent());
    }

    @Test
    public void testTransfer() {
        Account fromAccount = new Account();
        fromAccount.setBalance(BigDecimal.valueOf(1000));
        Account toAccount = new Account();
        toAccount.setBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.TEN;
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        accountService.transfer(1L, 2L, amount);

        assertEquals(BigDecimal.valueOf(990), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(510), toAccount.getBalance());
        verify(accountRepository, times(2)).findById(anyLong());
        verify(accountRepository).save(fromAccount);
        verify(accountRepository).save(toAccount);
        verify(feignNotification).createNotification(anyString());
        verify(kafkaProducer).send(anyString(), anyString());
    }

    @Test
    public void testWithdraw() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));
        BigDecimal amount = BigDecimal.TEN;
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.withdraw(1L, amount);

        assertEquals(BigDecimal.valueOf(990), account.getBalance());
        verify(accountRepository).findById(1L);
        verify(accountRepository).save(account);
        verify(feignNotification).createNotification(anyString());
        verify(kafkaProducer).send(anyString(), anyString());
    }

    @Test
    public void testGetAccountByNumber() {
        String accountNumber = "12345";
        Account account = new Account();
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        Optional<AccountDto> retrievedAccount = accountService.getAccountByNumber(accountNumber);

        assertTrue(retrievedAccount.isPresent());
    }

    @Test
    public void testUpdateAccountBalance() {
        Long accountId = 1L;
        BigDecimal newBalance = BigDecimal.valueOf(1000);
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        AccountDto updatedAccount = accountService.updateAccountBalance(accountId, newBalance);

        assertNotNull(updatedAccount);
        assertEquals(newBalance, updatedAccount.getBalance());
    }

    @Test
    public void testDeleteAccount() {
        Long accountId = 1L;

        accountService.deleteAccount(accountId);

        verify(accountRepository).deleteById(accountId);
        verify(feignNotification).createNotification(anyString());
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        when(accountRepository.findAll()).thenReturn(accountList);

        List<AccountDto> accounts = accountService.getAllAccounts();

        assertNotNull(accounts);
    }
}
