package com.ismayfly.coins.tools.controller;

import com.ismayfly.coins.tools.core.response.HandleResponse;
import com.ismayfly.coins.tools.model.po.MdSocialInformation;
import com.ismayfly.coins.tools.service.MdSocialInformationService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController(value = "/test")
public class IndexController {

    @Resource()
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


    @PostMapping("/getValue")
    public  synchronized  void getValue(){
        System.out.println(redisTemplate.opsForValue().get("test"));
    }




}
