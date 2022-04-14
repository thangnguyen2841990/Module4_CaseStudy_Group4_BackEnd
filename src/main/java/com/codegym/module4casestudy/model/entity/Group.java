package com.codegym.module4casestudy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table( name = "groups_")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String avatar;
    private String background;

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, String avatar, String background) {
        this.name = name;
        this.avatar = avatar;
        this.background = background;
    }
}
