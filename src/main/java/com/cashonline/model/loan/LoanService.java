package com.cashonline.model.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> listLoans(Optional<Integer> userId, Integer page, Integer size) {
        return loanRepository.list(userId, page, size);
    }

    public Long count(Optional<Integer> userId) {
        return loanRepository.count(userId);
    }
}
