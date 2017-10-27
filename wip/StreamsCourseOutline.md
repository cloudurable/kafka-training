# Kafka Streams

## What are Kafka Streams?

##### Overview
* Processing and transformation library for "streams" (lists? collections?) of Kafka data.

##### Use Cases
* Data Transformations / Manipulations
* Data Enrichment
* Data Monitoring
* ?????

## Architecture
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

## Objectives

* Learn
    * Fundamental concepts
    * Transformations - stateless and stateful
    * Grouping, aggregation and joins
    * Exactly Once Semantics and abilities
    * Comparison versus competetors.
    * Basic devops

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

## Custom Word Count - Concepts (Stream Lab 1)

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

## Custom Word Count - Use the DSL (Stream Lab 1)

## Stream Concepts
* Remember Kafka streams are key value pairs. Like a Java Map<Key, Value>
* https://kafka.apache.org/0110/javadoc/org/apache/kafka/streams/kstream/package-summary.html
* KStreamBuilder is the starting point for the DSL
* KStream is an abstraction of a record stream for a specific topic. It will give us a record at a time to work against.
* KTable is an abstraction of a changelog stream, but works much like a map (or a sql table with two fields, a primary key and the value)  

## Stream API Methods
* KStream.mapValues - Transform the value of each input record into a new value (with possible new type) of the output record.
* KStream.flatMapValues - Transform each record of the input stream into zero or more records in the output stream (both key and value type can be altered arbitrarily)
* KStream.selectKey - Set a new key (with possibly new type) for each input record.
* KStream.groupByKey - Group the records by their current key into a KGroupedStream
* KGroupedStream.count - Count the number of records in this stream by the grouped key;
* KTable.to - write the results back to a topic.

## Kafka Stream Demo (Stream Lab 1) 

Sidebar:
> Using the command line tools to prepare for the demo.
> Running the Kafka Word Count Demo

## Objectives (Stream Lab 1)

* Use Kafka demo code to see streams in action.  

![alt text](http://arondight.com/kafka/slab01-objectives.jpg "")

## Create input and output topics (Stream Lab 1)

* If not already running, start ZooKeeper and at least one broker
* Create Input Topic
* Create Output Topic 

![alt text](http://arondight.com/kafka/slab01-create-topics-1.jpg "")
![alt text](http://arondight.com/kafka/slab01-create-topics-2.jpg "")
 
## Produce to the input topic (Stream Lab 1)

![alt text](http://arondight.com/kafka/slab01-produce.jpg "")

## Check (consume) the input (Stream Lab 1)

![alt text](http://arondight.com/kafka/slab01-consume-input.jpg "")

## Consume the output (Stream Lab 1)
Start the output consumer first and leave it visible if possible.

![alt text](http://arondight.com/kafka/slab01-consume-output.jpg "")

## Use the Demo code (Stream Lab 1)

![alt text](http://arondight.com/kafka/slab01-rundemo.jpg "")
![alt text](http://arondight.com/kafka/slab01-consume-output-results.jpg "")

[Word Count demo in github](https://github.com/apache/kafka/blob/trunk/streams/examples/src/main/java/org/apache/kafka/streams/examples/wordcount/WordCountDemo.java)

## Kafka Stream Example (Stream Lab 2) 

Sidebar:
> Using the command line tools to prepare for the demo.
> Writing word count stream.

## Objectives (Stream Lab 2)

* Write your own version of the Kafka demo.

![alt text](http://arondight.com/kafka/slab02-objectives.jpg "")

## Create input and output topics (Stream Lab 2)

* If not already running, start ZooKeeper and at least one broker
* Create Input Topic
* Create Output Topic

![alt text](http://arondight.com/kafka/slab02-create-topics-1.jpg "")
![alt text](http://arondight.com/kafka/slab02-create-topics-2.jpg "")

## Produce to the input topic (Stream Lab 2) 

![alt text](http://arondight.com/kafka/slab02-produce.jpg "")

## Check (consume) the input (Stream Lab 2)

![alt text](http://arondight.com/kafka/slab02-consume-input.jpg "")

## Consume the output (Stream Lab 2)
Start the output consumer first and leave it visible if possible.

![alt text](http://arondight.com/kafka/slab02-consume-output.jpg "")

## Edit The Code 1 (Stream Lab 2)

## Edit The Code 2 (Stream Lab 2)

## Edit The Code 3 (Stream Lab 2)

## Run The Code 3 (Stream Lab 2)

![alt text](http://arondight.com/kafka/slab02-consume-output-results.jpg "")

## After Lab Discuss Alternate Impls using Lambda (Stream Lab 2)


