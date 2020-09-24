FROM openjdk:11

COPY ./build/libs/google-calendar-demo-0.0.1-SNAPSHOT.jar /home/google-calendar-demo.jar

EXPOSE 8080

CMD ["java", "-jar", "/home/google-calendar-demo.jar", "0.0.0.0"]
