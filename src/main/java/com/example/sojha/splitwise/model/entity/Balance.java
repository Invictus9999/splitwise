package com.example.sojha.splitwise.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_user_id", nullable = false)
    @Getter
    private SplitwiseUser firstUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_user_id", nullable = false)
    @Getter
    private SplitwiseUser secondUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = true)
    private ExpenseGroup group;

    /**
     * firstUser owes this amount to the secondUser.
     */
    @Column(nullable = false)
    @Getter
    private Long amount;

    @Version
    private Long version;

    public Balance(
            final SplitwiseUser firstUser,
            final SplitwiseUser secondUser,
            final ExpenseGroup group,
            final Long amount
    ) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.group = group;
        this.amount = amount;
    }

    public Long addToAmount(final Long amount) {
        this.amount += amount;
        return amount;
    }
}
