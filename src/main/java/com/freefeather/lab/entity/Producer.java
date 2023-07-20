package com.freefeather.lab.entity;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Producer {

    @Getter
    private Queue<Integer> queue = new LinkedList<>();

    private List<Consumer> consumerList = new ArrayList<>();

    public void subscribe(Consumer consumer) {
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
            log.info("find nothing");
        }

        log.info("customerList: " + consumerList.size());
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
