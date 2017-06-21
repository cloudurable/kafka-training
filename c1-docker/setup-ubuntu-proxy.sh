#!/bin/bash

#
# Usage: run this script as
#   ./set-proxy.sh 
#
# This script updates the below 3 files with proxy information.
# If there was one already preset, it'll delete those lines and create new 
# with updated information.
# /etc/bash.bashrc
# /etc/environment
# /etc/apt/apt.conf
#
# For some reason, DNS does not resolve the proxy name: entproxy.kdc.capitalone.com
# so it's IP is used instead
#
# originally taken from https://github.kdc.capitalone.com/eyi183/ubuntu-vm-setup/blob/master/set-proxy.sh
# on 2017-06-07

# proxy_url="http://entproxy.kdc.capitalone.com:8099/"
proxy_url="http://10.12.195.66:8099/"

file_name="/etc/bash.bashrc"

# search if the pattern already exists in the file
# if it does, then delete the lines
# perl is used as it allows in-place modification to the file
if grep -q -i "http_proxy" "${file_name}"; then
   sudo perl -i -ne 'print unless m/proxy/' "${file_name}"
fi
: $( echo "export http_proxy=${proxy_url}" | sudo tee -a "${file_name}" )
: $( echo "export https_proxy=${proxy_url}" | sudo tee -a "${file_name}" )
: $( echo "export ftp_proxy=${proxy_url}" | sudo tee -a "${file_name}" )
: $( echo 'export no_proxy="192.168.99.*,*.local,localhost,127.0.0.1,169.254/16,*.kdc.capitalone.com,*.cloud.capitalone.com,*.cof.ds.capitalone.com,192.168.59.*"' | sudo tee -a "${file_name}" )


file_name="/etc/environment"

if grep -q -i "http_proxy" "${file_name}"; then
   sudo perl -i -ne 'print unless m/proxy/' "${file_name}"
fi
: $( echo "http_proxy=${proxy_url}" | sudo tee -a "${file_name}" )
: $( echo "https_proxy=${proxy_url}" | sudo tee -a "${file_name}" )
: $( echo "ftp_proxy=${proxy_url}" | sudo tee -a "${file_name}" )
: $( echo 'export no_proxy="192.168.99.*,*.local,localhost,127.0.0.1,169.254/16,*.kdc.capitalone.com,*.cloud.capitalone.com,*.cof.ds.capitalone.com,192.168.59.*"' | sudo tee -a "${file_name}" )


file_name="/etc/apt/apt.conf"

if grep -q -i "[Pp]roxy" "${file_name}"; then
   sudo perl -i -ne 'print unless m/[Pp]roxy/' "${file_name}"
fi
: $( echo "Acquire::http::proxy \"${proxy_url}\";" | sudo tee -a "${file_name}" )
: $( echo "Acquire::https::proxy \"${proxy_url}\";" | sudo tee -a "${file_name}" )
: $( echo "Acquire::ftp::proxy \"${proxy_url}\";" | sudo tee -a "${file_name}" )

# set docker proxy 
