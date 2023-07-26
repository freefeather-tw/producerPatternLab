package com.freefeather.lab.controller;

import com.freefeather.lab.entity.Consumer;
import com.freefeather.lab.entity.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/producer")
@Slf4j
public class ProducerController {

    @Autowired
    private Producer producer;

    @GetMapping("/addCustomer/{name}")
    public String addConsumer(@PathVariable("name") String consumerName) {
        Consumer c = new Consumer(consumerName, 1000L);
        producer.subscribe(c);

        return "Finish";
    }

    @GetMapping("/removeCustomer/{name}")
    public String removeConsumer(@PathVariable("name") String consumerName) {
        Consumer c = new Consumer(consumerName);
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

    @GetMapping("/testUnderline")
    public String testUnderline() {
        int test = 123_456;

        log.debug("test; [{}]" , test);

        return "Finish";
    }

}
