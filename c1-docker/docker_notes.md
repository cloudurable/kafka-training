## Notes on Docker Setup

### The Proxy

One of the challenges with building Docker images is working behind a corporate proxy that requires users to authenticate (e.g., http://<EID>:<PASSWORD>@<PROXY_IP_ADDRESS>:<PROXY_PORT>).  This is because Docker stores everything you do to an image, and anyone who can execute `docker inspect` or `docker history` can see **your** EID and password if you just enter them the way you normally would.  In order to work with this "feature," you'll need to pass your credentials in a different way.  If you're running OS X or Linux, your life will be much easier thanks to the [localproxy project](https://github.kdc.capitalone.com/rvl994/localproxy).  Thanks to Jeffrey Damick for his excellent work on this here at Capital One.  It's a little tricky to install if you're not familiar with the Go programming language, but once it's up and running (and you have Enterprise Connect running properly) you're nearly set. 

If you're running OS X or Linux, you're probably going to have a much easier time.  If you're running Windows, it's even more difficult to get up and running.  Here are some links we used to get Windows 7 up and running:

* https://pulse.kdc.capitalone.com/docs/DOC-132165#comment-925190
* https://pulse.kdc.capitalone.com/docs/DOC-167715#jive_content_id_Fix_Local_Proxy_Settings
* https://pulse.kdc.capitalone.com/people/ZIY475/blog/2016/07/17/setting-up-docker-environment-on-developer-laptop-using-docker-toolbox
* https://www.howtogeek.com/tips/how-to-set-your-proxy-settings-in-windows-8.1/

Though no one of the above articles appears to be sufficient to get everything up and running.  That last article actually has some good information on Windows 7 as well, and it was very useful to figure out where the Local Area Network (LAN) Settings were hiding (spoiler:  they're in Internet Explorer).

#### A few learnings:

* Artifactory is your friend: https://artifactory.cloud.capitalone.com/artifactory/webapp/#/home - ultimately, a few items (Spark, Anaconda) were pulled from Artifactory, which is probably the best way to go anyway.  Kafka is still pulled from an Apache mirror, which requires that you setup your proxy settings correctly.

* The convention to use in Dockerfiles (see snippet below) seems to be to use an `ARG proxy=http://<PROXY_IP_ADDRESS>:<PROXY_PORT>`, where these values are hardcoded.  This still allows you to pass the `proxy` as a build-arg in the `docker build` statement.  Then, use `ENV var=${proxy}` where `var` is each of the following:
  * http_proxy
  * https_proxy
  * HTTP_PROXY
  * HTTPS_PROXY
* Use the `setup_ubuntu_proxy.sh` shell script immediately after this so you can run `apt-get install` and `apt-get update` commands.

Below is a partial snippet from the Dockerfile illustrating these learnings:

```
ARG proxy=http://10.12.195.66:8099

ENV http_proxy ${proxy}
ENV https_proxy ${proxy}
ENV HTTP_PROXY ${proxy}
ENV HTTPS_PROXY ${proxy}

RUN mkdir -p /home/streamuser
WORKDIR /home/streamuser

COPY setup-ubuntu-proxy.sh /home/streamuser/

RUN chmod +x /home/streamuser/setup-ubuntu-proxy.sh && /home/streamuser/setup-ubuntu-proxy.sh
```

The IP address is used because sometimes one of the proxy addresses may not resolve properly. You may want to use `entproxy.kdc.capitalone.com` instead - I believe that's the official and best one to use as of 2017-03-14 per Christophenr McDowell in [this Pulse post](https://pulse.kdc.capitalone.com/docs/DOC-132165#comment-925190). 

### Dockyard

The commands to use to push or pull the image to or from Dockyard are:

`docker push dockyard.cloud.capitalone.com/tech-college/data-discipline:streaming-data-capstone-ubuntu16.04-vXX`

`docker pull dockyard.cloud.capitalone.com/tech-college/data-discipline:streaming-data-capstone-ubuntu16.04-v-XX.YY`

Where `XX` is a major version number and `YY` is a minor version number.   

* Dockyard access *should* be open to everyone, so you should be able to create your own Namespaces and Repositories (Repositories are nested under Namespaces).  You will need to be an admin or a member of a repository in order to push or pull (or delete in the case of admins).
* The file called  `base_Dockerfile` was used to build the base image (tagged in Dockyard as `base-v01.01`), `Dockerfile` was used to build the final image (at the time of this writing, tagged in Dockyard as `streaming-data-capstone-v01.03`, though `streaming-data-capstone-v01.04` is the current version in development and it's likely to advance beyond that by launch).  Dockerfiles are located in the initial_setup/ubuntu16.04 directory. 
* Google Chrome sometimes has an issue with login credentials to Dockyard, therefore, you may have to use Safari (works) or Firefox (untested).  It's never a good idea to use Internet Explorer unless it's your Obi-Wan-Last-Hope kinda thing.

