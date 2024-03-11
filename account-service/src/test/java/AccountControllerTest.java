import com.example.bank.controller.AccountController;
import com.example.bank.dto.AccountDto;
import com.example.bank.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void testCreateAccount() {
        AccountDto accountDto = new AccountDto();
        when(accountService.createAccount(any(AccountDto.class))).thenReturn(accountDto);

        ResponseEntity<AccountDto> response = accountController.createAccount(accountDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
    }

    @Test
    public void testGetAccountById() {
        Long accountId = 1L;
        AccountDto accountDto = new AccountDto();
        when(accountService.getAccountById(accountId)).thenReturn(Optional.of(accountDto));

        ResponseEntity<AccountDto> response = accountController.getAccountById(accountId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
    }

    @Test
    public void testGetAccountByNumber() {
        String accountNumber = "12345";
        AccountDto accountDto = new AccountDto();
        when(accountService.getAccountByNumber(accountNumber)).thenReturn(Optional.of(accountDto));

        ResponseEntity<AccountDto> response = accountController.getAccountByNumber(accountNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
    }

    @Test
    public void testUpdateAccountBalance() {
        Long accountId = 1L;
        BigDecimal balance = BigDecimal.TEN;
        AccountDto updatedAccount = new AccountDto();
        when(accountService.updateAccountBalance(accountId, balance)).thenReturn(updatedAccount);

        ResponseEntity<AccountDto> response = accountController.updateAccountBalance(accountId, balance);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAccount, response.getBody());
    }

    @Test
    public void testDeleteAccount() {
        ResponseEntity<Void> response = accountController.deleteAccount(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAllAccounts() {
        List<AccountDto> accountList = new ArrayList<>();
        when(accountService.getAllAccounts()).thenReturn(accountList);

        ResponseEntity<List<AccountDto>> response = accountController.getAllAccounts();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountList, response.getBody());
    }

    @Test
    public void testTransfer() {
        ResponseEntity<String> response = accountController.transfer(1L, 2L, BigDecimal.TEN);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transfer successful", response.getBody());
    }

    @Test
    public void testWithdraw() {
        ResponseEntity<String> response = accountController.withdraw(1L, BigDecimal.TEN);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Withdrawal successful", response.getBody());
    }
}
