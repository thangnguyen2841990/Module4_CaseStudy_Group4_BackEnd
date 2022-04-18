package com.codegym.module4casestudy.model.dto;

import com.codegym.module4casestudy.model.entity.Group;
import com.codegym.module4casestudy.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "group_members")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean IfAdmin;
    private boolean block;
    private boolean status;

    @ManyToOne
    private UserInfo userInfo;

    @ManyToOne
    private Group group;

    public GroupMember(boolean ifAdmin, boolean block, boolean status, UserInfo userInfo, Group group) {
        IfAdmin = ifAdmin;
        this.block = block;
        this.status = status;
        this.userInfo = userInfo;
        this.group = group;
    }
}

