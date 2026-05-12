# eHKS – Mein Leitfaden zum Selbstmachen

> **Stand:** Befundstatus + Arztpraxis sind fertig.
> **Package:** `de.ehks._1_selfhautkrebs` (mit Unterpaketen `.model` und `.service`)

---

## Übersicht aller Schritte

| Schritt | Was | Dauer ca. | Pflicht? |
|---------|-----|-----------|----------|
| 1 | `Patient.java` | 30 Min | PFLICHT 1 |
| 2 | `ScreeningData.java` mit `validate()` | 60 Min | PFLICHT 1, 3 |
| 3 | `PatientTest.java` (mind. 5 Tests) | 30 Min | PFLICHT 4 (TDD) |
| 4 | `FileHandler.java` (CSV-Import/Export) | 45 Min | PFLICHT 2 |
| 5 | `DataManager.java` (Liste zur Laufzeit) | 15 Min | PFLICHT 1 |
| 6 | `MainApp.java` + `main.fxml` Grundgerüst | 30 Min | PFLICHT 1 |
| 7 | FXML: alle 4 Tabs (Arzt, Patient, Untersuchung, Daten) | 90 Min | PFLICHT 1 |
| 8 | `MainController.java` verdrahten | 90 Min | PFLICHT 1, 2, 3 |
| 9 | Plausibilität im GUI (disable/enable) | 30 Min | PFLICHT 3 |
| 10 | TableView mit Daten füllen | 20 Min | PFLICHT 1 |
| 11 | Optional: Farbkennzeichnung (am einfachsten) | 20 Min | mind. 1 optional |
| 12 | Testdatensatz exportieren | 5 Min | Abgabe |
| 13 | Dokumentation (TDD-Beschreibung, Klassendiagramm) | 2-3 Std | Abgabe |

---

## Schritt 1 – `Patient.java`

**Ort:** `src/main/java/de/ehks/_1_selfhautkrebs/model/Patient.java`

**Felder (private!):**
- `String name`
- `String vorname`
- `LocalDate geburtsdatum` — Import: `import java.time.LocalDate;`
- `String versichertenId`
- `String strasse`, `String hausnummer`, `String plz`, `String ort`
- `int versichertenart` — Werte: 1 (Mitglied), 3 (Familienangehöriger), 5 (Rentner)
- `String geschlecht` — Werte: "M", "F", "UN"

**Konstanten am Anfang der Klasse:**
```java
public static final int MITGLIED = 1;
public static final int FAMILIENANGEHOERIGER = 3;
public static final int RENTNER = 5;
public static final String MAENNLICH = "M";
public static final String WEIBLICH = "F";
public static final String UNBESTIMMT = "UN";
```

**Was du brauchst:**
- Leerer Konstruktor + voller Konstruktor mit allen 10 Feldern
- Getter und Setter für alle Felder (über IntelliJ Generate-Funktion!)
- `toString()`-Methode für Debugging

**IntelliJ-Tipp:** Rechtsklick im Code → **Generate (Alt+Insert)** → **Constructor** und dann nochmal **Getter and Setter** → alle Felder auswählen.

**Verifikation:** Klasse kompiliert ohne Fehler.

---

## Schritt 2 – `ScreeningData.java`

**Ort:** `src/main/java/de/ehks/_1_selfhautkrebs/model/ScreeningData.java`

**Felder (orientiert an PFLICHT 3, KBV-Plausi):**

| Feld | Typ | Hinweis |
|------|-----|---------|
| `patient` | `Patient` | Referenz auf Stammdaten |
| `arztpraxis` | `Arztpraxis` | Referenz auf Praxis |
| `untersuchungsdatum` | `LocalDate` | |
| `verdachtsdiagnoseND` | `boolean` | 1.1.1 |
| `malignesMelanom` | `boolean` | 1.1.2 |
| `basalzellkarzinom` | `boolean` | 1.1.3 |
| `spinozellulares` | `boolean` | 1.1.4 |
| `sonstigeVerdachtsdiagnose` | `boolean` | 1.1.5 |
| `gesundheitsuntersuchungDurchgefuehrt` | `boolean` | 1.2 |
| `ueberweisungErfolgt` | `boolean` | 1.3 |
| `ueberweisungDermatologeName` | `String` | |
| `biopsieEntnommen` | `boolean` | 2.4.1 |
| `anzahlBiopsien` | `int` | 2.4.2 (0–99) |
| `biopsiedatum` | `LocalDate` | |
| `histopathologieDurchgefuehrt` | `boolean` | 2.5 |
| `histopathologieBefund` | `String` | |

**Plus zwei wichtige Methoden:**

