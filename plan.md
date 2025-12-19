# Zeiterfassung

- Pro Mitarbeiter Zeiterfassung zu starten
- Manager & Admin sehen alles
- Employees können nur den Einstempelbereich sehen und auch nur
ihre Zeit.
- Berechtigungen sollen aus Keycloak Token erfasst werden
- Soll Stundenlohn von Mitarbeiter erfassen, mit Zeitlichem Bezug.
- Auswertungslogik für geleistete
Arbeitszeit zu Vergütung
- API-Endpunkte für Abfragen der Informationen


## Microservices außerhalb des Scopes der Zeiterfassung:
    - Mitarbeiterverwaltung

## Technologien:

- Spring Boot Microservice
- RabbitMQ Event Message Queue
- Keycloak

## Event trigger auf die reagiert wird:

- neue mitarbeiter 
- update (Strundenlohn) 
- löschen

## Events die ausgelöst werden:

- TODO

## Grundlegend:

- Microservice, nutzen von Spring Boot Defaults
- Benötigt Datenbank: SQLite? (Docker? - diskussion)
- Soll unabhängig von Mitarbeiterverwaltung lauffähig sein, soll trotzdem auf Events von Mitarbeiterverwaltung reagieren
