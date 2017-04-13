FROM anapsix/alpine-java
MAINTAINER thereisnospoon 
COPY target/scala-2.11/s3mock-assembly-0.1.10.jar s3mock.jar
CMD ["java","-jar","s3mock.jar"]
