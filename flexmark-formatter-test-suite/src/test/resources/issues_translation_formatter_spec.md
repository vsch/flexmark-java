---
title: Formatter Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Formatter

repeated translating snippets only translated once, ones containing placeholder with only
brackets of all types are excluded.

```````````````````````````````` example(Formatter: 1) options(details)
Softwareanforderungen
=====================

Approval Machine 1.0

## Freigabe

|       Dokument       |                      |       Version        |                      |
|:---------------------|:---------------------|:---------------------|:---------------------|
| Autor                | Felix Hohlwegler     | Datum                |                      |
|                      |                      |                      |                      |
| Name / Rolle         | Name / Rolle         | Name / Rolle         | Name / Rolle         |
| Unterschrift   Datum | Unterschrift   Datum | Unterschrift   Datum | Unterschrift   Datum |

## Inhaltsverzeichnis

Meta-Information
----------------

### Produkt / Software

| **Produktname**  |    | **Produktversion** |     |
|:-----------------|:---|:-------------------|:----|
| Approval Machine |    | Softwareversion    | 1.0 |

### Versionshistorie

| Version | Datum \*)  |      Autor       | Status\*\*)  | Änderungsbeschreibung |
|:--------|:-----------|:-----------------|:-------------|:----------------------|
| V1      | 2017-02-28 | Felix Hohlwegler | Masterarbeit | Abgabe                |
| V2      | 2018-02-15 | Felix Hohlwegler | Entwurf      | In Markdown überführt |
| V3      | 2018-02-27 | Felix Hohlwegler | Entwurf      | Korrekturen           |

\*) Format: JJJJ-MON-TT \*\*) Status: Entwurf, geprüft/genehmigt, freigegeben

Table 1: Versionshistorie

### Adressaten und Zweck des Dokuments

Dieses Dokument beschreibt die Anforderungen an das Software-System aus Black-Box-Sicht. Es soll
den Entwicklern als Input dienen, um dieses System – idealerweise ohne weitere Rückfragen
stellen zu müssen – technisch realisieren, d.h. entwickeln zu können. Es soll auch so
vollständig sein, dass die Software-Systemtester daraus ohne Rückfragen stellen zu müssen,
Testspezifikationen ableiten können.

### Relevante Dokumente

Folgende Dokumente sind für dieses Dokument relevant:

