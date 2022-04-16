package com.codegym.module4casestudy.model.dto;
import com.codegym.module4casestudy.model.entity.Group;
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

    private Group group;
}
