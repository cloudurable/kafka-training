# Lab 6.7: Implementing a priority queue with consumer.assign()

Welcome to the session 6 lab 7. The work for this lab is done in `~/kafka-training/lab6.7`.
In this lab, you are going to implement a priority queue with consumer.assign().

Please refer to the [Kafka course notes](https://goo.gl/a4kk5b) for any updates or changes to this lab.

Find the latest version of this lab [here](https://gist.github.com/RichardHightower/81a66e0f9822e9e74660deec10640d27).


## Lab Using consumer.assign to implement at priority queue
In this lab you will implement a priority processing queue.
You will use consumer.partitionsFor(TOPIC) to get a list of partitions.
Usage like this simplest when the partition assignment is also done manually using assign()
instead of subscribe().
Use assign(), pass TopicPartition from the consumer worker.
Use Partitioner from earlier example for Producer so only important stocks are sent to
the important partition.



## Lab Work

Use the slides for Session 6 as a guide.

## ***ACTION*** - EDIT `com.cloudurable.kafka.consumer.ConsumerMain` and follow the instructions in the file.
## ***ACTION*** - EDIT `com.cloudurable.kafka.consumer.StockPriceConsumerRunnable` and follow the instructions in the file.
## ***ACTION*** - RECREATE the topic with five partitions (HINT: bin/create-topic.sh) and use 5 partitions.


## ***ACTION*** - RUN ZooKeeper and Brokers if needed.
## ***ACTION*** - RUN ConsumerMain from IDE
## ***ACTION*** - RUN StockPriceKafkaProducer from IDE
## ***ACTION*** - OBSERVE and then STOP consumers and producer

## Expected behavior
It should run and should get messages like this:

#### Expected output

```sh
New ConsumerRecords par count 1 count 153, max offset
ticker IBM price 66.59 Thread 4
ticker UBER price 241.94 Thread 4

New ConsumerRecords par count 1 count 220, max offset
ticker ABC price 95.85 Thread 2
ticker BBB price 53.36 Thread 2
ticker FFF price 70.34 Thread 2

New ConsumerRecords par count 1 count 318, max offset
ticker GOOG price 458.44 Thread 0
ticker DDD price 68.38 Thread 0
ticker SUN price 91.90 Thread 0
ticker INEL price 65.94 Thread 0

New ConsumerRecords par count 1 count 364, max offset
ticker AAA price 66.53 Thread 1
ticker DEF price 65.94 Thread 1
ticker EEE price 70.34 Thread 1
ticker XYZ price 65.94 Thread 1

```

## Try the following

Try using different worker pool sizes and different consumer thread pool sizes.
Try adding a small wait for the processing. Try 10ms.
It should all run. Stop consumer and producer when finished.


