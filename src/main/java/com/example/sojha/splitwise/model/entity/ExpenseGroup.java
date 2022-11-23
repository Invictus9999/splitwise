package com.example.sojha.splitwise.model.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class ExpenseGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_membership",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<SplitwiseUser> members = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private Set<Expense> expenses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private Set<Balance> balances = new HashSet<>();
}
