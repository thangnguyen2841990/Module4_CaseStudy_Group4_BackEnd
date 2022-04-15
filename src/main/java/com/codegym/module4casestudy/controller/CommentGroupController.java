package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.dto.GroupMember;
import com.codegym.module4casestudy.model.entity.CommentPostGroup;
import com.codegym.module4casestudy.model.entity.PostGroup;
import com.codegym.module4casestudy.service.commentPostGroup.ICommentPostGroupService;
import com.codegym.module4casestudy.service.groupMember.IGroupMemberService;
import com.codegym.module4casestudy.service.postgroup.IPostGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cmt_group")
@CrossOrigin("*")
public class CommentGroupController {
    @Autowired
    private ICommentPostGroupService commentPostGroupService;

    @Autowired
    private IPostGroupService postGroupService;

    @Autowired
    private IGroupMemberService groupMemberService;

    @GetMapping("/{postGroupId}")
    private ResponseEntity<List<CommentPostGroup>> allComment(@PathVariable Long postGroupId ) {
        List<CommentPostGroup> commentPostGroups = commentPostGroupService.displayAllCommentOfPostGroup(postGroupId);
        if (commentPostGroups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } return new ResponseEntity<>(commentPostGroups, HttpStatus.OK);
    }

    @PostMapping("/{groupMemberId}/{postGroupId}")
    private ResponseEntity<CommentPostGroup> createCmt(@PathVariable Long postGroupId,@PathVariable Long groupMemberId, @RequestBody CommentPostGroup commentPostGroup) {
        PostGroup postGroup = postGroupService.findById(postGroupId).get();
        GroupMember groupMember = groupMemberService.findById(groupMemberId).get();
        String content  = commentPostGroup.getContent();
        CommentPostGroup commentPostGroup1 = new CommentPostGroup(content, postGroup, groupMember );
        commentPostGroupService.save(commentPostGroup1);
        return new ResponseEntity<>(commentPostGroup1, HttpStatus.OK);
    }
}
