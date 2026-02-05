package com.test.kata_backend.controller;


import com.test.kata_backend.entity.UsersDocument;
import com.test.kata_backend.entity.UsersEntity;
import com.test.kata_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/logstash")
@RequiredArgsConstructor
public class TestLogstashController {
    private static final Logger logger = LoggerFactory.getLogger(TestLogstashController.class);
    private final UserService userService;
    @GetMapping("/test1")
    public String test() {
        logger.info("Test log envoyé vers Logstash");
        logger.error("Test erreur");
        return "Logs envoyés !";
    }

    // GET /api/users/search?name=alice - Chercher par nom
    @GetMapping("/search")
    public List<UsersDocument> searchUsers(@RequestParam("name") String name) {
        return userService.searchByName(name);
    }
}
