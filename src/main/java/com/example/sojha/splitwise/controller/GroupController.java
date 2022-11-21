package com.example.sojha.splitwise.controller;

import com.example.sojha.splitwise.model.entity.ExpenseGroup;
import com.example.sojha.splitwise.model.request.group.AddMemberToGroup;
import com.example.sojha.splitwise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("/group/create")
    public void createGroup(@RequestBody ExpenseGroup expenseGroup) {
        groupService.createGroup(expenseGroup);
    }

    @PostMapping("group/add")
    public void addMembers(@RequestBody AddMemberToGroup addRequest) throws Exception {
        groupService.addMember(addRequest);
    }
}
