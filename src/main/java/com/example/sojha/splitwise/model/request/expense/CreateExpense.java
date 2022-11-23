package com.example.sojha.splitwise.model.request.expense;

import com.example.sojha.splitwise.constant.ExpenseDistributionStatus;
import com.example.sojha.splitwise.model.entity.ExpenseDistribution;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class CreateExpense {
    private String description;
    private Long amount;
    private Optional<Long> groupId = Optional.empty();
    private List<Distribution> distributions = new ArrayList<>();

    @Data
    public static class Distribution {
        private Long userId;
        private ExpenseDistributionStatus status;
        private Long amount;
    }
}
