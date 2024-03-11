import com.example.bank.dto.TransactionDto;
import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionType;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.service.TransactionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testCreateTransaction() {
        TransactionDto dto = new TransactionDto();
        Transaction transaction = new Transaction();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionDto createdTransaction = transactionService.createTransaction(dto);

        assertNotNull(createdTransaction);
    }

    @Test
    public void testGetAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        when(transactionRepository.findAll()).thenReturn(transactionList);

        List<TransactionDto> retrievedTransactions = transactionService.getAllTransactions();

        assertNotNull(retrievedTransactions);
    }

    @Test
    public void testGetTransactionById() {
        Long id = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));

        Optional<TransactionDto> retrievedTransaction = transactionService.getTransactionById(id);

        assertNotNull(retrievedTransaction);
    }

    @Test
    public void testGetTransactionsByAccountId() {
        Long accountId = 1L;
        List<Transaction> transactionList = new ArrayList<>();
        when(transactionRepository.findByAccountId(accountId)).thenReturn(transactionList);

        List<TransactionDto> retrievedTransactions = transactionService.getTransactionsByAccountId(accountId);

        assertNotNull(retrievedTransactions);
    }

    @Test
    public void testGetTransactionsByType() {
        TransactionType type = TransactionType.WITHDRAWAL;
        List<Transaction> transactionList = new ArrayList<>();
        when(transactionRepository.findByType(type)).thenReturn(transactionList);

        List<TransactionDto> retrievedTransactions = transactionService.getTransactionsByType(type);

        assertNotNull(retrievedTransactions);
    }

    @Test
    public void testGetTransactionsByAccountIdAndType() {
        Long accountId = 1L;
        TransactionType type = TransactionType.TRANSFER;
        List<Transaction> transactionList = new ArrayList<>();
        when(transactionRepository.findByAccountIdAndType(accountId, type)).thenReturn(transactionList);

        List<TransactionDto> retrievedTransactions = transactionService.getTransactionsByAccountIdAndType(accountId, type);

        assertNotNull(retrievedTransactions);
    }

    @Test
    public void testDeleteTransaction() {
        transactionService.deleteTransaction(1L);

        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAllTransactions() {
        transactionService.deleteAllTransactions();

        verify(transactionRepository, times(1)).deleteAll();
    }
}
