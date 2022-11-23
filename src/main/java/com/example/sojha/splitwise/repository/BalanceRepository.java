package com.example.sojha.splitwise.repository;

import com.example.sojha.splitwise.model.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    public Optional<Balance> findByFirstUserIdAndSecondUserIdAndGroupId(Long firstUserId, Long secondUserId, Long groupId);
    public Optional<Balance> findByFirstUserIdAndSecondUserIdAndGroupIsNull(Long firstUserId, Long secondUserId);
}
