package com.cashonline.model.loan;

import com.cashonline.model.user.User;
import com.cashonline.model.user.UserRepository;
import com.cashonline.model.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class LoanServiceTest {

    @MockBean
    LoanRepository loanRepository;

    @Autowired
    LoanService loanService;

    @Test
    public void whenCountLoans_thenRepositoryIsCalled() {
        // given
        Mockito.when(loanRepository.count(Optional.empty())).thenReturn(6L);

        // when
        Long amount = loanService.count(Optional.empty());

        // then
        Assertions.assertEquals(6, amount);
        Mockito.verify(loanRepository, Mockito.times(1)).count(Optional.empty());
    }

    @Test
    public void whenListLoans_thenRepositoryIsCalled() {
        // given
        User user1 = new User();
        user1.setEmail("email");
        user1.setFirstName("name");
        user1.setLastName("lastName");

        Loan loan1 = new Loan();
        loan1.setAmount(BigDecimal.valueOf(1000));
        loan1.setUser(user1);

        List<Loan> loans = List.of(loan1);

        Mockito.when(loanRepository.list(Optional.empty(), 1, 1)).thenReturn(loans);

        // when
        List<Loan> result = loanService.listLoans(Optional.empty(), 1, 1);

        // then
        Assertions.assertEquals(loans.size(), result.size());
        Assertions.assertTrue(result.contains(loan1));
        Mockito.verify(loanRepository, Mockito.times(1)).list(Optional.empty(), 1, 1);
    }
}
