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
