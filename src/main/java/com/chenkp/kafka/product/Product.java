package com.chenkp.kafka.product;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Product {

    /**
     * 单独发送
     */
    /*public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("metadata.broker.list","192.168.18.129:9092,192.168.18.130:9092,192.168.18.131:9092");
        props.setProperty("serializer.class","kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.chenkp.kafka.SimplePartitioner");

      ProducerConfig config = new ProducerConfig(props);
        //创建生产这对象
        Producer<String, String> producer = new Producer<>(config);

        String topic = "mytestkafka2";
        try {
            long startTime = System.currentTimeMillis();
            for(int i=1; i<200000; i++) {
                String k = "key" + i;
                String v = k + "--value" + i;
                producer.send(new KeyedMessage<String, String>(topic,k,v));
                System.err.println("发送第"+ i +"个消息");
            }
            long endTime = System.currentTimeMillis();
            System.err.println(endTime-startTime);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(producer != null){
                producer.close();
            }
        }
    }*/

    /**
     * 批量发送
     * @param args
     */
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.18.129:9092,192.168.18.130:9092,192.168.18.131:9092");
        props.put("client.id", "DemoProducer");
//        props.put("batch.size", 16384);//16M
        props.put("linger.ms", 1000);
        props.put("buffer.memory", 33554432);//32M
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String topic = "mytestkafka2";

       Producer<String, String> producer = new KafkaProducer<>(props);
        long startTime = System.currentTimeMillis();
       for(int i = 0; i <= 200000; i++) {
           producer.send(new ProducerRecord<String, String>(topic, Integer.toString(i), Integer.toString(i)));
           System.err.println("第"+i+"条消息");
       }
        long endTime = System.currentTimeMillis();
        System.err.println(endTime-startTime);
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        producer.close();
    }

}
