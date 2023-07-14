package com.freefeather.lab.entity;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
public class Producer {

    @Getter
    private Queue<Integer> queue = new LinkedList<>();

    private List<Customer> customerList = new ArrayList<>();

    public void subscribe(Customer customer) {
        synchronized (customerList) {
            if (!customerList.contains(customer)) {
                customer.setQueue(queue);
                customer.start();
                customerList.add(customer);
            }
        }
    }

    public void unSubscribe(Customer customer) {
        Optional<Customer> optional = customerList.stream().filter(c -> c.getName().equals(customer.getName())).findFirst();

        if (optional.isPresent()) {
            optional.get().stop();
            customerList.remove(customer);
        } else {
            log.info("find nothing");
        }

        log.info("customerList: " + customerList.size());
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
