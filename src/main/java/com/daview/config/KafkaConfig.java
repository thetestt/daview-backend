package com.daview.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 숲: Care Pulse Tracker를 위한 Kafka 설정
 * 나무: raw-events, pulse-alert 토픽에 대한 Producer/Consumer 설정
 * 주의: 기존 채팅 시스템 설정은 건드리지 않음
 */
@Configuration
@ConditionalOnProperty(name = "care-pulse.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:daview-tracker}")
    private String groupId;

    /**
     * 숲: LLM 전용 이벤트 데이터 Producer Factory (기존 채팅과 분리)
     * 나무: String key, JSON value로 직렬화하여 raw-events 토픽에 전송
     */
    @Bean("llmProducerFactory")
    public ProducerFactory<String, Object> llmProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        
        // 숲: 메시지 전송 신뢰성 보장을 위한 설정
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * 숲: LLM 전용 Kafka Template (JSON 객체용, 기존 채팅과 분리)
     * 나무: 이벤트 데이터를 비동기로 토픽에 전송
     */
    @Bean("llmJsonKafkaTemplate")
    public KafkaTemplate<String, Object> llmJsonKafkaTemplate() {
        return new KafkaTemplate<>(llmProducerFactory());
    }

    /**
     * 숲: LLM 전용 String Producer Factory (기존 채팅과 분리)
     * 나무: String key, String value로 직렬화하여 LLM 토픽에 전송
     */
    @Bean("llmStringProducerFactory")
    public ProducerFactory<String, String> llmStringProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        
        // 숲: 메시지 전송 신뢰성 보장을 위한 설정
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * 숲: LLM 전용 String KafkaTemplate (기존 채팅과 분리)
     * 나무: Care Pulse 관련 String 메시지 전송용
     */
    @Bean("llmKafkaTemplate")
    public KafkaTemplate<String, String> llmKafkaTemplate() {
        return new KafkaTemplate<>(llmStringProducerFactory());
    }

    /**
     * 숲: LLM 전용 Consumer Factory (기존 채팅과 분리)
     * 나무: pulse-alert 토픽에서 JSON 객체를 수신하여 해석 서비스로 전달
     */
    @Bean("llmConsumerFactory")
    public ConsumerFactory<String, Object> llmConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        
        // 숲: JSON 역직렬화 시 타입 안전성을 위한 설정
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.daview.dto");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS, false);
        
        // 숲: 메시지 처리 실패 시 재처리를 위한 오프셋 설정
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * 숲: LLM 전용 Kafka Listener Container Factory (기존 채팅과 분리)
     * 나무: 수동 커밋 모드로 메시지 처리 완료 후 오프셋 커밋
     */
    @Bean("llmKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> llmKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(llmConsumerFactory());
        
        // 숲: 수동 오프셋 커밋으로 메시지 처리 완료 보장
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        
        return factory;
    }
} 