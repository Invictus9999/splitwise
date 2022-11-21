package com.example.sojha.splitwise.model.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@NoArgsConstructor
public class ExpenseSettlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false)
    @Setter
    private Expense expense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_id", nullable = false)
    private SplitwiseUser payer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payee_id", nullable = false)
    private SplitwiseUser payee;

    @Column(nullable = false)
    private Long amount;

    public ExpenseSettlement(
            final SplitwiseUser payer,
            final SplitwiseUser payee,
            final Long amount
    ) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
    }
}
