package com.example.demo.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

    @PostMapping("/save")
    public void testGit(){
        System.out.println("保留该代码");
    }
}
