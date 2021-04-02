FROM openjdk:11
VOLUME /tmp
EXPOSE 9100
ADD ./target/service-oauth-1.0.0-SNAPSHOT.jar service-oauth.jar
ENTRYPOINT ["java", "-jar", "/service-oauth.jar"]