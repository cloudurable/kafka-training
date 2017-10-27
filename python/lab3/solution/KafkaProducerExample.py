#!/usr/bin/python3

from confluent_kafka import Producer
import time, sys

TOPIC = 'my-example-topic'
BOOTSTRAP_SERVERS = 'localhost:9092,localhost:9093,localhost:9094'
CLIENT_ID = 'KafkaExampleProducer'
DEFAULT_MSG_COUNT = 5

def createProducer():
	# Set Producer configuration.
	conf = {'bootstrap.servers':BOOTSTRAP_SERVERS,
			'client.id':CLIENT_ID}

	# Create Producer.
	return Producer(**conf)

# Callback function to print delivered record.
def onDelivered(err, msg):
	if err:
		sys.stderr.write('%% Message failed delivery: %s\n' % err)
	else:
		elapsedTime = int(time.time()) - (msg.timestamp()[1] / 1000)
		sys.stderr.write('%% sent record(key=%s value=%s) meta(partition=%d, offset=%d) time=%d\n'
						 %(msg.key(), msg.value(), msg.partition(), msg.offset(), elapsedTime))

def runProducer(producer, sendMessageCount):
	timeNow = int(time.time())

	try:
		for index in range(timeNow, timeNow + sendMessageCount):
			producer.produce(TOPIC, key=str(index), value='Hello Mom %d' % index, callback=onDelivered)

	except BufferError as e:
            sys.stderr.write('%% Local producer queue is full (%d messages awaiting delivery): try again\n' %len(producer))
	finally:
		producer.poll(0)
		
	# Wait until all messages have been delivered
	sys.stderr.write('%% Waiting for %d deliveries\n' % len(producer))
	producer.flush(30)

if __name__ == '__main__':
	producer = createProducer()
	msgCount = DEFAULT_MSG_COUNT

	if len(sys.argv) == 2:
		msgCount = int(sys.argv[1])

	runProducer(producer, msgCount)