### `validate()` – Plausibilitätsprüfung (PFLICHT 3)
```java
public List<String> validate() {
    List<String> fehler = new ArrayList<>();

    // Regel 1: Wenn Verdachtsdiagnose vorhanden → mind. eine konkrete Diagnose
    if (verdachtsdiagnoseND
        && !malignesMelanom && !basalzellkarzinom
        && !spinozellulares && !sonstigeVerdachtsdiagnose) {
        fehler.add("Bitte mindestens eine konkrete Verdachtsdiagnose angeben (1.1.2–1.1.5).");
    }

    // Regel 2: Wenn Biopsie = Ja → Anzahl muss 0–99 sein
    if (biopsieEntnommen && (anzahlBiopsien < 0 || anzahlBiopsien > 99)) {
        fehler.add("Anzahl Biopsien muss zwischen 0 und 99 liegen.");
    }

    // Regel 3: Wenn Biopsie = Nein → Anzahl darf nicht > 0 sein
    if (!biopsieEntnommen && anzahlBiopsien > 0) {
        fehler.add("Anzahl Biopsien darf nicht gesetzt sein, wenn keine Biopsie entnommen.");
    }

    return fehler;
}
```

Imports: `import java.util.ArrayList;` und `import java.util.List;`

### `getBefundstatus()` – Farbkennzeichnung (Optional 5)
```java
public Befundstatus getBefundstatus() {
    int auffaellig = 0;
    if (verdachtsdiagnoseND) auffaellig++;
    if (biopsieEntnommen) auffaellig++;
    if (histopathologieDurchgefuehrt) auffaellig++;

    if (auffaellig == 0) return Befundstatus.UNAUFFAELLIG;
    if (auffaellig == 1) return Befundstatus.AUFFAELLIG;
    return Befundstatus.MEHRFACH_AUFFAELLIG;
}
```

**Verifikation:** Im Test einen ScreeningData mit `verdachtsdiagnoseND=true` aber ohne konkrete Diagnose erstellen → `validate()` muss eine Fehlermeldung liefern.

---

## Schritt 3 – `PatientTest.java` (TDD)

**Ort:** `src/test/java/de/ehks/_1_selfhautkrebs/model/PatientTest.java`
(Den Ordner `model` musst du in `src/test/java/de/ehks/_1_selfhautkrebs/` erst noch erstellen.)

**Mindestens 5 Tests:**

```java
package de.ehks._1_selfhautkrebs.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void konstruktorSetztAlleFelder() {
        Patient p = new Patient("Müller", "Hans", LocalDate.of(1980, 1, 15),
                "A123456789", "Musterstr.", "1", "12345", "Berlin", 1, "M");
        assertEquals("Müller", p.getName());
        assertEquals("Hans", p.getVorname());
        assertEquals(LocalDate.of(1980, 1, 15), p.getGeburtsdatum());
        assertEquals(1, p.getVersichertenart());
    }

    @Test
    void versichertenartKonstantenStimmen() {
        assertEquals(1, Patient.MITGLIED);
        assertEquals(3, Patient.FAMILIENANGEHOERIGER);
        assertEquals(5, Patient.RENTNER);
    }

    @Test
    void geschlechtKonstantenStimmen() {
        assertEquals("M", Patient.MAENNLICH);
        assertEquals("F", Patient.WEIBLICH);
        assertEquals("UN", Patient.UNBESTIMMT);
    }

    @Test
    void setterAendertWert() {
        Patient p = new Patient();
        p.setName("Schmidt");
        assertEquals("Schmidt", p.getName());
    }

    @Test
    void leererKonstruktorErzeugtObjekt() {
        Patient p = new Patient();
        assertNotNull(p);
        assertNull(p.getName());
    }
}
```

**Tests laufen lassen:** Grüner Play-Button neben der Klasse oder neben einer einzelnen Methode.
Alle 5 müssen grün sein.

**Dokumentation:** Notiere dir nebenher: welcher Test welche Eigenschaft prüft → kommt später in die TDD-Doku.

---

## Schritt 4 – `FileHandler.java`

**Ort:** Erst `service`-Paket erstellen: Rechtsklick auf `de.ehks._1_selfhautkrebs` → New → Package → `service`
Dann `FileHandler.java` darin anlegen.

**Wichtig:** Danach `module-info.java` ergänzen:
```java
opens de.ehks._1_selfhautkrebs.service to javafx.fxml;
exports de.ehks._1_selfhautkrebs.service;
```

