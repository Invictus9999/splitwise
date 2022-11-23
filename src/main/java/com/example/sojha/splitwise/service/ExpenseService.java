package com.example.sojha.splitwise.service;

import com.example.sojha.splitwise.model.entity.*;
import com.example.sojha.splitwise.model.request.expense.CreateExpense;
import com.example.sojha.splitwise.service.settlementstrategy.SettlementService;
import com.example.sojha.splitwise.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private BalanceService balanceService;

    @Transactional(rollbackOn = {Exception.class})
    public void createExpense(CreateExpense request) throws Exception {
        // Group is optional for a new expense
        Optional<ExpenseGroup> groupOptional = groupService.findById(request.getGroupId());

        // Create expense
        Expense expense = new Expense(request.getDescription(), request.getAmount(), groupOptional.orElse(null));

        // Add distributions to expense
        for(CreateExpense.Distribution distribution: request.getDistributions()) {
            Optional<SplitwiseUser> userOptional = userService.findById(distribution.getUserId());

            if (userOptional.isPresent()) {
                ExpenseDistribution expenseDistribution = new ExpenseDistribution(
                        userOptional.get(),
                        distribution.getStatus(),
                        distribution.getAmount()
                );

                expense.addDistribution(expenseDistribution);
            }
            else {
                throw new Exception(String.format("userId: %s not found", distribution.getUserId()));
            }
        }

        // Add settlements to expense
        List<ExpenseSettlement> settlements = settlementService.generateSettlements(expense.getDistributions());
        settlements.forEach(s -> expense.addSettlement(s));

        // Save expense.
        expenseRepository.save(expense);

        // Update Balances
        for (ExpenseSettlement s: settlements) {
            Long newBalance = balanceService.addAmountToBalance(
                    s.getPayer(),
                    s.getPayee(),
                    s.getAmount(),
                    groupOptional.orElse(null)
            );
            System.out.println(
                    String.format("Balance changed from %s to %s", newBalance-s.getAmount(), newBalance)
            );
        }
    }
}
