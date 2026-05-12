package de.ehks._1_selfhautkrebs.model;

/**
 * Stammdaten einer Arztpraxis.
 * Felder lt. KBV-Vorgabe für eHKS (PFLICHT 1).
 */
public class Arztpraxis {

    private String betriebsstaettennummer;
    private String lebenslangArztNummer;   // LANR
    private String arztname;

    /** Leerer Konstruktor – wird z.B. beim Import gebraucht. */
    public Arztpraxis() {
    }

    /** Konstruktor mit allen Feldern. */
    public Arztpraxis(String betriebsstaettennummer, String lebenslangArztNummer, String arztname) {
        this.betriebsstaettennummer = betriebsstaettennummer;
        this.lebenslangArztNummer = lebenslangArztNummer;
        this.arztname = arztname;
    }

    // --- Getter ---
    public String getBetriebsstaettennummer() { return betriebsstaettennummer; }
    public String getLebenslangArztNummer()   { return lebenslangArztNummer; }
    public String getArztname()                { return arztname; }

    // --- Setter ---
    public void setBetriebsstaettennummer(String betriebsstaettennummer) {
        this.betriebsstaettennummer = betriebsstaettennummer;
    }
    public void setLebenslangArztNummer(String lebenslangArztNummer) {
        this.lebenslangArztNummer = lebenslangArztNummer;
    }
    public void setArztname(String arztname) {
        this.arztname = arztname;
    }

    @Override
    public String toString() {
        return arztname + " (BSN: " + betriebsstaettennummer + ", LANR: " + lebenslangArztNummer + ")";
    }
}
