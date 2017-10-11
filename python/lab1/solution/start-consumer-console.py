#!/usr/bin/python3

import os
from subprocess import call

os.chdir(os.getenv('HOME') + '/kafka-training/kafka/bin/')

# Create a topic
call(["./kafka-console-consumer.sh", "--bootstrap-server", "localhost:9092", "--topic", "my-topic", "--from-beginning"])
