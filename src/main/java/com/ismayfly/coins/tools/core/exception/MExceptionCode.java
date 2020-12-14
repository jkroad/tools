package com.ismayfly.coins.tools.core.exception;

public enum MExceptionCode {
    /**
     * 系统错误码
     */
    SUCCESS("SUCCESS", 1000, "success", "操作成功" ),
    FAILED("FAILED", 1001, "failed", "操作失败" ),
    REQUEST_PARAM_TOKEN_ERROR("REQUEST_PARAM_TOKEN_ERROR", 1004, "token is invalid", "您未登录，请登录"),
    PARAM_ERROR("PARAM_ERROR", 1111, "param error", "参数错误"),
    RESP_DATA_ERR("RESP_DATA_ERR", 1121, "resp data error", "响应错误", ""),
    FALLBACK_ERROR("FALLBACK_ERROR", 2222, "fallback error", "服务降级"),
    SYSTEM_ERROR("SYSTEM_ERROR", 9999, "system error", "流量过大，系统开小车" ),
    REQUEST_TIMEOUT("REQUEST_TIMEOUT",6666,"request timeout","请求超时，请稍后重试" ,""),
    SERVICE_NOT_FOUND_ERROR("SERVICE_NOT_FOUND_ERROR", 4444, "service not found", "请求资源不存在" ),
    FALLBACK_PREWARN_ERROR("FALLBACK_PREWARN_ERROR", 3333, "fallback prewarn error", " 接口在10分钟内出现5次错误，请及时查看" ),

    ;
    /** 错误码 */
    private String code;

    /** 错误编号 */
    private int errorCode;

    /**  英文错误描述 */
    private String enDesc;

    /**  中文错误描述 */
    private String desc;

    /** 自定义的错误信息 */
    private String customMsg;


    MExceptionCode(String code, int errorCode, String enDesc, String desc, String customMsg) {
        this.code = code;
        this.errorCode = errorCode;
        this.enDesc = enDesc;
        this.desc = desc;
        this.customMsg = customMsg;
    }

    MExceptionCode(String code, int errorCode, String enDesc, String desc) {
        this.code = code;
        this.errorCode = errorCode;
        this.enDesc = enDesc;
        this.desc = desc;
        this.customMsg = "";
    }

    public static MExceptionCode getByCode(int errorCode) {
        MExceptionCode[] errorCodes = values();

        for (MExceptionCode exceptionCode : errorCodes) {
            if (exceptionCode.getErrorCode() == errorCode) {
                return exceptionCode;
            }
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getEnDesc() {
        return enDesc;
    }

    public void setEnDesc(String enDesc) {
        this.enDesc = enDesc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCustomMsg() {
        return customMsg;
    }

    public void setCustomMsg(String customMsg) {
        this.customMsg = customMsg;
    }
}