**Klassenstruktur:**
```java
package de.ehks._1_selfhautkrebs.service;

import de.ehks._1_selfhautkrebs.model.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileHandler {

    private static final String TRENNER = ";";
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final String STAMM_HEADER =
        "name;vorname;geburtsdatum;versichertenId;strasse;hausnummer;plz;ort;" +
        "versichertenart;geschlecht;bsn;lanr;arztname";

    public void exportStammdaten(String pfad, List<ScreeningData> daten) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pfad))) {
            bw.write(STAMM_HEADER);
            bw.newLine();
            for (ScreeningData sd : daten) {
                bw.write(toStammZeile(sd));
                bw.newLine();
            }
        }
    }

    public List<ScreeningData> importStammdaten(String pfad) throws IOException {
        List<ScreeningData> liste = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pfad))) {
            br.readLine(); // Header überspringen
            String zeile;
            while ((zeile = br.readLine()) != null) {
                if (!zeile.isBlank()) liste.add(fromStammZeile(zeile));
            }
        }
        return liste;
    }

    private String toStammZeile(ScreeningData sd) {
        Patient p = sd.getPatient();
        Arztpraxis a = sd.getArztpraxis();
        return String.join(TRENNER,
            p.getName(), p.getVorname(),
            p.getGeburtsdatum() != null ? p.getGeburtsdatum().format(FORMAT) : "",
            p.getVersichertenId(), p.getStrasse(), p.getHausnummer(),
            p.getPlz(), p.getOrt(),
            String.valueOf(p.getVersichertenart()), p.getGeschlecht(),
            a.getBetriebsstaettennummer(), a.getLebenslangArztNummer(), a.getArztname()
        );
    }

    private ScreeningData fromStammZeile(String zeile) {
        String[] t = zeile.split(TRENNER, -1);
        Patient p = new Patient(
            t[0], t[1],
            t[2].isBlank() ? null : LocalDate.parse(t[2], FORMAT),
            t[3], t[4], t[5], t[6], t[7],
            Integer.parseInt(t[8]), t[9]
        );
        Arztpraxis a = new Arztpraxis(t[10], t[11], t[12]);
        ScreeningData sd = new ScreeningData();
        sd.setPatient(p);
        sd.setArztpraxis(a);
        return sd;
    }
}
```

**Vorher ohne GUI testen** – temporäre `main()`-Methode dazuschreiben:
```java
public static void main(String[] args) throws Exception {
    FileHandler fh = new FileHandler();
    Patient p = new Patient("Müller", "Hans", LocalDate.of(1980,1,1),
        "A1", "Str.", "1", "12345", "Berlin", 1, "M");
    Arztpraxis a = new Arztpraxis("B1", "L1", "Dr. Test");
    ScreeningData sd = new ScreeningData();
    sd.setPatient(p); sd.setArztpraxis(a);
    fh.exportStammdaten("test.csv", List.of(sd));
    List<ScreeningData> geladen = fh.importStammdaten("test.csv");
    System.out.println(geladen.get(0).getPatient().getName()); // muss "Müller" ausgeben
}
```

Wenn das funktioniert, `main()` wieder löschen.

---

## Schritt 5 – `DataManager.java`

**Ort:** `service/DataManager.java`

Verwaltet die Liste aller Datensätze während die App läuft. Sehr kurz:

```java
package de.ehks._1_selfhautkrebs.service;

import de.ehks._1_selfhautkrebs.model.ScreeningData;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private final List<ScreeningData> alle = new ArrayList<>();

    public void hinzufuegen(ScreeningData sd) { alle.add(sd); }
    public List<ScreeningData> getAlle()      { return new ArrayList<>(alle); }
    public void setAlleErsetzt(List<ScreeningData> neu) { alle.clear(); alle.addAll(neu); }

    // Optional 6: Filter nach Nachname
    public List<ScreeningData> filterNachNachname(String suchbegriff) {
        return alle.stream()
            .filter(sd -> sd.getPatient().getName()
                .toLowerCase().contains(suchbegriff.toLowerCase()))
            .toList();
    }
}
```

---

## Schritt 6 – `MainApp.java` + `main.fxml`

**Umbenennen:**
1. Im Projekt-Baum: Rechtsklick auf `HelloApplication.java` → **Refactor → Rename** → `MainApp`
2. Rechtsklick auf `HelloController.java` → **Refactor → Rename** → `MainController`
3. Rechtsklick auf `hello-view.fxml` → **Refactor → Rename** → `main.fxml`
4. In `MainApp.java` die Zeile mit `getResource("hello-view.fxml")` ändern zu `getResource("main.fxml")`
5. Titel auf `"eHKS – Hautkrebs-Screening"` ändern
6. Fenstergröße auf `900, 650`

In `pom.xml` ggf. den mainClass-Eintrag aktualisieren falls IntelliJ ihn nicht selbst angepasst hat.

---

## Schritt 7 – FXML mit 4 Tabs

**Datei:** `src/main/resources/de/ehks/_1_selfhautkrebs/main.fxml`

Komplettes Grundgerüst mit allen 4 Tabs. Kopiere das als Startpunkt – ergänze später Felder die fehlen:

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

---

## Schritt 8 – `MainController.java` verdrahten

