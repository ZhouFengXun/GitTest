package com.example.demo.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

    @PostMapping("/save")
    public void testGit(){
        System.out.println("学习git合并代码");
        System.out.println("学习git合并代码");
        System.out.println("学习git合并代码");
    }
}
