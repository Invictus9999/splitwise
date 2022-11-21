package com.example.sojha.splitwise.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class SplitwiseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "members")
    private Set<ExpenseGroup> memberships = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ExpenseDistribution> expenseDistributions;

    public SplitwiseUser(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    // TODO: override equals and hashCode for correct removal from ExpenseGroup
    // when the removeMember function is implemented on it.
    // https://vladmihalcea.com/hibernate-facts-equals-and-hashcode/
}
