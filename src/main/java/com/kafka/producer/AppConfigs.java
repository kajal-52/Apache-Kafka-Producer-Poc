package com.kafka.producer;

class AppConfigs {
    final static String applicationID = "Kafka-Producer";
    final static String applicationIDNew ="Multithreaded-Kafka-Producer";
    final static String bootstrapServers = "localhost:9092,localhost:9093";
    final static String topicName = "producer-topic";
    final static String topicNameNew = "multithreaded-producer";

    final static String kafkaConfigFile = "kafka.properties";
    final static int numEvents = 1000000;
    final static String[] eventFilesPath ={"resources/data/NSE05NOV2018BHAV.csv","resources/data/NSE05NOV2018BHAV.csv"};
}
