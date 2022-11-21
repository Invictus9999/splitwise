package com.example.sojha.splitwise.service;

import com.example.sojha.splitwise.model.entity.ExpenseGroup;
import com.example.sojha.splitwise.model.entity.SplitwiseUser;
import com.example.sojha.splitwise.model.request.group.AddMemberToGroup;
import com.example.sojha.splitwise.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserService userService;

    public void createGroup(ExpenseGroup expenseGroup) {
        groupRepository.save(expenseGroup);
    }

    public void addMember(AddMemberToGroup addRequest) throws Exception {
        Optional<ExpenseGroup> optionalExpenseGroup = groupRepository.findById(addRequest.getGroupId());

        if (optionalExpenseGroup.isPresent()) {
            ExpenseGroup group = optionalExpenseGroup.get();

            for(Long memberId: addRequest.getMemberIds()) {
                Optional<SplitwiseUser> userOptional = userService.findById(memberId);

                if (userOptional.isPresent()) {
                    group.getMembers().add(userOptional.get());
                }
            }

            groupRepository.save(group);
        }
        else {
            throw new Exception("GroupId not found");
        }
    }

    public Optional<ExpenseGroup> findById(Long id) {
        return groupRepository.findById(id);
    }
}
