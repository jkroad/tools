package com.ismayfly.coins.tools.core.exception;

public class MayflyException extends RuntimeException {

    private MExceptionCode errorCode;

    public MayflyException() {
        super();
        errorCode = MExceptionCode.SYSTEM_ERROR;
    }
    public  MayflyException(String msg){
        super(msg);
    }
    public MayflyException(MExceptionCode exceptionCode) {
        super(exceptionCode.getDesc());
        errorCode = exceptionCode;
        exceptionCode.setCustomMsg("");
    }

    public MayflyException(MExceptionCode exceptionCode, Throwable e) {
        super(e.getMessage());
        errorCode = exceptionCode;
        exceptionCode.setCustomMsg("");
    }

    public MayflyException(MExceptionCode exceptionCode, String customMsg) {
        super(customMsg);
        errorCode = exceptionCode;
        exceptionCode.setCustomMsg(customMsg);
    }

    public MExceptionCode getErrorCode() {
        return errorCode;
    }
}
