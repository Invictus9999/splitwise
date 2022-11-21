package com.example.sojha.splitwise.service.settlementstrategy;

import com.example.sojha.splitwise.model.entity.ExpenseDistribution;
import com.example.sojha.splitwise.model.entity.ExpenseSettlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettlementService {

    @Autowired
    @Qualifier("simple")
    private SettlementStrategy settlementStrategy;

    public List<ExpenseSettlement> generateSettlements(List<ExpenseDistribution> distributions) throws Exception {
        return settlementStrategy.settle(distributions);
    }
}
