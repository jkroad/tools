package com.ismayfly.coins.tools.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class WebConfig {


    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("noFilter","/test");
        filterRegistrationBean.setName("ApiFilter");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    public FilterRegistrationBean twoFilterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("OtherFilter");
        return  filterRegistrationBean;
    }
}
