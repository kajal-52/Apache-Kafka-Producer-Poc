package com.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class DispatcherDemo {
    private static final Logger logger = Logger.getLogger("org");

    public static void main(String[] args) {
        Properties prop =new Properties();
        try {
            InputStream is = new FileInputStream(AppConfigs.kafkaConfigFile);
            prop.load(is);
            prop.put(ProducerConfig.CLIENT_ID_CONFIG,AppConfigs.applicationIDNew);
            prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
            prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        }
        catch (IOException e){
            throw new RuntimeException();
        }
        KafkaProducer<Integer,String> producer= new KafkaProducer<Integer, String>(prop);
        Thread[] dispatchers = new Thread[AppConfigs.eventFilesPath.length];
        logger.info("Starting dispatcher thread");
        for (int i =0 ; i< AppConfigs.eventFilesPath.length;i++){
            dispatchers[i] =  new Thread(new Dispatcher(producer,AppConfigs.topicNameNew,AppConfigs.eventFilesPath[i]));
            dispatchers[i].start();

        }
        try {
            for (Thread t : dispatchers) t.join();
        }catch (InterruptedException e)
            logger.info("Main thread interrupted");

    }
}
