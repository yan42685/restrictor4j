package org.skoal.restrictor.basic;

import lombok.Getter;

@Getter
public class KnownException extends RuntimeException {
    private static final long serialVersionUID = 3413470958629677916L;
    /**
     * 异常代码
     */
    private Integer errorCode;

    public KnownException(ExceptionEnum exception) {
        super(exception.getErrorMsg());
        this.errorCode = exception.getErrorCode();
    }

    /**
     * 自定义 message
     */
    public KnownException(String message) {
        super(message);
        this.errorCode = -9999;
    }
}
