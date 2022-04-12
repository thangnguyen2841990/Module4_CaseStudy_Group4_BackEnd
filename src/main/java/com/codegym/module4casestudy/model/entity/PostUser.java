package com.codegym.module4casestudy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDate dateCreated;
    @OneToOne
    private RolePostUser rolePostUser;

    @ManyToOne
    private User user;

    public PostUser(String content, LocalDate dateCreated, RolePostUser rolePostUser, User user) {
        this.content = content;
        this.dateCreated = dateCreated;
        this.rolePostUser = rolePostUser;
        this.user = user;
    }
}
