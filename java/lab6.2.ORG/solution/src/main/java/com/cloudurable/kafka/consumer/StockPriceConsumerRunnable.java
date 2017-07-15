package com.cloudurable.kafka.consumer;

import com.cloudurable.kafka.model.StockPrice;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.cloudurable.kafka.StockAppConstants.TOPIC;


public class StockPriceConsumerRunnable implements Runnable {
    private static final Logger logger =
            LoggerFactory.getLogger(StockPriceConsumerRunnable.class);

    private final Consumer<String, StockPrice> consumer;
    private final int readCountStatusUpdate;
    private final int threadIndex;
    private final AtomicBoolean stopAll;
    private boolean running = true;

    //Store blocking queue by TopicPartition.
    private final Map<TopicPartition, BlockingQueue<ConsumerRecord>>
            commitQueueMap = new ConcurrentHashMap<>();

    //Worker pool.
    private final ExecutorService threadPool;


    public StockPriceConsumerRunnable(final Consumer<String, StockPrice> consumer,
                                      final int readCountStatusUpdate,
                                      final int threadIndex,
                                      final AtomicBoolean stopAll,
                                      final int numWorkers) {
        this.consumer = consumer;
        this.readCountStatusUpdate = readCountStatusUpdate;
        this.threadIndex = threadIndex;
        this.stopAll = stopAll;
        threadPool = Executors.newFixedThreadPool(numWorkers);
    }


    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    void runConsumer() throws Exception {
        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(TOPIC));
        final Map<String, StockPriceRecord> lastRecordPerStock = new HashMap<>();
        try {
            int readCount = 0;
            while (isRunning()) {
                pollRecordsAndProcess(lastRecordPerStock, readCount);
            }
        } finally {
            consumer.close();
        }
    }


    private void pollRecordsAndProcess(
            final Map<String, StockPriceRecord> currentStocks,
            final int readCount) throws Exception {

        final ConsumerRecords<String, StockPrice> consumerRecords =
                consumer.poll(100);

        if (consumerRecords.count() == 0) {
            if (stopAll.get()) this.setRunning(false);
            return;
        }

        consumerRecords.forEach(record ->
                currentStocks.put(record.key(),
                        new StockPriceRecord(record.value(), true, record)
                ));

        threadPool.execute(() ->
                processRecords(currentStocks, consumerRecords));

        processCommits();

        if (readCount % readCountStatusUpdate == 0) {
            displayRecordsStatsAndStocks(currentStocks, consumerRecords);
        }
    }


    private void processRecords(Map<String, StockPriceRecord> currentStocks,
                                ConsumerRecords<String, StockPrice> consumerRecords) {

        doWork(currentStocks, consumerRecords);
        consumerRecords.forEach(record -> {

            final TopicPartition topicPartition =
                    new TopicPartition(record.topic(), record.partition());

            final BlockingQueue<ConsumerRecord> outputQueue = getOutputQueue(topicPartition);
            try {
                outputQueue.put(record);
            } catch (InterruptedException e) {
                logger.error("Unable to commit record", e);
                Thread.interrupted();
            }

        });
    }

    private void processCommits() {

        commitQueueMap.entrySet().forEach(queueEntry -> {
            final BlockingQueue<ConsumerRecord> queue = queueEntry.getValue();
            final TopicPartition topicPartition = queueEntry.getKey();

            ConsumerRecord consumerRecord = queue.poll();
            ConsumerRecord highestOffset = consumerRecord;

            while (consumerRecord != null) {
                if (consumerRecord.offset() > highestOffset.offset()) {
                    highestOffset = consumerRecord;
                }
                consumerRecord = queue.poll();
            }

            if (highestOffset != null) {
                logger.info(String.format("Sending commit %s %d",
                        topicPartition, highestOffset.offset()));
                try {
                    consumer.commitSync(Collections.singletonMap(topicPartition,
                            new OffsetAndMetadata(highestOffset.offset())));
                } catch (CommitFailedException cfe) {
                    logger.info("Failed to commit record", cfe);
                }
            }

        });
    }

    private void processRecord(ConsumerRecord<String, StockPrice> record) {

    }

    private void commitTransaction() {
    }

    private void rollbackTransaction() {
    }

    private void startTransaction() {

    }


    private BlockingQueue<ConsumerRecord> getOutputQueue(TopicPartition topicPartition) {
        BlockingQueue<ConsumerRecord> queue = commitQueueMap.get(topicPartition);
        if (queue == null) {
            queue = new ArrayBlockingQueue<ConsumerRecord>(10);
            commitQueueMap.put(topicPartition, queue);
        }
        return queue;
    }


    private void doWork(Map<String, StockPriceRecord> currentStocks, ConsumerRecords<String, StockPrice> consumerRecords) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void displayRecordsStatsAndStocks(
            final Map<String, StockPriceRecord> stockPriceMap,
            final ConsumerRecords<String, StockPrice> consumerRecords) {

        System.out.printf("New ConsumerRecords par count %d count %d, max offset\n",
                consumerRecords.partitions().size(),
                consumerRecords.count());
        stockPriceMap.forEach((s, stockPrice) ->
                System.out.printf("ticker %s price %d.%d saved %s Thread %d\n",
                        stockPrice.getName(),
                        stockPrice.getDollars(),
                        stockPrice.getCents(),
                        stockPrice.isSaved(),
                        threadIndex));
        System.out.println();
    }

    @Override
    public void run() {
        try {
            runConsumer();
        } catch (Exception ex) {
            logger.error("Run Consumer Exited with", ex);
            throw new RuntimeException(ex);
        }
    }
}
