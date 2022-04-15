package com.codegym.module4casestudy.model.entity;

import com.codegym.module4casestudy.model.dto.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Date dateCreated;
    private boolean status;

    @ManyToOne
    private GroupMember groupMember;

    public PostGroup(String content, Date dateCreated, boolean status, GroupMember groupMember) {
        this.content = content;
        this.dateCreated = dateCreated;
        this.status = status;
        this.groupMember = groupMember;
    }
}
