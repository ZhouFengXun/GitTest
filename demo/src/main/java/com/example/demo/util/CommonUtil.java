package com.example.demo.util;

import cn.hutool.core.lang.Snowflake;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class CommonUtil
{
    /**
     * 获取String参数
     */
    public static String getParameter(String name)
    {
        return getRequest().getParameter(name);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest()
    {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse()
    {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession()
    {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }



    private static final ThreadLocal<SimpleDateFormat> LOCAL=ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));

    public static String getCurrDate(){
        //return LOCAL.get().format(new Date());
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }


    /*public static void to64Msg(BigInteger i){
        BigDecimal divide = new BigDecimal(64);
        BigDecimal decimal = new BigDecimal(i);
        String res = "";
        //循环取余
        while (BigDecimal.ZERO.compareTo(decimal) != 0) {
            // 返回数组：[0] 商值(this / val) ， [1] 余数(this % val)
            BigDecimal[] divRes = decimal.divideAndRemainder(divide);
            decimal = divRes[0];
            res = S64BASE.charAt(divRes[1].intValue()) + res;
        }
        return res;
    }*/

    public static void main(String[] args) throws Exception{
        Snowflake snowflake=new Snowflake(3,2);
        IntStream.range(1,10).parallel().forEach(value -> {
            String v=snowflake.nextIdStr();
        });
    }


    //打乱改字符数组的组合顺序，可以得到不同的转换结果
    private static final char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g','h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '8', '5', '2', '7', '3', '6', '4', '0', '9', '1', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '+', '-' };

    /**
     * @param number double类型的10进制数,该数必须大于0
     * @return string类型的64进制数
     */
    public static String encode(double number) {
        double rest = number;
        // 创建栈
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest >= 1) {
            // 进栈,
            // 也可以使用(rest - (rest / 64) * 64)作为求余算法
            stack.add(array[new Double(rest % 64).intValue()]);
            rest = rest / 64;
        }
        for (; !stack.isEmpty();) {
            // 出栈
            result.append(stack.pop());
        }
        return result.toString();

    }

    /**
     * 支持范围是A-Z,a-z,0-9,+,-
     *
     * @param str 64进制的数字
     * @return 10进制的数字
     */
    public static double decode(String str) {
        // 倍数
        int multiple = 1;
        double result = 0;
        Character c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(str.length() - i - 1);
            result += decodeChar(c) * multiple;
            multiple = multiple * 64;
        }
        return result;
    }

    private static int decodeChar(Character c) {
        for (int i = 0; i < array.length; i++) {
            if (c == array[i]) {
                return i;
            }
        }
        return -1;
    }
}

