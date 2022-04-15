package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.dto.GroupMember;
import com.codegym.module4casestudy.model.dto.PostGroupFrontEnd;
import com.codegym.module4casestudy.model.entity.ImagePostGroup;
import com.codegym.module4casestudy.model.entity.PostGroup;
import com.codegym.module4casestudy.service.commentPostGroup.ICommentPostGroupService;
import com.codegym.module4casestudy.service.groupMember.IGroupMemberService;
import com.codegym.module4casestudy.service.imagePostGroup.IImagePostGroupService;
import com.codegym.module4casestudy.service.likePostGroup.ILikePostGroupService;
import com.codegym.module4casestudy.service.postgroup.IPostGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("post_group")
@CrossOrigin("*")
public class PostGroupController {
    @Autowired
    private IPostGroupService postGroupService;

    @Autowired
    private IImagePostGroupService iImagePostGroupService;

    @Autowired
    private ICommentPostGroupService commentPostGroupService;

    @Autowired
    private IGroupMemberService groupMemberService;

    @Autowired
    private ILikePostGroupService likePostGroupService;

    @Value("C:/Users/Admin/Documents/image/")
    private String uploadPath;

    @GetMapping
    private ResponseEntity<List<PostGroupFrontEnd>> showAllPostGroup() {
        List<PostGroup> postGroups = (List<PostGroup>) postGroupService.findAll();
        List<PostGroupFrontEnd> postGroupFrontEnds = new ArrayList<>();
        for (int i = 0; i< postGroups.size(); i++) {
            postGroupFrontEnds.add(new PostGroupFrontEnd(postGroups.get(i).getId(), postGroups.get(i).getContent(), iImagePostGroupService.listImage(postGroups.get(i).getId()),
                    postGroups.get(i).getDateCreated(), postGroups.get(i).getGroupMember(), likePostGroupService.totalLikeByPost(postGroups.get(i).getId()).size(),
                    commentPostGroupService.displayAllCommentOfPostGroup(postGroups.get(i).getId()),
                    commentPostGroupService.displayAllCommentOfPostGroup(postGroups.get(i).getId()).size()
            ));
        }
        if (postGroupFrontEnds.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }         return new ResponseEntity<>(postGroupFrontEnds, HttpStatus.OK);

    }

    @GetMapping("/{groupMember_id}/{postGroup_id}")
    private ResponseEntity<PostGroupFrontEnd> findPostGroupById(@PathVariable Long groupMember_id, @PathVariable Long postGroup_id ) {
        Optional<PostGroup> postGroup = postGroupService.findById(postGroup_id);
        Optional<GroupMember> groupMember = groupMemberService.findById(groupMember_id);
        ImagePostGroup[] imagePostGroups = iImagePostGroupService.listImage(postGroup_id);
        PostGroupFrontEnd postGroupFrontEnd =  new PostGroupFrontEnd(postGroup_id, postGroup.get().getContent(), imagePostGroups,postGroup.get().getDateCreated(), groupMember.get());
        return new ResponseEntity<>(postGroupFrontEnd, HttpStatus.OK);
    }




}
