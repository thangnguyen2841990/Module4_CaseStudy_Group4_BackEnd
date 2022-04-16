package com.codegym.module4casestudy.controller;


import com.codegym.module4casestudy.model.entity.Group;
import com.codegym.module4casestudy.model.entity.GroupMember;
import com.codegym.module4casestudy.model.entity.NotificationAddGroup;
import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.service.group.IGroupService;
import com.codegym.module4casestudy.service.groupMember.IGroupMemberService;
import com.codegym.module4casestudy.service.notifiationAddGroup.INotificationGroupService;
import com.codegym.module4casestudy.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/groupmembers")
public class GroupMemberController {
    @Autowired
    private IGroupMemberService groupMemberService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private INotificationGroupService notificationGroupService;


    @PostMapping("/{group_id}/{member_id}")
    public ResponseEntity<GroupMember> acceptMember(@PathVariable Long group_id, @PathVariable Long member_id) {
        Group group = groupService.findById(group_id).get();
        UserInfo userInfo = userInfoService.findById(member_id).get();

        GroupMember newMember = groupMemberService.save(new GroupMember(group, userInfo, 1));
        Optional<NotificationAddGroup> notificationAddGroup = notificationGroupService.findNotification(group_id, userInfo.getId());
        notificationGroupService.deleteById(notificationAddGroup.get().getId());
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    @DeleteMapping("/{group_id}/{member_id}")
    public ResponseEntity<GroupMember> deleteMember(@PathVariable Long group_id, @PathVariable Long member_id) {
        Group group = groupService.findById(group_id).get();
        UserInfo userInfo = userInfoService.findById(member_id).get();

        GroupMember newMember = new GroupMember();
        Optional<NotificationAddGroup> notificationAddGroup = notificationGroupService.findNotification(group_id, userInfo.getId());
        notificationGroupService.deleteById(notificationAddGroup.get().getId());
        return new ResponseEntity<>(newMember, HttpStatus.OK);
    }
    @GetMapping("/{groupId}")
    public ResponseEntity<List<GroupMember>> showGroupMemberById(@PathVariable Long groupId) {
        List<GroupMember> groupMembers = groupMemberService.findGroupMemberByGroupId(groupId);
        return new ResponseEntity<>(groupMembers, HttpStatus.OK);
    }
}
