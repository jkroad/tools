package com.ismayfly.coins.tools.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {


    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        //单节点
//        config.useSingleServer().setAddress("redis://localhost:6379");
        //主从模式
//        config.useMasterSlaveServers()
//                .setMasterAddress("redis://127.0.0.1:6379")
//                .addSlaveAddress("redis://127.0.0.1:6380")
//                .addSlaveAddress("redis://127.0.0.1:6381");
        //集群模式
        config.useClusterServers()
                .setScanInterval(1200) //集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress("redis://127.0.0.1:6371,redis://127.0.0.1:6372,redis://127.0.0.1:6373");
        return  Redisson.create(config);
    }
}