| Nr. |                                                                                         Dokument                                                                                         |                                                               Verbindung                                                               |
|:----|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------|
| 1   | [Zweckbestimmung](https://bitbucket.org/jigmbh/ji_approvalmachine_frontend/src/c0deb54af1c36bbbfa817f8f779b96b42293f2d8/docs/assets/Zweckbestimmung%20Approval%20Machine.docx?at=master) | Enthält die Zweckbestimmung (Basis für alle Risikoanalyse) und Initialen Vorgaben z.B.: Marktinformationen oder bekannte Gefährdungen. |
| 2   | [VA Softwareentwicklung](https://dms.johner-institut.de/ji/ji-qms/blob/master/03a-Core-Processes-General/PD-Product-Development/PD-SOP-01-DE_Internal-SW-Development.mdown)              | Gibt Anweisungen für den gesamten Software-Entwicklungsprozess, einschließlich der Fertigstellung dieses Dokuments.                    |
| 3   | [Softwareentwicklungsplan](https://bitbucket.org/jigmbh/ji_approvalmachine_frontend/src/d9aa09d1b1f3460d5e31dec75720c547d15d61e9/docs/documents/projektplan.md?at=master)                | Enthält die Projektspezifischen Anforderungen.                                                                                         |
| 4   | [SWAR]()                                                                                                                                                                                 | Software Architektur Dokument                                                                                                          |

Table 2: Relevante Dokumente

Überblick und Systemgrenzen
---------------------------

![](./_media/diagrams/Kontextdiagramm.png)

Hier abgebildet ist das Kontextdiagramm des zu entwickelnden Systems. Die
Qualitätsmanagement-Software bietet diverse Schnittstellen an, so beispielsweise zu einer
Datenbank und zu einem lokalen oder externen File Storage. Weiterhin werden Schnittstellen zu
externen Stellen wie Behörden oder Warenwirtschaftssystemen aufgezeigt, die einen Austausch
ermöglichen sollen. Der Mailversand des Systems wird über einen Mailserver mittels SMTP
durchgeführt. Ebenso ist eine Schnittstelle zu LDAP und Active Directory dargestellt, welcher
zur Authentifizierung von Benutzern verwendet wird.

Auf dem Server wird zusätzlich zum entwickelnden System eine Datenbank sowie eine
Versionsverwaltung benötigt. Die Versionsverwaltung ist für die Dokumente, die nicht direkt im
System erzeugt aber mit im System vorhandenen Dokumenten oder Prozessen verknüpft werden.

Laufzeitumgebung
----------------

Die ApprovalMachine wird als mandantenfähige Web-Applikation entwickelt. Zum Betrieb der
Plattform wird ein Server benötigt, auf dem die benötigten Komponenten, unter anderem Docker,
installiert sind.

### Laufzeitumgebung Server

Folgend werden die zum Betrieb des Projektes identifizierten Anforderungen an den Server
aufgelistet.

#### a) Hardware

**SSRS_RUNTIME_001** Die Software läuft bei Amazon Webservices in einem EC-2 Container welcher
Mindestens 2GB RAM haben muss. Festplattenkapazität wird über Amazon S3 bereitgestellt. Die
Applikation benötigt mindestens 200MB Festplatten Speicher.

#### b) Betriebssystem

**SSRS_RUNTIME_002** Die Applikation ist sowohl unter Linux als auch Windows lauffähig.
Empfohlen wird jedoch Linux. Empfohlen wird Debian oder Ubuntu jeweils in der neuesten
verfügbaren LTS (Long Term Support) variante. Linux wird hierbei in einem Docker-Container
ausgeführt.

#### c) Laufzeitumgebung

**SSRS_RUNTIME_003** Es müssen auf dem Server folgende Produkte installiert sein:

- JDK oder Open JDK Version 8.X
- aktuellste SBT Version
- aktuellste Python Version
- aktuellste Linux Debconf-Werkzeuge (Sofern es sich um einen Linux Server handelt)

#### d) Weitere Softwareanwendungen

**SSRS_RUNTIME_004** Weiterhin wird ein Datenbankserver benötigt, um die anfallenden Daten zu
persistieren. Hierzu wird ein MySQL-Server in aktueller Version vorausgesetzt.

### Laufzeitumgebung Client

#### a) Hardware

**SSRS_RUNTIME_005**

- Die Software muss auf einem handelsüblichen Büro-PC mit 8GB RAM, einer 100MBit Netzwerkkarte
  (Lan/Wlan) lauffähig sein.
- Die Software muss auf einem handelsüblichen Tablet oder Smartphone lauffähig sein.
- Restriktionen bezüglich der Bildschirmauflösung gibt es keine, da die Approval Machine als
  responsive Web-Applikation umgesetzt werden soll und diese sich somit automatisch an
  unterschiedliche Auflösungen anpasst.

#### Betriebssystem

**SSRS_RUNTIME_006** Es gibt keinerlei Restriktionen in Bezug auf das Betriebssystem des Clients

#### Laufzeitumgebung

**SSRS_RUNTIME_007** Es gibt keinerlei Restriktionen in Bezug auf die Laufzeitumgebung des
Clients.

#### d) Weitere Softwareanwendungen

**SSRS_RUNTIME_008** Auf dem Client muss ein Browser (Chrome/Firefox/Edge) in aktuellster
Version installiert sein.

(Grafische) Nutzerschnittstellen (GUI)
--------------------------------------

### Allgemeine Anforderungen

#### Layout

Das Layout definieren wir über Bootstrap und Material Design (React Toolbox).
> Todo Bootstrap & Material verlinken

#### Performanz

**SSRS_UI_PF001**: Bei einem Handelsüblichen Office-PC mit durchschnittlicher Internetverbindung
(16 Mbit) ist die Webapplikation in max. 10. Sekunden geladen.

#### Sonstiges

**SSRS_SONST_S001** Das System ist in Englisch und Deutsch verfügbar.

**SSRS_SONST_S002** Das System kann sowohl mit Maus und Tastatur als auch per Touch bedient
werden.

**SSRS_SONST_S003** Das System soll an das Corporate Design des Kunden angepasst werden können
(z.B. Schriftarten, Farben)

### Screens Statisch - Sicht des Verwalters

Das folgende Kapitel zeigt, nach Kernaufgaben sortiert, das statische Verhalten der Software als
Mock-Ups. Hierbei handelt es sich um prototypische Zeichnungen der Benutzeroberfläche, die das
Layout, die Ideen und die Funktionen der Anwendung ohne Interaktion darstellen.

Zunächst wird die Benutzeroberfläche nur aus Sicht des Verwalters gezeigt. Verwalter sind
Benutzer mit Rechten zur Erstellung von Templates.

#### Screen 1 Navigation

![](_media/mockups/single_images/approval_machine_mockup1.png)

**SSRS_UI_SCREEN001**: Das System zeigt die Navigationspunkte an. Die angezeigten
Navigationspunkte variieren je nach den Berechtigungen, die dem jeweiligen Benutzer zugewiesen
wurden.

#### Screen 2 Mandant anlegen

![](_media/mockups/single_images/approval_machine_mockup2.png)

**SSRS_UI_SCREEN002**: Die Mandantenverwaltung wird durch einen Plattform-Administrator des
Plattformbetreibers durchgeführt. Die Abbildung zeigt die Eingabemaske für das Anlegen eines
neuen Mandanten.

1. Eingabefeld für den Namen des Mandanten
2. Auswahl für den Haupt-Admin des Mandanten
3. Auswahl für die Default-Sprache der Benutzeroberfläche
4. Eingabefeld für die Namensschablone für Dateiexporte
5. Benutzerübersicht des Mandanten

#### Screen 3 Systemaufgabe anlegen

![](_media/mockups/single_images/approval_machine_mockup3.png)

**SSRS_UI_SCREEN003**: Das System zeigt die Einstellungsmöglichkeiten für neue Systemaufgaben zu
sehen.

1. Titel der Systemaufgabe
2. Berechtigungseinstellungen für die Systemaufgabe
3. Auswahl für den Typ der Systemaufgabe

#### Screen 4 Dashboard

![](_media/mockups/single_images/approval_machine_mockup4.png)

**SSRS_UI_SCREEN004**: Hat sich der Nutzer erfolgreich eingeloggt landet er automatisch auf
seinem persönlichen Dashboard. Hier werden die Aufgaben und Prozesse, die dem Benutzer
zugewiesen wurden, angezeigt.

1. Hier werden die Aufgaben, die dem Nutzer zugewiesen wurden, angezeigt
2. Hier werden die Prozesse, die dem Nutzer zugewiesen wurden, angezeigt. Zudem werden
   Informationen wie der Status und der Fortschritt des Prozesses angezeigt.

#### Screen 5 Alle  Aufgaben-Templates

![](_media/mockups/single_images/approval_machine_mockup5.png)

**SSRS_UI_SCREEN005**: In der „Alle Aufgaben-Templates"-View erhält der Benutzer eine Übersicht
über alle im System vorhanden Aufgaben-Templates. Obige Abbildung zeigt diese View. Hier ist die
Checkbox für die Vorgängerversionen angehakt. Somit werden alle alten Versionen einer Aufgabe
mit angezeigt.

1. Filter Funktionen für die Ansicht
2. Alle Aufgaben-Templates

#### Screen 6 Aufgaben-Template erstellen

![](_media/mockups/single_images/approval_machine_mockup6.png)

**SSRS_UI_SCREEN006**: Das System zeigt die Eingabemaske zum Anlegen neuer Aufgaben-Templates
Das Fenster teilt sich in drei Bereiche auf: Links werden die Aufgaben, Einstellungen und
Berechtigungen festleget. Die Mitte beinhaltet die Aufgabenbeschreibung und die rechte Seite ist
für die Zuweisung von internen Dokumenten oder Dokumenten von außerhalb zuständig.

1. Hier wird der Titel der Aufgabe festgelegt.
2. Zuweisung der Berechtigungen nach dem RACI-Prinzip.
3. Hier wird die Deadline der Aufgabe festgelegt.
4. Das Kommentarfeld ist ein optionales Feld und ist nur für den Template-Ersteller und
   -Bearbeiter sichtbar.
5. Hier wird der Prüf- und Freigabeprozess für die Aufgabe festgelegt.
6. Hier wird der Schulungsprozess für diese Aufgabe festgelegt
7. Textfeld zur Beschreibung der Aufgabe.
8. Die Aufgabenbeschreibung kann aber auch durch Inhaltsbausteine definiert werden. Hier stehen
   versionierte Bausteine, sogenannte Systembausteine, und nicht versionierte Bausteine zur
   Verfügung.
9. Hier können Dokumente, die innerhalb des Approval Machines erstellt wurden, zugewiesen bzw.
   Dokumente, die außerhalb erstellt wurden, hochgeladen werden

#### Screen 7 Inhaltsbaustein suchen

![](_media/mockups/single_images/approval_machine_mockup7.png)

**SSRS_UI_SCREEN007**: Allen Entitäten (Aufgaben, Dokumenten & Prozessen) können sogenannte
Inhaltsbausteine hinzugefügt werden. Das System zeigt den Dialog „Inhaltsbaustein suchen", hier
kann der Anwender aus vorhandenen Systembausteinen auswählen oder neue Bausteine anlegen.

#### Screen 8 Inhaltsbaustein erstellen

![](_media/mockups/single_images/approval_machine_mockup8.png)

**SSRS_UI_SCREEN008**: Das System zeigt , wie Inhaltsbausteine erstellt werden. In der Abbildung
handelt es sich um einen Inhaltsbaustein vom Typ Text.

1. Hier wird der Baustein-Typ ausgewählt
2. Hier wird der Bausteinname eingegeben
3. Eingabe des Textes
4. Auswahl, ob es sich um einen Systembaustein (wiederverwendbaren Baustein) handelt

Weitere Mock-Ups zu den verschiedenen Inhaltsbaustein-Typen sind im Anhang zu finden.

#### Screen 9 Dokumenten-Template erstellen

![](_media/mockups/single_images/approval_machine_mockup9.png)

**SSRS_UI_SCREEN009_001**: Das System zeigt die Benutzeroberfläche für die Teilaufgabe
Dokumenten-Template erstellen. Diese unterteilt sich ähnlich wie die View zur Erstellung von
Aufgaben in unterschiedliche Bereiche. Die linke Seite ist nahezu analog zur View bei der
Erstellung von Aufgaben-Templates aufgebaut. Hier werden die Dokumenteneinstellungen und
-berechtigungen festgelegt. Die rechte Seite ist für die Dokumentenerstellung reserviert. Diese
wird jedoch erst angezeigt sobald der Nutzer ausgewählt hat, ob er ein Dokument innerhalb des
Systems erstellen möchte, oder ob er ein externes Dokument hochladen und unter die
Versionskontrolle inklusive Prüf- und Freigabeprozess stellen will

1. Hier wird der Dokumententitel festgelegt.
2. Festlegung der Berechtigungen nach RACI.
3. Hier wird der Dokumententyp festgelegt.
4. Hier wird die Art des Dokumentes festgelegt
5. Hier wird festgelegt, ob es sich um ein Dokument handelt, welches innerhalb des Systems
   angelegt wird oder ob ein Dokument, welches außerhalb des Approval Machines angelegt wurde,
   hochgeladen werden soll.
6. Festlegung des Prüf- und Freigabeprozesses sowie des Schulungsprozesses für das Dokument.

![](_media/mockups/single_images/approval_machine_mockup10.png) **SSRS_UI_SCREEN009_002**: Das
System zeigt die Bearbeitungsoberfläche für ein innerhalb des Approval Machines erstelltes
Dokument zu sehen. Der Nutzer kann hier Prozesse, Aufgaben, Dokumente und Inhaltsbausteine
hinzufügen und in Kapitel strukturieren.

![](_media/mockups/single_images/approval_machine_mockup11.png) **SSRS_UI_SCREEN009_003**: Das
System die View für ein Dokument, das außerhalb des Approval Machines erstellt wurde. Dieses
kann hier hochgeladen und ebenfalls der Versionierung sowie dem Prüf- und Freigabeprozess
unterzogen werden. Zusätzlich können einem externen Dokument ebenfalls noch Elemente aus dem
System angefügt werden.

1. Hochgeladenes Dokument
2. Ergänzend angefügter Prozess

#### Screen 10 Suche

![](_media/mockups/single_images/approval_machine_mockup12.png)

**SSRS_UI_SCREEN010_001**: Der Benutzer hat jederzeit die Möglichkeit, über das Suchfeld im
rechten oberen Bereich der Anwendung eine Suche zu starten. Das System zeig das Suchfeld.

1. Eingabefeld für Suchbegriffe

![](_media/mockups/single_images/approval_machine_mockup13.png)

**SSRS_UI_SCREEN010_002**: Auf der Suchseite, hat der Benutzer weitere
Einstellungsmöglichkeiten, um die Suchergebnisse zu filtern.

1. Eingabefeld für Suchbegriffe
2. Typ-Filter, hierbei kann auch zwischen Instanz eines Typs und Templates unterschieden werden
3. Filter Erstellungszeitraum
4. Suchergebnisse

#### Screen 11 Prozess definieren

![](_media/mockups/single_images/approval_machine_mockup14.png)

**SSRS_UI_SCREEN011**: Das System zeigt die View Prozess Template anlegen.

#### Screen 12 Einstellungen - Rolle anlegen

![](_media/mockups/single_images/approval_machine_mockup15.png)

**SSRS_UI_SCREEN012**: Die Rollenverwaltung wird auf Mandantenebene durch den Verwalter des
Mandanten durchgeführt. Das System zeigt die Eingabemaske für eine neue Rolle.

1. Eingabefeld für den Rollentitel
2. Rollenbeschreibung
3. Suchfeld für Benutzer des Mandanten
4. Auflistung der Benutzer, die bereits dieser Rolle hinzugefügt wurden
5. Berechtigungseinstellungsmöglichkeiten für die Rolle. Hier können die Rechte für die
   jeweiligen Entitäten vergeben werden. Es wird zwischen Zugriffsrechten für Templates und
   Instanzen unterschieden.

#### Screen 13 Einstellungen - Layout

![](_media/mockups/single_images/approval_machine_mockup16.png)

Aufgaben, Dokumente und Prozesse sind innerhalb des Approval Machines komplett ohne Corporate
Design (CD) des jeweiligen Mandanten. Als CD bezeichnet man das einheitliche Firmendesign,
welches die Firma nach innen und außen präsentiert. Hierzu gehören Konstanten wie unter anderem
das Logo, die Typografie oder Farben. Möchte der Mandant seine Dokumente zur Weitergabe an
Kunden oder benannte Stellen exportieren, so kann er diese als PDF exportieren und für diesen
Export ein Layout passend zu seinem CD festlegen.

**SSRS_UI_SCREEN013**: Das System zeigt den Layout „Editor".

1. Logo-Upload-Feld
2. Eingabefelder für die Seitenränder
3. Editoren für die Kopf- und Fußzeile des Dokumentes
4. Auswahl für Schriftarten
5. Auswahlfelder für die Schriftfarben

#### Screen 14 Berichte - Audit Log

![](_media/mockups/single_images/approval_machine_mockup17.png)

**SSRS_UI_SCREEN014**: Der Approval Machine loggt alle Logins am System. Diese Zeigt das System
Protokolliert wird der Timestamp, der Status des Logins, welcher Benutzer mit welcher
Emailadresse eingeloggt war und im Fehlerfall die Fehlermeldung.

1. Filterfunktionen für die Log-Einträge
2. Checkbox, um die Log-Einträge nach Zugriffen, die über die Restschnittstelle protokolliert
   wurden, zu filtern
3. Auflistung der Log-Einträge

#### Screen 15 Berichte - Änderungshistorie

![](_media/mockups/single_images/approval_machine_mockup18.png)

**SSRS_UI_SCREEN015**: Zusätzlich zum Audit-Log werden auch sämtliche Änderungen, die im System
vorgenommen werden, protokolliert. Dabei handelt es sich nicht nur um die Dokumentation der
Änderungen an Aufgaben, Prozessen und Dokumenten, sondern auch an Systemaufgaben sowie Benutzer-
und Rolleneinstellungen. Die Auflistung aller Änderungen ist in der obigen Abbildung
dargestellt.

1. Filterfunktionen nach Datum und Benutzer
2. Filterfunktionen nach Entitätstyp
3. Auflistung aller Änderungen

### Screens Statisch - Sicht des Benutzers

Im vorangegangenen Kapitel wurde die Benutzeroberfläche aus der Sichtweise des Verwalters
gezeigt, folgend werden nun die Mock-Ups aus Sicht des System-Benutzers gezeigt. Der
System-Benutzer ist ein ausführender Benutzer, welcher die Aufgaben, Prozesse und Dokumente
ausführt oder ausfüllt. Die Prozesse, Dokumente und Aufgaben sind also alles Instanzen von
Templates.

#### Screen 16 Meine Aufgaben

![](_media/mockups/single_images/approval_machine_mockup19.png)

**SSRS_UI_SCREEN016**: Die Aufgaben (Instanzen), die einem Benutzer zugewiesen sind, werden in
einer separaten View angezeigt.

#### Screen 17→Aufgabe durchführen (Instanz erzeugen)

SSRS_UI_SCREEN017: Bei der Erzeugung einer neuen Aufgabeninstanz, welche in der folgenden
Abbildung dargestellt ist, besteht die Möglichkeit, dass die im Template hinterlegten
Einstellungen, wie z.B. die Berechtigungen und die Deadline der Aufgabe, nochmals geändert
werden. ![](_media/mockups/single_images/approval_machine_mockup20.png)

1. Auswahlfelder für die Berechtigungseinstellungen
2. Eingabefeld für die Deadline
3. Kommentarfeld

#### Screen 18 Aufgabe (Checkliste) durchführen

**SSRS_UI_SCREEN018**: Das System zeigt die Instanz-Sicht einer Aufgabe.

![](_media/mockups/single_images/approval_machine_mockup21.png)

1. Aufgabentitel
2. Aufgabenbeschreibung
3. Checklistenpunkte
4. Benutzerdefinierte Eingabefelder
5. Information über Timestamp und Benutzer der letzten Änderung

#### Screen 19 Compliance View – Aufgabe

**SSRS_UI_SCREEN019**: Das System zeigt die Compliance View.

In der folgenden Abbildung wird eine mögliche Ansicht einer Compliance View für eine Aufgabe
abgebildet. Die Compliance View kann nach Belieben passend für die entsprechende regulatorische
oder prüfende Stelle konfiguriert werden. In diesem Beispiel werden die Versionshistorie des
Aufgaben-Templates, alle Instanzen der Aufgabe, die komplette Änderungshistorie der Aufgabe
sowie die Verwendungsübersicht gezeigt.

![](_media/mockups/single_images/approval_machine_mockup22.png)

#### Screen 20 Dokument lesen

**SSRS_UI_SCREEN020**: Das System zeigt ein Dokument im Lesemodus. Dem Dokument ist ein Prozess
zugeordnet, welcher automatisch in das Inhaltsverzeichnis mit übernommen wird.

![](_media/mockups/single_images/approval_machine_mockup23.png)

1. Inhaltsverzeichnis des Dokuments
2. Inhalt des Dokuments (Kapitel 6 – Verknüpfter Prozess)
3. Timestamp der letzten Bearbeitung des Dokuments
4. Dokumentenversion

#### Screen 21 Prozess durchführen (Instanz erzeugen)

**SSRS_UI_SCREEN021**: Das System zeigt die Prozess Instanzerzeugung.

Bei der Erzeugung einer neuen Prozessinstanz, folgend dargestellt, besteht die Möglichkeit, dass
die im Template hinterlegten Einstellungen, wie z.B. die Berechtigungen und die Deadline des
Prozesses, nochmals geändert werden.

![](_media/mockups/single_images/approval_machine_mockup24.png)

1. Auswahlfelder für die Berechtigungseinstellungen
2. Eingabefeld für die Deadline
3. Kommentarfeld

#### Screen 22 Prozess durchführen

**SSRS_UI_SCREEN022**: Das System zeigt die Prozess Instanz-Sicht.

Die beiden folgenden Mock-Ups, zeigen die Prozess-Durchführung auf zwei verschiedene Arten.
Zuerst in der Standardansicht, dem „Detailed View“, und anschließend im „Full View“, bei welchem
die einzelnen Prozessschritte eingeklappt sind.

![](_media/mockups/single_images/approval_machine_mockup25.png)

1. Prozessbeschreibung
2. Fortschrittsbalken des Prozesses
3. Beschreibung des Prozessschrittes
4. Typ des Prozessschrittes (Pflicht, optional, variabel)
5. Für den Prozessschritt festgelegter Benutzer
6. Input des Prozesses
7. Output, der nach dem Prozessschritt entstehen muss
8. Parallelität von Prozessschritten
9. Deadline des Prozesses

![](_media/mockups/single_images/approval_machine_mockup26.png)

![](_media/mockups/single_images/approval_machine_mockup27.png)

In der vorangegangenen Abbildung wird eine mögliche Ansicht einer Compliance View für einen
Prozess gezeigt. Hierbei werden die Prozessschritte mit allen relevanten Informationen
tabellarisch angezeigt. Die Compliance View kann nach Belieben für die entsprechende
regulatorische oder prüfende Stelle passend konfiguriert werden.

### Screens Dynamisch – Sicht des Verwalters

Nachdem im vorangegangenen Kapitel die Benutzeroberfläche spezifiziert und dargestellt wurde,
werden nun im Folgenden die Funktionalitäten der jeweiligen Schaltflächen, Eingabemasken, etc.
erläutert. Ebenso wird auch aufgezeigt, wie die Anwendung auf Nutzereingaben reagiert, sich
verändert beziehungsweise dem Nutzer Feedback gibt. Die Mock-Ups werden beim dynamischen
Verhalten ebenso wie beim statischen Verhalten nach Sicht auf das System getrennt. Alle
klickbaren Elemente sind mit Tool-Tips versehen.

#### Screen 23 Navigation

**SSRS_UI_SCREEN023**: Das System zeigt die Navigation.

Beim Klick auf den Navigations-Button „Aufgaben“ klappt das in Abbildung 35 gezeigte Menü auf.
Dieses ist in Aufgaben, Instanzen und Aufgaben-Templates unterteilt. Die roten Badges zeigen die
Anzahl an Aufgaben an, die dem eingeloggten Benutzer zugewiesen sind. Folgend dargestellt, das
Menü, welches bei Klick auf den Dokumenten-Button angezeigt wird.

![](_media/mockups/single_images/approval_machine_mockup28.png)

Beim Klick auf den Berichte-Button in der Navigation klappt das Menü auf. Hier kann der
Audit-Log, die Sonderfreigaben und die Änderungshistorie eingesehen werden. Folgende Abbildung
zeigt das Menü für den Betreiber des Approval Machines, welches beim Klick auf den
Verwalten-Button in der Navigation angezeigt wird. Hier können die Mandanten des Systems
verwaltet werden.

![](_media/mockups/single_images/approval_machine_mockup29.png)

Die folgende Abbildung zeigt das Profilmenü des Benutzers, welches beim Klick auf den
Profil-Button angezeigt wird.

![](_media/mockups/single_images/approval_machine_mockup30.png)

Beim Klick auf den Einstellungs-Button in der Navigationsleiste wird, dass nachfolgend gezeigte
Menü für die Verwaltung der Plattform aus Mandantensicht dargestellt.

![](_media/mockups/single_images/approval_machine_mockup31.png)

#### Screen 24 Alle Aufgaben-Templates

**SSRS_UI_SCREEN024**: Das System zeigt alle Aufgaben-Templates als dynamisches Mock-Up.

![](_media/mockups/single_images/approval_machine_mockup32.png)

1. Beim Klick auf den Button öffnet sich ein Pop-Up, welches die kompletten
   Berechtigungseinstellungen zeigt.
2. Beim Klick auf den Button öffnet sich ein Pop-Up, welches alle Prozesszuordnungen auflistet.
3. Durch Klick auf Prüfen wird der Prüfprozess gestartet.
4. Beim Klick auf diesen Button wird eine neue Instanz einer Aufgabe erzeugt. Es öffnet sich ein
   Pop-Up, welches in Abbildung 27 gezeigt wird.
5. Erzeugt beim Klick eine Kopie dieser Aufgabe.
6. Öffnet die Aufgabe im Bearbeitungsmodus. Dies ist aber nur möglich, wenn das Template noch
   nicht geprüft und noch nicht freigegeben wurde.
7. Öffnet beim Klick das Dokument im Lesemodus.
8. Durch Klicken auf den Button wird ein neues Aufgaben-Template angelegt.

#### Screen 25 Aufgaben-Template erstellen

**SSRS_UI_SCREEN025**: Das System die Benutzeroberfläche für das Erstellen neuer
Aufgaben-Templates gezeigt und alle dynamischen Elemente erklärt.

![](_media/mockups/single_images/approval_machine_mockup33.png)

1. Beim Klick in das Eingabefeld öffnen sich bereits Vorschläge, die sich automatisch an die
   Eingabe des Benutzers anpassen.
2. Beim Klick in das Prüf- und Freigabeprozessfeld und ebenfalls bei Klick in das
   Schulungsprozessfeld öffnet sich ein Dialog, in welchem der jeweilige Prozess aus den im
   System vorhandenen Prozessen ausgewählt werden kann.
3. Beim Klick auf den „Neuen Inhaltsbaustein erstellen“-Button öffnet sich ein Pop-Up, in dem
   direkt ein neuer Baustein angelegt werden kann.
4. Beim Klick auf den „Go“-Button wird in den bereits im System vorhandenen Systembausteinen
   nach passenden Suchergebnissen gesucht. Diese werden in einem Pop-Up angezeigt.
5. Die Reihenfolge der Inhaltsbausteine kann über dieses Symbol per Drag’N’Drop geändert werden.
6. Beim Klick auf das „Mülleimer“-Symbol wird dieser Inhaltsbaustein von der Aufgabe entfernt.
   Es erscheint vor dem Entfernen noch ein Sicherheitshinweis.
7. Beim Klick auf den „Browse“-Button öffnet sich der systemabhängige Durchsuchen-Dialog zum
   File-Upload.
8. Beim Klick auf „Go“ öffnet sich ein Pop-Up mit Suchvorschlägen für Dokumente aus dem System.
9. Zuordnungen zu Dokumenten können über einen Klick auf das „Mülleimer“-Symbol aufgehoben
   werden.
10. Durch Klicken auf „Speichern“ wird das Template gespeichert. Durch Klick auf „Als neue
    Version speichern + prüfen + freigeben“ wird das Dokumenten-Template gespeichert, für das
    weitere Bearbeiten gesperrt und der Prüf- und Freigabe-Prozess gestartet.

#### Screen 26 Dokument erstellen

**SSRS_UI_SCREEN026**: Das System zeig das dynamische Verhalten der „Dokumenten-Template
erstellen“-Maske.

![](_media/mockups/single_images/approval_machine_mockup34.png)

1. Dropdown-Auswahl für den Dokumententyp
2. Dropdown-Auswahl für die Art des Dokumentes
3. Drag’N’Drop-Anker, um die Reihenfolge der Kapitel zu ändern. Es ist auch möglich,
   Unterkapitel anzulegen, indem das Kapitel per Drag’N’Drop eine Ebene nach unten verschoben
   wird.
4. Bei Klick auf die Pfeil-Buttons wird entweder über oder unter diesem Kapitel ein neues
   Kapitel eingefügt.
5. Der „Collapse“-Button blendet bei Klick den Kapitelinhalt ein oder aus.
6. Durch Klick auf das „Mülleimer“-Symbol wird das Kapitel gelöscht.
7. Das Pop-Up für die Prozesszuordnung zum Dokument wird durch Klick auf den Prozess-Button
   geöffnet.
8. Durch Klick auf den Dokumenten-Button öffnet sich das Dokumenten-Such-Pop-Up.
9. Durch Klick auf den grünen Button können dem Dokument neue oder bereits vorhandene
   Systembausteine zugeordnet werden. Durch Klick auf den blauen Button kann ein neues
   Unterkapitel erzeugt werden.

Die folgende Abbildung zeigt die Unterschiede zu der View für Dokumenten-Templates, die
außerhalb des Systems erstellt wurden

![](_media/mockups/single_images/approval_machine_mockup35.png)

1. Durch Klick auf den „Browse“-Button öffnet sich der betriebssystemspezifische
   Durchsuchen-Dialog.
2. Durch Klick auf das „Mülleimer“-Symbol wird die Zuordnung einer Datei zu einem Template
   aufgelöst.
3. Der Lesemodus des Dokumentes wird durch Klick auf den Lesen-Button geöffnet.

#### Screen 27 Schreibschutzhinweis für Dokumente

**SSRS_UI_SCREEN027**: Das System zeigt den Schreibschutzhinweis für Dokumente.

Dokumente können immer nur von einem Benutzer bearbeitet werden, ein zweiter Nutzer wird beim
Öffnen eines Dokumentes darauf hingewiesen, dass sich das Dokument in Bearbeitung befindet. Der
Benutzer hat hier die Möglichkeit, dem Bearbeiter eine E-Mail zu schreiben.

![](_media/mockups/single_images/approval_machine_mockup36.png)

#### Screen 28 Suche

**SSRS_UI_SCREEN028**: Das System zeigt die Suchfunktion.

![](_media/mockups/single_images/approval_machine_mockup37.png)

1. Durch Klick auf die Filtereinstellungen in der Suche werden die Suchergebnisse ohne erneutes
   Laden der Seite neu sortiert.
2. Durch Klick auf den Lesen-Button öffnet das jeweilige Artefakt im Lesemodus.
3. Durch Klick auf den „Compliance View“-Button wird die jeweilige Compliance View angezeigt

#### Screen 29 Prozess definieren

**SSRS_UI_SCREEN029**: Das System zeigt das dynamische Verhalten der Prozess definieren View.

![](_media/mockups/single_images/approval_machine_mockup38.png)

1. Durch Klick auf den „Aufgabe zuordnen“-Button öffnet sich das Auswahlfenster für die im
   System angelegten Aufgaben, die dem Prozessschritt zugeordnet werden können.
2. Durch Klick auf den „Prozess zuordnen“-Button öffnet sich das Such-Pop-Up für Prozesse.
3. Durch Klick auf den „Dokument zuordnen“-Button können im System vorhandene Dokumente dem
   Prozessschritt zugeordnet werden.
4. Der „Inhaltsbaustein hinzufügen“-Button ermöglicht das Suchen und Erstellen von
   Inhaltsbausteinen.
5. Der blaue „Prozessschritt hinzufügen“-Button legt einen neuen leeren Prozessschritt an.
6. Der pinke „Systemaufgabe hinzufügen“-Button öffnet das Auswahl-Pop-Up für Systemaufgaben.
7. Die Reihenfolge der Prozessschritte kann per Drag’N’Drop verändert werden. Prozessschritte
   können auch nebeneinander angeordnet werden, um parallele Prozessschritte zu ermöglichen.
8. Beim Klick in das Feld für abgeleitete Prozesse öffnet sich das Prozess-hinzufügen-Pop-Up.
   Hier können Prozesse ausgewählt werden, die von diesem Prozessschritt aus manuell gestartet
   werden können.
9. Beim Klick in die Felder für Vor- und Nachbedingungen öffnet sich ein Pop-Up, in dem
   Bedingungen für den jeweiligen Prozessschritt oder für den ganzen Prozess erfüllt sein
   müssen, damit der Prozess oder Prozessschritt ausgeführt werden kann. Ebenso werden hier die
   Nachbedingungen eingetragen, die erfüllt sein müssen, wenn der Prozess als erledigt markiert
   wird.

Nachbedingungen sind also der Output eines Prozessschrittes. Das folgende Mock-Up zeigt
exemplarisch wie das Zuweisungs-Pop-Up aussieht.

![](_media/mockups/single_images/approval_machine_mockup39.png)

1. Filterfunktionen, die bei Klick die Suchergebnisse filtern ohne die Seite neu zu laden.
2. Suchfunktion, die bei Klick auf „Go“ die Suchergebnisse filtern.
3. Durch Markierung der Checkboxen und Klick auf den „Auswahl hinzufügen“-Button können mehrere
   Bedingungen auf einmal hinzugefügt werden.

#### Screen 30 Einstellungen - Layout

**SSRS_UI_SCREEN030**: Das System zeigt die dynamische Sicht des Layout-einstellen-Mock-Ups. Bei
Klick auf eine der Farben öffnet sich der Color-Picker mit dem die Corporate Design-Farben des
Mandanten ausgewählt oder über HEX-Werte eingegeben werden können.

![](_media/mockups/single_images/approval_machine_mockup40.png)

### Screens Dynamisch - Sicht des Benutzers

Nachfolgend werden die dynamischen Mock-Ups aus Sicht des Benutzers erläutert.

#### Screen 31 Meine Aufgaben

**SSRS_UI_SCREEN030**: Das System Zeigt die dem aktuell eingeloggten Benutzer zugewiesenen
Aufgaben an.

![](_media/mockups/single_images/approval_machine_mockup41.png)

1. Durch Klick auf den „Neue Aufgabe erstellen“-Button kann der Benutzer eine neue Aufgabe
   erstellen. Dies ist kein Aufgaben-Template, das wiederverwendet werden kann, sondern eine
   Aufgabe, die nur für diesen Benutzer sichtbar ist.

#### Screen 32 Prozess durchführen

**SSRS_UI_SCREEN032**: Das System zeigt die dynamische Sicht des Prozess durchführenden.

![](_media/mockups/single_images/approval_machine_mockup42.png)

1. Beim Klick auf das „User“-Symbol öffnet sich eine Liste, die die Berechtigungen für den
   gesamten Prozess oder eines Prozessschrittes anzeigt.
2. Beim Klick auf den „Als erledigt markieren“-Button öffnet sich das Pop-Up zum Quittieren.
3. Beim Klick auf den „Ab hier neu starten“-Button wird der Prozess ab diesem Schritt neu
   gestartet. Alle nachfolgenden Prozessschritte müssen neu durchlaufen werden.
4. Beim Klick auf den „Complaint XY starten“-Button kann ein Complaint direkt aus dem Prozess
   heraus gestartet werden
5. Compliance-View starten.
6. Statistik-View öffnen.
7. Umschalter zwischen der Detailansicht oder der Minimized-Ansicht.

#### Screen 33 Prozess durchführen – Prozessschritt oder Prozess quittieren

**SSRS_UI_SCREEN033**: Das System zeigt das Quittieren Pop-Up.

Ist ein Prozessschritt erledigt, muss dieser vom durchführenden Benutzer quittiert werden.
Klickt er auf den „Als erledigt markieren“-Button öffnet sich ein Pop-Up, in dem er den
Prozessschritt digital „unterschreiben“ muss. Abbildung 54 zeigt das Pop-Up. Hierbei muss der
Benutzer jedes Mal seinen Benutzernamen und sein Kennwort eingeben. Alternativ kann er eine
Smartcard zum Unterschreiben verwenden. Zusätzlich kann ein Kommentar gespeichert werden.

![](_media/mockups/single_images/approval_machine_mockup43.png)

1. Bei Klick auf Speichern wird der Prozessschritt quittiert und damit als erledigt markiert.
   Der Benutzer muss davor aber seinen Benutzernamen und Kennwort oder den Pin für seine
   Smartcard eingegeben haben. Andernfalls erscheint ein Sicherheitshinweis und er kann den
   Prozessschritt nicht abschließen.

#### Screen 34 Prozess durchführen – Prozess ab Schritt X neu starten

**SSRS_UI_SCREEN034**: Das System zeigt den Pop-Up der beim neustarten eines Prozesse ab
Prozess-Schritt X erscheint.

![](_media/mockups/single_images/approval_machine_mockup44.png)

Soll ein Prozess ab einem bestimmten Schritt neu gestartet werden, muss der „Ab diesem Schritt
neu starten“-Button angeklickt werden. Dann öffnet sich ein Pop-Up, in dem der Benutzer einen
Grund (1) für den Neustart angeben muss. Bei Klick auf den „Starten“-Button (2) werden alle
Schritte, die nach Schritt X durchgeführt werden müssen, zurückgesetzt. Diese Prozessschritte
müssen nun erneut durchgeführt werden.

### Sonstige

#### Allgemein

Möglicherweise sind die folgenden Funktionalitäten schon oben beschrieben, daher hier nochmals
nur zur Erinnerung:

- Remote-Zugriff
- Akustische Signale (wann, wie laut, welches)

Auch diese Anforderung(en) ist/sind mit einer eindeutigen ID zu versehen.

#### Sicherheit

##### Rollenkonzept

**SSRS_UI_SECURITY001** Die Rollenverwaltung soll so gestaltet sein, dass Rollen hinzugefügt und
bearbeitet werden können. Zudem sollen die Berechtigungen, die den einzelnen Rollen bzw. einem
einzelnen Nutzer zugeteilt sind, über das User-Interface des QMS verwaltet werden können.
Berechtigungen (Read / Read Write) sollen über eine Access List verwaltet werden. So können den
Rollen oder einzelnen Nutzern Rechte für Funktionen des Systems zugeteilt werden.

Näheres zu den Rollen sind in **SSRS_UI_SCREEN012** beschrieben.

**SSRS_UI_SECURITY002** Für den Login ist ein Benutzername + Kennwort erforderlich. Näheres zu
den Kennwortrestriktionen sind 21 CFR Part 11 zu entnehmen.
> Beschreiben Passwortstärke

**SSRS_UI_SECURITY003** Die Anzahl an Login-Fehlversuchen ist auf drei beschränkt. Danach muss
das Kennwort des Nutzers zurückgesetzt werden.

**SSRS_UI_SECURITY004** Passwörter werden über die von Amazon Cognito zur Verfügung gestellten
Funktionen realisiert. Vorgehensweise:

1.) Der Nutzer fordert ein neues Kennwort an. Hierzu muss er seine Mail-Adresse eingeben. 2.) An
diese Mail-Adresse wird eine E-Mail verschickt mit dem Link zum Formular und einem Token. 3.)
Unter Angabe des Tokens kann er sein Kennwort neu vergeben.

