#!/bin/bash

# generate config for Jupyter notebook
jupyter notebook --generate-config


export NOTEBOOKTOKEN="Tech-College-Data-@2017"
# Uncomment and replace auto-generated token for notebook
# sed -i '/^#c.NotebookApp.token/s/[<]generated[>]/Tech-College-Data-@2017/' /root/.jupyter/jupyter_notebook_config.py
sed -i "/^#c.NotebookApp.token/s/[<]generated[>]/${NOTEBOOKTOKEN}/" /root/.jupyter/jupyter_notebook_config.py
sed -i '/^#c.NotebookApp.token/s/^#//' /root/.jupyter/jupyter_notebook_config.py

# start Jupyter Notebook
nohup jupyter notebook --port=8888 --no-browser --ip=* > jupyter.log 2>&1 &

# start Zookeeper
nohup /opt/kafka/bin/zookeeper-server-start.sh /opt/kafka/config/zookeeper.properties > zookeeper.log 2>&1 &

echo "Zookeeper is starting, one moment please..."
sleep 10

# uncomment lines from Kafka config/server.properties file

sed -i '/listeners=PLAINTEXT:\/\/:9092/s/^#//' /opt/kafka/config/server.properties
sed -i '/delete.topic.enable=true/s/^#//' /opt/kafka/config/server.properties

# start Kafka server
nohup /opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/server.properties > kafka.log 2>&1 &

export SPARK_HOME=/opt/spark
export KAFKA_HOME=/opt/kafka
export ZK_HOME=/opt/zookeeper

export PYTHONPATH=/home/streamuser:/home/root/anaconda/lib/python3.6/site-packages
export PYTHONPATH=$SPARK_HOME/python:$SPARK_HOME/python/build:$PYTHONPATH
export PYTHONPATH=$SPARK_HOME/python/lib/py4j-0.8.2.1-src.zip:$PYTHONPATH

echo "To print a list of running Jupyter servers, enter:"
echo "jupyter notebook list"
echo "If you're running this on a Mac, you can Ctrl + right-click this link and "
echo "then select 'Open URL' from the context menu.  Alternatively, you may copy"
echo "and paste this link into your Web browser:"
echo "http://127.0.0.1:8888/tree?token=${NOTEBOOKTOKEN}"
