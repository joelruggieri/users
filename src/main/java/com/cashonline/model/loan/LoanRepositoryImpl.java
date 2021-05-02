package com.cashonline.model.loan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoanRepositoryImpl implements LoanRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Loan> list(Optional<Integer> userId, Integer page, Integer size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Loan> cq = cb.createQuery(Loan.class);
        Root<Loan> loan = cq.from(Loan.class);
        Path<Integer> userIdValue = loan.get("user").get("id");
        List<Predicate> predicates = new ArrayList<>();

        userId.ifPresent(id -> predicates.add(cb.equal(userIdValue, id)));

        cq.select(loan)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        int offset = (page - 1) * size;

        return entityManager.createQuery(cq).setFirstResult(offset).setMaxResults(size).getResultList();
    }


    @Override
    public Long count(Optional<Integer> userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Loan> loan = cq.from(Loan.class);
        Path<Integer> userIdValue = loan.get("user").get("id");
        List<Predicate> predicates = new ArrayList<>();
        userId.ifPresent(id -> predicates.add(cb.equal(userIdValue, id)));

        cq.select(cb.count(loan))
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(cq).getSingleResult();
    }
}
