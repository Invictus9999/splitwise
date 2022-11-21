package com.example.sojha.splitwise.controller;

import com.example.sojha.splitwise.model.request.expense.CreateExpense;
import com.example.sojha.splitwise.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/expense/create")
    public void createExpense(@RequestBody CreateExpense request) throws Exception {
        // TODO: validate request for amount/distribution consistency

        expenseService.createExpense(request);
    }
}
