package com.codegym.module4casestudy.controller;


import com.codegym.module4casestudy.model.entity.User;
import com.codegym.module4casestudy.model.entity.UserInfo;
import com.codegym.module4casestudy.service.user.UserService;
import com.codegym.module4casestudy.service.userInfo.UserInfoService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.Transport;
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

    @Autowired
    private UserService userService;

    @PostMapping("/forgot_password")
    public ResponseEntity<HashMap> processForgotPassword(@RequestParam("email") String email , @RequestParam("currentUrl") String currentUrl){
        String token = RandomString.make(30);
        HashMap message = new HashMap<>();
        try {
            userInfoService.updateResetPasswordToken(token, email);
//           String currentURLstr = currentUrl;
//            String changePassUrl = currentURLstr.replace("passwordRetrieval","changePassword");
            String resetPasswordLink = currentUrl + "?token=" + token;
//            String resetPasswordLink = changePassUrl;
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
//        String from = "fackbookc11@gmail.com";
//        String to = "ngomai692@gmail.com";
////        String username = "fackbookc11@gmail.com";
////        String password = "15042022";
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setSubject("This is HTML email");
//        helper.setFrom(from);
//        helper.setTo(to);
//
//        boolean html = true;
//        helper.setText("<b>Hello world!<b>", html);
//       mailSender.send(message);
//
//
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("fackbookc11@gmail.com", "fackbookc11 Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello, </p>"
                + "<p>You sent the reset password request </p>"
                + "<p>Please click the link below to active the reset process </p>"
                + "<p><a href=\"" + link + "\">Change password</a></p>"
                + "<br>"
                + "<p>Please ignore this email if you remember your password. </p>";
//
        helper.setSubject(subject);

        helper.setText(content, true);
        mailSender.send(message);

//        mailSender.send(new MimeMessagePreparator() {
//            public void prepare(MimeMessage mimeMessage) throws MessagingException {
//                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//                message.setFrom("fackbookc11@gmail.com");
//                message.setTo(recipientEmail);
//                message.setSubject( "Here's the link to reset your password");
//                message.setText("<p>Xin chào,</p>"
//                        + "<p>Bạn đã gửi yêu cầu thiết lập lại mật khẩu.</p>"
//                        + "<p>Hãy kích vào link bên dưới để thay đổi mật khẩu:</p>"
//                        + "<p><a href=\"" + link + "\">Thay đổi mật khẩu</a></p>"
//                        + "<br>"
//                        + "<p>Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa thực hiện yêu cầu.</p>", true);
//            }
//        });
    }

    @GetMapping("/reset_password")
    public ResponseEntity<HashMap> showResetPasswordFrom(@Param(value = "token") String token){
        UserInfo userInfo = userInfoService.getByResetPasswordToken(token);
        HashMap message = new HashMap<>();
        if (userInfo == null) {
            message.put("message", "Invalid Token");
        }else {
            message.put("token",token);
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<HashMap> processResetPassword(@RequestParam("token") String token , @RequestParam("password") String password){
        HashMap message = new HashMap<>();
        UserInfo userInfo = userInfoService.getByResetPasswordToken(token);
        User user = userService.findById(userInfo.getUser().getId()).get();
        if (userInfo == null) {
            message.put("message", "Invalid Token");
        } else {
            userInfoService.updatePassword(userInfo, password);
            user.setPassword(password);
            userService.saveAdmin(user);

            message.put("message", "You have successfully changed your password.");
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
}
