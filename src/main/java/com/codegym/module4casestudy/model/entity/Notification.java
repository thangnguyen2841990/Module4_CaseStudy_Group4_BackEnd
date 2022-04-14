package com.codegym.module4casestudy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    private User fromUser;
    @OneToMany
    private List<User> toUserList;

    public Notification(String content, User fromUser, List<User> toUserList) {
        this.content = content;
        this.fromUser = fromUser;
        this.toUserList = toUserList;
    }
}
