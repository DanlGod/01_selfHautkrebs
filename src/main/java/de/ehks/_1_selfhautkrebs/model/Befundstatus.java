package de.ehks._1_selfhautkrebs.model;

/**
 * Status eines Untersuchungsbefundes – wird für die Farbkennzeichnung (Optional 5) genutzt.
 *
 * UNAUFFAELLIG         → grün  (keine Auffälligkeiten)
 * AUFFAELLIG           → gelb  (eine Auffälligkeit)
 * MEHRFACH_AUFFAELLIG  → rot   (mehrere Auffälligkeiten)
 */
public enum Befundstatus {
    UNAUFFAELLIG,
    AUFFAELLIG,
    MEHRFACH_AUFFAELLIG
}
