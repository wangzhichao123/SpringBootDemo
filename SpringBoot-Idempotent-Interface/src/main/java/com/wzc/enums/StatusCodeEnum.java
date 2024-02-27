package com.wzc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    SUCCESS(200,"操作成功"),

    FAIL(510,"操作失败"),

    SERVICE_UNAVAILABLE(503,"服务不可用"),

    UNAUTHORIZED(401, "未授权");

    private final Integer code;

    private final String desc;
}
