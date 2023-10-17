package org.hidubai.publisher.constants;


public enum ErrorCode {

    COMMON_ERROR_40001(40001),
    RABIT_MQ_ERROR_50001(50001),
    AUTH_ERROR_20001(20001),
    VALIDATION_ERROR_10001(10001),

    SUCCESS(200);

    private Integer code;

    ErrorCode(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }


}
