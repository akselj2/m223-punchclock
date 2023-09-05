# M223 Abschlussprojekt LB-B: CoWorking Space

Hierbei handelt es sich um eine API für die Verwaltung eines "co working space", wobei die meisten Anforderungen vom Lehrer des Moduls 223 bereitgestellt werden. Die Nutzer können sich registrieren und anmelden und ihre jeweiligen Plätze buchen. Es gibt 3 Rollen: Admins, Mitglieder und Besucher. Admins sind Administratoren, Mitglieder sind registrierte Besucher, die sich angemeldet haben, und Besucher sind nicht autorisierte Nutzer, die die Website besuchen.

## Erste Schritte

1. Das Projekt in Visual Studio Code öffnen
1. Das Projekt im DevContainer Öffnen
1. Das Projekt mit dem Command `Quarkus: Debug current Quarkus Project`
1. Das Projekt ist auf http://localhost:8080 erreichbar

### Datenbankadministration

Über http://localhost:5050 ist PgAdmin4 erreichbar. Damit lässt sich die Datenbank komfortabel verwalten. Der Benutzername lautet `zli@example.com` und das Passwort `zli*123`. Die Verbindung zur PostgreSQL-Datenbank muss zuerst mit folgenden Daten konfiguriert werden:
 - Host name/address: `db`
 - Port: `5432`
 - Maintenance database: `postgres`
 - Username: `postgres`
 - Password: `postgres`

## Automatische Tests

Die automatischen Tests können mit `./mvnw quarkus:test` ausgeführt werden. Für die automatischen Tests wird nicht die PostgreSQL-Datenbank verwendet, sondern eine H2-Datenbank, welche sich im Arbeitsspeicher während der Ausführung befindet.

## Testdaten

Die Testdaten sind in der import.sql datei vorhanden, unter diesem Pfad: /workspace/src/main/resources/import.sql
Da sind 2 User und 2 Buchungen vorhanden, 1 Admin und 1 Member.

### Anmeldedaten

#### Admin
 - Email: test@admin.test
 - Passwort: 123456

#### Member
 - Email: test@member.test
 - Passwort: 123456

http://localhost:8080/login

## Abweichungen zur Planung

Ich konnte keine Benutzervalidierung noch rechtzeitig einfügen, aus dem Grund könnte man theoretisch SQL Injections ausführen :(
Ich habe auch noch für die Lesbarkeit ein seperaten Entity für das Login erstellt. Damit folge ich dem Konzept von "Seperation of Concerns".

Somit bedanke ich mich am Kursleiter für diese 5 Tage. 
molotovch

## Github
 [GitHub Link](https://github.com/akselj2/m223-punchclock).