**SSRS_UI_SECURITY005** Das Eingeben von URLs ohne Authentifizierung ist nicht möglich. Die
Anwendung blockt die Zugriffe und zeigt einen entsprechenden Hinweis an.

**SSRS_UI_SECURITY006** Die Anwendung loggt jegliche Änderung am System und speichert diese. Ein
Audit-Log Eintrag enthält einen eindeutigen Zeitstempel und den Nutzer sowie eine Meldung über
Erfolg oder Misserfolg.

**SSRS_UI_SECURITY007** Die Nutzer können Sie mit einem Benutzernamen und Kennwort oder einer
Smartcard am System einloggen.

**SSRS_UI_SECURITY008** Es ist möglich alle schützenswerten Daten im System zu löschen.

Technische Schnittstellen
-------------------------

### Allgemeine Anforderungen

#### Performanz

Antwortzeiten (ggf. in Abhängigkeit von Datenvolumen oder Transaktionszahl). Auch diese
Anforderung(en) ist/sind mit einer eindeutigen ID zu versehen.

#### Datensicherheit

Verschlüsselung, Algorithmen. Auch diese Anforderung(en) ist/sind mit einer eindeutigen ID zu
versehen.

### Schnittstelle 001 Datenbank-Schnittstelle

**SSRS_INTERFACE001**

