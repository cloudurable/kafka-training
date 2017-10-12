#!/usr/bin/python3

import os
from subprocess import call

os.chdir(os.getenv('HOME') + '/kafka-training/kafka/bin/')

# Start Kafka Consumer
call(["./kafka-console-producer.sh", "--broker-list", "localhost:9092,localhost:9093",
									 "--topic", "my-failsafe-topic"])