**Vollständiger Controller** (jeder `fx:id` aus FXML braucht ein passendes `@FXML`-Feld):

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

---

## Schritt 12 – Testdatensatz erstellen

1. App über Maven starten: `mvn javafx:run` (oder Maven-Panel → Plugins → javafx → javafx:run)
2. Alle Tabs durchgehen und einen vollständigen Datensatz eingeben
3. **Datensatz speichern** klicken
4. Tab **Daten** → **CSV exportieren** → Datei aufbewahren
5. Diese CSV-Datei gehört zur Abgabe

---

## Schritt 13 – Dokumentation

**Pflichtteile für die Abgabe:**

### 13.1 TDD-Beschreibung (1 Seite)
Beschreibe für `Patient`:
- Welche 5 Tests hast du geschrieben?
- Was prüft jeder Test genau?
- Warum diese Tests? (Beispiel: Konstruktor-Test prüft dass keine Felder verloren gehen)
- Was wäre passiert wenn du den Code zuerst geschrieben hättest und dann erst getestet?

### 13.2 Klassendiagramm
- Tool: [draw.io](https://draw.io) – kostenlos, online, kein Account nötig
- Zeige alle Klassen aus `model/` und `service/` als Kästen
- Pfeile: `ScreeningData` → hat → `Patient` und `Arztpraxis`
- Pfeile: `MainController` → nutzt → `DataManager`, `FileHandler`
- Aufbau: Klassenname oben, Felder Mitte, Methoden unten

### 13.3 Anwendungsleitfaden (1-2 Seiten)
- Screenshot vom Hauptfenster
- Schritt-für-Schritt: "So legen Sie einen neuen Datensatz an"
- Screenshot mit der Plausibilitätswarnung
- Erklärung der Befundstatus-Farben

---

## Häufige Fehler & Lösungen

| Fehler | Lösung |
|--------|--------|
| `Location is required` beim Start | FXML liegt im falschen Ordner. Muss in `src/main/resources/de/ehks/_1_selfhautkrebs/` |
| `NullPointerException` in `initialize()` | `fx:id` im FXML stimmt nicht mit `@FXML`-Name im Controller überein |
| `IllegalAccessException ... module ... does not "opens" ...` | In `module-info.java` das betroffene Paket mit `opens` öffnen |
| Tabelle bleibt leer | `setCellValueFactory()` für die Spalten vergessen |
| Tests laufen nicht | Annotation `@Test` muss von `org.junit.jupiter.api.Test` kommen, NICHT von `org.junit.Test` |
| ComboBox leer | `setItems()` in `initialize()` vergessen |

---

## Nützliche Links

| Thema | Link |
|-------|------|
| JavaFX Tutorials | https://jenkov.com/tutorials/javafx/index.html |
| JavaFX TableView | https://jenkov.com/tutorials/javafx/tableview.html |
| JUnit 5 | https://www.baeldung.com/junit-5 |
| LocalDate Java | https://www.baeldung.com/java-localdate |
| BufferedReader / CSV | https://www.baeldung.com/java-bufferedreader |
| draw.io Klassendiagramm | https://drawio-app.com/blog/uml-class-diagrams |
| Bro Code – Java OOP | YouTube-Suche: "Bro Code Java Full Course" |
| GenuineCoder – JavaFX | YouTube-Suche: "GenuineCoder JavaFX" |

---

## Abgabe-Checkliste

- [ ] `Befundstatus.java` (fertig ✓)
- [ ] `Arztpraxis.java` (fertig ✓)
- [ ] `Patient.java`
- [ ] `ScreeningData.java` mit `validate()` + `getBefundstatus()`
- [ ] `PatientTest.java` mit mind. 5 grünen Tests
- [ ] `FileHandler.java` – CSV Import/Export funktioniert
- [ ] `DataManager.java`
- [ ] `MainApp.java` + `main.fxml` – App startet
- [ ] Tab Arztpraxis funktioniert
- [ ] Tab Patient funktioniert
- [ ] Tab Untersuchung funktioniert
- [ ] Plausibilität: Felder werden disabled/enabled
- [ ] Plausibilität: Speichern zeigt Warnung bei Fehler
- [ ] Tab Daten: TableView zeigt Datensätze
- [ ] Export CSV + Import CSV testen
- [ ] Mindestens 1 Optional umgesetzt (z.B. Farbkennzeichnung)
- [ ] Testdatensatz als CSV abgegeben
- [ ] TDD-Beschreibung geschrieben
- [ ] Klassendiagramm erstellt
- [ ] Anwendungsleitfaden geschrieben
- [ ] Präsentation vorbereitet (Folien + Live-Demo)
- [ ] Code kommentiert
- [ ] Abgabe spätestens am **20.05.2026**

Viel Erfolg! 💪