Daten, die ins System direkt eingegeben und gespeichert werden, werden in einer Datenbank
persistiert. Dies betrifft sowohl Daten, die manuell von einem User erfasst werden, aber auch
Daten, die automatisch vom System erzeugt werden.

|  Interoperabilität  |                                                                                               |
|:--------------------|:----------------------------------------------------------------------------------------------|
| Organisatorische IO | Lesende / schreibende Zugriffe. Abhängig vom System sowie vom Nutzer und dessen Berechtigung. |
| Semantische IO                                                                                                     ||
| Syntaktische IO     | Datenbank-Schema                                                                              |
| Strukturelle IO     | Die Datenübertragung erfolgt über jdbc:mysql-Protokoll                                        |

### Schnittstelle 002→Client-PC-Schnittstelle

**SSRS_INTERFACE002**

Zusätzlich zu den Daten, die direkt im Approval Machine angelegt werden, können bereits
vorhandene Dokumente zur Dokumentenlenkung beispielsweise als Word-Dokument oder PDF hochgeladen
werden.

|  Interoperabilität  |                                                                                                                                                                                                             |
|:--------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Organisatorische IO | Die Berechtigungen und Zugriffsrechte hängen von der Rolle, die dem aktuell eingeloggten User zugewiesen wurden, ab. Nicht jeder User darf die Rolle des Verwalters einnehmen und auch Templates verwalten. |
| Semantische IO      | Templates dürfen nicht einfach so hochgeladen werden, sondern müssen zum Beispiel mit einem Titel und den Berechtigungseinstellungen (Pflichtfeldern) belegt werden.                                        |
| Syntaktische IO     | Hochgeladene Dokumente müssen folgende Datentypen haben: Word, PDF                                                                                                                                          |
| Strukturelle IO     | Die Datenübertragung erfolgt über HTTPS/Post                                                                                                                                                                |

