package com.wzc.domain;

import lombok.Data;

@Data
public class AliPay {
    private String traceNo;
    private Double totalAmount;
    private String subject;
    private String alipayTraceNo;
}
