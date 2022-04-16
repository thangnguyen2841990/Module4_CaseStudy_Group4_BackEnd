package com.codegym.module4casestudy.model.dto;

import com.codegym.module4casestudy.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostGroupFrontEnd {
    private Long postGroupId;

    private String content;

    private ImagePostGroup[] listImage;

    private Date dateCreated;

    private GroupMember groupMember;

    private Integer totalLike;

    private List<CommentPostGroup> comments;

    private int totalComments;

    private Group group;

    public PostGroupFrontEnd(Long postGroupId, String content, ImagePostGroup[] listImage, Date dateCreated, GroupMember groupMember) {
        this.postGroupId = postGroupId;
        this.content = content;
        this.listImage = listImage;
        this.dateCreated = dateCreated;
        this.groupMember = groupMember;
    }

    public PostGroupFrontEnd(Long postGroupId, String content, ImagePostGroup[] listImage, Date dateCreated, GroupMember groupMember, Group group) {
        this.postGroupId = postGroupId;
        this.content = content;
        this.listImage = listImage;
        this.dateCreated = dateCreated;
        this.groupMember = groupMember;
        this.group = group;
    }
}