Im Kontextdiagramm wird auch die Schnittstelle zu Smartphones und Tablets beschrieben, welche
ihre interne Kamera zum Scannen von Dokumenten oder zur Aufnahme von Bildern verwenden sollen.
Die Scans und Bilder sollen beispielsweise Prozessen oder Aufgaben zugeordnet werden können. Die
Schnittstelle zu Smartphones und Tablets unterscheidet sich von der Client-PC-Schnittstelle nur
in der syntaktischen Interoperabilität. Denn hier sollen nur Daten in JPEG- oder MP4-Format
akzeptiert werden.

### Schnittstelle 003 REST-Schnittstelle

**SSRS_INTERFACE003**

Der Approval Machine wird komplett als RESTFUL Webservice konzipiert. Daher werden alle Anfragen
an den Approval Machine über REST abgewickelt. Zusätzlich können aber auch
Drittanbieteranwendungen Daten über die REST-Schnittstelle mit dem Approval Machine austauschen.

|  Interoperabilität  |                                                                                                                                                                                   |
|:--------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Organisatorische IO | Die Berechtigungen und Zugriffsrechte hängen bei der REST-Schnittstelle ebenfalls von den Rechten des authentifizierten Users ab.                                                 |
| Semantische IO      | Semantische Korrektheit muss vor dem Absenden zum Beispiel über Pflichtfelder oder Pre-Validierung gelöst werden. Zusätzlich muss vor dem Persistieren nochmals validiert werden. |
| Syntaktische IO     | JSON                                                                                                                                                                              |
| Strukturelle IO     | Die Datenübertragung erfolgt über HTTPS/Post                                                                                                                                      |

