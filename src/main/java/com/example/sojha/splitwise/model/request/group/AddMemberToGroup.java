package com.example.sojha.splitwise.model.request.group;

import lombok.Data;

@Data
public class AddMemberToGroup {
    private Long groupId;
    private Long[] memberIds;
}
