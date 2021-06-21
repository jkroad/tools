package com.ismayfly.coins.tools.controller;

import com.ismayfly.coins.tools.core.response.HandleResponse;
import com.ismayfly.coins.tools.model.po.MdSocialInformation;
import com.ismayfly.coins.tools.service.MdSocialInformationService;
import com.ismayfly.coins.tools.service.ServiceBImpl;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class IndexController {

    @Autowired
    private ServiceBImpl serviceB;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MdSocialInformationService mdSocialInformationService;

    @Cacheable(value = "index",key="#root.methodName")
    @PostMapping("/index")
    public MdSocialInformation index(){
        System.out.println("2343");
        MdSocialInformation mdSocialInformatio = new MdSocialInformation();
        mdSocialInformatio.setMobile("15068149290");
        return mdSocialInformatio;
    }

    @PostMapping("/redisValue")
    public void getRedisValueTest(){
        log.info("start into");
        Object str  = redisTemplate.opsForValue().get("test");
        log.info("result:{}",str);
    }


    @PostMapping("/setValue")
    public HandleResponse setValue(){
        log.info("set value start");
        redisTemplate.opsForValue().set("test","Link-demo",5, TimeUnit.MINUTES);
        log.info("end");
       return HandleResponse.successResponse();

    }
    @PostMapping("/getValue")
    public  synchronized  void getValue(){
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @GetMapping("/tx")
    public void  TransactTest(){
        serviceB.testB();
    }






}