### Schnittstelle 004 LDAP / Active Directory

**SSRS_INTERFACE004**

Der Approval Machine soll für die Benutzerverwaltung und Authentifizierung auch eine LDAP- und
Active Directory-Schnittstelle bereitstellen. Es muss noch geklärt werden, ob die Schnittstelle
im Approval Machine oder in einer übergeordneten Anwendung, die die Mandantenfähigkeit auch für
andere Produkte wie zum Beispiel dem SRS-Manager bereitstellt, implementiert wird.

|  Interoperabilität  |                                                                                                                                                                                                                 |
|:--------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Organisatorische IO | Die Berechtigungen werden ebenfalls an den Rechten, die dem jeweiligen Benutzer zugewiesen wurden, festgemacht. Diese Schnittstelle ermöglicht lediglich die Authentifizierung über einen LDAP- oder AD-Server. |
| Semantische IO      |                                                                                                                                                                                                                 |
| Syntaktische IO     | JSON                                                                                                                                                                                                            |
| Strukturelle IO     | Die Datenübertragung erfolgt über HTTPS/Post                                                                                                                                                                    |

### Schnittstelle 005 LDAP / Active Directory

**SSRS_INTERFACE005**

Um beispielsweise mit einer Smartcard (SC) einen Prozessschritt oder das Prüfen/Freigeben von
Templates zu quittieren, kann über die in Windows integrierte PC-SC-Schnittstelle kommuniziert
werden. Ab Schnittstellenversion 2.0 ist dies sogar kontaktlos möglich.

