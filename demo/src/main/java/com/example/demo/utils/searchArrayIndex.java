package com.example.demo.utils;

/**
 *
 * @author lenovo
 
 * 二分查找   数组必须要从小到大,数组排序，排序
 */
public class searchArrayIndex {
    //数组
    static int[] array = {1, 2, 3, 4, 6, 7, 9};

    static int find(int searchKey) {
        //最小索引
        int lowerBound = 0;
        // 最大索引
        int upperBound = array.length - 1;
        // 中间值
        int middle;

        while (true) {
            //取中间值
            middle = (lowerBound + upperBound) / 2;
            //如果中间索引真好对应searchKey直接返回索引
            if (array[middle] == searchKey) {
                return middle;
            } else if (lowerBound > upperBound) {
                return 0;
            } else {
                //searchKey < 中间值 该值在中间值的左边
                if (searchKey < array[middle]) {
                    upperBound = middle - 1;
                } else {
                    lowerBound = middle + 1;
                }
            }
        }
    }
    public static void main(String[] args) {
        int i = find(90);
        System.out.println(i);
    }


}
