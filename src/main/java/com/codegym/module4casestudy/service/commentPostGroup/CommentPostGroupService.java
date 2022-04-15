package com.codegym.module4casestudy.service.commentPostGroup;

import com.codegym.module4casestudy.model.entity.CommentPostGroup;
import com.codegym.module4casestudy.repository.ICommentPostGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentPostGroupService implements ICommentPostGroupService {
    @Autowired
    private ICommentPostGroupRepository commentPostGroupRepository;
    @Override
    public Page<CommentPostGroup> findAll(Pageable pageable) {
        return commentPostGroupRepository.findAll(pageable);
    }

    @Override
    public Optional<CommentPostGroup> findById(Long id) {
        return commentPostGroupRepository.findById(id);
    }

    @Override
    public CommentPostGroup save(CommentPostGroup commentPostGroup) {
        return commentPostGroupRepository.save(commentPostGroup);
    }

    @Override
    public void deleteById(Long id) {
        commentPostGroupRepository.deleteById(id);
    }

    @Override
    public List<CommentPostGroup> displayAllCommentOfPostGroup(Long postGroupId) {
        return commentPostGroupRepository.displayAllCommentOfPostGroup(postGroupId);
    }
}
