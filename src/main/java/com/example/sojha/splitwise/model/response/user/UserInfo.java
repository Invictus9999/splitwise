package com.example.sojha.splitwise.model.response.user;

import com.example.sojha.splitwise.model.entity.ExpenseGroup;
import com.example.sojha.splitwise.model.entity.SplitwiseUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfo {
    private Long id;
    private String name;
    private String email;

    private List<GroupInfo> groups;

    public UserInfo(SplitwiseUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();

        this.groups = new ArrayList<>();

        for(ExpenseGroup group: user.getMemberships()) {
            this.groups.add(new GroupInfo(group.getId(), group.getName()));
        }
    }

    @Data
    @AllArgsConstructor
    class GroupInfo {
        private Long id;
        private String name;
    }
}
