# Docker Installation and Configuration

1.  Install Docker - select the relevant link for your OS:
    1. [Mac](https://www.docker.com/docker-mac)
    1. [Windows](https://www.docker.com/docker-windows)
    1. [Linux/Cloud Platforms](https://www.docker.com/get-docker)
1.  Start Docker
    1. Mac: click the Docker icon in _Applications_
    1. Windows: launch Docker from the programs list
    1. **NOTE:** You may also want to search for the application through your OS's search function.
1.  Configure Docker
    1. Enter Docker's Preferences section by clicking on the icon in the menu bar (Mac) or toolbar (Windows) and click Preferences, then click Advanced.
    1. Change the number of CPUs to use to 4, and the amount of Memory to 2.0 GB or more.
  ![Docker Configuration](https://github.kdc.capitalone.com/TechCollege/streaming-data-capstone/blob/master/images/docker-config.png)
1.  Restart Docker if it does not automatically restart.

To test whether you have a working installation of Docker, you'll need to make some configuration changes to ensure that you are properly setup to work with the Capital One proxy.  We recommend utilizing [localproxy](https://github.kdc.capitalone.com/rvl994/localproxy) which was written by Capital One's own Jeffrey Damick.  

## Streamlined localproxy Instructions

First, ensure that you've signed in to Enterprise Connect, as localproxy will not work without it.
1. localproxy is written in go-lang, so you'll need to install: https://golang.org/dl/
1. Get the code for the project - enter your $GOPATH (``/Users/<YOUR EID>/go/src``) and use ```go get github.kdc.capitalone.com/rvl994/localproxy```
1. Run `make` from `$GOPATH/src/github.kdc.capitalone.com/rvl994/localproxy`
1. Install localproxy by running `sudo build/localproxy_darwin_amd64 install <YOUR EID>` from same directory
1. Follow remaining steps listed [here](https://github.kdc.capitalone.com/rvl994/localproxy/blob/master/README.md#mac)



### Test Docker Installation

There are two Docker repositories we'll be using, Docker Cloud (managed by Docker) and Dockyard, Capital One's private repository server.  For this learning path, we'll only need to pull a test image from Docker Cloud - the Docker image with all the code is hosted by Dockyard.

