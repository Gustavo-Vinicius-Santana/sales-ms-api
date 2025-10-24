# Etapa 1 — Build com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte e faz o build
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2 — Runtime com JDK leve
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o .jar do build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão (sobrescrita pelo Docker Compose)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
