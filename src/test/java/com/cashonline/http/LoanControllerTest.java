package com.cashonline.http;

import com.cashonline.http.output.LoansPaginatedResponse;
import com.cashonline.model.loan.Loan;
import com.cashonline.model.loan.LoanService;
import com.cashonline.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class LoanControllerTest {

    @MockBean
    LoanService loanService;

    @Autowired
    LoanController loanController;

    @Test
    public void whenFindLoans_thenOkIsReturnedWithPaginatedLoansList() {
        // given
        User user1 = new User();
        user1.setId(2);
        user1.setEmail("email");
        user1.setFirstName("name");
        user1.setLastName("lastName");

        Loan loan1 = new Loan();
        loan1.setAmount(BigDecimal.valueOf(1000));
        loan1.setUser(user1);

        List<Loan> loans = List.of(loan1);

        Mockito.when(loanService.count(Optional.of(1))).thenReturn(2L);
        Mockito.when(loanService.listLoans(Optional.of(1), 1, 1)).thenReturn(loans);

        // when
        ResponseEntity response = loanController.findLoans(1, 1, Optional.of(1));
        LoansPaginatedResponse paginatedResponse = ((LoansPaginatedResponse) response.getBody());

        // then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, paginatedResponse.getPaging().getPage());
        Assertions.assertEquals(1, paginatedResponse.getPaging().getSize());
        Assertions.assertEquals(loans.size(), paginatedResponse.getLoans().size());
        Assertions.assertEquals(loan1.getId(), paginatedResponse.getLoans().get(0).getId());
        Assertions.assertEquals(loan1.getAmount().doubleValue(), paginatedResponse.getLoans().get(0).getTotal());
        Mockito.verify(loanService, Mockito.times(1))
                .count(Optional.of(1));
        Mockito.verify(loanService, Mockito.times(1))
                .listLoans(Optional.of(1), 1, 1);
    }
}
