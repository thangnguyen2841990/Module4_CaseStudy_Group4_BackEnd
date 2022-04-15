package com.codegym.module4casestudy.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostGroupForm {
    private Long id;

    private String content;

    private MultipartFile[] image;

    private GroupMember groupMember;

    private boolean status;
}
