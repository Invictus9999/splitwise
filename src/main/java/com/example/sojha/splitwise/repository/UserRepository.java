package com.example.sojha.splitwise.repository;

import com.example.sojha.splitwise.model.entity.SplitwiseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SplitwiseUser, Long> {
}
