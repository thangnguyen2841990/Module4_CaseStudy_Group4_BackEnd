package com.codegym.module4casestudy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private UserInfo userInfoPost;

    @ManyToOne
    private PostUser postUser;

    public CommentPostUser(String content, UserInfo userInfoPost, PostUser postUser) {
        this.content = content;
        this.userInfoPost = userInfoPost;
        this.postUser = postUser;
    }
}
