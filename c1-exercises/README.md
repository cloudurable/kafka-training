
## Streaming Data Hands-on Exercises

This repository stores things like Dockerfiles, Jenkins jobs, code, and jar files to support the hands-on exercises for the streaming data offerings within the Tech College's Data discipline.  Throughout this guide, the word `terminal` shall be used to indicate the Terminal program on OS X or NIX environment, or Powershell in the Windows environment.


## Streaming Data Overview

![Streaming Data Learning Path](images/StreamingDataImage.png)

## Prerequisites

Before you begin, it this content collection assumes that you have familiarity with the following:

* Unix - if you need further training in this area, check out our [Unix Foundations](https://capitalone.pathgather.com/content/unix-foundations) course.
* Git - if you need further training in this area, check out our [Git Foundations](https://capitalone.pathgather.com/content/git-foundations) course.
* Python 3 - if you need further training in this area, check out Python offerings.
  * [Python Foundations I](https://capitalone.pathgather.com/content/python-foundation-i)
  * [Python Foundations II](https://capitalone.pathgather.com/content/python-foundation-ii)
* You have an SSH client
  * For Macs - we recommend Terminal
  * For Windows - we recommend [Git Bash](https://git-for-windows.github.io/)


## Setup Instructions
Once you have met the prerequisites, please follow the environment setup steps below.  If you are experienced user, here's a [condensed version of the setup instructions](quickstart.md).

**Current Version of Docker Image:** [Dockyard - tech-college/data-discipline](https://dockyardaws.cloud.capitalone.com/dockyard/#/dashboard/repositoryDetails?imagename=tech-college%2Fdata-discipline)

1. Clone this repository with Git by typing `git clone git@github.kdc.capitalone.com:TechCollege/streaming-data-capstone.git` in terminal.  
1. Install and configure Docker using the [Docker Installation instructions](../../wiki/Docker-Installation).
1. Pull the Docker image from the Capital One Dockyard:
    1. Login into Dockyard by typing `docker login dockyard.cloud.capitalone.com` in terminal.  Authenticate using EID and password.
    1. Pull the Streaming Data image from the Capital One dockyard via the following command in terminal - **replace text between <> with information specific to your installation** `docker pull dockyard.cloud.capitalone.com/tech-college/data-discipline:streaming-data-capstone-v<XX.YY>`
1. Run the Docker image using the following command in terminal - **replace text between <> with information specific to your installation** `docker run -it -v </full/path/to/local/github/repo>:/home/streamuser/host_documents -p 4040:4040 -p 8888:8888 -p 9092:9092 -p 9999:9999 -p 2181:2181 dockyard.cloud.capitalone.com/tech-college/data-discipline:streaming-data-capstone-v<XX.YY>`
1. Run `sh start_services.sh` to start services within the Docker image.
1. Navigate to [http://localhost:8888/tree?token=Tech-College-Data-@2017](http://localhost:8888/tree?token=Tech-College-Data-@2017) to launch the Jupyter Notebook.
1. Navigate to the `host_documents` folder within Jupyter.
1. Navigate to the folder that coincides with the course you are working on (i.e 'foundation', 'creation', etc.) within Jupyter.
1. For further instructions, please see the README in the root directory of your project.
