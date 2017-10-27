#!/usr/bin/python3

from confluent_kafka import Consumer
import time, sys

TOPIC = 'my-example-topic'
BOOTSTRAP_SERVERS = 'localhost:9092,localhost:9093,localhost:9094'
GROUP_ID = 'KafkaExampleConsumer'

def createConsumer():
	# Set Consumer configuration.
	conf = {'bootstrap.servers':BOOTSTRAP_SERVERS,
			'group.id':GROUP_ID}

	# Create the Consumer using configuration.
	consumer = Consumer(**conf)

	# Subscribe to the topic.
	consumer.subscribe([TOPIC])
	return consumer

def runConsumer(consumer):
	try:
		giveUp = 1000
		noRecordsCount = 0;
		while True:
			msg = consumer.poll(timeout=0.1)
			if msg is None:
				noRecordsCount += 1
				if noRecordsCount > giveUp:
					print('Gave up consuming records!')
					break
				else:
					continue
				if msg.error():
					# Error or event
					if msg.error().code() == KafkaError._PARTITION_EOF:
						# End of partition event
						sys.stderr.write('%% %s [%d] reached end at offset %d\n' %
							(msg.topic(), msg.partition(), msg.offset()))
					elif msg.error():
						# Error
						raise KafkaException(msg.error())
			else:
				# Proper message
				if msg.key() is not None:
					sys.stderr.write('Consumer Record:(%s, %s, %d, %d)\n' %
						(msg.key(), msg.value(), msg.partition(), msg.offset()))

			consumer.commit()

	except KeyboardInterrupt:
		sys.stderr.write('%% Aborted by user\n')

	finally:
		# Close down consumer to commit final offsets.
		consumer.close()

if __name__ == '__main__':
	consumer = createConsumer()
	runConsumer(consumer)
