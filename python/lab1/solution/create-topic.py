#!/usr/bin/python3

import os
from subprocess import call

os.chdir(os.getenv('HOME') + '/kafka-training/kafka/bin/')

# Create a topic
call(["./kafka-topics.sh", "--create", "--zookeeper", "localhost:2181", "--replication-factor", "1", "--partitions", "13", "--topic", "my-topic"])
