package com.example.demo.annotation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepeatSubmitCacheDto implements Serializable {

    //请求体的数据
    private String requestData;

    //当前时间-用于比较有效期
    private Long currTime;

    private String requestUrl;
}
