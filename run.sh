#!/bin/bash

sbt "run-main kduraj.UpdateGeoIPDatabase"

aws s3 ls s3://kduraj/geoip2/

# aws s3 rm s3://kduraj/geoip2/GeoLite2-City-20171107.mmdb

