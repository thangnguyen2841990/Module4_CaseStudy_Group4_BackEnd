package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.dto.GroupMember;
import com.codegym.module4casestudy.model.entity.LikePostGroup;
import com.codegym.module4casestudy.model.entity.PostGroup;
import com.codegym.module4casestudy.service.groupMember.GroupMemberService;
import com.codegym.module4casestudy.service.likePostGroup.ILikePostGroupService;
import com.codegym.module4casestudy.service.postgroup.PostGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likegr")
@CrossOrigin("*")
public class LikePostGroupController {
    @Autowired
    private ILikePostGroupService likePostGroupService;

    @Autowired
    private PostGroupService postGroupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @GetMapping("/{postGroupId}/{groupId}")
    private ResponseEntity<Integer> countLikePostGroup(@PathVariable Long postGroupId) {
        List<LikePostGroup> likePostGroups = likePostGroupService.totalLikeByPost(postGroupId);
        Integer totalLike = likePostGroups.size();
        return new ResponseEntity<>(totalLike, HttpStatus.OK);
    }

    @PostMapping("/{groupMemberId}/{postGroupId}")
    private ResponseEntity<LikePostGroup> createLike(@PathVariable Long postGroupId , @PathVariable Long groupMemberId) {
        PostGroup postGroup = postGroupService.findById(postGroupId).get();
        GroupMember groupMember = groupMemberService.findById(groupMemberId).get();
        Optional<LikePostGroup> likePostGroup = likePostGroupService.findLikePostGroupByGroupMemberIdAndPostGroupId(groupMember.getId(), postGroupId);
        if (!likePostGroup.isPresent()) {
            LikePostGroup likePostGroup1 = new LikePostGroup(true, postGroup, groupMember);
            likePostGroupService.save(likePostGroup1);
            return new ResponseEntity<>(likePostGroup1, HttpStatus.OK);
        } else {
            likePostGroupService.deleteById(likePostGroup.get().getId());
            return new ResponseEntity<>(likePostGroup.get(), HttpStatus.OK);
        }
    }
}
