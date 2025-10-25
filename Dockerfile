FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el JAR
COPY target/microservice-1.0.0.jar app.jar

# Crear usuario no-root para seguridad
RUN useradd -m -u 1000 microservice
USER microservice

# Exponer puerto
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/health || exit 1

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]