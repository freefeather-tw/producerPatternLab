package com.freefeather.lab.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
public class Consumer implements Runnable {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Getter
    private String name;

    private Long delay;

    private Queue<Integer> queue;

    private Thread thread;

    private String status = "START";

    private Integer batch = 10;

    private Consumer() {}

    public Consumer(String name) {
        this(name, 0L);
    }

    public Consumer(String name, Long delay) {
        this(name, delay, 10);
    }

    public Consumer(String name, Long delay, Integer batch) {
        this.name = name;
        this.delay = delay;
        this.batch = batch;
    }

    void start() {
        status = "START";

        if (null != delay) {
            try {
                log.info(getDateTimeString() +": " + name + " 要delay了！！");
                Thread.sleep(delay);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info(getDateTimeString() + ": " + name + " 開始執行thread");

        thread = new Thread(this);
        thread.start();

    }

    void stop() {
        status = "STOP";
    }

    @Override
    public void run() {
        while (true) {
            if ("START".equals(status)) {
                List<Integer> processList = new ArrayList<>();
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        //log.info(name + " Queue 空空如也，沒有資料可以處理了！！");
                    } else {
                        log.info("queue 還有 " + queue.size() + "筆資料");
                        IntStream.range(0, batch)
                                .forEach(i -> {
                                    Integer result = queue.poll();

                                    if (null != result) {
                                        processList.add(result);
                                    }
                                });
                    }
                }

                //處理資料
                if (!processList.isEmpty()) {
                    log.info(name + " 處理了: " + Strings.join(processList, ','));
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //跳出迴圈
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Consumer consumer = (Consumer) o;

        return Objects.equals(name, consumer.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    void setQueue(Queue queue) {
        this.queue = queue;
    }

    private String getDateTimeString() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        return dateTimeFormatter.format(localDateTime);
    }
}
