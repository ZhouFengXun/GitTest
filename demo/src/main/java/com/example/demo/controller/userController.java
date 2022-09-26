package com.example.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

    @PostMapping("/save")
    public void testGit(){
        System.out.println("保留该代码");
    }

    @GetMapping("/git")
    public void print(){
        System.out.println("这是合并并?");
    }
}
