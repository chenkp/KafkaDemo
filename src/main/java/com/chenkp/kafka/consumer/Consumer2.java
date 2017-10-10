package com.chenkp.kafka.consumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringEncoder;

import java.util.*;

public class Consumer2 {
    public static Properties props;
    static {
        props = new Properties();
        props.put("zookeeper.connect","192.168.18.129:2181,192.168.18.130:2181,192.168.18.131:2181");
//        props.put("metadata.broker.list", "192.168.18.129:9092,192.168.18.130:9092,192.168.18.131:9092");
        props.put("serializer.class", StringEncoder.class.getName());
        props.put("group.id", "group"); // group组的名字 （做group组区分）
        props.put("group.name", "2"); // 当前group组中的名字
//        props.put("zookeeper.session.timeout.ms", "400");
//        props.put("zookeeper.sync.time.ms", "200");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("auto.offset.reset", "smallest");
        // （在相同的group组中做consumer的qufe）
    }

    public static void main(String[] args) throws InterruptedException {
        String topic = "mytestkafka2";
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, 1); // 取哪一个topic 取几条数据
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer
                .createMessageStreams(topicCountMap);
        final KafkaStream<byte[], byte[]> kafkaStream = messageStreams.get(topic).get(0);
        ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
        while (iterator.hasNext()) {
            String item = new String(iterator.next().message());
            System.out.println("消费者2收到消息：" + item);
        }
    }
}
