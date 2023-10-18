package org.hidubai.publisher.constants;


public enum HttpCode {

    RABIT_MQ_ERROR_50001(50001),
    AUTH_ERROR_20001(20001),
    VALIDATION_ERROR_10001(10001),

    PUBLISHED(200);

    private Integer code;

    HttpCode(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }


}
