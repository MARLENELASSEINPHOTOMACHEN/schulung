# ----- Phase 1: Build-Umgebung -----
# Wir verwenden ein Image mit Maven und JDK 25 für den Build
FROM eclipse-temurin:25-jdk AS build

# Maven installieren
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere die pom.xml und lade die Abhängigkeiten herunter.
# Dies wird zwischengespeichert, solange sich die pom.xml nicht ändert.
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopiere den restlichen Quellcode und baue die Anwendung.
COPY src ./src
RUN mvn package -DskipTests

# ----- Phase 2: Laufzeit-Umgebung -----
# Wir verwenden ein schlankes Image mit nur der JRE
FROM eclipse-temurin:25-jre

WORKDIR /app

# Verzeichnis für die SQLite-Datenbank erstellen
RUN mkdir -p /app/data

# Kopiere nur die gebaute .jar-Datei aus der Build-Phase.
COPY --from=build /app/target/*.jar app.jar

# Exponiere den Port, auf dem die Anwendung läuft.
EXPOSE 8080

# Der Befehl zum Starten der Anwendung.
ENTRYPOINT ["java", "-jar", "app.jar"]
