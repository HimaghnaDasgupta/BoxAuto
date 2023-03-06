#

FROM ubuntu:java

COPY ./target/box-1.0.0.jar .

EXPOSE 80

ENTRYPOINT java -Dbox.auto.https=false -jar box-1.0.0.jar

