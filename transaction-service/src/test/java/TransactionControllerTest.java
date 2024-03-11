import com.example.bank.controller.TransactionController;
import com.example.bank.dto.TransactionDto;
import com.example.bank.model.TransactionType;
import com.example.bank.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void testCreateTransaction() {
        // Mock
        TransactionDto dto = new TransactionDto();
        when(transactionService.createTransaction(any(TransactionDto.class))).thenReturn(dto);

        // Test
        ResponseEntity<TransactionDto> response = transactionController.createTransaction(dto);

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetAllTransactions() {
        // Mock
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        when(transactionService.getAllTransactions()).thenReturn(transactionDtoList);

        // Test
        List<TransactionDto> retrievedTransactions = transactionController.getAllTransactions();

        // Verify
        assertNotNull(retrievedTransactions);
        assertEquals(transactionDtoList, retrievedTransactions);
    }

    @Test
    public void testGetTransactionById() {
        // Mock
        Long id = 1L;
        TransactionDto dto = new TransactionDto();
        when(transactionService.getTransactionById(id)).thenReturn(Optional.of(dto));

        // Test
        ResponseEntity<TransactionDto> response = transactionController.getTransactionById(id);

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetTransactionsByAccountId() {
        // Mock
        Long accountId = 1L;
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        when(transactionService.getTransactionsByAccountId(accountId)).thenReturn(transactionDtoList);

        // Test
        List<TransactionDto> retrievedTransactions = transactionController.getTransactionsByAccountId(accountId);

        // Verify
        assertNotNull(retrievedTransactions);
        assertEquals(transactionDtoList, retrievedTransactions);
    }

    @Test
    public void testGetTransactionsByType() {
        // Mock
        String type = "TRANSFER";
        TransactionType transactionType = TransactionType.TRANSFER;
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        when(transactionService.getTransactionsByType(transactionType)).thenReturn(transactionDtoList);

        // Test
        List<TransactionDto> retrievedTransactions = transactionController.getTransactionsByType(type);

        // Verify
        assertNotNull(retrievedTransactions);
        assertEquals(transactionDtoList, retrievedTransactions);
    }

    @Test
    public void testGetTransactionsByAccountIdAndType() {
        // Mock
        Long accountId = 1L;
        String type = "WITHDRAWAL";
        TransactionType transactionType = TransactionType.WITHDRAWAL;
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        when(transactionService.getTransactionsByAccountIdAndType(accountId, transactionType)).thenReturn(transactionDtoList);

        // Test
        List<TransactionDto> retrievedTransactions = transactionController.getTransactionsByAccountIdAndType(accountId, type);

        // Verify
        assertNotNull(retrievedTransactions);
        assertEquals(transactionDtoList, retrievedTransactions);
    }

    @Test
    public void testDeleteTransaction() {
        // Test
        ResponseEntity<Void> response = transactionController.deleteTransaction(1L);

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(transactionService, times(1)).deleteTransaction(1L);
    }

    @Test
    public void testDeleteAllTransactions() {
        // Test
        ResponseEntity<Void> response = transactionController.deleteAllTransactions();

        // Verify
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(transactionService, times(1)).deleteAllTransactions();
    }
}
