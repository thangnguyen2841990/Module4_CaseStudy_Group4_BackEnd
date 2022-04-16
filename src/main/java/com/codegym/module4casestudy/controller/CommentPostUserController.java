package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.entity.CommentPostUser;
import com.codegym.module4casestudy.model.entity.PostUser;
import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.service.commentPostUser.ICommentPostUserService;
import com.codegym.module4casestudy.service.post.IPostService;
import com.codegym.module4casestudy.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/comments")
public class CommentPostUserController {
        @Autowired
        private ICommentPostUserService commentPostUserService;
        @Autowired
        private IUserInfoService userInfoService;
        @Autowired
        private IPostService postService;

        @PostMapping("/{userId}/{postUserId}")
        public ResponseEntity<CommentPostUser> createNewComment(@PathVariable Long userId, @PathVariable Long postUserId, @RequestBody CommentPostUser commentPostUser){
                UserInfo userInfo = this.userInfoService.findByUserId(userId).get();
                PostUser postUser = this.postService.findById(postUserId).get();
                String content = commentPostUser.getContent();
                CommentPostUser newComment = this.commentPostUserService.save(new CommentPostUser(content,userInfo,postUser));
                return new ResponseEntity<>(newComment, HttpStatus.OK);
        }
        @GetMapping("/{postUserId}")
        public ResponseEntity<List<CommentPostUser>> displayAllCommentOfPost(@PathVariable Long postUserId){
                List<CommentPostUser> comments = this.commentPostUserService.displayAllCommentOfPost(postUserId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
        }

}
