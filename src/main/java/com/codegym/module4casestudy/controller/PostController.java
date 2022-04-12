package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.dto.PostUserForm;
import com.codegym.module4casestudy.model.entity.ImagePostUser;
import com.codegym.module4casestudy.model.entity.PostUser;
import com.codegym.module4casestudy.model.entity.User;
import com.codegym.module4casestudy.service.imagePostUser.IImagePostUserService;
import com.codegym.module4casestudy.service.post.IPostService;
import com.codegym.module4casestudy.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Value("C:/Users/nguye/OneDrive/Desktop/image/")
    private String uploadPath;

    @GetMapping
    public ResponseEntity<Page<PostUser>> showAllPostUser(@PageableDefault(value = 5) Pageable pageable) {
        Page<PostUser> posts = this.postService.findAll(pageable);
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostUser> findPostById(@PathVariable Long id) {
        Optional<PostUser> postUserOptional = this.postService.findById(id);

        if (!postUserOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postUserOptional.get(), HttpStatus.OK);
    }
    @PostMapping("/createPost/{userId}/{postUserId}")
    public ResponseEntity<PostUser> createNewPostUser(@ModelAttribute PostUserForm postUserForm,  @PathVariable Long userId , @PathVariable Long postUserId){
        MultipartFile[] listImageFile = postUserForm.getImage();

        List<String> listFileName = new ArrayList<>();

        Optional<PostUser> postUser = this.postService.findById(postUserId);


        Optional<User>  userOptional = this.userService.findById(userId);
        // lưu 1 post User
        PostUser newPostUser = new PostUser(postUserForm.getContent(), LocalDate.now(),postUserForm.getRolePostUser(), userOptional.get());
        this.postService.save(newPostUser);

        // đổi tên ảnh sang string và add vào list tên ảnh
        for (int i = 0; i < listImageFile.length; i++) {
            listFileName.add(listImageFile[i].getOriginalFilename());
        }
        // save ảnh vào trong database và lưu ảnh vào thư mục chứa ảnh
        for (int i = 0; i < listFileName.size(); i++) {
            this.imagePostUser.save(new ImagePostUser(listFileName.get(i), postUser.get()));
            try {
                FileCopyUtils.copy(listImageFile[i].getBytes(),new File(uploadPath+listFileName.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(newPostUser,HttpStatus.CREATED);

    }


}
