# Dockerfile
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar o JAR da aplicação
COPY ./target/investimentos-caixa-1.0.0-SNAPSHOT.jar app.jar
#COPY target/*.jar app.jar

# Criar usuário não-root para segurança
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Expor porta da aplicação
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/q/health || exit 1

# Comando de execução
ENTRYPOINT ["java", "-Dquarkus.http.host=0.0.0.0", "-jar", "app.jar"]