import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class SparbuchTest {

    Sparbuch sb;
    Girokonto gk;

    @BeforeEach
    void setUp() {
        sb = new Sparbuch();
        gk = new Girokonto();
        gk.entsperren();
        sb.entsperren();
    }

    @org.junit.jupiter.api.Test
    void abhebenEinzelsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        assertTrue(sb.abheben(2000));
        assertEquals(8000.0, sb.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenEinzelgkTest() throws GesperrtException {
        gk.einzahlen(10000);
        assertTrue(gk.abheben(2000));
        assertEquals(8000.0, gk.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        assertTrue(sb.abheben(1000));
        assertTrue(sb.abheben(1000));
        assertEquals(8000.0, sb.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachgkTest() throws GesperrtException {
        gk.einzahlen(10000);
        assertTrue(gk.abheben(1000));
        assertTrue(gk.abheben(1000));
        assertEquals(8000.0, gk.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachUeberschrittensbTest() throws GesperrtException {
        sb.einzahlen(10000);
        assertTrue(sb.abheben(1000));
        assertTrue(sb.abheben(1000));
        assertFalse(sb.abheben(2000));
        assertEquals(8000.0, sb.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachUeberschrittengkTest() throws GesperrtException {
        gk.einzahlen(10000);
        assertTrue(gk.abheben(10000));
        assertTrue(gk.abheben(500));
        assertFalse(gk.abheben(2000));
        assertEquals(-500.0, gk.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenEinzelFremdwaehrungsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        assertTrue(sb.abheben(2000, Waehrung.BGN));
        assertEquals(8977.416237607564, sb.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenEinzelFremdwaehrunggkTest() throws GesperrtException {
        gk.einzahlen(10000);
        assertTrue(gk.abheben(2000, Waehrung.BGN));
        assertEquals(8977.416237607564, gk.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachFremdwaehrungsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        assertTrue(sb.abheben(1000, Waehrung.BGN));
        assertTrue(sb.abheben(1000, Waehrung.BGN));
        assertEquals(8977.416237607564, sb.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachFremdwaehrunggkTest() throws GesperrtException {
        gk.einzahlen(10000);
        assertTrue(gk.abheben(1000, Waehrung.BGN));
        assertTrue(gk.abheben(1000, Waehrung.BGN));
        assertEquals(8977.416237607564, gk.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void wahrungsWechselEinzelsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        sb.waehrungsWechsel(Waehrung.BGN);
        assertEquals(Waehrung.BGN, sb.getAktuelleWaehrung());
        assertEquals(19558.3, sb.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void wahrungsWechselEinzelgkTest() throws GesperrtException {
        gk.einzahlen(10000);
        gk.waehrungsWechsel(Waehrung.BGN);
        assertEquals(Waehrung.BGN, gk.getAktuelleWaehrung());
        assertEquals(19558.3, gk.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void wahrungsWechselMehrfachsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        sb.waehrungsWechsel(Waehrung.BGN);
        assertEquals(Waehrung.BGN, sb.getAktuelleWaehrung());
        assertEquals(19558.3, sb.getKontostand(),0.001);
        sb.waehrungsWechsel(Waehrung.LTL);
        assertEquals(Waehrung.LTL, sb.getAktuelleWaehrung());
        assertEquals(34528, sb.getKontostand(),0.001); // Tolle Methode um gratis Geld zu bekommen ^^
    }

    @org.junit.jupiter.api.Test
    void wahrungsWechselMehrfachgkTest() throws GesperrtException {
        gk.einzahlen(10000);
        gk.waehrungsWechsel(Waehrung.BGN);
        assertEquals(Waehrung.BGN, gk.getAktuelleWaehrung());
        assertEquals(19558.3, gk.getKontostand(),0.001);
        gk.waehrungsWechsel(Waehrung.LTL);
        assertEquals(Waehrung.LTL, gk.getAktuelleWaehrung());
        assertEquals(34528, gk.getKontostand(),0.001); // Tolle Methode um gratis Geld zu bekommen ^^
    }

    @org.junit.jupiter.api.Test
    void einzahlenFremwaehrungEinzelsbTest() throws GesperrtException {
        sb.einzahlen(10000,Waehrung.LTL);
        assertEquals(2896.2001, sb.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void einzahlenFremwaehrungEinzelgkTest() throws GesperrtException {
        gk.einzahlen(10000,Waehrung.LTL);
        assertEquals(2896.2001, gk.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void einzahlenFremwaehrungMehrfachsbTest() throws GesperrtException {
        sb.einzahlen(10000,Waehrung.LTL);
        sb.einzahlen(10000,Waehrung.KM);
        assertEquals(8009.1189, sb.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void einzahlenFremwaehrungMehrfachgkTest() throws GesperrtException {
        gk.einzahlen(10000,Waehrung.LTL);
        gk.einzahlen(10000,Waehrung.KM);
        assertEquals(8009.1189, gk.getKontostand(),0.001);
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