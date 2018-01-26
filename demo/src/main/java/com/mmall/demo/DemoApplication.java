package com.mmall.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping(value = "/")
    public String home() {
        return "hello world";
    }

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello world";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @RequestMapping(value = "/roleAuth")
    public String role() {
        return "roleAuth";
    }

    @PreAuthorize("#id<10 and principal.username.equals(#username)")
    @PostAuthorize("returnObject%2==0")
    @RequestMapping(value = "/test")
    public Integer test(@RequestParam(value = "id") Integer id,
            @RequestParam(value = "username") String username) {
        return id;
    }
}
