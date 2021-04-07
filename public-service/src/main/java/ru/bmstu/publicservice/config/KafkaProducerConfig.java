package ru.bmstu.publicservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.bmstu.publicservice.controller.Record;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.producer.value-deserializer}")
    private String valueDeserializer;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueDeserializer);
        return props;
    }

    @Bean
    public ProducerFactory<String, Record> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Record> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
