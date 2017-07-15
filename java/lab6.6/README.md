# Lab 6.6: Consumer with many threads

Welcome to the session 6 lab 6. The work for this lab is done in `~/kafka-training/lab6.6`.
In this lab, you are going to implement consumers with many threads.

Please refer to the [Kafka course notes](https://goo.gl/a4kk5b) for any updates or changes to this lab.

Find the latest version of this lab [here](https://gist.github.com/RichardHightower/041c2c546e513a3481289ca16fe11cd4).


## Lab Consumer with many threads

Recall that unlike Kafka producers, Kafka consumers are not thread-safe.

All network I/O happens in a thread of the application making calls.  Kafka Consumers
manage buffers, and connections state that threads can't share.

The only exception thread safe method that the consumer has is consumer.wakeup().
The wakeup() method forces the consumer to throw a WakeupException on any thread the
consumer client is blocking.  You can use this to shutdown a consumer from another thread.
The solution and lab use wakeup. This is an easter egg.




## Consumer with many thread

Decouple Consumption and Processing: One or more consumer threads that consume from Kafka
and hands off ConsumerRecords instances to a thread pool where a worker thread can process it.
This approach uses a blocking queue per topic partition to commit offsets to Kafka.
This method is useful if per record processing is time-consuming.

An advantage is option allows independently scaling consumers count and processors count.
Processor threads are independent of topic partition count is also a big advantage.

The problem with this approach is that guaranteeing order across processors requires care as
threads execute independently and a later record could be processed before an
earlier record and then you have to do consumer commits somehow with this out of order offsets.
How do you commit the position unless there is some order? You have to provide the ordering.
(ConcurrentHashMap of BlockingQueues where topic, partition is the key (TopicPartition)?)


## StockPriceConsumerRunnable is still Runnable but now it has many threads

You will need to add a worker threadPool to the StockPriceConsumerRunnable.
The `StockPriceConsumerRunnable` uses a map of blocking queues per TopicPartition to manage
sending offsets to Kafka.


## ConsumerMain

`ConsumerMain` now uses wakeup to gracefuly stop consumer threads.
Check this out. `ConsumerMain` also passes the number of worker threads that each
`StockPriceConsumerRunnable` runs.


## Lab Work

Use the slides for Session 6 as a guide.

## ***ACTION*** - EDIT `com.cloudurable.kafka.consumer.StockPriceConsumerRunnable` and follow the instructions in the file.
## ***ACTION*** - EDIT `com.cloudurable.kafka.consumer.ConsumerMain` and follow the instructions in the file.
## ***ACTION*** - RECREATE the topic with more partitions (HINT: bin/create-topic.sh).


## ***ACTION*** - RUN ZooKeeper and Brokers if needed.
## ***ACTION*** - RUN ConsumerMain from IDE
## ***ACTION*** - RUN StockPriceKafkaProducer from IDE
## ***ACTION*** - OBSERVE and then STOP consumers and producer

## Expected behavior
It should run and should get messages like this:

#### Expected output

```sh
15:57:34.945 [pool-1-thread-2] INFO  c.c.k.c.StockPriceConsumerRunnable -
Sending commit stock-prices-9 320
15:57:34.947 [pool-1-thread-5] INFO  c.c.k.c.StockPriceConsumerRunnable -
Sending commit stock-prices-20 161
15:57:34.947 [pool-1-thread-3] INFO  c.c.k.c.StockPriceConsumerRunnable -
Sending commit stock-prices-12 317
15:57:34.947 [pool-1-thread-4] INFO  c.c.k.c.StockPriceConsumerRunnable -
Sending commit stock-prices-18 320
15:57:34.950 [pool-1-thread-5] INFO  c.c.k.c.StockPriceConsumerRunnable -
Sending commit stock-prices-24 305
15:57:34.952 [pool-1-thread-5] INFO  c.c.k.c.StockPriceConsumerRunnable -
Sending commit stock-prices-21 489
```

## Try the following

Try using different worker pool sizes and different consumer thread pool sizes.
Try adding a small wait for the processing. Try 10ms.


It should all run. Stop consumer and producer when finished.


