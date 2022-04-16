package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.dto.PostUserForm;
import com.codegym.module4casestudy.model.dto.PostUserFrontEnd;
import com.codegym.module4casestudy.model.entity.*;
import com.codegym.module4casestudy.service.commentPostUser.ICommentPostUserService;
import com.codegym.module4casestudy.service.imagePostUser.IImagePostUserService;
import com.codegym.module4casestudy.service.likePostUser.ILikePostUserService;
import com.codegym.module4casestudy.service.post.IPostService;
import com.codegym.module4casestudy.service.user.IUserService;
import com.codegym.module4casestudy.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private IPostService postService;
    @Autowired
    private IImagePostUserService imagePostUser;

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IImagePostUserService imagePostUserService;

    @Autowired
    private ILikePostUserService likePostUserService;

    @Autowired
    private ICommentPostUserService commentPostUserService;

    @Value("C:/Users/nguye/OneDrive/Desktop/image/")
    private String uploadPath;

    @GetMapping
    public ResponseEntity<List<PostUserFrontEnd>> showAllPostUser() {
        List<PostUser> postUsers = (List<PostUser>) this.postService.findAll();
        List<PostUserFrontEnd> postUserFrontEnd = new ArrayList<>();
        List<CommentPostUser> comments = new ArrayList<>();
        for (int i = 0; i < postUsers.size(); i++) {
            postUserFrontEnd.add(new PostUserFrontEnd(postUsers.get(i).getId(), postUsers.get(i).getContent(), imagePostUserService.listImage(postUsers.get(i).getId()),
                    postUsers.get(i).getDateCreated(), postUsers.get(i).getUserInfo(), likePostUserService.totalLikeByPost(postUsers.get(i).getId()).size(),
                    commentPostUserService.displayAllCommentOfPost(postUsers.get(i).getId()),
                    commentPostUserService.displayAllCommentOfPost(postUsers.get(i).getId()).size()
            ));
        }
        return new ResponseEntity<>(postUserFrontEnd, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{postUserId}")
    public ResponseEntity<PostUserFrontEnd> findPostById(@PathVariable Long userId, @PathVariable Long postUserId) {
        Optional<PostUser> postUserOptional = this.postService.findById(postUserId);
        ImagePostUser[] listImage = this.imagePostUserService.listImage(postUserId);
        Optional<UserInfo> userInfo = this.userInfoService.findByUserId(userId);
        PostUserFrontEnd postUserFrontEnd = new PostUserFrontEnd(postUserId, postUserOptional.get().getContent(), listImage, postUserOptional.get().getDateCreated(), userInfo.get());
        return new ResponseEntity<>(postUserFrontEnd, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<PostUser> createNewPostUser(@ModelAttribute PostUserForm postUserForm, @PathVariable Long userId) {
        MultipartFile[] listImageFile = postUserForm.getImage();
        List<String> listFileName = new ArrayList<>();
        Optional<UserInfo> userInfoOptional = this.userInfoService.findByUserId(userId);
        // lưu 1 post User
        PostUser newPostUser = new PostUser(postUserForm.getContent(), new Date(), postUserForm.isStatus(), userInfoOptional.get());
        this.postService.save(newPostUser);
        // đổi tên ảnh sang string và add vào list tên ảnh
        for (int i = 0; i < listImageFile.length; i++) {
            listFileName.add(listImageFile[i].getOriginalFilename());
        }
        // save ảnh vào trong database và lưu ảnh vào thư mục chứa ảnh
        for (int i = 0; i < listFileName.size(); i++) {
            this.imagePostUser.save(new ImagePostUser(listFileName.get(i), newPostUser));
            try {
                FileCopyUtils.copy(listImageFile[i].getBytes(), new File(uploadPath + listFileName.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(newPostUser, HttpStatus.CREATED);
    }

    @PostMapping("/edit/{userId}/{postUserId}")
    public ResponseEntity<PostUser> editPostUser(@PathVariable Long userId, @PathVariable Long postUserId
            , @ModelAttribute PostUserForm postUserForm) {
        MultipartFile[] listImageFile = postUserForm.getImage();
        List<String> listFileName = new ArrayList<>();
        Optional<UserInfo> userInfoOptional = this.userInfoService.findByUserId(userId);
        // lưu 1 post User
        PostUser newPostUser = new PostUser(postUserId, postUserForm.getContent(), new Date(), postUserForm.isStatus(), userInfoOptional.get());
        this.postService.save(newPostUser);
        Optional<ImagePostUser> imagePostUser = this.imagePostUserService.findByPostUserId(postUserId);
        if (listImageFile.length > 0) {
            // đổi tên ảnh sang string và add vào list tên ảnh
            for (int i = 0; i < listImageFile.length; i++) {
                listFileName.add(listImageFile[i].getOriginalFilename());
            }
            // save ảnh vào trong database và lưu ảnh vào thư mục chứa ảnh
            for (int i = 0; i < listFileName.size(); i++) {
                this.imagePostUser.save(new ImagePostUser(imagePostUser.get().getId(), listFileName.get(i), newPostUser));
                try {
                    FileCopyUtils.copy(listImageFile[i].getBytes(), new File(uploadPath + listFileName.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseEntity<>(newPostUser, HttpStatus.OK);
    }

    @DeleteMapping("/{postUserId}")
    public ResponseEntity<PostUser> deletePost(@PathVariable Long postUserId) {
        Optional<PostUser> postUser = this.postService.findById(postUserId);
        if (!postUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.postService.deletePost(postUserId);
        return new ResponseEntity<>(postUser.get(), HttpStatus.NO_CONTENT);
    }


}
