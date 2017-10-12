#!/usr/bin/python3

import os
from subprocess import call

os.chdir(os.getenv('HOME') + '/kafka-training/kafka/bin/')

# Create failsafe topic
call(["./kafka-topics.sh", "--create", "--zookeeper", "localhost:2181", "--replication-factor", "3", "--partitions", "13", "--topic", "my-failsafe-topic"])
