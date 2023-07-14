package com.freefeather.lab.controller;

import com.freefeather.lab.entity.Customer;
import com.freefeather.lab.entity.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private Producer producer;

    @GetMapping("/addCustomer/{name}")
    public String addCustomer(@PathVariable("name") String customerName) {
        Customer c = new Customer(customerName, 1000L);
        producer.subscribe(c);

        return "Finish";
    }

    @GetMapping("/removeCustomer/{name}")
    public String removeCustomer(@PathVariable("name") String customerName) {
        Customer c = new Customer(customerName);
        producer.unSubscribe(c);

        return "Finish";
    }

    @PostMapping("/addData")
    public String addData(@RequestBody Map<String, Integer> data) {
        Integer start = data.get("start");
        Integer end   = data.get("end");

        producer.addAll(IntStream.range(start, end).boxed().collect(Collectors.toList()));

        return "Finish";
    }

}
