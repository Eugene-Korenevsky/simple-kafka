package com.example.abwkafkaproducer.kafka_consumers;

import com.example.abwkafkaproducer.models.clients.abw.car_ad.CarAdDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CarAdKafkaConfig {
    String bootstrapAddress = "localhost:9092";

    @Bean
    public ProducerFactory<String, CarAdDTO> carAdDTOProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }


    public ConsumerFactory<String, CarAdDTO> carAdDTOKafkaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "currency_exchangeGroup");
        JsonDeserializer<CarAdDTO> jsonDeserializer
                = new JsonDeserializer<>(CarAdDTO.class, false);
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public KafkaTemplate<String, CarAdDTO> carAdDTOKafkaTemplate() {
        return new KafkaTemplate<>(carAdDTOProducerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CarAdDTO> carAdDTOKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CarAdDTO> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(carAdDTOKafkaConsumerFactory());
        factory.setReplyTemplate(carAdDTOKafkaTemplate());
        return factory;
    }
}
