package com.atguigu.gmall.mock.util;

/**
 * @author dengyu
 * @data 2019/11/25 - 13:24
 */
public class RanOpt<T>{
    T value ;
    int weight;

    public RanOpt ( T value, int weight ){
        this.value=value ;
        this.weight=weight;
    }

    public T getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }
}