Die Schnittstelle wird nicht im Kontextdiagramm aufgeführt, weil es keine direkte Schnittstelle
zwischen dem Approval Machine und dem Chipkartenleser ist. Sie ist indirekt, da der Approval
Machine eine Schnittstelle zum Client und der auf dem Client installierte Treiber wiederum eine
Schnittstelle zum Chipkartenleser hat. Da die Authentifizierung über die Chipkarten aber in der
Anwendung vorgesehen ist, wird an dieser Stelle die Schnittstelle erläutert.

|  Interoperabilität  |                                                                                                |
|:--------------------|:-----------------------------------------------------------------------------------------------|
| Organisatorische IO | Berechtigung, um die Schnittstelle verwenden zu können. Wird über das Betriebssystem geregelt. |
| Semantische IO      |                                                                                                |
| Syntaktische IO     | Abhängig von Treiber                                                                           |
| Strukturelle IO     | PKCS #11 Interface                                                                             |

Sonstiges Anforderungen an das Produkt
--------------------------------------

### Lizenzmanagement

Die Approval Machine wird mit einem bisher noch nicht näher spezifizierten Lizenzmodell
angeboten.

### Update, Upgrade, Deinstallation

**SSRS_UPDATE001**: Da es sich um eine Cloud Applikation handelt müssen die Nutzer die Software
nicht installieren, updaten oder deinstallieren. Die Updates werden vom Betreiber auf dem Server
eingespielt und sind automatisch für alle Mandanten verfügbar.

### Nutzer- und Installationshandbücher, Gebrauchsanweisungen

#### Nutzerhandbuch

**SSRS_MANUAL001**: Dem Nutzer werden im System Hilfemöglichkeiten zur Verfügung gestellt. Diese
sind sowohl in Deutsch als auch in Englisch verfügbar. Zur Hilfe stehen "Hilfe-Icons" mit
entsprechenden Hinweisen, ToolTips und eine Umfangreiche Hilfe im Hilfe Menü zur Verfügung.

#### Installationshandbuch

**SSRS_MANUAL002**: Es wird eine Workinstruction bereit gestellt die für den Betreiber die
Installationsanleitung und Updateanleitung umfasst.

### Gesetzliche Vorgaben

#### →ISO 13485

Die ISO 13485 „Quality Management Systems — Requirements for regulatory purposes“ beschreib
Anforderungen an das Qualitätsmanagement sowie an das QMS. Da der Approval Machine als
unterstützendes Tool für die ISO 13485 eingesetzt wird, muss er die Anforderungen der ISO
unterstützen. Folgende Anforderungen werden hierbei erhoben, die auch im Approval Machine
berücksichtigt werden: Hinsichtlich der Verwaltung von Dokumenten wird eine Dokumenten-Lenkung
gefordert, ebenso wie die Erstellung und Lenkung eines QM-Handbuchs. Es sollen jedoch nicht nur
einzelne Dokumente, sondern auch ganze Prozesse für die Prozessbereiche Entwicklung, Produktion
und Dienstleistungserbringung definiert und in ihrer Ausführung überwacht werden können. Hierbei
soll auch die Messung der Qualität und die Zielerreichung in einem sogenannten Compliance View
realisiert werden, was ein individualisiertes Monitoring von Prozessen ermöglicht. Darüber
hinaus fordert die ISO 13485:2016 jedoch auch, dass die zur QM-Unterstützung verwendete Software
validiert werden muss. Hierzu folgt mehr im Kapitel der ISO 80002-2. Ebenso wird, wie auch von
der FDA, ein Device Master Record gefordert.

