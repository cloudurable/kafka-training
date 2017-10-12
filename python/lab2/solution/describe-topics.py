#!/usr/bin/python3

import os
from subprocess import call

os.chdir(os.getenv('HOME') + '/kafka-training/kafka/bin/')

# Describe failsafe topic
call(["./kafka-topics.sh", "--describe", "--topic", "my-failsafe-topic", "--zookeeper", "localhost:2181"])
