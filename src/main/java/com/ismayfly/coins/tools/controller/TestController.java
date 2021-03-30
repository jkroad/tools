package com.ismayfly.coins.tools.controller;

import com.ismayfly.coins.tools.service.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private RedissonService redissonService;

    @RequestMapping("/test-redis")
    public void test(String recordId){
        RLock lock = redissonService.getRLock(recordId);
        try{
            boolean bs = lock.tryLock(1,5, TimeUnit.SECONDS);
            if(bs){
                log.info("start");
            }else{
                log.info("not get lock ");
                Thread .sleep(3000);
            }
        }catch (Exception e){
            log.info("e",e);
        }finally {
            lock.unlock();
        }
    }


    @PostMapping("/bloom")
    public void bloomTest(){
        RBloomFilter bloomFilter = redissonService.getRBloomFilter("test");
        //初始化过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(55000000l,0.03);
        bloomFilter.add("xdd");
        bloomFilter.add("vdd");
    }

    @PostMapping("/filter")
    public void filter(String str){
        RBloomFilter bloomFilter  =redissonService.getRBloomFilter("test");
        if(bloomFilter.contains(str)){
            log.info("bloomFilter contains value:{}",str);
        }else{
            log.info("ss");
        }
    }
}
