#FROM maven:3.9.5-amazoncorretto-17 AS build
#WORKDIR /build
#COPY src ./src
#COPY pom.xml ./
#RUN mvn clean package

FROM openjdk:17-jdk-slim
RUN mkdir /app
RUN pwd
RUN cp /home/runner/work/java17_movie_review/java17_movie_review/target/movie_review*jar ./app/movie_review.jar
WORKDIR /app

#COPY --from=build /build/target/movie_review*jar ./movie_review.jar
EXPOSE 8089
CMD ["java", "-jar", "movie_review.jar"]
