# Übergabe-Dokument – eHKS Hautkrebs-Screening

> **Für meinen Klassenkameraden, der ab Schritt 6 (GUI) übernimmt.**
> Alles bis einschließlich Schritt 5 (DataManager) ist fertig und getestet.

---

## 0. NetBeans 22 Setup

Das Projekt wurde mit IntelliJ erstellt, läuft aber genauso gut in NetBeans 22.
Da es ein **Maven-Projekt** ist, erkennt NetBeans alles automatisch –
keine NetBeans-spezifischen Projektdateien (`nbproject/`) nötig.
Die Anweisungen weiter unten erwähnen manchmal IntelliJ-Shortcuts –
hier die NetBeans-Entsprechungen.

### Voraussetzungen

1. **JDK 21** installiert (https://adoptium.net/ → "Temurin 21 LTS")
2. **NetBeans 22** installiert (https://netbeans.apache.org/front/main/download/)
3. **Maven** – ist bei NetBeans 22 schon eingebaut, du musst nichts extra installieren

### Java-Plattform in NetBeans prüfen

`Tools → Java Platforms` → Es sollte **JDK 21** aufgelistet sein.
Falls nicht: "Add Platform" → JDK-Ordner auswählen (z. B. `C:\Program Files\Eclipse Adoptium\jdk-21...`).

### Projekt öffnen

`File → Open Project` → den Ordner `01_selfHautkrebs` auswählen
(NetBeans erkennt das Maven-Symbol am Ordner – kleines lila Symbol).
**Nicht** "Open Folder" – das ist was anderes.

Beim ersten Öffnen lädt NetBeans alle Maven-Dependencies (JavaFX, JUnit).
Unten rechts läuft eine Fortschrittsanzeige – ein paar Minuten Geduld.

### App starten

Drei Wege:
1. **Hauptfenster oben:** grüner Pfeil ▶ ("Run Project") – kann beim ersten Mal fragen welche Main-Klasse → `MainApp` auswählen
2. **Rechtsklick aufs Projekt** im Projects-Panel links → "Run"
3. **Terminal/Cmd** im Projektordner: `mvn javafx:run`
4. **Maven-Goal direkt:** Rechtsklick aufs Projekt → "Custom" → "Goals..." → eingeben: `javafx:run`

### Tests starten

Rechtsklick auf `PatientTest.java` im Projects-Panel → **"Test File"**
oder Tastenkürzel **`Strg+F6`**.

Für alle Tests: Rechtsklick aufs Projekt → **"Test"** (`Alt+F6`).

Unten erscheint das "Test Results"-Fenster mit den grünen/roten Häkchen.

### IntelliJ → NetBeans Shortcut-Übersetzung

| IntelliJ | NetBeans 22 |
|---|---|
| `Alt+Insert` (Generate Getter/Setter) | `Alt+Einfg` – derselbe Shortcut! → "Getter and Setter" auswählen |
| `Strg+Shift+F10` (Run Test) | `Strg+F6` (aktuelle Test-Datei) |
| Rechtsklick → Refactor → Rename | `Strg+R` (auf markiertem Symbol) oder Rechtsklick → "Refactor → Rename" |
| Rechtsklick → New → Java Class | Rechtsklick auf Paket → "New → Java Class..." |
| Rechtsklick → New → Package | Rechtsklick auf `Source Packages` → "New → Java Package..." |
| `Strg+Alt+L` (Format Code) | `Alt+Shift+F` |
| `Strg+B` (Go to Definition) | `Strg+B` – derselbe Shortcut! |
| `Strg+Space` (Autocomplete) | `Strg+Space` – derselbe Shortcut! |
| Maven-Reload | Rechtsklick aufs Projekt → "Reload POM" |

### Klasse anlegen in NetBeans

1. Im **Projects-Panel** (links) Projekt aufklappen → `Source Packages` aufklappen
2. Rechtsklick auf das Ziel-Paket (z. B. `de.ehks._1_selfhautkrebs.model`)
3. **New → Java Class...** (oder bei Enum: New → Other → Java → Enum)
4. Name eingeben (ohne `.java`) → Finish

NetBeans setzt die `package`-Zeile automatisch korrekt.

### Getter/Setter generieren in NetBeans

1. Cursor in den Klassenrumpf setzen
2. **`Alt+Einfg`** drücken (oder Rechtsklick → "Insert Code...")
3. **"Getter and Setter..."** wählen → alle Felder ankreuzen → Generate

Genauso für **"Constructor..."** und **"toString()..."**.

### Datei umbenennen (mit Auto-Refactor)

Rechtsklick auf die Datei → **Refactor → Rename...** → neuer Name → Enter.
NetBeans aktualisiert automatisch alle Verweise im Code.

> WICHTIG: NICHT die Datei im Datei-Explorer von Windows umbenennen –
> dann verliert NetBeans die Referenzen. Immer über Refactor!

### FXML-Datei bearbeiten – Scene Builder

NetBeans hat **keinen eingebauten** visuellen FXML-Editor, aber Integration mit Scene Builder.

1. **Scene Builder installieren:** https://gluonhq.com/products/scene-builder/
2. In NetBeans: `Tools → Options → Java → JavaFX` → Scene Builder Home auf den Installationsordner setzen
3. Danach: Doppelklick auf `.fxml` öffnet die Datei automatisch im Scene Builder

Für dieses Projekt ist aber **reines Text-Editing völlig ausreichend** – die FXML steht ja schon komplett im Dokument.

Falls die FXML nur als Code angezeigt wird statt im Scene Builder:
Rechtsklick → **"Open"** (statt "Edit") → öffnet im Scene Builder.

### Wo welche Ordner sind im Projects-Panel

NetBeans zeigt Maven-Projekte mit **logischer** Ordnerstruktur an,
nicht 1:1 wie auf der Festplatte:

| Im Projects-Panel | Real auf der Platte |
|---|---|
| Source Packages | `src/main/java/` |
| Other Sources → resources | `src/main/resources/` |
| Test Packages | `src/test/java/` |
| Project Files | `pom.xml`, `module-info.java` |
| Dependencies | von Maven verwaltet, nicht anfassen |

Wenn du die "echte" Ordnerstruktur sehen willst: oben links neben "Projects" auf
**"Files"** umschalten – zeigt die normale Ordneransicht.

### Häufige NetBeans-Stolperfallen

| Problem | Lösung |
|---|---|
| Rote Fehler obwohl Code OK ist | Rechtsklick aufs Projekt → "Clean and Build" → dann "Reload POM" |
| Maven-Befehle finden Dependencies nicht | Rechtsklick aufs Projekt → "Reload POM" – immer der erste Schritt bei seltsamen Fehlern |
| `module-info.java` Fehler | Sicherstellen dass JDK 21 in `Project Properties → Sources → Source/Binary Format = 21` |
| FXML wird im Editor nicht erkannt | XML-Plugin via `Tools → Plugins → Installed` aktivieren |
| Test-Runner findet keine Tests | JUnit 5 Engine nicht geladen → Rechtsklick → "Reload POM" |
| App startet aber Fenster leer | In der Konsole nach `Caused by:` suchen – meist `fx:id` falsch geschrieben |
| `mvn javafx:run` funktioniert nicht aus dem grünen Pfeil | Stattdessen Rechtsklick aufs Projekt → "Custom → Goals..." → `javafx:run` |
| Encoding-Warnungen (Umlaute kaputt) | `File → Project Properties → Sources → Encoding = UTF-8` |

---

## 1. Was ist das Projekt?

Eine JavaFX-Desktop-App zur Dokumentation von Hautkrebs-Screening-Untersuchungen
nach den KBV-eHKS-Vorgaben (Kassenärztliche Bundesvereinigung). Die App muss:

- **PFLICHT 1:** Stammdaten erfassen (Patient, Arztpraxis, Untersuchung) und in einer Tabelle anzeigen
- **PFLICHT 2:** Daten als CSV exportieren und wieder importieren können
- **PFLICHT 3:** Plausibilitätsprüfung (z. B. Diagnose-Felder nur aktiv wenn "Verdachtsdiagnose=Ja")
- **PFLICHT 4:** Objektorientierte Umsetzung mit TDD (Trennung Daten / Logik / GUI)
- **Mindestens 1 optionales Feature** (am einfachsten: Farbkennzeichnung des Befundstatus)

**Abgabe-Deadline:** Code + Dokumentation 20.05.2026 · Präsentation 21.05.2026 (25 Min/Team)

---

## 2. Projektstruktur – was schon da ist

```
src/main/java/de/ehks/_1_selfhautkrebs/
├── model/                                     ← Datenklassen (FERTIG)
│   ├── Befundstatus.java       (enum: UNAUFFAELLIG / AUFFAELLIG / MEHRFACH_AUFFAELLIG)
│   ├── Arztpraxis.java         (BSN, LANR, Arztname)
│   ├── Patient.java            (10 Felder + 6 Konstanten für KBV-Werte)
│   └── ScreeningData.java      (verknüpft Patient + Arztpraxis + Untersuchungsdaten,
│                                hat validate() und getBefundstatus())
│
├── service/                                   ← Logik (FERTIG)
│   ├── FileHandler.java        (CSV Import/Export der Stammdaten)
│   └── DataManager.java        (hält Liste aller Datensätze im RAM, hat Filter)
│
├── HelloApplication.java       ← muss zu MainApp.java umbenannt werden (Schritt 6)
├── HelloController.java        ← muss zu MainController.java umbenannt werden (Schritt 6)
└── Launcher.java               ← bleibt wie es ist (JavaFX-Startklasse)

src/main/resources/de/ehks/_1_selfhautkrebs/
└── hello-view.fxml             ← muss zu main.fxml umbenannt werden (Schritt 6)

src/test/java/de/ehks/_1_selfhautkrebs/
└── model/
    └── PatientTest.java        (5 JUnit-5 Tests – alle grün)

src/main/java/module-info.java  ← schon angepasst für alle Pakete

pom.xml                         ← Java 21, JavaFX 21, JUnit 5 – schon konfiguriert
```

---

## 3. Wie die fertigen Klassen zusammenspielen

```
        ┌─────────────┐         ┌──────────────┐
        │   Patient   │         │  Arztpraxis  │
        └──────┬──────┘         └──────┬───────┘
               │                       │
               └──────┐         ┌──────┘
                      ▼         ▼
                ┌──────────────────────┐
                │   ScreeningData      │
                │  (+ validate())      │
                │  (+ getBefundstatus) │
                └──────────┬───────────┘
                           │
                ┌──────────┴───────────┐
                ▼                      ▼
        ┌─────────────┐         ┌──────────────┐
        │ DataManager │         │ FileHandler  │
        │  (RAM-Liste)│         │  (CSV-Datei) │
        └─────────────┘         └──────────────┘
                           ▲
                           │
                   nutzt von hier:
                           │
                ┌──────────┴───────────┐
                │   MainController     │  ← DEINE AUFGABE ab Schritt 6
                │   (Glue zwischen     │
                │    FXML und Logik)   │
                └──────────────────────┘
```

**Datenfluss bei "Speichern" (Button im GUI):**
1. Nutzer füllt die Felder aller drei Tabs aus
2. Controller liest die Werte aus → erstellt `Patient`, `Arztpraxis`, `ScreeningData`
3. `ScreeningData.validate()` läuft → bei Fehler: Warn-Dialog, Abbruch
4. Bei Erfolg: `DataManager.hinzufuegen(sd)` → Tabelle aktualisieren

**Datenfluss bei "CSV Export":**
1. Nutzer klickt Button → FileChooser-Dialog öffnet sich
2. Controller ruft `FileHandler.exportStammdaten(pfad, dataManager.getAlle())` auf

**Datenfluss bei "CSV Import":**
1. Nutzer wählt Datei → `FileHandler.importStammdaten(pfad)` liefert Liste zurück
2. `DataManager.setAlleErsetzt(liste)` → Tabelle aktualisieren

---

## 4. Was die einzelnen Klassen können (API-Spickzettel)

### `Patient`
```java
new Patient()                                         // leerer Konstruktor
new Patient(name, vorname, geburtsdatum, versId,
            str, hausnr, plz, ort, versichertenart, geschlecht)
// + alle Getter/Setter

// Konstanten zum Vergleichen:
Patient.MITGLIED              // = 1
Patient.FAMILIENANGEHOERIGER  // = 3
Patient.RENTNER               // = 5
Patient.MAENNLICH             // = "M"
Patient.WEIBLICH              // = "F"
Patient.UNBESTIMMT            // = "UN"
```

### `Arztpraxis`
```java
new Arztpraxis(betriebsstaettennummer, lebenslangArztNummer, arztname)
// + Getter/Setter
```

### `ScreeningData`
```java
ScreeningData sd = new ScreeningData();
sd.setPatient(p);
sd.setArztpraxis(a);
sd.setUntersuchungsdatum(LocalDate.now());
sd.setVerdachtsdiagnoseND(true);
// ...weitere Setter für alle 16 Felder

List<String> fehler = sd.validate();         // leer = ok
Befundstatus status = sd.getBefundstatus();  // für Farbe
```

### `DataManager`
```java
DataManager dm = new DataManager();
dm.hinzufuegen(sd);                                   // einzelnen DS dazu
List<ScreeningData> alle = dm.getAlle();              // alle holen
dm.setAlleErsetzt(neueListe);                         // komplett ersetzen
List<ScreeningData> treffer = dm.filterNachNachname("müller");
```

### `FileHandler`
```java
FileHandler fh = new FileHandler();
fh.exportStammdaten("pfad/zur/datei.csv", dataManager.getAlle());
List<ScreeningData> geladen = fh.importStammdaten("pfad/zur/datei.csv");
// Wirft IOException → try/catch im Controller!
```

---

## 5. Wie das Projekt gebaut und gestartet wird

In IntelliJ:
1. Rechts auf das Maven-Panel klicken (Elefanten-Icon)
2. `01_selfHautkrebs` → `Plugins` → `javafx` → Doppelklick auf **`javafx:run`**

Oder über die Kommandozeile im Projektordner:
```
mvn javafx:run
```

Maven holt automatisch alle Dependencies (JavaFX, JUnit). Beim ersten Start kann das ein paar Minuten dauern.

---

## 6. Deine Schritte ab hier

| Schritt | Was | Dauer ca. | Pflicht? |
|---------|-----|-----------|----------|
| 6 | Hello-Vorlage in MainApp/MainController/main.fxml umbenennen | 30 Min | PFLICHT 1 |
| 7 | FXML mit allen 4 Tabs befüllen | 90 Min | PFLICHT 1 |
| 8 | MainController verdrahten (@FXML, Buttons) | 90 Min | PFLICHT 1, 2, 3 |
| 9 | Plausibilität im GUI (disable/enable bei Checkboxen) | 30 Min | PFLICHT 3 |
| 10 | TableView mit Daten füllen | 20 Min | PFLICHT 1 |
| 11 | Farbkennzeichnung Befundstatus (Optional 5) | 20 Min | Optional |
| 12 | Testdatensatz erstellen und als CSV exportieren | 5 Min | Abgabe |
| 13 | Dokumentation (TDD-Beschreibung, Klassendiagramm, Anwendungsleitfaden) | 2-3 Std | Abgabe |

---

## Schritt 6 – Hello-Vorlage umbenennen

**Ziel:** Die generischen JavaFX-Vorlagen-Namen durch projektspezifische ersetzen.

### Umbenennen (immer per Refactor, NICHT manuell!)

1. Im Projektbaum: Rechtsklick auf `HelloApplication.java` → **Refactor → Rename** → `MainApp` → Enter
2. Rechtsklick auf `HelloController.java` → **Refactor → Rename** → `MainController` → Enter
3. Im `resources`-Ordner: Rechtsklick auf `hello-view.fxml` → **Refactor → Rename** → `main.fxml`

> **Warum Refactor und nicht einfach umbenennen?** IntelliJ ändert dadurch automatisch alle Verweise im Code mit – sonst startet die App nicht mehr.

### Code in `MainApp.java` anpassen

Suche die Methode `start(...)` und ändere drei Stellen:
- `getResource("hello-view.fxml")` → `getResource("main.fxml")`
- `setTitle("Hello!")` → `setTitle("eHKS – Hautkrebs-Screening")`
- `new Scene(fxmlLoader.load(), 320, 240)` → `new Scene(fxmlLoader.load(), 900, 650)`

### `pom.xml` prüfen

In `pom.xml` nach `<mainClass>` suchen. Wenn da noch `HelloApplication` steht, durch `MainApp` ersetzen.

### Verifikation

`mvn javafx:run` ausführen → Es öffnet sich ein leeres Fenster mit Titel "eHKS – Hautkrebs-Screening" in Größe 900×650.

---

## Schritt 7 – FXML: alle 4 Tabs

**Datei:** `src/main/resources/de/ehks/_1_selfhautkrebs/main.fxml`

Den vorhandenen Inhalt **komplett ersetzen** durch:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<?import javafx.geometry.Insets?>

<BorderPane xmlns:fx="http://javafx.com/fxml"
            fx:controller="de.ehks._1_selfhautkrebs.MainController"
            prefWidth="900" prefHeight="650">

  <top>
    <Label text="eHKS – Hautkrebs-Screening"
           style="-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 12;"/>
  </top>

  <center>
    <TabPane>

      <!-- TAB 1: Arztpraxis -->
      <Tab text="Arztpraxis" closable="false">
        <GridPane hgap="10" vgap="10">
          <padding><Insets top="20" left="20"/></padding>
          <Label text="BSN:" GridPane.rowIndex="0" GridPane.columnIndex="0"/>
          <TextField fx:id="tfBSN" GridPane.rowIndex="0" GridPane.columnIndex="1"/>
          <Label text="LANR:" GridPane.rowIndex="1" GridPane.columnIndex="0"/>
          <TextField fx:id="tfLANR" GridPane.rowIndex="1" GridPane.columnIndex="1"/>
          <Label text="Arztname:" GridPane.rowIndex="2" GridPane.columnIndex="0"/>
          <TextField fx:id="tfArztname" GridPane.rowIndex="2" GridPane.columnIndex="1"/>
        </GridPane>
      </Tab>

      <!-- TAB 2: Patient -->
      <Tab text="Patient" closable="false">
        <GridPane hgap="10" vgap="10">
          <padding><Insets top="20" left="20"/></padding>
          <Label text="Nachname:"      GridPane.rowIndex="0" GridPane.columnIndex="0"/>
          <TextField fx:id="tfName"    GridPane.rowIndex="0" GridPane.columnIndex="1"/>
          <Label text="Vorname:"       GridPane.rowIndex="1" GridPane.columnIndex="0"/>
          <TextField fx:id="tfVorname" GridPane.rowIndex="1" GridPane.columnIndex="1"/>
          <Label text="Geburtsdatum:"  GridPane.rowIndex="2" GridPane.columnIndex="0"/>
          <DatePicker fx:id="dpGeburtsdatum" GridPane.rowIndex="2" GridPane.columnIndex="1"/>
          <Label text="Versicherten-ID:" GridPane.rowIndex="3" GridPane.columnIndex="0"/>
          <TextField fx:id="tfVersichertenId" GridPane.rowIndex="3" GridPane.columnIndex="1"/>
          <Label text="Straße:" GridPane.rowIndex="4" GridPane.columnIndex="0"/>
          <TextField fx:id="tfStrasse" GridPane.rowIndex="4" GridPane.columnIndex="1"/>
          <Label text="Hausnummer:" GridPane.rowIndex="5" GridPane.columnIndex="0"/>
          <TextField fx:id="tfHausnummer" GridPane.rowIndex="5" GridPane.columnIndex="1"/>
          <Label text="PLZ:" GridPane.rowIndex="6" GridPane.columnIndex="0"/>
          <TextField fx:id="tfPlz" GridPane.rowIndex="6" GridPane.columnIndex="1"/>
          <Label text="Ort:" GridPane.rowIndex="7" GridPane.columnIndex="0"/>
          <TextField fx:id="tfOrt" GridPane.rowIndex="7" GridPane.columnIndex="1"/>
          <Label text="Versichertenart:" GridPane.rowIndex="8" GridPane.columnIndex="0"/>
          <ComboBox fx:id="cbVersichertenart" GridPane.rowIndex="8" GridPane.columnIndex="1"/>
          <Label text="Geschlecht:" GridPane.rowIndex="9" GridPane.columnIndex="0"/>
          <ComboBox fx:id="cbGeschlecht" GridPane.rowIndex="9" GridPane.columnIndex="1"/>
        </GridPane>
      </Tab>

      <!-- TAB 3: Untersuchung -->
      <Tab text="Untersuchung" closable="false">
        <ScrollPane fitToWidth="true">
          <GridPane hgap="10" vgap="10">
            <padding><Insets top="20" left="20"/></padding>

            <Label text="Untersuchungsdatum:" GridPane.rowIndex="0" GridPane.columnIndex="0"/>
            <DatePicker fx:id="dpUntersuchungsdatum" GridPane.rowIndex="0" GridPane.columnIndex="1"/>

            <CheckBox fx:id="cbVerdachtsdiagnoseND" text="Verdachtsdiagnose (1.1.1)"
                      GridPane.rowIndex="1" GridPane.columnIndex="0" GridPane.columnSpan="2"/>
            <CheckBox fx:id="cbMalignesMelanom" text="Malignes Melanom (1.1.2)" disable="true"
                      GridPane.rowIndex="2" GridPane.columnIndex="1"/>
            <CheckBox fx:id="cbBasalzellkarzinom" text="Basalzellkarzinom (1.1.3)" disable="true"
                      GridPane.rowIndex="3" GridPane.columnIndex="1"/>
            <CheckBox fx:id="cbSpinozellulares" text="Spinozelluläres (1.1.4)" disable="true"
                      GridPane.rowIndex="4" GridPane.columnIndex="1"/>
            <CheckBox fx:id="cbSonstigeDiagnose" text="Sonstige (1.1.5)" disable="true"
                      GridPane.rowIndex="5" GridPane.columnIndex="1"/>

            <CheckBox fx:id="cbGesundheitsunt" text="Gesundheitsuntersuchung (1.2)"
                      GridPane.rowIndex="6" GridPane.columnIndex="0" GridPane.columnSpan="2"/>
            <CheckBox fx:id="cbUeberweisung" text="Überweisung zum Dermatologen (1.3)"
                      GridPane.rowIndex="7" GridPane.columnIndex="0" GridPane.columnSpan="2"/>
            <Label text="Name Dermatologe:" GridPane.rowIndex="8" GridPane.columnIndex="0"/>
            <TextField fx:id="tfDermatologeName" GridPane.rowIndex="8" GridPane.columnIndex="1"/>

            <CheckBox fx:id="cbBiopsie" text="Biopsie entnommen (2.4.1)"
                      GridPane.rowIndex="9" GridPane.columnIndex="0" GridPane.columnSpan="2"/>
            <Label text="Anzahl Biopsien (0–99):" GridPane.rowIndex="10" GridPane.columnIndex="0"/>
            <TextField fx:id="tfAnzahlBiopsien" disable="true"
                       GridPane.rowIndex="10" GridPane.columnIndex="1"/>
            <Label text="Biopsiedatum:" GridPane.rowIndex="11" GridPane.columnIndex="0"/>
            <DatePicker fx:id="dpBiopsiedatum" disable="true"
                        GridPane.rowIndex="11" GridPane.columnIndex="1"/>

            <CheckBox fx:id="cbHistoPathologie" text="Histopathologie (2.5)"
                      GridPane.rowIndex="12" GridPane.columnIndex="0" GridPane.columnSpan="2"/>
            <Label text="Histobefund:" GridPane.rowIndex="13" GridPane.columnIndex="0"/>
            <TextField fx:id="tfHistoBefund" GridPane.rowIndex="13" GridPane.columnIndex="1"/>

            <Button text="Datensatz speichern" onAction="#onSpeichern"
                    GridPane.rowIndex="15" GridPane.columnIndex="0"/>
          </GridPane>
        </ScrollPane>
      </Tab>

      <!-- TAB 4: Daten -->
      <Tab text="Daten" closable="false">
        <VBox spacing="10">
          <padding><Insets top="15" left="15" right="15" bottom="15"/></padding>

          <HBox spacing="10">
            <Label text="Befundstatus:" style="-fx-font-weight: bold;"/>
            <Label fx:id="lblBefundstatus" text="–" style="-fx-padding: 4 12;"/>
          </HBox>

          <HBox spacing="10">
            <Label text="Suche Nachname:"/>
            <TextField fx:id="tfFilter" prefWidth="200"/>
            <Button text="Filtern" onAction="#onFiltern"/>
          </HBox>

          <TableView fx:id="tableView" VBox.vgrow="ALWAYS">
            <columns>
              <TableColumn fx:id="colNachname"       text="Nachname"        prefWidth="150"/>
              <TableColumn fx:id="colVorname"        text="Vorname"         prefWidth="120"/>
              <TableColumn fx:id="colVersichertenId" text="Versicherten-ID" prefWidth="150"/>
              <TableColumn fx:id="colDatum"          text="Datum"           prefWidth="120"/>
              <TableColumn fx:id="colBefund"         text="Befund"          prefWidth="150"/>
            </columns>
          </TableView>

          <HBox spacing="10">
            <Button text="CSV exportieren" onAction="#onExportieren"/>
            <Button text="CSV importieren" onAction="#onImportieren"/>
          </HBox>
        </VBox>
      </Tab>

    </TabPane>
  </center>
</BorderPane>
```

### Wichtige Konzepte im FXML

- **`fx:id="..."`** = ID die im Controller mit `@FXML private ... xyz;` verbunden wird (gleicher Name!)
- **`onAction="#onSpeichern"`** = ruft beim Klick die Methode `onSpeichern()` im Controller auf
- **`disable="true"`** = das Feld ist beim Start ausgegraut (wird später per Listener aktiviert)
- **`GridPane.rowIndex` / `columnIndex`** = Position in der Tabelle
- **`closable="false"`** = der Tab kann nicht weggeklickt werden

### Verifikation

`mvn javafx:run` → Es erscheinen 4 Tabs mit allen Eingabefeldern. Die App stürzt **nicht** ab.
Wenn doch: meistens steht im Konsolen-Output, welches `@FXML`-Feld fehlt → das ist normal, das fixen wir im nächsten Schritt.

---

## Schritt 8 – `MainController.java` verdrahten

**Ziel:** Jede `fx:id` aus dem FXML braucht ein passendes `@FXML`-Feld im Controller, und jede `onAction`-Methode (`onSpeichern`, `onExportieren`, `onImportieren`, `onFiltern`) muss als Methode existieren.

**Datei komplett ersetzen** durch:

```java
package de.ehks._1_selfhautkrebs;

import de.ehks._1_selfhautkrebs.model.*;
import de.ehks._1_selfhautkrebs.service.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MainController {

    // --- Arztpraxis ---
    @FXML private TextField tfBSN, tfLANR, tfArztname;

    // --- Patient ---
    @FXML private TextField tfName, tfVorname, tfVersichertenId,
                            tfStrasse, tfHausnummer, tfPlz, tfOrt;
    @FXML private DatePicker dpGeburtsdatum;
    @FXML private ComboBox<String> cbVersichertenart, cbGeschlecht;

    // --- Untersuchung ---
    @FXML private DatePicker dpUntersuchungsdatum, dpBiopsiedatum;
    @FXML private CheckBox cbVerdachtsdiagnoseND, cbMalignesMelanom,
                           cbBasalzellkarzinom, cbSpinozellulares,
                           cbSonstigeDiagnose, cbGesundheitsunt,
                           cbUeberweisung, cbBiopsie, cbHistoPathologie;
    @FXML private TextField tfDermatologeName, tfAnzahlBiopsien, tfHistoBefund;

    // --- Daten ---
    @FXML private TableView<ScreeningData> tableView;
    @FXML private TableColumn<ScreeningData, String> colNachname, colVorname,
                                                       colVersichertenId, colDatum, colBefund;
    @FXML private TextField tfFilter;
    @FXML private Label lblBefundstatus;

    private final DataManager dataManager = new DataManager();
    private final FileHandler fileHandler = new FileHandler();
    private final ObservableList<ScreeningData> tabellenDaten = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cbVersichertenart.setItems(FXCollections.observableArrayList(
            "1 - Mitglied", "3 - Familienangehöriger", "5 - Rentner"));
        cbGeschlecht.setItems(FXCollections.observableArrayList(
            "M - männlich", "F - weiblich", "UN - unbestimmt"));

        // Plausibilität: Diagnose-Checkboxen nur aktiv wenn Verdachtsdiagnose=Ja
        cbVerdachtsdiagnoseND.selectedProperty().addListener((obs, alt, neu) -> {
            cbMalignesMelanom.setDisable(!neu);
            cbBasalzellkarzinom.setDisable(!neu);
            cbSpinozellulares.setDisable(!neu);
            cbSonstigeDiagnose.setDisable(!neu);
        });

        // Plausibilität: Biopsie-Felder
        cbBiopsie.selectedProperty().addListener((obs, alt, neu) -> {
            tfAnzahlBiopsien.setDisable(!neu);
            dpBiopsiedatum.setDisable(!neu);
        });

        // Tabellenspalten verdrahten
        tableView.setItems(tabellenDaten);
        colNachname.setCellValueFactory(sd ->
            new SimpleStringProperty(sd.getValue().getPatient().getName()));
        colVorname.setCellValueFactory(sd ->
            new SimpleStringProperty(sd.getValue().getPatient().getVorname()));
        colVersichertenId.setCellValueFactory(sd ->
            new SimpleStringProperty(sd.getValue().getPatient().getVersichertenId()));
        colDatum.setCellValueFactory(sd -> new SimpleStringProperty(
            sd.getValue().getUntersuchungsdatum() != null
                ? sd.getValue().getUntersuchungsdatum().toString() : ""));
        colBefund.setCellValueFactory(sd ->
            new SimpleStringProperty(sd.getValue().getBefundstatus().name()));
    }

    @FXML
    private void onSpeichern() {
        ScreeningData sd = erstelleAusEingabe();
        List<String> fehler = sd.validate();
        if (!fehler.isEmpty()) {
            zeigeWarnung("Fehlerhafte Eingabe", String.join("\n", fehler));
            return;
        }
        dataManager.hinzufuegen(sd);
        aktualisiereTabelle();
        aktualisiereStatusfarbe(sd);
    }

    @FXML
    private void onExportieren() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File datei = fc.showSaveDialog(null);
        if (datei == null) return;
        try {
            fileHandler.exportStammdaten(datei.getAbsolutePath(), dataManager.getAlle());
        } catch (IOException e) { zeigeWarnung("Exportfehler", e.getMessage()); }
    }

    @FXML
    private void onImportieren() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File datei = fc.showOpenDialog(null);
        if (datei == null) return;
        try {
            dataManager.setAlleErsetzt(fileHandler.importStammdaten(datei.getAbsolutePath()));
            aktualisiereTabelle();
        } catch (IOException e) { zeigeWarnung("Importfehler", e.getMessage()); }
    }

    @FXML
    private void onFiltern() {
        if (tfFilter.getText().isBlank()) aktualisiereTabelle();
        else tabellenDaten.setAll(dataManager.filterNachNachname(tfFilter.getText()));
    }

    private ScreeningData erstelleAusEingabe() {
        int va = cbVersichertenart.getValue() != null
                 ? Integer.parseInt(cbVersichertenart.getValue().substring(0, 1)) : 1;
        String g = cbGeschlecht.getValue() != null
                   ? cbGeschlecht.getValue().split(" ")[0] : "M";

        Patient p = new Patient(tfName.getText(), tfVorname.getText(),
            dpGeburtsdatum.getValue(), tfVersichertenId.getText(),
            tfStrasse.getText(), tfHausnummer.getText(), tfPlz.getText(),
            tfOrt.getText(), va, g);
        Arztpraxis a = new Arztpraxis(tfBSN.getText(), tfLANR.getText(), tfArztname.getText());

        ScreeningData sd = new ScreeningData();
        sd.setPatient(p);
        sd.setArztpraxis(a);
        sd.setUntersuchungsdatum(dpUntersuchungsdatum.getValue() != null
            ? dpUntersuchungsdatum.getValue() : LocalDate.now());
        sd.setVerdachtsdiagnoseND(cbVerdachtsdiagnoseND.isSelected());
        sd.setMalignesMelanom(cbMalignesMelanom.isSelected());
        sd.setBasalzellkarzinom(cbBasalzellkarzinom.isSelected());
        sd.setSpinozellulares(cbSpinozellulares.isSelected());
        sd.setSonstigeVerdachtsdiagnose(cbSonstigeDiagnose.isSelected());
        sd.setGesundheitsuntersuchungDurchgefuehrt(cbGesundheitsunt.isSelected());
        sd.setUeberweisungErfolgt(cbUeberweisung.isSelected());
        sd.setUeberweisungDermatologeName(tfDermatologeName.getText());
        sd.setBiopsieEntnommen(cbBiopsie.isSelected());
        if (cbBiopsie.isSelected() && !tfAnzahlBiopsien.getText().isBlank()) {
            try { sd.setAnzahlBiopsien(Integer.parseInt(tfAnzahlBiopsien.getText())); }
            catch (NumberFormatException ignored) {}
        }
        sd.setBiopsiedatum(dpBiopsiedatum.getValue());
        sd.setHistopathologieDurchgefuehrt(cbHistoPathologie.isSelected());
        sd.setHistopathologieBefund(tfHistoBefund.getText());
        return sd;
    }

    private void aktualisiereTabelle() {
        tabellenDaten.setAll(dataManager.getAlle());
    }

    // Optional 5: Farbkennzeichnung
    private void aktualisiereStatusfarbe(ScreeningData sd) {
        switch (sd.getBefundstatus()) {
            case UNAUFFAELLIG ->
                lblBefundstatus.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            case AUFFAELLIG ->
                lblBefundstatus.setStyle("-fx-background-color: #FFC107;");
            case MEHRFACH_AUFFAELLIG ->
                lblBefundstatus.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        }
        lblBefundstatus.setText(sd.getBefundstatus().name());
    }

    private void zeigeWarnung(String titel, String text) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(titel); a.setContentText(text); a.showAndWait();
    }
}
```

### Was passiert in `initialize()`?

Diese Methode wird **einmal beim App-Start** automatisch von JavaFX aufgerufen, nachdem das FXML geladen ist:
1. ComboBoxen mit Auswahlwerten füllen
2. Listener registrieren, die Felder aktivieren/deaktivieren (PFLICHT 3 Plausibilität)
3. Tabellenspalten verbinden mit den Feldern der ScreeningData-Objekte

### Verifikation

`mvn javafx:run` → App startet ohne Konsolen-Fehler. Du kannst:
- Felder ausfüllen
- "Verdachtsdiagnose" anklicken → die 4 darunter werden aktiv
- "Biopsie entnommen" anklicken → Anzahl + Datum werden aktiv
- "Datensatz speichern" klicken → entweder Warnung (wenn ungültig) oder Zeile erscheint in der Tabelle auf Tab 4

---

## Schritte 9 + 10 + 11

Diese sind im obigen Controller-Code **schon mit eingebaut**:

- **Schritt 9 (Plausibilität)** ✓ → die zwei Listener in `initialize()`
- **Schritt 10 (TableView füllen)** ✓ → die `setCellValueFactory(...)`-Calls in `initialize()`
- **Schritt 11 (Farbkennzeichnung)** ✓ → die Methode `aktualisiereStatusfarbe()`

Du musst nichts mehr machen, außer testen dass alles funktioniert. Falls eines nicht klappt:

| Problem | Lösung |
|---|---|
| Tabelle bleibt leer | `setCellValueFactory()` für die Spalten gestrichen? Im FXML fehlt vielleicht ein `fx:id` |
| Farbe ändert sich nicht | `lblBefundstatus`-Label im FXML hat `fx:id="lblBefundstatus"`? |
| Checkbox-Felder bleiben immer aktiv | `disable="true"` im FXML vergessen, oder Listener-Code wurde gelöscht |

---

## Schritt 12 – Testdatensatz erstellen

1. `mvn javafx:run`
2. Tab "Arztpraxis" → BSN, LANR, Arztname ausfüllen
3. Tab "Patient" → alle 10 Felder ausfüllen (z. B. Müller, Hans, 15.01.1980, ...)
4. Tab "Untersuchung" → Datum + ein paar Checkboxen ankreuzen (z. B. "Verdachtsdiagnose" + "Malignes Melanom")
5. "Datensatz speichern" klicken → Tab "Daten" zeigt jetzt eine Zeile
6. **Wiederhole das mit 2-3 verschiedenen Patienten** (für die Demo gut)
7. Tab "Daten" → **"CSV exportieren"** klicken → Datei aufbewahren (z. B. `testdaten.csv`)

Diese CSV-Datei gehört zur Abgabe!

---

## Schritt 13 – Dokumentation

### 13.1 TDD-Beschreibung (1 Seite)

Schreibe (z. B. in Word/Google Docs):
- "Wir haben für die `Patient`-Klasse 5 JUnit-Tests geschrieben **bevor** wir den GUI-Code implementiert haben."
- Was jeder Test prüft – siehe Datei `PatientTest.java`:
  1. `konstruktorSetztAlleFelder` – prüft dass der volle Konstruktor alle 10 Argumente korrekt in die Felder schreibt
  2. `versichertenartKonstantenStimmen` – prüft die KBV-Werte 1/3/5
  3. `geschlechtKonstantenStimmen` – prüft die KBV-Werte M/F/UN
  4. `setterAendertWert` – prüft dass Setter funktionieren (Kapselung)
  5. `leererKonstruktorErzeugtObjekt` – prüft dass auch ein leerer Konstruktor ein gültiges Objekt erzeugt
- Reflexion: "Hätten wir den Code zuerst geschrieben und dann erst Tests, hätten wir z. B. den Fall vergessen, dass der leere Konstruktor `null`-Werte erzeugt – das hätte später beim CSV-Import zu NullPointerExceptions geführt."

### 13.2 Klassendiagramm

Tool: **[draw.io](https://draw.io)** – kostenlos, online, kein Account nötig.

Zu zeichnen (als Kästen mit Klassenname oben, Felder Mitte, Methoden unten):
- `Patient`, `Arztpraxis`, `ScreeningData`, `Befundstatus` (alle aus model/)
- `DataManager`, `FileHandler` (aus service/)
- `MainController`, `MainApp` (root)

Pfeile:
- `ScreeningData` ──hat──> `Patient`
- `ScreeningData` ──hat──> `Arztpraxis`
- `ScreeningData` ──liefert──> `Befundstatus`
- `MainController` ──nutzt──> `DataManager`
- `MainController` ──nutzt──> `FileHandler`
- `DataManager` ──hält──> `List<ScreeningData>`
- `MainApp` ──lädt──> `MainController` (via FXML)

### 13.3 Anwendungsleitfaden (1-2 Seiten)

- Screenshot vom Hauptfenster (alle 4 Tabs einmal)
- Schritt-für-Schritt: "So legen Sie einen neuen Datensatz an"
  1. Tab "Arztpraxis" ausfüllen
  2. Tab "Patient" ausfüllen
  3. Tab "Untersuchung" → relevante Felder ankreuzen
  4. "Datensatz speichern" → bei Warnung: Felder korrigieren
  5. Tab "Daten" → Tabelle und Befundstatus prüfen
- Screenshot mit der Plausibilitätswarnung (z. B. Verdachtsdiagnose ohne konkrete Diagnose)
- Erklärung der Befundstatus-Farben:
  - **Grün** = UNAUFFAELLIG (keine Auffälligkeit)
  - **Gelb** = AUFFAELLIG (eine Auffälligkeit: Verdacht ODER Biopsie ODER Histo)
  - **Rot** = MEHRFACH_AUFFAELLIG (mehrere)

---

## Häufige Fehler

| Fehler | Lösung |
|--------|--------|
| `Location is required` beim Start | `main.fxml` liegt nicht in `src/main/resources/de/ehks/_1_selfhautkrebs/` |
| `NullPointerException` in `initialize()` | `fx:id` im FXML stimmt nicht mit `@FXML`-Variablen-Name im Controller überein |
| `IllegalAccessException ... module does not "opens" ...` | `module-info.java` öffnet das Paket nicht für javafx.fxml |
| Tabelle bleibt leer | `setCellValueFactory()` für die Spalten fehlt oder `fx:id` der Spalten falsch |
| ComboBox leer | `setItems()` in `initialize()` vergessen |
| Tests laufen nicht | Annotation `@Test` muss von `org.junit.jupiter.api.Test` kommen (JUnit 5) |
| Datum als "1980-01-15" statt deutsch | Im Controller mit eigenem Formatter formatieren – aktuell ist `LocalDate.toString()` ISO |

---

## Nützliche Links

| Thema | Link |
|---|---|
| JavaFX Tutorials | https://jenkov.com/tutorials/javafx/index.html |
| JavaFX TableView | https://jenkov.com/tutorials/javafx/tableview.html |
| JavaFX FXML Controller | https://jenkov.com/tutorials/javafx/fxml.html |
| JUnit 5 | https://www.baeldung.com/junit-5 |
| LocalDate Java | https://www.baeldung.com/java-localdate |
| draw.io UML | https://drawio-app.com/blog/uml-class-diagrams |
| Bro Code Java Full Course | YouTube-Suche |
| GenuineCoder JavaFX | YouTube-Suche |

---

## Abgabe-Checkliste

**Bereits erledigt (von vorherigem Bearbeiter):**
- [x] `Befundstatus.java` (enum)
- [x] `Arztpraxis.java`
- [x] `Patient.java`
- [x] `ScreeningData.java` mit `validate()` + `getBefundstatus()`
- [x] `PatientTest.java` mit 5 grünen Tests
- [x] `FileHandler.java` – CSV Import/Export
- [x] `DataManager.java`
- [x] `module-info.java` für alle 3 Pakete
- [x] `pom.xml` mit Java 21 + JavaFX 21 + JUnit 5

**Deine Aufgaben:**
- [ ] `MainApp.java` + `main.fxml` (Schritt 6)
- [ ] FXML: alle 4 Tabs (Schritt 7)
- [ ] `MainController.java` komplett (Schritt 8)
- [ ] App startet ohne Fehler
- [ ] Tab Arztpraxis funktioniert (3 Felder)
- [ ] Tab Patient funktioniert (10 Felder)
- [ ] Tab Untersuchung funktioniert (alle Checkboxen + Felder)
- [ ] Plausibilität: Verdachtsdiagnose enabled die 4 darunter (Schritt 9)
- [ ] Plausibilität: Biopsie enabled Anzahl + Datum (Schritt 9)
- [ ] Plausibilität: Speichern zeigt Warnung bei Fehler
- [ ] Tab Daten: TableView zeigt gespeicherte Datensätze (Schritt 10)
- [ ] Farbkennzeichnung Befundstatus funktioniert (Schritt 11, Optional 5)
- [ ] CSV Export erzeugt Datei (Schritt 12)
- [ ] CSV Import lädt Datei zurück
- [ ] Testdatensatz als CSV bereit für Abgabe
- [ ] TDD-Beschreibung geschrieben (Schritt 13.1)
- [ ] Klassendiagramm erstellt (Schritt 13.2)
- [ ] Anwendungsleitfaden geschrieben (Schritt 13.3)
- [ ] Code kommentiert (kurze Erklärungen über jeder Klasse / komplizierten Methode)
- [ ] Präsentation vorbereitet (Folien + Live-Demo, 25 Min)
- [ ] Abgabe spätestens am **20.05.2026**, Präsentation **21.05.2026**

Viel Erfolg! 💪
