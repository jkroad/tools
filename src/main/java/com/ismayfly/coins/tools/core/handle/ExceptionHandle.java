package com.ismayfly.coins.tools.core.handle;

import com.ismayfly.coins.tools.core.exception.MExceptionCode;
import com.ismayfly.coins.tools.core.exception.MayflyException;
import com.ismayfly.coins.tools.core.response.HandleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServlet;
import java.lang.reflect.Member;

/**
 * @desc 异常处理切面，controller的异常统一处理
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    /**
     * 异常捕获,捕获所有异常
     *
     * @return
     */
    @ExceptionHandler({Exception.class, MayflyException.class})
    @ResponseBody
    public HandleResponse handle(HttpServlet httpServlet, Exception e) {
        log.error(" handler Exception", e);
        if (e instanceof MayflyException) {
            //自定义异常
            return HandleResponse.failedResponse(((MayflyException) e).getErrorCode(), "内部错误");
        } else if (e instanceof HttpMessageNotReadableException) {
            // 比如 @RequestBody 请求的body没有时会报此错误
            return HandleResponse.failedResponse(MExceptionCode.PARAM_ERROR, "");
        } else if (e instanceof RuntimeException) {
            log.error("ExceptionHandle error:", e);
            throw new RuntimeException();
        }
        return HandleResponse.failedResponse(MExceptionCode.SYSTEM_ERROR, "");
    }
}
