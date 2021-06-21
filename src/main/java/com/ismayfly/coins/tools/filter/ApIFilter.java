package com.ismayfly.coins.tools.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class ApIFilter  {

    private String[] noFilterUrls;


    public void init(FilterConfig filterConfig) throws ServletException {
        String noFilterUrl = filterConfig.getInitParameter("noFilterUrl");
        if (noFilterUrl != null && noFilterUrl.length() > 0) {
            noFilterUrls = noFilterUrl.split(",");
        }

    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        boolean flag = isNoFilter(httpServletRequest);
        if (flag) {
            log.info("is pass");
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            log.info("not pass");
            throw new RuntimeException("not pass");

        }
    }


    public void destroy() {

    }


    /**
     * 判断路径是否需要过滤
     *
     * @param request
     * @return
     */
    public boolean isNoFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        for (String url : noFilterUrls) {
            if (requestURI.contains(url)) {
                return true;
            }
        }
        return false;
    }
}
