import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;
import bankprojekt.verarbeitung.Waehrung;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class SparbuchTest {

    Sparbuch a;

    @BeforeEach
    void setUp() {
       a = new Sparbuch(Kunde.MUSTERMANN,1);
       a.entsperren();
    }

    @org.junit.jupiter.api.Test
    void abhebenEinzelTest() throws GesperrtException {
        a.einzahlen(10000);
        assertTrue(a.abheben(2000));
        assertEquals(8000.0,a.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachTest() throws GesperrtException {
        a.einzahlen(10000);
        assertTrue(a.abheben(1000));
        assertTrue(a.abheben(1000));
        assertEquals(8000.0,a.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachUeberschrittenTest() throws GesperrtException {
        a.einzahlen(10000);
        assertTrue(a.abheben(1000));
        assertTrue(a.abheben(1000));
        assertFalse(a.abheben(2000));
        assertEquals(8000.0,a.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenEinzelFremdwaehrungTest() throws GesperrtException {
        a.einzahlen(10000);
        assertTrue(a.abheben(2000, Waehrung.BGN));
        assertEquals(8977.416237607564,a.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachFremdwaehrungTest() throws GesperrtException {
        a.einzahlen(10000);
        assertTrue(a.abheben(1000, Waehrung.BGN));
        assertTrue(a.abheben(1000, Waehrung.BGN));
        assertEquals(8977.416237607564,a.getKontostand(),0.001);
    }
}