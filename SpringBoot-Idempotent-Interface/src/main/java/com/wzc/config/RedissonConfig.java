package com.wzc.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class RedissonConfig {
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    /**
     * @return RedissonClient
     */
    @Bean(name="redissonClient", destroyMethod="shutdown") // 服务停止后调用 shutdown 方法
    public RedissonClient redissonClient() {
        // 1、创建配置
        Config config = new Config();
        // 2、集群模式
             // config.useClusterServers().addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");
        config.useSingleServer()
                .setDatabase(0)
                .setPassword(StringUtils.isEmpty(password) ? null : password)
                .setAddress(host.contains("://") ? "" : "redis://" + host + ":" + port);
        return Redisson.create(config);
    }
}
