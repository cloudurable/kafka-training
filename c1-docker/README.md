## Initial Setup

This directory holds a dockerfile and supporting files (jars, python scripts, etc.) to build a Docker image to use for hands-on streaming data exercises.

The image is available in Dockyard - you can issue this command while Docker is running to pull verison 1.04, for instance: 

 `docker pull dockyard.cloud.capitalone.com/tech-college/data-discipline:streaming-data-capstone-v01.04` at the time of this writing.  The version number may have changed by the time you read this, so please check https://dockyardaws.cloud.capitalone.com/dockyard/#/dashboard/repositoryDetails?imagename=tech-college%2Fdata-discipline for the most up-to-date version.

The Docker image for these exercises includes the following:

* The Anaconda distribution of Python (including 450+ libraries, many commonly used for working with data)
* Apache Kafka (version 0.10, built for Scala 2.11)
* Apache Spark (version 2.1.1, built for Hadoop 2.7)
* Apache Zookeeper (version 3.4.10, necessary for Kafka)
* LibrdKafka (necessary for Confluent Kafka Python libraries)
* Jupyter notebooks with code to faciliatate producing to and consuming from Kafka topics
* Jars necessary for Spark streaming with Kafka
* Cleaned transactions to use for streaming

See the requirements.txt file for a full list of other Python libraries installed.

To build this image from scratch, you'll need to `docker build` using the `base_Dockerfile` file, then use that image as the base image in `Dockerfile`.

For instructions on getting started, please [click here](https://github.kdc.capitalone.com/TechCollege/streaming-data-capstone/blob/master/README.md#setup-instructions).

For tips and tricks working with Docker, the Capital One proxy, and how to setup your (Mac) environment to make them play nicely together, please [click here](https://github.kdc.capitalone.com/TechCollege/streaming-data-capstone/blob/master/initial_setup/docker_notes.md).

### TODO

* Refactor Docker image to create a streamuser user account, install virtually everything under this user account with a Docker `USER` directive
