package com.example.demo.annotation;

import com.example.demo.util.BaseResponse;
import com.example.demo.util.CommonUtil;
import com.example.demo.util.StatusCode;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public abstract class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {

    //开始拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit submitAnnotation = method.getAnnotation(RepeatSubmit.class);
            if (submitAnnotation != null) {
                //如果是重复提交，则进行拦截，拒绝请求
                if (this.isRepeatSubmit(request)) {
//                    BaseResponse subResponse=new BaseResponse();
                    CommonUtil.renderString(response,new Gson().toJson("重复"));
                    return false;
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    //自定义方法逻辑-判定是否重复提交
    public abstract boolean isRepeatSubmit(HttpServletRequest request);
}