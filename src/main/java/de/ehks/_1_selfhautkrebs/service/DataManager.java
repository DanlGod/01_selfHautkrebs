package de.ehks._1_selfhautkrebs.service;

import de.ehks._1_selfhautkrebs.model.ScreeningData;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet die Liste aller ScreeningData-Datensätze zur Laufzeit.
 */
public class DataManager {

    private final List<ScreeningData> alle = new ArrayList<>();

    /** Fügt einen neuen Datensatz hinzu. */
    public void hinzufuegen(ScreeningData sd) {
        alle.add(sd);
    }

    /** Gibt eine Kopie der aktuellen Liste zurück (Schutz vor Außenänderung). */
    public List<ScreeningData> getAlle() {
        return new ArrayList<>(alle);
    }

    /** Ersetzt die komplette Liste – z.B. nach einem CSV-Import. */
    public void setAlleErsetzt(List<ScreeningData> neu) {
        alle.clear();
        alle.addAll(neu);
    }

    /** Optional 6: Filter nach Nachname (case-insensitive, Teiltreffer). */
    public List<ScreeningData> filterNachNachname(String suchbegriff) {
        return alle.stream()
                .filter(sd -> sd.getPatient().getName()
                        .toLowerCase()
                        .contains(suchbegriff.toLowerCase()))
                .toList();
    }
}