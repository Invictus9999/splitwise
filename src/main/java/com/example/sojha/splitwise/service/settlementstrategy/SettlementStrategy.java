package com.example.sojha.splitwise.service.settlementstrategy;

import com.example.sojha.splitwise.model.entity.ExpenseDistribution;
import com.example.sojha.splitwise.model.entity.ExpenseSettlement;

import java.util.List;

public interface SettlementStrategy {
    public List<ExpenseSettlement> settle(List<ExpenseDistribution> distributions) throws Exception;
}
