package com.cashonline.model.loan;

import com.cashonline.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoanRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LoanRepository loanRepository;

    private User user1, user2;
    private Loan loan1, loan2, loan3;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setEmail("email");
        user1.setFirstName("joel");
        user1.setLastName("rugg");

        loan1 = new Loan();
        loan1.setAmount(BigDecimal.valueOf(1000));
        loan1.setUser(user1);

        loan2 = new Loan();
        loan2.setAmount(BigDecimal.valueOf(1500));
        loan2.setUser(user1);

        user2 = new User();
        user2.setEmail("email2");
        user2.setFirstName("joel2");
        user2.setLastName("rugg2");

        loan3 = new Loan();
        loan3.setAmount(BigDecimal.valueOf(400));
        loan3.setUser(user2);

        entityManager.persist(user1);
        entityManager.flush();
        entityManager.persist(loan1);
        entityManager.flush();
        entityManager.persist(user2);
        entityManager.flush();
        entityManager.persist(loan2);
        entityManager.flush();
        entityManager.persist(loan3);
        entityManager.flush();
    }

    @Test
    public void whenCountAllLoans_thenReturnCountingOfAllPersistedLoans() {
        Long loansAmount = loanRepository.count(Optional.empty());

        assertThat(3L).isEqualTo(loansAmount);
    }

    @Test
    public void whenCountLoansFilteringByUser_thenReturnCountingOfRequestedUserLoans() {
        Long loansAmount = loanRepository.count(Optional.of(user1.getId()));

        assertThat(2L).isEqualTo(loansAmount);
    }

    @Test
    public void whenListAllLoansInOnePage_thenReturnAllLoans() {
        List<Loan> loans = loanRepository.list(Optional.empty(), 1, 4);

        assertThat(3L).isEqualTo(loans.size());
        assertThat(loans.contains(loan1));
        assertThat(loans.contains(loan2));
        assertThat(loans.contains(loan3));
    }

    @Test
    public void whenListAllUserLoansInOnePage_thenReturnAllUserLoans() {
        List<Loan> loans = loanRepository.list(Optional.of(user1.getId()), 1, 4);

        assertThat(2L).isEqualTo(loans.size());
        assertThat(loans.contains(loan1));
        assertThat(loans.contains(loan2));
    }

    @Test
    public void whenListOneUserLoanByPage_thenReturnOnlyOneLoanInEachPage() {
        List<Loan> loansPage1 = loanRepository.list(Optional.of(user1.getId()), 1, 1);
        List<Loan> loansPage2 = loanRepository.list(Optional.of(user1.getId()), 2, 1);

        assertThat(1L).isEqualTo(loansPage1.size());
        assertThat(loansPage1.contains(loan1));
        assertThat(1L).isEqualTo(loansPage2.size());
        assertThat(loansPage2.contains(loan2));
    }

    @Test
    public void whenListAllUserLoansInFirstPage_thenSecondPageIsEmpty() {
        List<Loan> loansPage1 = loanRepository.list(Optional.of(user2.getId()), 1, 6);
        List<Loan> loansPage2 = loanRepository.list(Optional.of(user2.getId()), 2, 6);

        assertThat(1L).isEqualTo(loansPage1.size());
        assertThat(loansPage1.contains(loan3));
        assertThat(loansPage2.isEmpty());
    }
}
