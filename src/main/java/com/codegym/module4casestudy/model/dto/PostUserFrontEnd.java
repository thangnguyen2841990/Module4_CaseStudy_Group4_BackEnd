package com.codegym.module4casestudy.model.dto;

import com.codegym.module4casestudy.model.entity.ImagePostUser;
import com.codegym.module4casestudy.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUserFrontEnd {
    private Long postUserId;

    private String content;

    private ImagePostUser[] listImage;

    private Date dateCreated;

    private UserInfo userInfo;


}
