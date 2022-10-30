package com.example.demo.controller;


import com.example.demo.annotation.RepeatSubmit;
import com.example.demo.util.HttpHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class userController {

    @RepeatSubmit
    @GetMapping("/getSessionId")
    public String show(HttpServletRequest request){
        String bodyString = HttpHelper.getBodyString(request);
        System.out.println(bodyString);

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 用户请求唯一标识，获取当前sessionId
        String sessionId = attributes.getSessionId();
        System.out.println(sessionId);
        // 用户请求的uri
        String uri = attributes.getRequest().getServletPath();
        // 针对用户对应接口的唯一key
        String key = sessionId + "-" + uri;
        stringObjectHashMap.put(key,key);
        if (!stringObjectHashMap.containsKey(key)){
            return "通过";
        }else {
            System.out.println(key);
            // 将生成的key存储在redis中，便于集群处理
            System.out.println(sessionId);
            System.out.println("展示");
            System.out.println("展示");
            System.out.println("展示");
            return "重复请求";
        }
    }
}