#### →21 CFR Part 11

Damit ein Medizinprodukt am US-Amerikanischen Markt zugelassen werden kann, muss dieses von der
FDA geprüft werden. Die FDA spezifiziert mit der 21 CFR Part 11 Anforderungen an elektronische
Aufzeichnungen und Unterschriften. Aufgrund der Freigaben und Testprotokolle, die im Approval
Machine geführt werden können, und der Möglichkeit zur Erstellung und Lenkung von
Verfahrensanweisungen genügt der Approval Machine diesen Anforderungen. Zu den Anforderungen,
die 21 CFR Part 11 spezifiziert, gehört ebenso, dass das System validiert sein muss. Validiert
werden muss besonders die Performanz, die Funktionstauglichkeit und die Veränderungen an
Aufzeichnungen, oder ungültige Aufzeichnungen zu erkennen. Weithin muss das System in der Lage
sein, auch menschenlesbare Aufzeichnungen zu erzeugen und die Aufzeichnungen zu schützen. Zum
einen müssen die Aufzeichnungen und Dokumente permanent verfügbar sein. Zum anderen muss ein
Zugriffsschutz implementiert sein; nur berichtigte Personen dürfen Zugriff erlangen. Weiterhin
gehört zu den Anforderungen ein Audit Trail, in dem eindeutig ersichtlich sein muss, wer sich am
System eingeloggt hat und welcher Benutzer zu welcher Zeit was an welchem Dokument geändert hat.
Es muss also jegliche Tätigkeit am System protokolliert werden. Und diese Informationen müssen
auch zugänglich gemacht werden. Weiterhin muss nicht nur protokolliert werden, wer auf das
System zugreift, sondern es müssen auch geeignete Maßnahmen getroffen werden, damit nur
berechtigte Personen Zugriff zum System erlangen. Es müssen Verfahrensprüfungen implementiert
sein um sicherzustellen, dass Prozesse nur in den vorgegebenen Reihenfolgen durchgeführt werden
können. Personen, die das System verwenden, müssen für das System geschult sein. Zusätzlich zu
den Anforderungen an das Software-System selbst werden auch Anforderungen an eine digitale
Unterschrift gestellt. Da im Approval Machine sämtliche Aktivtäten wie Freigaben oder
Prozessschritt-Quittierungen digital unterschrieben werden sollen, greifen auch diese
Anforderungen. Die digitale Unterschrift muss aus dem Namen der unterschreibenden Person, dem
Datum mit Zeitangabe sowie dem Grund der Unterschrift bestehen. Weiterhin muss gewährleistet
werden, dass die Unterschrift nicht geändert werden kann und permanent eine Verbindung zu dem
Dokument besteht. Die Unterschrift muss zweifelsfrei einer realen Person zugeordnet werden
können. Da im Falle des Approval Machines keine biometrischen Verfahren eingesetzt werden, muss
die digitale Unterschrift aus zwei Komponenten bestehen, beispielsweise aus Benutzername und
Kennwort. Benutzernamen müssen eindeutig sein und dürfen nur einer Person zugeordnet werden und
es müssen regelmäßige Intervalle zur Passwortaktualisierung festgelegt sein. Es müssen ebenfalls
Prozesse dazu definiert sein, wie bei Verlust von Kennwörtern oder der Smartcard vorgegangen
wird, denn dem Nutzer müssen in diesem Fall die bisherigen Zugangsrechte entzogen und neue
angelegt werden. Weiterhin müssen im Approval Machine Verfahren zur Erkennung von Angriffen
implementiert sein.

#### ISO 80002-2

Die ISO 80002-2 (Medical device Software – Part2: Validation of software for medical device
quality systems) ist eine Norm zur Validierung einer Prozesssoftware. Auch diese Norm soll vom
Approval Machine berücksichtigt werden, da das Definieren, Ausführen und Validieren von
Prozessen zum Funktionsumfang des Approval Machines gehören. Die ISO 80002-2 definiert eine
Toolbox zur Validierung, unteranderem auch für Stresstests. Große Teile der Norm werden bereits
durch diese Arbeit abgedeckt, Nutzungsanforderungen und Softwareanforderungen sind bereits
erhoben. Die Prozessdefinition wird im Approval Machine direkt vorgenommen. Lediglich für das
Testen der Prozesse muss eine Plattform geschaffen werden.

Anforderungen an nachfolgende Phasen
------------------------------------

### Anforderungen an die Entwicklung

#### Software-Sicherheitsklasse

Es handelt sich nicht um medizinische Software. Wir orientieren uns an den Anforderungen der
Klasse B.

#### Entwicklungsdokumentation

Keine weiteren Anforderungen als die bereits durch das QM-System abgedeckt sind.

#### Einschränkungen des Lösungsraums

#### Backend

Die Approval Machine wird bei Amazon Webservices gehostet und hat eine Microservice Architektur.
Die Mircorservices werden mit dem Play-Framework realisiert. Als Programmiersprache wird Scala
verwendet.

#### Datenbank

Zur Persistierung der Daten soll eine bei Amazon Webservices gehostet MySQL Datenbank verwendet
werden.

#### Frontend

Die Benutzeroberfläche des Approval Machines wird mit aktuellen Web-Technologie-Standards
umgesetzt. Hierzu zählen HTML5 und JavaScript mit dem JavaScript-Framework reactJS. Weiterhin
wird die CSS- und JavaScript-Bibliothek Bootstrap mit eingebunden, um schon von Beginn an eine
moderne und zukunftssichere Benutzeroberfläche realisieren zu können. Vor allem das von
Bootstrap bereitgestellte Grid-System erspart aufwendige Anpassungen für die responsive
Oberfläche der Anwendung. Der Approval Machine soll sich automatisch an die Bildschirme und die
unterschiedlichen Auflösungen der Anwender anpassen können.

### Anforderungen an das Testen

Die Anforderungen der Testumgebung und Art der Tests ist dem Projektplan zu entnehmen.

Traceability
------------

Dieses Kapitel muss nur ausgefüllt werden, falls die Software-Systemanforderungen aus
Systemanforderungen und/oder aus Nutzungsanforderungen abgeleitet wurden.

| ID Nutzungs- bzw. Systemanforderung | ID Softwareanforderung | Kommentar |
|:------------------------------------|:-----------------------|:----------|
|                                     |                        |           |
|                                     |                        |           |
|                                     |                        |           |

Anhang
------

### Hinweis für Auditoren

Die IEC 62304 fordert in Kapitel 5.2.g) die Spezifikation von Datendefinitionen und
Datenbank-Anforderungen. Dies ist ein konzeptioneller Fehler der Norm, weil die Auswahl einer
Datenbank als Systembestandteil erst in der Architekturphase erfolgen soll. Sollen hingegen
Datenbanken angesprochen werden, die nicht Teil des Produkts sind, so sind die Hinweise
(„Anmerkung 6&quot;) in der Norm ungeeignet.

### Glossar

Alternativ zum ausgelagerten Glossar (wie hier ausgeführt) kann das Glossar auch hier eingefügt
werden. Birgt die Gefahr von Fehlern und Widersprüchen über die gesamte technische Dokumentation
hinweg.

Relevante allgemeingültige und firmenspezifische Begriffe, Definitionen und Abkürzungen sind im
Glossar [1] festgelegt.
.
````````````````````````````````

