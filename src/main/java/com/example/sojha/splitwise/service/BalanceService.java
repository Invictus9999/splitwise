package com.example.sojha.splitwise.service;

import com.example.sojha.splitwise.model.entity.*;
import com.example.sojha.splitwise.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;

    public Long addAmountToBalance(
            SplitwiseUser firstUser,
            SplitwiseUser secondUser,
            Long amount,
            ExpenseGroup group
    ) throws Exception {
        Pair<SplitwiseUser, SplitwiseUser> sortedById = sortById(firstUser, secondUser);

        if (firstUser != sortedById.getFirst()) {
            firstUser = sortedById.getFirst();
            secondUser = sortedById.getSecond();
            amount = amount * (-1);
        }

        Balance currentBalance = findExistingOrCreateEmptyBalance(firstUser, secondUser, group);

        currentBalance.addToAmount(amount);
        balanceRepository.save(currentBalance);

        return currentBalance.getAmount();
    }

    private Balance findExistingOrCreateEmptyBalance(
            SplitwiseUser firstUser,
            SplitwiseUser secondUser,
            ExpenseGroup group
    ) {
        Optional<Balance> existingBalance;

        if (group != null) {
            existingBalance = balanceRepository.findByFirstUserIdAndSecondUserIdAndGroupId(
                    firstUser.getId(),
                    secondUser.getId(),
                    group.getId()
            );
        }
        else {
            existingBalance = balanceRepository.findByFirstUserIdAndSecondUserIdAndGroupIsNull(
                    firstUser.getId(),
                    secondUser.getId()
            );
        }

        if (existingBalance.isPresent())
            return existingBalance.get();

        return new Balance(firstUser, secondUser, group, 0L);
    }

    private Pair<SplitwiseUser, SplitwiseUser> sortById(SplitwiseUser firstUser, SplitwiseUser secondUser) throws Exception {
        if (firstUser.getId() == secondUser.getId()) {
            throw new Exception("Both Users cannot have the same id");
        }

        if (firstUser.getId() > secondUser.getId()) {
            return Pair.of(secondUser, firstUser);
        }

        return Pair.of(firstUser, secondUser);
    }
}
