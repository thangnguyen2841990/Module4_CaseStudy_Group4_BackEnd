package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.entity.Group;
import com.codegym.module4casestudy.model.dto.GroupMember;
import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.service.group.IGroupService;
import com.codegym.module4casestudy.service.groupMember.IGroupMemberService;
import com.codegym.module4casestudy.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/groups")
@CrossOrigin("*")
public class GroupController {
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IGroupMemberService groupMemberService;

    @GetMapping
    public ResponseEntity<Page<Group>> findAllGroup(@PageableDefault(value = 100) Pageable pageable) {
        Page<Group> groups = groupService.findAll(pageable);
        if (groups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> findGroupById(@PathVariable Long id) {
        Optional<Group> optionalGroup = groupService.findById(id);
        if (!optionalGroup.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalGroup.get(), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Group> createGroup(@RequestBody Group group, @PathVariable Long userId) {
        Group newGroup = this.groupService.save(group);
        Optional<UserInfo> admin = this.userInfoService.findByUserId(userId);
        GroupMember newGroupMember = new GroupMember(true, false, true, admin.get(), newGroup);
        this.groupMemberService.save(newGroupMember);
        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> editGroup(@PathVariable Long id, @RequestBody Group group) {
        Optional<Group> optionalGroup = groupService.findById(id);
        if (!optionalGroup.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        group.setId(id);
        return new ResponseEntity<>(groupService.save(group), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Group> deleteGroup(@PathVariable Long id) {
        Optional<Group> optionalGroup = groupService.findById(id);
        if (!optionalGroup.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupService.deleteById(id);
        return new ResponseEntity<>(optionalGroup.get(), HttpStatus.OK);
    }
}
