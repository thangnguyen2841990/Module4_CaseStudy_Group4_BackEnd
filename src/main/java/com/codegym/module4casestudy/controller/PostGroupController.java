package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.dto.GroupMember;
import com.codegym.module4casestudy.model.dto.PostGroupForm;
import com.codegym.module4casestudy.model.dto.PostGroupFrontEnd;
import com.codegym.module4casestudy.model.entity.*;
import com.codegym.module4casestudy.service.commentPostGroup.ICommentPostGroupService;
import com.codegym.module4casestudy.service.group.IGroupService;
import com.codegym.module4casestudy.service.groupMember.IGroupMemberService;
import com.codegym.module4casestudy.service.imagePostGroup.IImagePostGroupService;
import com.codegym.module4casestudy.service.likePostGroup.ILikePostGroupService;
import com.codegym.module4casestudy.service.postgroup.IPostGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private  IGroupService groupService;

    @Value("C:/Users/Admin/Documents/image/")
    private String uploadPath;

    @GetMapping("/{groupId}")
    public ResponseEntity<List<PostGroupFrontEnd>> showAllPostGroup(@PathVariable Long groupId) {
        Group group = groupService.findById(groupId).get();
        List<PostGroup> postGroups = postGroupService.findByGroup(groupId);
        List<PostGroupFrontEnd> postGroupFrontEnds = new ArrayList<>();
        for (int i = 0; i< postGroups.size(); i++) {
            postGroupFrontEnds.add(new PostGroupFrontEnd(postGroups.get(i).getId(), postGroups.get(i).getContent(), iImagePostGroupService.listImage(postGroups.get(i).getId()),
                    postGroups.get(i).getDateCreated(),
                    postGroups.get(i).getGroupMember(),
                    likePostGroupService.totalLikeByPost(postGroups.get(i).getId()).size(),
                    commentPostGroupService.displayAllCommentOfPostGroup(postGroups.get(i).getId()),
                    commentPostGroupService.displayAllCommentOfPostGroup(postGroups.get(i).getId()).size(),
                    group
            ));
        }
        if (postGroupFrontEnds.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }         return new ResponseEntity<>(postGroupFrontEnds, HttpStatus.OK);
    }

    @GetMapping("/{groupMember_id}/{postGroup_id}/{groupId}")
    public ResponseEntity<PostGroupFrontEnd> findPostGroupById(@PathVariable Long groupMember_id, @PathVariable Long postGroup_id, @PathVariable Long groupId ) {
        Optional<PostGroup> postGroup = postGroupService.findById(postGroup_id);
        Optional<Group> group = groupService.findById(groupId);
        Optional<GroupMember> groupMember = groupMemberService.findById(groupMember_id);
        ImagePostGroup[] imagePostGroups = iImagePostGroupService.listImage(postGroup_id);
        PostGroupFrontEnd postGroupFrontEnd =  new PostGroupFrontEnd(postGroup_id, postGroup.get().getContent(), imagePostGroups,postGroup.get().getDateCreated(), groupMember.get(), group.get());
        return new ResponseEntity<>(postGroupFrontEnd, HttpStatus.OK);
    }

    @PostMapping("/{groupMemberId}/{groupId}")
    public ResponseEntity<PostGroup> createNewPost(@PathVariable Long groupId,@PathVariable Long groupMemberId, @ModelAttribute PostGroupForm postGroupForm){
        MultipartFile[] multipartFiles = postGroupForm.getImage();
        List<String> fileName = new ArrayList<>();
        Optional<Group> group = groupService.findById(groupId);
        Optional<GroupMember> groupMember = groupMemberService.findById(groupMemberId);
        PostGroup postGroup = new PostGroup(postGroupForm.getContent(), new Date(), postGroupForm.isStatus(),groupMember.get(), group.get());
        postGroupService.save(postGroup);
        for (int i = 0; i < multipartFiles.length; i++) {
            fileName.add(multipartFiles[i].getOriginalFilename());
        }

        for (int  i = 0; i < fileName.size(); i++) {
            iImagePostGroupService.save(new ImagePostGroup(fileName.get(i), postGroup));
            try {
                FileCopyUtils.copy(multipartFiles[i].getBytes(), new File(uploadPath + fileName.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(postGroup, HttpStatus.OK);
    }

    @PostMapping("/edit/{groupMemberId}/{postGroupId}/{groupId}")
    public ResponseEntity<PostGroup> editPostGroup(@PathVariable Long groupMemberId, @PathVariable Long postGroupId, @ModelAttribute PostGroupForm postGroupForm) {
        MultipartFile[] listImageFile = postGroupForm.getImage();
        List<String> listFileName = new ArrayList<>();
        Optional<GroupMember> groupMember = this.groupMemberService.findById(groupMemberId);
        // lưu 1 post User
        PostGroup postGroup = new PostGroup(postGroupId, postGroupForm.getContent(), new Date(),postGroupForm.isStatus(), groupMember.get(), postGroupForm.getGroup());
        this.postGroupService.save(postGroup);
        Optional<ImagePostGroup> imagePostGroup = this.iImagePostGroupService.findByPostGroupId(postGroupId);
        if (listImageFile.length > 0) {
            // đổi tên ảnh sang string và add vào list tên ảnh
            for (int i = 0; i < listImageFile.length; i++) {
                listFileName.add(listImageFile[i].getOriginalFilename());
            }
            // save ảnh vào trong database và lưu ảnh vào thư mục chứa ảnh
            for (int i = 0; i < listFileName.size(); i++) {
                this.iImagePostGroupService.save(new ImagePostGroup(imagePostGroup.get().getId(), listFileName.get(i), postGroup));
                try {
                    FileCopyUtils.copy(listImageFile[i].getBytes(), new File(uploadPath + listFileName.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseEntity<>(postGroup, HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}/{postGroupId}")
    public ResponseEntity<PostGroup> deletePostGroup(@PathVariable Long postGroupId) {
        Optional<PostGroup> postGroup = postGroupService.findById(postGroupId);
        if (!postGroup.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postGroupService.deletePostGroup(postGroupId);
        return new ResponseEntity<>(postGroup.get(),HttpStatus.OK);
    }






}
