package com.codegym.module4casestudy.config.security;

import com.codegym.module4casestudy.config.CustomAccessDeniedHandler;
import com.codegym.module4casestudy.config.JwtAuthenticationFilter;
import com.codegym.module4casestudy.config.RestAuthenticationEntryPoint;
import com.codegym.module4casestudy.model.entity.Role;
import com.codegym.module4casestudy.model.entity.User;
import com.codegym.module4casestudy.service.role.IRoleService;
import com.codegym.module4casestudy.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }



    @Bean
    public PasswordEncoder passwordEncoder() { //bean mã hóa pass
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //lấy user từ DB
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

//    @Bean
//    public JavaMailSenderImpl mailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
////
////        mailSender.setHost("smtp.gmail.com");
////        mailSender.setPort(587);
////
////        mailSender.setUsername("fackbookc11@gmail.com");
////        mailSender.setPassword("15042022");
////
////        Properties props = mailSender.getJavaMailProperties();
////        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
////        props.put("mail.transport.protocol", "smtp");
////        props.put("mail.smtp.auth", "true");
////        props.put("mail.smtp.starttls.enable", "true");
////        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
    @PostConstruct
    public void init() {
        List<User> users = (List<User>) userService.findAll();
        List<Role> roleList = (List<Role>) roleService.findAll();
        if (roleList.isEmpty()) {
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleService.save(roleAdmin);
            Role roleUser= new Role("ROLE_USER");
            roleService.save(roleUser);
            Role roleAdminGroup= new Role("ROLE_ADMIN_GROUP");
            roleService.save(roleUser);
        }
        if (users.isEmpty()) {
            User admin = new User("admin","thuthuyda1");
            userService.saveAdmin(admin);
        }
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**");
        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());//Tùy chỉnh lại thông báo 401 thông qua class restEntryPoint
        http.authorizeRequests()
                .antMatchers("/**",
                        "/login",
                        "/register",
                        "/findUserId/{email}/{phone}",
                        "/changePassword/{id}",
                        "/posts/createPost/{userId}/{postUserId}",
                        "/userInfo",
                        "/userInfo/{id}",
                "/image/**").permitAll()
                .antMatchers("/facebooks/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and().csrf().disable();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors() ;
    }
}


