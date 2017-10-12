#!/usr/bin/python3

import os
from subprocess import call

kafkaHome = os.getenv('HOME') + '/kafka-training/kafka/bin/'

## Run Kafka Server
call([kafkaHome + "kafka-server-start.sh", "config/server-2.properties"])
