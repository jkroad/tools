package com.ismayfly.coins.tools.core.response;

import com.github.pagehelper.PageInfo;
import com.ismayfly.coins.tools.core.exception.MExceptionCode;
import com.ismayfly.coins.tools.core.exception.MayflyException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 统一响应
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class HandleResponse<T> {

    /**
     * 是否分页1: 是 0：否
     */
    private int page;
    private long total;
    private int pageNum;
    private int pageSize;
    private String xcode = "coins";

    private String id;

    private int code;

    private String msg;

    private String path;

    private T data;

    private HandleResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private HandleResponse(MExceptionCode mExceptionCode, T data) {
        this.code = mExceptionCode.getErrorCode();
        if (StringUtils.isNotBlank(mExceptionCode.getCustomMsg())) {
            this.msg = mExceptionCode.getCustomMsg();
        } else {
            this.msg = mExceptionCode.getDesc();
        }
        this.data = data;

    }

    /**
     * 封装成功的响应
     *
     * @return
     */
    public static <T> HandleResponse<T> successResponse(T data) {
        HandleResponse handleResponse = new HandleResponse(MExceptionCode.SUCCESS, data);
        try {
            //分页做相关参数封装
            if (null != data && data instanceof List) {
                PageInfo pageInfo = new PageInfo((List) data);
                handleResponse.setPage(1).setTotal(pageInfo.getTotal()).setPageSize(pageInfo.getPageSize()).setPageNum(pageInfo.getPageNum());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return handleResponse;
    }

    /**
     * 封装失败的响应
     *
     * @return
     */
    public static <T> HandleResponse<T> failedResponse(MExceptionCode code, T data) {
        return new HandleResponse(code, data);
    }

    /**
     * 全局统一异常处理封装失败的响应
     *
     * @return
     */
    public static <T> HandleResponse<T> failedResponseForGlobal(int code, String msg, T data) {
        return new HandleResponse(code, msg, data);
    }


    public static <T> HandleResponse<T> successResponse() {
        HandleResponse handleResponse = new HandleResponse(MExceptionCode.SUCCESS, "");
        return handleResponse;
    }


}
