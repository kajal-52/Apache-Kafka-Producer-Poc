package com.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Dispatcher implements Runnable{
    private static final Logger logger = Logger.getLogger("org");
    private String fileLocation;
    private String topicName;
    private KafkaProducer<Integer, String> kafkaProducer;

    public Dispatcher(String fileLocation, String topicName, KafkaProducer<Integer, String> kafkaProducer) {
        this.fileLocation = fileLocation;
        this.topicName = topicName;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void run() {
        logger.info("Start Processing "+ fileLocation);
        File file = new File(fileLocation);
        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()){
                String line =scanner.nextLine();
                kafkaProducer.send(new ProducerRecord<>(topicName,null,line));
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException();
        }

    }
}
