package com.pulley.freewheelin.global.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.jvm.java

@Configuration
class RedissonConfig {

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config.fromYAML(RedissonConfig::class.java.getResource("/redisson.yaml"))
        return Redisson.create(config)
    }
}
