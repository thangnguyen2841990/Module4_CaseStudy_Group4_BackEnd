package com.codegym.module4casestudy.model.dto;

import com.codegym.module4casestudy.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageFrontend {

    private UserInfo fromUser;


    private UserInfo toUser;

    private String dateCreated;

    private int status;

    private int total;


}
