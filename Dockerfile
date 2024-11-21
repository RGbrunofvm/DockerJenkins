FROM gradle:jdk17 AS build
WORKDIR /app
COPY . /app
CMD ["gradle", "test"]
