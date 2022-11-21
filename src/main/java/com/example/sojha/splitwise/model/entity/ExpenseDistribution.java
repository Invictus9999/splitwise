package com.example.sojha.splitwise.model.entity;

import com.example.sojha.splitwise.constant.ExpenseDistributionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ExpenseDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false)
    @Setter
    private Expense expense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private SplitwiseUser user;

    @Enumerated(EnumType.STRING)
    @Column(length = 4)
    private ExpenseDistributionStatus status;

    @Column(nullable = false)
    private Long amount;

    public ExpenseDistribution(
            final SplitwiseUser user,
            final ExpenseDistributionStatus status,
            final Long amount
    ) {
        this.user = user;
        this.amount = amount;
        this.status = status;
    }
}
