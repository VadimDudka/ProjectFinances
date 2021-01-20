package com.niqz.projectfinances.controller;

import com.niqz.projectfinances.model.UserModel;
import com.niqz.projectfinances.repository.UserRepository;
import com.niqz.projectfinances.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final MailService mailService;

    @GetMapping
    public String test() {
        return "Greetings from backend!";
    }

    @GetMapping("/user/{username}")
    public UserModel getUser(@PathVariable String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @GetMapping("/user/username/{username}")
    public boolean checkByUsernameUser(@PathVariable String username) {
        return userRepository.existByUsername(username);
    }

    @GetMapping("/user/email/{email}")
    public boolean checkByEmailUser(@PathVariable String email) {
        return userRepository.existByEmail(email);
    }
}
