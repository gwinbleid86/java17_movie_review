FROM maven:3.9.5-amazoncorretto-17 AS build
WORKDIR /build
COPY src ./src
COPY pom.xml ./
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app

COPY --from=build /build/target/movie_review*jar movie_review.jar
EXPOSE 8089
CMD ["java", "-jar", "movie_review.jar"]
