package com.chenkp.kafka.consumer;


import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Consumer1 {
    public static Properties props;
    static {
        props = new Properties();
        props.put("zookeeper.connect","192.168.18.129:2181,192.168.18.130:2181,192.168.18.131:2181");
//        props.put("metadata.broker.list", "192.168.18.129:9092,192.168.18.130:9092,192.168.18.131:9092");
        props.put("serializer.class", StringEncoder.class.getName());
        props.put("group.id", "group"); // group组的名字 （做group组区分）
        props.put("group.name", "1"); // 当前group组中的名字
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
            System.out.println("消费者1收到消息：" + item );
        }
    }

}
