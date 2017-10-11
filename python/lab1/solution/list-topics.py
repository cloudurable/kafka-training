#!/usr/bin/python3

import os
from subprocess import call

os.chdir(os.getenv('HOME') + '/kafka-training/kafka/bin/')

# List existing topics
call(["./kafka-topics.sh", "--list", "--zookeeper", "localhost:2181"])
