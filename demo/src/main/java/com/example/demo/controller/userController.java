package com.example.demo.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

    @PostMapping
    public void show(){
        System.out.println("展示");
        System.out.println("展示");
        System.out.println("展示");
    }


}
