FROM openjdk:8

ADD iCache-demo/target/web-cache-core-1.0-SNAPSHOT.jar /app.jar

# 暴露 Spring Boot 应用的端口号
EXPOSE 4001
# 启动 Spring Boot 应用
CMD ["java", "-jar", "app.jar"]
