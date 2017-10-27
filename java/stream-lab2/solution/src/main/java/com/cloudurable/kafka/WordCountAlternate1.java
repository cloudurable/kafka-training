package com.cloudurable.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Arrays;
import java.util.Properties;

public class WordCountAlternate1 {
    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        KStreamBuilder builder = new KStreamBuilder();

        // 1 - stream from Kafka
        KStream<String, String> input = builder.stream("word-count-input");

        // 2 - map values to lowercase
        //     mapValues(ValueMapper<? super V, ? extends VR> mapper);
        // 3 - flatmap values split by space
        //     flatMapValues(final ValueMapper<? super V, ? extends Iterable<? extends VR>> processor);
        // 4 - select key to apply a key
        //     selectKey(KeyValueMapper<? super K, ? super V, ? extends KR> mapper);
        // 5 - group by key before aggregation
        //     groupByKey()
        // 6 - count occurences

        KTable<String, Long> wordCounts = input
                .mapValues(str -> str.toLowerCase())
                .flatMapValues(textLine -> Arrays.asList(textLine.split(" ")))
                .selectKey((key, word) -> word)
                .groupByKey()
                .count("Counts");

        // 7 - to in order to write the results back to kafka
        wordCounts.to(Serdes.String(), Serdes.Long(), "word-count-output");

        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();

        // print the topology
        System.out.println(streams.toString());

        // shutdown hook to correctly close the streams application
        // the shutdown hook is just a thread, that will not be started until shutdown time
        Runtime.getRuntime().addShutdownHook(new Thread(() -> streams.close()));
    }
}
