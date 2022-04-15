package com.codegym.module4casestudy.model.entity;

import com.codegym.module4casestudy.model.dto.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikePostGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean status;

    @ManyToOne
    private PostGroup postGroup;

    @ManyToOne
    private GroupMember groupMember;

    public LikePostGroup(boolean status, PostGroup postGroup, GroupMember groupMember) {
        this.status = status;
        this.postGroup = postGroup;
        this.groupMember = groupMember;
    }
}
