package com.atguigu.gmall.logger.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.atguigu.gmall.common.constant.GmallConstant;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author dengyu
 * @data 2019/11/25 - 18:46
 */

@RestController
public class LoggerController {
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerController.class);

    //@PostMapping("/log")
    @RequestMapping(value = "/log",method = RequestMethod.POST)
    public String dolog(@RequestBody String logJson){


        //因为我传入的数据不是json格式的所以需要转换格式在传入JSON中
        String[] split = logJson.split("=");
        String s = split[1];

        //补时间戳
        //jsonObject本质上是HashMap
        JSONObject jsonObject = JSON.parseObject(s.toString());
        jsonObject.put("ts",System.currentTimeMillis());

        //落盘到logfile  使用log4j
        logger.info(jsonObject.toJSONString());
        //发送kafka
        if ("startup".equals(jsonObject.getString("type"))){
            kafkaTemplate.send(GmallConstant.KAFKA_TOPIC_STARTUP,jsonObject.toJSONString());
        }else {
            kafkaTemplate.send(GmallConstant.KAFKA_TOPIC_EVENT,jsonObject.toJSONString());
        }

        System.out.println(jsonObject);

        return logJson;
    }

}