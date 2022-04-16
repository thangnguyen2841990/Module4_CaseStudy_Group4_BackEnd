package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.service.imagePostGroup.IImagePostGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/images_gr")
public class ImagePostGroupController {
    @Autowired
    private IImagePostGroupService iImagePostGroupService;

    @GetMapping("/{imageId}")
    public ResponseEntity<String> findById(@PathVariable Long imageId) {
        String image = this.iImagePostGroupService.findImageById(imageId);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
