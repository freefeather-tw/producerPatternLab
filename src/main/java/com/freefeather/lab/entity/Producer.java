package com.freefeather.lab.entity;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Component
public class Producer {

    @Getter
    private Queue<Integer> queue = new LinkedList<>();

    private List<Consumer> consumerList = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        log.debug("Producer initialize");

        IntStream.range(0, 2)
                .forEach(i -> {
                    Consumer c = new Consumer("consumer" + i, i * 1500L, 10);
                    this.subscribe(c);
                });

        log.info("consumerList: " + consumerList.size());
    }

    @PreDestroy
    public void destroy() {
        log.debug("Producer destroy");
        //在這裡可以將consumerList寫入檔案
    }

    public void subscribe(Consumer consumer) {
        log.debug("subscribe: " + consumer.getName());
        synchronized (consumerList) {
            if (!consumerList.contains(consumer)) {
                consumer.setQueue(queue);
                consumer.start();
                consumerList.add(consumer);
            }
        }
    }

    public void unSubscribe(Consumer consumer) {
        Optional<Consumer> optional = consumerList.stream().filter(c -> c.getName().equals(consumer.getName())).findFirst();

        if (optional.isPresent()) {
            optional.get().stop();
            consumerList.remove(consumer);
        } else {
            log.debug("find nothing");
        }

        log.debug("customerList: {}", consumerList.size());
    }

    public void add(Integer integer) {
        synchronized (queue) {
            queue.add(integer);
        }
    }

    public void addAll(Collection<Integer> collection) {
        synchronized (queue) {
            queue.addAll(collection);
        }
    }
}
