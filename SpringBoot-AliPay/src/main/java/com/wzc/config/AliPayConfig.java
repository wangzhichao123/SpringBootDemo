package com.wzc.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    /**
     * APPID
     */
    private String appId;
    /**
     * 应用公钥
     */
    private String appPrivateKey;
    /**
     * 应用私钥
     */
    private String alipayPublicKey;
    /**
     * 回调地址
     */
    private String notifyUrl;
    /**
     * 网关地址
     */
    private String gatewayUrl;

    /**
     * 支付成功跳转地址
     */
    private String returnUrl;

    @Bean
    public AlipayClient alipayClient() throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(gatewayUrl);
        alipayConfig.setAppId(appId);
        alipayConfig.setPrivateKey(appPrivateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        return new DefaultAlipayClient(alipayConfig);
    }

}
