FROM openjdk:17
RUN mkdir /app
ADD ./target/movie_review*jar /app/movie_review.jar

WORKDIR /app

EXPOSE 8089
CMD ["java", "-jar", "movie_review.jar"]