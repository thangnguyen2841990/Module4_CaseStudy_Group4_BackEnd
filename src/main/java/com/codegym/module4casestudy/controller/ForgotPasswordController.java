package com.codegym.module4casestudy.controller;


import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.service.userInfo.UserInfoService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Controller
@CrossOrigin("*")
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserInfoService userInfoService;


    @PostMapping("/forgot_password")
    public ResponseEntity<HashMap> processForgotPassword(@RequestParam("email") String email , @RequestParam("currentUrl") String currentUrl){
        String token = RandomString.make(30);
        HashMap message = new HashMap<>();
        try {
            userInfoService.updateResetPasswordToken(token, email);
            String resetPasswordLink = currentUrl + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            message.put("message","Chúng tôi đã gửi link reset mật khẩu vào email của bạn.");

        } catch (NotFoundException ex) {
            message.put("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            message.put("error", "Error while sending email");
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    public void sendEmail(String recipientEmail, String link)throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("fackbookc11@gmail.com", "fackbookc11 Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Xin chào,</p>"
                + "<p>Bạn đã gửi yêu cầu thiết lập lại mật khẩu.</p>"
                + "<p>Hãy kích vào link bên dưới để thay đổi mật khẩu:</p>"
                + "<p><a href=\"" + link + "\">Thay đổi mật khẩu</a></p>"
                + "<br>"
                + "<p>Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa thực hiện yêu cầu.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordFrom(@Param(value = "token") String token, Model model){
        UserInfo userInfo = userInfoService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (userInfo == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "changePassword";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model){
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        UserInfo userInfo = userInfoService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (userInfo == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userInfoService.updatePassword(userInfo, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }
}
