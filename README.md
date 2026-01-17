# Java-Fussball-Simulation
Eine Simulation einer Fußballliga in Java.
## Features
* **Detaillierte Spielsimulation:** Jedes Spiel wird Minute für Minute (1-90) simuliert.
* **Ereignis-System:** Zufallsbasierte Generierung von Ereignissen wie Torschüssen, Paraden, Ecken, sowie Gelben und Roten Karten basierend auf Teamstärken (Sturm, Mittelfeld, Verteidigung).
* **Intelligente Tabellenlogik:**
  * Automatische Sortierung nach: Punkte > Tordifferenz > Geschossene Tore.
  * Direkte Aktualisierung der Statistiken nach Spielende.
* **Daten-Persistenz:**
  * Export der Tabelle in CSV-Dateien.
  * Import und Parsing existierender CSV-Datenbestände.
* **Statistik-Tracking:** Verfolgung von gespielten Spielen, Siegen, Niederlagen und Torverhältnissen.

## Technologien
* **Sprache:** Java (JDK)
* **Konzepte:** OOP, File I/O (CSV Parsing), Custom Sorting (`Comparable` Interface), Wahrscheinlichkeitsberechnung.

## So funktioniert es
Die Klasse `Fussballspiel` nutzt die Klasse `Wuerfel`, um Spielzüge basierend auf den Attributen der `Mannschaft` (Sturm, Verteidigung, Torwart) zu berechnen. Nach Spielende aktualisiert die Klasse `Tabelle` die `Verein`-Objekte und sortiert die Liste neu.

## Starten der Anwendung
Der Einstiegspunkt ist die Klasse `Main.java`. Sie initialisiert Test-Vereine, simuliert Begegnungen und gibt die resultierende Tabelle auf der Konsole aus.
## Voraussetzungen
Um das Projekt zu kompilieren, werden folgende Bibliotheken im Classpath benötigt:
turban.utils.jar
