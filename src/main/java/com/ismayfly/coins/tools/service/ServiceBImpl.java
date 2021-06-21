package com.ismayfly.coins.tools.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("serviceB")
public class ServiceBImpl {

    @Autowired
    private ServiceAImpl serviceA;

    public void  testB(){
        try{
            serviceA.testA();
        }catch (Exception e){
            log.info("error",e);
        }
    }
}
