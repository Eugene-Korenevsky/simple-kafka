package com.example.abwkafkaproducer;

import com.example.abwkafkaproducer.models.CarAdDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:kafka.properties")
public class KafkaConfig {

    @Value("${kafka.host}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, ?> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * in DeadLetterPublishingRecoverer we can add some logic with dead letter
     * also if we will use DeadLetterPublishingRecoverer with default configs, it will add ".DLT" to original topic
     **/
    @Bean
    public KafkaListenerContainerFactory<?> kafkaJsonListenerContainerFactory(ConsumerFactory consumerFactory,
                                                                              KafkaTemplate<Object, Object> template) {
        DeadLetterPublishingRecoverer recovered = new DeadLetterPublishingRecoverer(template,
                (r, e) -> {
                    return new TopicPartition(r.topic() + ".DLT", r.partition());
                });
        ErrorHandler errorHandler = new SeekToCurrentErrorHandler(recovered, new FixedBackOff(1000L, 2L));
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setMessageConverter(new JsonMessageConverter());
        factory.setErrorHandler(errorHandler);
        return factory;
    }


}
