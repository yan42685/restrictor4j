package org.skoal.restrictor.basic;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    /**
     * 异常码规则：
     * 正数表示内部异常
     * 负数表示外部异常，应由调用者处理
     */
    UNIMPLEMENTED(-99, "方法未实现");

    private int errorCode;
    private String errorMsg;


}
