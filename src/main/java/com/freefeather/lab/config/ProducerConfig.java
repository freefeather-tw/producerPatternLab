package com.freefeather.lab.config;

import com.freefeather.lab.entity.Consumer;
import com.freefeather.lab.entity.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
public class ProducerConfig {

    @Bean
    public Producer producer() {
        Producer producer = new Producer();
        IntStream.range(0, 2)
                .forEach(i -> {
                    Consumer c = new Consumer("consumer" + i, i * 1500L, 10);
                    producer.subscribe(c);
                });

        return producer;
    }
}
