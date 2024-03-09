package com.example.bank.config;

import com.example.bank.model.Account;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    // Настройки для подключения к брокеру Kafka
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // Другие настройки Kafka
        return props;
    }

    // Фабрика для создания Kafka Producer
    public ProducerFactory<String, Account> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    // Шаблон для отправки сообщений в Kafka
    public KafkaTemplate<String, Account> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}