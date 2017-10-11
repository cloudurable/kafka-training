#!/usr/bin/python3

import os
from subprocess import call

os.chdir(os.getenv('HOME') + '/kafka-training/kafka/bin/')

# Create a topic
call(["./kafka-console-producer.sh", "--broker-list", "localhost:9092", "--topic", "my-topic"])