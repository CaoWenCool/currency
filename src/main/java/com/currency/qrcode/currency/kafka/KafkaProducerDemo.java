package com.currency.qrcode.currency.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

/**
 * 发送端代码添加自定义分区
 */
public class KafkaProducerDemo {

    private String topic;
    private Boolean isAsync;
    private Producer producer;

    public KafkaProducerDemo(String topic, Boolean isAsync) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"ip设置");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"kafkaProducerDemo");
        properties.put(ProducerConfig.ACKS_CONFIG,"-1");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.demo.MyPartitioner");
        producer = new KafkaProducer<Integer,String>(properties);
        this.topic = topic;
        this.isAsync = isAsync;
    }
}
