package com.example.sojha.splitwise.repository;

import com.example.sojha.splitwise.model.entity.ExpenseGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<ExpenseGroup, Long> {
}
