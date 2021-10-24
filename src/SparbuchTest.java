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

    @org.junit.jupiter.api.Test
    void wahrungsWechselEinzelTest() throws GesperrtException {
        a.einzahlen(10000);
        a.waehrungsWechsel(Waehrung.BGN);
        assertEquals(Waehrung.BGN,a.getAktuelleWaehrung());
        assertEquals(19558.3,a.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void wahrungsWechselMehrfachTest() throws GesperrtException {
        a.einzahlen(10000);
        a.waehrungsWechsel(Waehrung.BGN);
        assertEquals(Waehrung.BGN,a.getAktuelleWaehrung());
        assertEquals(19558.3,a.getKontostand(),0.001);
        a.waehrungsWechsel(Waehrung.LTL);
        assertEquals(Waehrung.LTL,a.getAktuelleWaehrung());
        assertEquals(34528,a.getKontostand(),0.001); // Tolle Methode um gratis Geld zu bekommen ^^
    }

    @org.junit.jupiter.api.Test
    void einzahlenFremwaehrungEinzelTest() throws GesperrtException {
        a.einzahlen(10000,Waehrung.LTL);
        assertEquals(2896.2001,a.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void einzahlenFremwaehrungMehrfachTest() throws GesperrtException {
        a.einzahlen(10000,Waehrung.LTL);
        a.einzahlen(10000,Waehrung.KM);
        assertEquals(8009.1189,a.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void waehrungInEuroTest() throws GesperrtException {
        assertEquals(0.51129,Waehrung.BGN.waehrungInEuroUmrechnen(1),0.001);
    }

    @org.junit.jupiter.api.Test
    void euroInWaehrungTest() throws GesperrtException {
        assertEquals(1.95583,Waehrung.BGN.euroInWaehrungUmrechnen(1),0.001);
    }
}