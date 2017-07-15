# Lab 6.4: StockPriceConsumer Exactly Once Consumer Messaging Semantics

Welcome to the session 6 lab 4. The work for this lab is done in `~/kafka-training/lab6.4`.
In this lab, you are going to implement Exactly Once messaging semantics.

Please refer to the [Kafka course notes](https://goo.gl/a4kk5b) for any updates or changes to this lab.

Find the latest version of this lab [here](https://gist.github.com/RichardHightower/dfec9a329c617f69e2e5835b218b01b9).


## Lab Exactly Once Semantics


To implement Exactly-Once semantics, you have to control and store the offsets
for the partitions with the output of your consumer operation.
You then have to read the stored positions when your consumer is assigned partitions to consume.

Remember that consumers do not have to use Kafka's built-in offset storage, and to implement
exactly once messaging semantics, you will need to read the offsets from a stable storage.
In this example, we use a JDBC database.

You will need to store offsets with processed record output to make it “exactly once” message consumption.

You will store the output of record consumption in an RDBMS with the offset, and partition.
This approach allows committing both processed record output and location (partition/offset of record) in a single
transaction thus implementing “exactly once” messaging.

## Initializing and saving offsets from ConsumerRebalanceListener

If implementing “exactly once” message semantics, then you have to manage offset positioning
with a `ConsumerRebalanceListener` which gets notified when partitions are assigned or taken away from
a consumer.

You will implement a `ConsumerRebalanceListener` and then
pass the `ConsumerRebalanceListener` instance in call to
`kafkaConsumer.subscribe(Collection, ConsumerRebalanceListener)`.

`ConsumerRebalanceListener` is notified when partitions get taken away from a consumer,
so the consumer can commit its offset for partitions by implementing
`ConsumerRebalanceListener.onPartitionsRevoked(Collection)`.

When partitions get assigned to a consumer, you will need to look up the offset
in a database for new partitions and correctly initialize consumer to that position
by implementing `ConsumerRebalanceListener.onPartitionsAssigned(Collection)`.

## Lab Work

Use the slides for Session 6 as a guide.

## ***ACTION*** - EDIT `com.cloudurable.kafka.consumer.SeekToLatestRecordsConsumerRebalanceListener` follow the instructions in the file.
## ***ACTION*** - EDIT `SimpleStockPriceConsumer.runConsumer` method and follow the instructions to subsribe to topic using SeekToLatestRecordsConsumerRebalanceListener.
## ***ACTION*** - EDIT `SimpleStockPriceConsumer.pollRecordsAndProcess` method and follow the instructions to commit database transaction and Kafka log.

## ***ACTION*** - EDIT `StockPriceRecord` and follow instructions in file.
## ***ACTION*** - EDIT `DatabaseUtilities` and follow instructions in file.




## ***ACTION*** - RUN ZooKeeper and Brokers if needed.
## ***ACTION*** - RUN SimpleStockPriceConsumer from IDE
## ***ACTION*** - RUN StockPriceKafkaProducer from IDE
## ***ACTION*** - OBSERVE and then STOP consumer and producer

## Expected behavior
You should see offset messages from SeekToLatestRecordsConsumerRebalanceListener
in log for consumer.

## ***ACTION*** - STOP SimpleStockPriceConsumer from IDE (while you leave StockPriceKafkaProducer for 30 seconds)
## ***ACTION*** - RUN SimpleStockPriceConsumer from IDE


## Expected behavior
Again, you should see offset messages from SeekToLatestRecordsConsumerRebalanceListener
in log for consumer.

It should all run. Stop consumer and producer when finished.
