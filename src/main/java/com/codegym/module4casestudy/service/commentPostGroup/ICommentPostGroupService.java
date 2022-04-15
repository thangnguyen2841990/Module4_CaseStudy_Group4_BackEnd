package com.codegym.module4casestudy.service.commentPostGroup;

import com.codegym.module4casestudy.model.entity.CommentPostGroup;
import com.codegym.module4casestudy.service.IGeneralService;

import java.util.List;

public interface ICommentPostGroupService extends IGeneralService<CommentPostGroup> {
    List<CommentPostGroup> displayAllCommentOfPostGroup(Long postGroupId);
}
