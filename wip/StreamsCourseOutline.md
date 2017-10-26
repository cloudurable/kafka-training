# Kafka Streams

## What is Kafka Streams?

##### Overview
* Processing and transformation library for "streams" (lists? collections?) of Kafka data.

##### Use Cases
* Data Transformations / Manipulations
* Data Enrichment
* Data Monitoring

##### General
* Integrated (built in) to Kafka - no extra libraries or runtimes.
* Part of the existing cluster.

* Exactly Once Capabilities
* One record at a time (NO BATCHES)
* Small or big projects.

## Architecture

* Standard clustering
* One or more stream client apps
* Competes with Apache Spark and Flink
* Completed for release 0.11.0.0 (June 2017)
* Newness opens the project up for changes - won't conceptually changes, but there will be tweaks and improvements.

## Objectives

* Practice
    * Familiarize with general concepts
    * Practice transformations
    * Use aggregations and Exactly Once
    * Enrich a stream.

* Learn
    * Fundamental concepts
    * Transformations - stateless and stateful
    * Grouping, aggregation and joins
    * Exactly Once Semantics and abilities
    * Comparison versus competetors.
    * Basic devops

## Stream Lab 1

Example Word Count Go To Lab...

## Kafka Streams vs Spark Streaming, NiFi, Flink

* Constantly evolving and growing.
* Spark does micro batches, small batches, Kafka does one record at a time. 
* Spark, NiFi and Flink needs a cluster.
* You can scale the stream to match the number of partitions by adding consumers.
* Kafka has Exactly Once semantics. Spark did not, but does now.
* Spark commonly uses Kafka itself as a data source  

## Terminology

* A **stream** is a sequence of immutable data elements. A topic can be considered an ordered, immutable stream.
* A **stream processor** processes the stream. Since the input is immutable, stream processors may create a new stream from the original stream, but transformed.
* The **High Level DSL** is essentially the java api.

## Custom Word Count - Concepts

* Kafka streaming leverages the already existing Consumer and Producer API.
* There are additional classes that make up the Kafka Streams DLS (classes / api) 
* Configuration properties are similar to normal Consumer / Producer apps with some additional properties.
    * application.id (`StreamsConfig.APPLICATION_ID_CONFIG`) is a specific property
        * Used as the consumer group.id
        * Other internal uses.
    * auto.offset.reset (`ConsumerConfig.AUTO_OFFSET_RESET_CONFIG`)
        * What to do when there is no initial offset in Kafka or if the current offset does not exist any more on the server
    * 'serde' (SERialization and DEserialization of data)
        * default.key.serde (`StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG`)
        * default.value.serde (`StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG`)
* Use Java 8 Language features: `Lambda`

## Custom Word Count - Use the DSL

##### Stream Concepts
* Remember Kafka streams are key value pairs. Like a Java Map<Key, Value>
* https://kafka.apache.org/0110/javadoc/org/apache/kafka/streams/kstream/package-summary.html
* KStreamBuilder is the starting point for the DSL
* KStream is an abstraction of a record stream for a specific topic. It will give us a record at a time to work against.
* KTable is an abstraction of a changelog stream, but works much like a map (or a sql table with two fields, a primary key and the value)  

##### Stream API Methods
* KStream.mapValues - Transform the value of each input record into a new value (with possible new type) of the output record.
* KStream.flatMapValues - Transform each record of the input stream into zero or more records in the output stream (both key and value type can be altered arbitrarily)
* KStream.selectKey - Set a new key (with possibly new type) for each input record.
* KStream.groupByKey - Group the records by their current key into a KGroupedStream
* KGroupedStream.count - Count the number of records in this stream by the grouped key;
* KTable.to - write the results back to a topic.

    
    



