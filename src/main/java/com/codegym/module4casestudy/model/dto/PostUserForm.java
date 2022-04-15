package com.codegym.module4casestudy.model.dto;

import com.codegym.module4casestudy.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUserForm {

    private Long id;

    private String content;

    private MultipartFile[] image;

    private UserInfo userInfo;

    private boolean status;
}
