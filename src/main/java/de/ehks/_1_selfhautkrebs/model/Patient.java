package de.ehks._1_selfhautkrebs.model;
import java.time.LocalDate;
/**
 * Stammdaten einer Arztpraxis.
 * Felder lt. KBV-Vorgabe für eHKS (PFLICHT 1).
 */
public class Patient {

    // ============ Konstanten ============
    public static final int MITGLIED              = 1;
    public static final int FAMILIENANGEHOERIGER  = 3;
    public static final int RENTNER               = 5;

    public static final String MAENNLICH  = "M";
    public static final String WEIBLICH   = "F";
    public static final String UNBESTIMMT = "UN";

    // ============ Felder ============
    private String name;
    private String vorname;
    private LocalDate geburtsdatum;
    private String versichertenId;
    private String strasse;
    private String hausnummer;
    private String plz;
    private String ort;
    private int versichertenart;
    private String geschlecht;

    // ============ Konstruktoren ============
    public Patient() {
        // Leerer Konstruktor – wird beim CSV-Import gebraucht
    }

    public Patient(String name, String vorname, LocalDate geburtsdatum,
                   String versichertenId, String strasse, String hausnummer,
                   String plz, String ort, int versichertenart, String geschlecht) {
        this.name = name;
        this.vorname = vorname;
        this.geburtsdatum = geburtsdatum;
        this.versichertenId = versichertenId;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.plz = plz;
        this.ort = ort;
        this.versichertenart = versichertenart;
        this.geschlecht = geschlecht;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getVersichertenId() {
        return versichertenId;
    }

    public void setVersichertenId(String versichertenId) {
        this.versichertenId = versichertenId;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getVersichertenart() {
        return versichertenart;
    }

    public void setVersichertenart(int versichertenart) {
        this.versichertenart = versichertenart;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", vorname='" + vorname + '\'' +
                ", geburtsdatum=" + geburtsdatum +
                ", versichertenId='" + versichertenId + '\'' +
                ", strasse='" + strasse + '\'' +
                ", hausnummer='" + hausnummer + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                ", versichertenart=" + versichertenart +
                ", geschlecht='" + geschlecht + '\'' +
                '}';
    }
}
