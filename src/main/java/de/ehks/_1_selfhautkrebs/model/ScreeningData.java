package de.ehks._1_selfhautkrebs.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Ein kompletter Screening-Datensatz (PFLICHT 1).
 * Verknüpft Patient + Arztpraxis + Untersuchungsergebnisse.
 */
public class ScreeningData {

    // --- Referenzen auf andere Objekte ---
    private Patient patient;
    private Arztpraxis arztpraxis;

    // --- Untersuchungsdaten ---
    private LocalDate untersuchungsdatum;

    // 1.1 Verdachtsdiagnosen
    private boolean verdachtsdiagnoseND;        // 1.1.1 Oberbegriff
    private boolean malignesMelanom;            // 1.1.2
    private boolean basalzellkarzinom;          // 1.1.3
    private boolean spinozellulares;            // 1.1.4
    private boolean sonstigeVerdachtsdiagnose;  // 1.1.5

    // 1.2 / 1.3
    private boolean gesundheitsuntersuchungDurchgefuehrt;
    private boolean ueberweisungErfolgt;
    private String  ueberweisungDermatologeName;

    // 2.4 Biopsie
    private boolean   biopsieEntnommen;
    private int       anzahlBiopsien;    // 0–99
    private LocalDate biopsiedatum;

    // 2.5 Histopathologie
    private boolean histopathologieDurchgefuehrt;
    private String  histopathologieBefund;

    // --- Konstruktor ---
    public ScreeningData() {
        // Leerer Konstruktor reicht – Felder werden per Setter befüllt
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Arztpraxis getArztpraxis() {
        return arztpraxis;
    }

    public void setArztpraxis(Arztpraxis arztpraxis) {
        this.arztpraxis = arztpraxis;
    }

    public LocalDate getUntersuchungsdatum() {
        return untersuchungsdatum;
    }

    public void setUntersuchungsdatum(LocalDate untersuchungsdatum) {
        this.untersuchungsdatum = untersuchungsdatum;
    }

    public boolean isVerdachtsdiagnoseND() {
        return verdachtsdiagnoseND;
    }

    public void setVerdachtsdiagnoseND(boolean verdachtsdiagnoseND) {
        this.verdachtsdiagnoseND = verdachtsdiagnoseND;
    }

    public boolean isMalignesMelanom() {
        return malignesMelanom;
    }

    public void setMalignesMelanom(boolean malignesMelanom) {
        this.malignesMelanom = malignesMelanom;
    }

    public boolean isBasalzellkarzinom() {
        return basalzellkarzinom;
    }

    public void setBasalzellkarzinom(boolean basalzellkarzinom) {
        this.basalzellkarzinom = basalzellkarzinom;
    }

    public boolean isSpinozellulares() {
        return spinozellulares;
    }

    public void setSpinozellulares(boolean spinozellulares) {
        this.spinozellulares = spinozellulares;
    }

    public boolean isSonstigeVerdachtsdiagnose() {
        return sonstigeVerdachtsdiagnose;
    }

    public void setSonstigeVerdachtsdiagnose(boolean sonstigeVerdachtsdiagnose) {
        this.sonstigeVerdachtsdiagnose = sonstigeVerdachtsdiagnose;
    }

    public boolean isGesundheitsuntersuchungDurchgefuehrt() {
        return gesundheitsuntersuchungDurchgefuehrt;
    }

    public void setGesundheitsuntersuchungDurchgefuehrt(boolean gesundheitsuntersuchungDurchgefuehrt) {
        this.gesundheitsuntersuchungDurchgefuehrt = gesundheitsuntersuchungDurchgefuehrt;
    }

    public boolean isUeberweisungErfolgt() {
        return ueberweisungErfolgt;
    }

    public void setUeberweisungErfolgt(boolean ueberweisungErfolgt) {
        this.ueberweisungErfolgt = ueberweisungErfolgt;
    }

    public String getUeberweisungDermatologeName() {
        return ueberweisungDermatologeName;
    }

    public void setUeberweisungDermatologeName(String ueberweisungDermatologeName) {
        this.ueberweisungDermatologeName = ueberweisungDermatologeName;
    }

    public boolean isBiopsieEntnommen() {
        return biopsieEntnommen;
    }

    public void setBiopsieEntnommen(boolean biopsieEntnommen) {
        this.biopsieEntnommen = biopsieEntnommen;
    }

    public int getAnzahlBiopsien() {
        return anzahlBiopsien;
    }

    public void setAnzahlBiopsien(int anzahlBiopsien) {
        this.anzahlBiopsien = anzahlBiopsien;
    }

    public LocalDate getBiopsiedatum() {
        return biopsiedatum;
    }

    public void setBiopsiedatum(LocalDate biopsiedatum) {
        this.biopsiedatum = biopsiedatum;
    }

    public boolean isHistopathologieDurchgefuehrt() {
        return histopathologieDurchgefuehrt;
    }

    public void setHistopathologieDurchgefuehrt(boolean histopathologieDurchgefuehrt) {
        this.histopathologieDurchgefuehrt = histopathologieDurchgefuehrt;
    }

    public String getHistopathologieBefund() {
        return histopathologieBefund;
    }

    public void setHistopathologieBefund(String histopathologieBefund) {
        this.histopathologieBefund = histopathologieBefund;
    }

    /**
     * Plausibilitätsprüfung (PFLICHT 3).
     * Gibt eine Liste mit Fehlertexten zurück – leere Liste = alles ok.
     */
    public List<String> validate() {
        List<String> fehler = new ArrayList<>();

        // Regel 1: Wenn Verdachtsdiagnose vorhanden → mind. eine konkrete Diagnose
        if (verdachtsdiagnoseND
                && !malignesMelanom && !basalzellkarzinom
                && !spinozellulares && !sonstigeVerdachtsdiagnose) {
            fehler.add("Bitte mindestens eine konkrete Verdachtsdiagnose angeben (1.1.2–1.1.5).");
        }

        // Regel 2: Biopsie=Ja → Anzahl 0–99
        if (biopsieEntnommen && (anzahlBiopsien < 0 || anzahlBiopsien > 99)) {
            fehler.add("Anzahl Biopsien muss zwischen 0 und 99 liegen.");
        }

        // Regel 3: Biopsie=Nein → Anzahl darf nicht > 0 sein
        if (!biopsieEntnommen && anzahlBiopsien > 0) {
            fehler.add("Anzahl Biopsien darf nicht gesetzt sein, wenn keine Biopsie entnommen.");
        }

        return fehler;
    }

    /**
     * Ermittelt den Befundstatus für die Farbkennzeichnung (Optional 5).
     * Je mehr Auffälligkeiten, desto "roter".
     */
    public Befundstatus getBefundstatus() {
        int auffaellig = 0;
        if (verdachtsdiagnoseND)          auffaellig++;
        if (biopsieEntnommen)             auffaellig++;
        if (histopathologieDurchgefuehrt) auffaellig++;

        if (auffaellig == 0) return Befundstatus.UNAUFFAELLIG;
        if (auffaellig == 1) return Befundstatus.AUFFAELLIG;
        return Befundstatus.MEHRFACH_AUFFAELLIG;
    }
}