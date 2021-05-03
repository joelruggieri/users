package com.cashonline.model.loan;

import com.cashonline.model.user.User;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.ManyToOne;
import javax.persistence.Embedded;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @Embedded
    private User user;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Integer getUserId() { return user.getId(); }

}
