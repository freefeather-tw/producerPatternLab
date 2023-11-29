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
@Slf4j
public class ProducerController {

    @Autowired
    private Producer producer;

    @PostMapping("/producer/consumer/{name}")
    public String addConsumer(@PathVariable("name") String consumerName) {
        Consumer c = new Consumer(consumerName, 1000L);
        producer.subscribe(c);

        return "Finish";
    }

    @DeleteMapping("/producer/consumer/{name}")
    public String removeConsumer(@PathVariable("name") String consumerName) {
        Consumer c = new Consumer(consumerName);
        producer.unSubscribe(c);

        return "Finish";
    }

    @PutMapping("/producer/consumer:pause/{name}")
    public String pauseConsumer(@PathVariable("name") String consumerName) {
        producer.pauseConsumer(consumerName);

        return "Finish";
    }

    @PutMapping("/consumer:resume/{name}")
    public String resumeConsumer(@PathVariable("name") String consumerName) {
        producer.resumeConsumer(consumerName);

        return "Finish";
    }

    @PostMapping("/producer/data")
    public String addData(@RequestBody Map<String, Integer> data) {
        log.debug("data: [{}]" , data);
        Integer start = data.get("start");
        Integer end   = data.get("end");

        producer.addAll(IntStream.range(start, end).boxed().collect(Collectors.toList()));

        return "Finish";
    }

    @GetMapping("/producer/test:underline")
    public String testUnderline() {
        int test = 123_456;

        log.debug("test: [{}]" , test);

        return "Finish";
    }
}
