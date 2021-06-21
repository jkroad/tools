package com.ismayfly.coins.tools.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("aService")
public class ServiceAImpl {

    @Transactional
    public void testA(){
        int i = 1/0;
    }
}
