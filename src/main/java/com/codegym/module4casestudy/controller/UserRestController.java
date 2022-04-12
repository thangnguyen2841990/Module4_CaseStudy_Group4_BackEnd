package com.codegym.module4casestudy.controller;

import com.codegym.module4casestudy.model.dto.ChangePassword;
import com.codegym.module4casestudy.model.dto.JwtResponse;
import com.codegym.module4casestudy.model.dto.SignUpForm;
import com.codegym.module4casestudy.model.dto.UserPrincipal;
import com.codegym.module4casestudy.model.entity.User;
import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.service.JwtService;
import com.codegym.module4casestudy.service.user.IUserService;
import com.codegym.module4casestudy.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class UserRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    // Hiển thị danh sách thông tin user

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        //Kiểm tra username và pass có đúng hay không
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        //Lưu user đang đăng nhập vào trong context của security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities()));
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody SignUpForm user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = new User(user.getUsername(), user.getPassword());
        userService.saveAdmin(newUser);
        UserInfo userInfo = new UserInfo(user.getEmail(),user.getFullName(),user.getPhoneNumber(),
                user.getDateOfBirth(),user.getAddress(), newUser);
        userInfoService.save(userInfo);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @GetMapping("/findUserId/{email}/{phone}")
    public ResponseEntity<User> findUserId(@PathVariable String email, @PathVariable String phone){
        Long userId = this.userInfoService.findUserId(email,phone);
        Optional<User> user = this.userService.findById(userId);
        if (!user.isPresent()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(),HttpStatus.OK);
    }
    @PostMapping("/changePassword/{id}")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody ChangePassword changePassword){
        Optional<User> user = this.userService.findById(id);
        String newPassword;
        if (!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (changePassword.getPassword().equals(changePassword.getConfirmPassword())) {
             newPassword = changePassword.getPassword();
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.get().setPassword(newPassword);
        return new ResponseEntity<>(user.get(), HttpStatus.OK);

    }
}