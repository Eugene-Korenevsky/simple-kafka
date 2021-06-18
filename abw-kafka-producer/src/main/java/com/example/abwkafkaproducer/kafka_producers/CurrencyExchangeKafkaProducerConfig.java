package com.example.abwkafkaproducer.kafka_producers;

import com.example.abwkafkaproducer.models.clients.abw.currency.CurrencyExchangeDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class CurrencyExchangeKafkaProducerConfig {
    private final String bootstrapAddress = "localhost:9092";

    @Bean
    public ProducerFactory<String, CurrencyExchangeDTO> userProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ReplyingKafkaTemplate<String, CurrencyExchangeDTO, CurrencyExchangeDTO> replyingKafkaTemplate(
            ProducerFactory<String, CurrencyExchangeDTO> pf, ConcurrentKafkaListenerContainerFactory<String, CurrencyExchangeDTO> factory) {
        ConcurrentMessageListenerContainer<String, CurrencyExchangeDTO> replyContainer = factory.createContainer("currency_exchange-result");
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId("currency_exchangeGroup");
        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }

    public ConsumerFactory<String, CurrencyExchangeDTO> kafkaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "currency_exchangeGroup");
        JsonDeserializer<CurrencyExchangeDTO> jsonDeserializer
                = new JsonDeserializer<>(CurrencyExchangeDTO.class, false);
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CurrencyExchangeDTO> exchangeKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CurrencyExchangeDTO> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory());
        factory.setReplyTemplate(userKafkaTemplate());
        return factory;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, CurrencyExchangeDTO> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, CurrencyExchangeDTO> containerFactory) {

        ConcurrentMessageListenerContainer<String, CurrencyExchangeDTO> repliesContainer =
                containerFactory.createContainer("currency_exchange-result");
        repliesContainer.getContainerProperties().setGroupId("currency_exchangeGroup");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }


    public KafkaTemplate<String, CurrencyExchangeDTO> userKafkaTemplate() {
        return new KafkaTemplate<>(userProducerFactory());
    }
}
