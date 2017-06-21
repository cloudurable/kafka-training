FROM dockyard.cloud.capitalone.com/tech-college/data-discipline:base-v01.01 
 
MAINTAINER Maashu Cloney <matthew.cloney@capitalone.com>

ARG proxy=http://10.12.195.66:8099

ENV DEBIAN_FRONTEND=noninteractive
ENV http_proxy ${proxy}
ENV https_proxy ${proxy}
ENV HTTP_PROXY ${proxy}
ENV HTTPS_PROXY ${proxy}

RUN /home/streamuser/setup-ubuntu-proxy.sh

###########################################
# BEGIN SECOND PHASE
###########################################

# download and un-tar Kafka from Apache mirror
RUN wget http://apache.claz.org/kafka/0.10.2.1/kafka_2.11-0.10.2.1.tgz
RUN tar -xvzf kafka_2.11-0.10.2.1.tgz

# move into the /opt directory and remove source file
RUN mv kafka_2.11-0.10.2.1/ /opt/kafka
RUN rm kafka_2.11-0.10.2.1.tgz

# librdkafka necessary for confluent-kafka Python library
RUN curl -L https://github.com/edenhill/librdkafka/archive/v0.9.2-RC1.tar.gz | tar xzf -
RUN cd librdkafka-0.9.2-RC1/ &&  ./configure --prefix=/usr && make -j && sudo make install

# copy jars - NOTE: May want to pull these from Artifactory
COPY jars/ /home/streamuser/jars

RUN chmod +x /home/streamuser/jars/spark-sql-kafka-0-10_2.11-2.1.1.jar
RUN chmod +x /home/streamuser/jars/spark-streaming-kafka-0-8-assembly_2.11-2.1.1.jar

# install kafka libraries and py4j for Spark
RUN cd /home/streamuser &&  pip install -U -r requirements.txt

COPY start_services.sh /home/streamuser/
RUN chmod +x /home/streamuser/start_services.sh

# Add lines for extraClassPath - probably a better way to do this, like copying jars into existing path?
RUN cp $SPARK_HOME/conf/spark-defaults.conf.template $SPARK_HOME/conf/spark-defaults.conf
RUN echo "spark.driver.extraClassPath        /home/streamuser/jars/spark-streaming-kafka-0-8-assembly_2.11-2.1.1.jar:/home/streamuser/jars/spark-sql-kafka-0-10_2.11-2.1.1.jar" >> $SPARK_HOME/conf/spark-defaults.conf
RUN echo "spark.executor.extraClassPath      /home/streamuser/jars/spark-streaming-kafka-0-8-assembly_2.11-2.1.1.jar:/home/streamuser/jars/spark-sql-kafka-0-10_2.11-2.1.1.jar" >> $SPARK_HOME/conf/spark-defaults.conf

# effectively, this is the command that the users will be running when they start the image
# CMD ["sh", "-c", "/home/streamuser/start_services.sh"]
