#!/usr/bin/python3

import os
from subprocess import call

os.chdir(os.getenv('HOME') + '/kafka-training/kafka/bin/')

# Start Kafka Consumer
call(["./kafka-console-consumer.sh", "--bootstrap-server", "localhost:9094,localhost:9092",
									 "--topic", "my-failsafe-topic",
									 "--consumer-property", "group.id=mygroup",
									 "--from-beginning"])
