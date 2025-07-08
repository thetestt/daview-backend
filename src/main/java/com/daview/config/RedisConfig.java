package com.daview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

/**
 * 숲: Care Pulse 데이터 캐싱을 위한 Redis 설정
 * 나무: 알림 데이터와 분석 결과를 메모리에 캐싱하여 응답 속도 향상
 * 주의: 기존 시스템 설정은 건드리지 않음
 */
@Configuration
@EnableCaching
@ConditionalOnProperty(name = "care-pulse.enabled", havingValue = "true", matchIfMissing = false)
public class RedisConfig {

    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private int redisPort;

    @Value("${spring.data.redis.password:}")
    private String redisPassword;

    /**
     * 숲: Redis 연결 팩토리 설정
     * 나무: Lettuce 커넥션 풀로 Redis 서버와 안정적인 연결 유지
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisHost, redisPort);
        
        // 숲: Redis 인증이 설정된 경우 패스워드 적용
        if (!redisPassword.isEmpty()) {
            factory.setPassword(redisPassword);
        }
        
        return factory;
    }

    /**
     * 숲: Redis 데이터 조작을 위한 Template
     * 나무: String key와 JSON value로 Pulse Alert 및 사용자 알림 저장
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        
        // 숲: Key는 String, Value는 JSON으로 직렬화하여 타입 안전성 보장
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        
        return template;
    }

    /**
     * 숲: Spring Cache 추상화를 위한 Cache Manager
     * 나무: @Cacheable 어노테이션으로 메서드 결과를 자동 캐싱
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(30))  // 숲: 30분 TTL로 최신 데이터 유지
            .serializeKeysWith(org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(redisConnectionFactory())
            .cacheDefaults(config)
            .build();
    }
} 