package de.ehks._1_selfhautkrebs.service;

import de.ehks._1_selfhautkrebs.model.Arztpraxis;
import de.ehks._1_selfhautkrebs.model.Patient;
import de.ehks._1_selfhautkrebs.model.ScreeningData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV-Import und -Export der Stammdaten (PFLICHT 2).
 */
public class FileHandler {

    private static final String TRENNER = ";";
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final String STAMM_HEADER =
            "name;vorname;geburtsdatum;versichertenId;strasse;hausnummer;plz;ort;" +
                    "versichertenart;geschlecht;bsn;lanr;arztname";

    /** Schreibt alle übergebenen Datensätze als CSV in die angegebene Datei. */
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

    /** Liest eine CSV-Datei und gibt die enthaltenen Datensätze als Liste zurück. */
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

    // ===== Hilfsmethoden =====

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