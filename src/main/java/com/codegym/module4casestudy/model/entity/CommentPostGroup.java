package com.codegym.module4casestudy.model.entity;

import com.codegym.module4casestudy.model.dto.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostGroup {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String content;

        @ManyToOne
    private PostGroup postGroup;

        @ManyToOne
    private GroupMember groupMember;

    public CommentPostGroup(String content, PostGroup postGroup, GroupMember groupMember) {
        this.content = content;
        this.postGroup = postGroup;
        this.groupMember = groupMember;
    }
}
