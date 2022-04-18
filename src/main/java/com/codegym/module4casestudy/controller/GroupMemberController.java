package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.dto.GroupMember;
import com.codegym.module4casestudy.service.groupMember.IGroupMemberService;
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
@CrossOrigin("*")
@RequestMapping("/groupmembers")
public class GroupMemberController {
    @Autowired
    private IGroupMemberService groupMemberService;

    @GetMapping
    public ResponseEntity<Page<GroupMember>> findAllGroupMember(@PageableDefault(value = 100) Pageable pageable) {
        Page<GroupMember> groupMembers = groupMemberService.findAll(pageable);
        if (groupMembers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(groupMembers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupMember> findGroupMemberById(@PathVariable Long id) {
        Optional<GroupMember> groupMemberOptional = this.groupMemberService.findById(id);
        if (!groupMemberOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(groupMemberOptional.get(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<GroupMember> createGroupMember(@RequestBody GroupMember groupMember) {
        return new ResponseEntity<>(groupMemberService.save(groupMember), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GroupMember> editGroupMember(@PathVariable Long id, @RequestBody GroupMember groupMember) {
        Optional<GroupMember> optionalGroupMember = groupMemberService.findById(id);
        if (!optionalGroupMember.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupMember.setId(id);
        return new ResponseEntity<>(groupMemberService.save(groupMember), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GroupMember> deleteGroupMemberById(@PathVariable Long id) {
        Optional<GroupMember> groupMemberOptional = groupMemberService.findById(id);
        if (!groupMemberOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupMemberService.deleteById(id);
        return new ResponseEntity<>(groupMemberOptional.get(), HttpStatus.OK);
    }
}
