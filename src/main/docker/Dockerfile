FROM java:8

#install Spring Boot artifact
VOLUME /tmp
ADD spring-jcache-hazelcast-1.0-SNAPSHOT.jar spring-jcache-hazelcast-1.0-SNAPSHOT.jar
RUN sh -c 'touch spring-jcache-hazelcast-1.0-SNAPSHOT.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","spring-jcache-hazelcast-1.0-SNAPSHOT.jar"]
