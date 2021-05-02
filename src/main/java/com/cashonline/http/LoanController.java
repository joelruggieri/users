package com.cashonline.http;

import com.cashonline.http.output.LoanResponse;
import com.cashonline.http.output.LoansPaginatedResponse;
import com.cashonline.http.output.PageResponse;
import com.cashonline.model.loan.Loan;
import com.cashonline.model.loan.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Validated
@RequestMapping(path="/loans")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @GetMapping()
    public @ResponseBody
    ResponseEntity findLoans(@RequestParam(name = "page") @Min(1) Integer page,
                              @RequestParam(name = "size") @Min(1) Integer size,
                              @RequestParam(name = "user_id") Optional<Integer> userId) {

        List<Loan> loans = loanRepository.list(userId, page, size);
        Long total = loanRepository.count(userId);
        List<LoanResponse> loansResponses = loans.stream().map(loan -> {
            LoanResponse loanResponse = new LoanResponse();
            loanResponse.setId(loan.getId());
            loanResponse.setUserId(loan.getUserId());
            loanResponse.setTotal(loan.getAmount().doubleValue());
            return  loanResponse;
        }).collect(Collectors.toList());

        PageResponse pageResponse = new PageResponse();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotal(total.intValue());

        LoansPaginatedResponse loansResponse = new LoansPaginatedResponse();
        loansResponse.setLoans(loansResponses);
        loansResponse.setPage(pageResponse);

        return ResponseEntity.ok(loansResponse);
    }
}