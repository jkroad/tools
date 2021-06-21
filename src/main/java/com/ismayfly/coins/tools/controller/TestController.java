package com.ismayfly.coins.tools.controller;

import com.ismayfly.coins.tools.core.thread.ThreadPoolMonitor;
import com.ismayfly.coins.tools.model.query.BankMCCInfoQuery;
import com.ismayfly.coins.tools.service.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.checkerframework.checker.units.qual.A;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private RedissonService redissonService;

    @Autowired
    private KafkaTemplate<String,String> stringKafkaTemplate;

    private ThreadPoolMonitor threadPoolMonitor = new ThreadPoolMonitor(6,60,1000,TimeUnit.SECONDS,new LinkedBlockingQueue<>());



    @RequestMapping("/redis/test-redis")
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

    @PostMapping("/strToJson")
    public void jsonToStrTest(@RequestBody BankMCCInfoQuery bankMCCInfoQuery){
        System.out.println("bankName"+bankMCCInfoQuery.getBankName()+""+bankMCCInfoQuery.getCardType()+"mcc"+bankMCCInfoQuery.getMccStr()+""+"");
    }


    @PostMapping("/redis/bloom")
    public void bloomTest(){
        RBloomFilter bloomFilter = redissonService.getRBloomFilter("test");
        //初始化过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(55000000l,0.03);
        bloomFilter.add("xdd");
        bloomFilter.add("vdd");
    }

    @PostMapping("/redis/filter")
    public void filter(String str){
        RBloomFilter bloomFilter  =redissonService.getRBloomFilter("test");
        if(bloomFilter.contains(str)){
            log.info("bloomFilter contains value:{}",str);
        }else{
            log.info("ss");
        }
    }

    @PostMapping("/kafka/send")
    public void kafkaSendMessage(String topic,String message){
        for(int i = 0;i< 100000;i++){
            message += " "+i;
            stringKafkaTemplate.send(topic,message);
        }

    }


    @PostMapping("/test/query/mysql")
    public void  batchQueryDate(){

    }

    @KafkaListener(groupId = "local-test",topics = "local-topic")
    public void kafkaConsumerDemo(List<ConsumerRecord> records){
        log.info("records.size: " + records.size() + " in all");
        for(ConsumerRecord consumerRecord:records){
            try{
                Thread.sleep(7000);
            }catch (Exception e){
                log.info("commit",e);

            }finally {
                log.info("start commit offset");

                log.info("start commit offset ");
            }
        }


    }



}
