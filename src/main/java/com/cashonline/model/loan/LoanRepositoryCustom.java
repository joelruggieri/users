package com.cashonline.model.loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepositoryCustom {
    List<Loan> list(Optional<Integer> userId, Integer page, Integer size);

    Long count(Optional<Integer> userId);
}
