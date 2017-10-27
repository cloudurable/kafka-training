#!/usr/bin/python3

import os
from subprocess import call

os.chdir(os.getenv('HOME') + '/kafka-training/kafka/bin/')

# Create topic
call(["./kafka-topics.sh", "--create",
						   "--replication-factor", "3",
						   "--partitions", "13",
						   "--topic", "my-example-topic",
						   "--zookeeper", "localhost:2181"])

## List created topics
call(["./kafka-topics.sh", "--list",
						   "--zookeeper", "localhost:2181"])
