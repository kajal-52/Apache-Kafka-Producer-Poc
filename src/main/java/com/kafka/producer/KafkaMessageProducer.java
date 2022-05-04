package com.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.logging.Logger;

public class KafkaMessageProducer {
    private static final Logger logger = Logger.getLogger("org");

    public static void main(String[] args) {
        logger.info("Creating Kafka Producer: ");
        //customizing necessary config for producer to work
        Properties properties = new Properties();
        //client id stores info where the message is coming from
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<>(properties);
        for (int i = 0; i< AppConfigs.numEvents; i++){
            kafkaProducer.send(new ProducerRecord<>(AppConfigs.topicName, i, "message_"+i));
        }
        logger.info("Finished sending messages, closing producer");
        //Close producer else end of causing leak of resources
        kafkaProducer.close();


    }
}
