package com.example.sojha.splitwise.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = true)
    private ExpenseGroup group;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "expense")
    @Getter
    private List<ExpenseDistribution> distributions = new ArrayList<>(); // TODO: Use Set<> instead of List<>

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "expense")
    private List<ExpenseSettlement> settlements = new ArrayList<>(); // TODO: Use Set<> instead of List<>

    public Expense(final String description, final Long amount, final ExpenseGroup group) {
        this.description = description;
        this.amount = amount;
        this.group = group;
    }

    public void addDistribution(ExpenseDistribution distribution) {
        distributions.add(distribution);
        distribution.setExpense(this);
    }

    public void removeDistribution(ExpenseDistribution distribution) {
        // TODO: Use of List makes this inefficient
        // https://vladmihalcea.com/hibernate-facts-favoring-sets-vs-bags/
        distributions.remove(distribution);
        distribution.setExpense(null);
    }

    public void addSettlement(ExpenseSettlement settlement) {
        settlements.add(settlement);
        settlement.setExpense(this);
    }

    public void removeSettlement(ExpenseSettlement settlement) {
        // TODO: Use of List makes this inefficient
        // https://vladmihalcea.com/hibernate-facts-favoring-sets-vs-bags/
        settlements.remove(settlement);
        settlement.setExpense(null);
    }

    // TODO: override equals and hashCode for correct removal from ExpenseGroup
    // when the removeMember function is implemented on it.
    // https://vladmihalcea.com/hibernate-facts-equals-and-hashcode/
}
