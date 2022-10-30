package com.example.demo.annotation;

import com.example.demo.util.HttpHelper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 判断是否重复提交，整体的思路：
 * 获取当前请求的URL作为键Key，暂且标记为：A1，其取值为映射Map（Map里面的元素由：请求的链接url 和 请求体的数据组成） 暂且标记为V1;
 * 从缓存中（本地缓存或者分布式缓存）查找Key=A1的值V2，如果V2和V1的值一样，即代表当前请求是重复提交的，拒绝执行后续的请求，否则可以继续往后面执行
 * 其中，设定重复提交的请求的间隔有效时间为8秒
 *
 * 注意点：如果在有效时间内，如8秒内，一直发起同个请求url、同个请求体，那么重复提交的有效时间将会自动延长
 * @author 修罗debug
 * @date 2020/10/21 8:12
 * @link 微信：debug0868  QQ：1948831260
 * @blog fightjava.com
 */
@Component
public class SameUrlDataRepeatInterceptor extends RepeatSubmitInterceptor {
    private static final String REPEAT_PARAMS = "RepeatParams";
    private static final String REPEAT_TIME = "RepeatTime";

    //防重提交key
    public static final String REPEAT_SUBMIT_KEY = "Repeat_Submit:";
    private static final int IntervalTime = 8;

    //构建本地缓存,有效时间为8秒钟
    private final Cache<String,String> cache= CacheBuilder.newBuilder().expireAfterWrite(IntervalTime, TimeUnit.SECONDS).build();

    //真正实现“是否重复提交的逻辑”
    @Override
    public boolean isRepeatSubmit(HttpServletRequest request) {
        String currParams= HttpHelper.getBodyString(request);

        if (StringUtils.isBlank(currParams)){
            currParams=new Gson().toJson(request.getParameterMap());
        }
        //获取请求地址,充当A1
        String url=request.getRequestURI();
        //充当B1
        RepeatSubmitCacheDto currCacheData=new RepeatSubmitCacheDto(currParams,System.currentTimeMillis(),url);
        //充当键A1
        String cacheRepeatKey=REPEAT_SUBMIT_KEY+url;

        String cacheValue=cache.getIfPresent(cacheRepeatKey);
        //从缓存中查找A1对应的值，如果存在，说明当前请求不是第一次了.
        if (StringUtils.isNotBlank(cacheValue)){
            //充当B2
            RepeatSubmitCacheDto preCacheData=new Gson().fromJson(cacheValue,RepeatSubmitCacheDto.class);
            if (this.compareParams(currCacheData,preCacheData) && this.compareTime(currCacheData,preCacheData)){
                return true;
            }
        }

        //否则，就是第一次请求
        Map<String, Object> cacheMap = new HashMap<>();
        cacheMap.put(url, currCacheData);
        cache.put(cacheRepeatKey,new Gson().toJson(currCacheData));

        return false;
    }

    //比较参数
    private boolean compareParams(RepeatSubmitCacheDto currCacheData, RepeatSubmitCacheDto preCacheData){
        Boolean res=currCacheData.getRequestData().equals(preCacheData.getRequestData());
        return res;
    }
    //判断两次间隔时间
    private boolean compareTime(RepeatSubmitCacheDto currCacheData, RepeatSubmitCacheDto preCacheData){
        Boolean res=( (currCacheData.getCurrTime() - preCacheData.getCurrTime()) < (IntervalTime * 1000) );
        return res;
    }
}
