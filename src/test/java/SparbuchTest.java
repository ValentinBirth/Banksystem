import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Sparbuch;
import bankprojekt.verarbeitung.Waehrung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SparbuchTest {

    Sparbuch sb;

    @BeforeEach
    void setUp() {
        sb = new Sparbuch();
        sb.entsperren();
    }

    @Test
    void abhebenEinzelsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        assertTrue(sb.abheben(2000));
        assertEquals(8000.0, sb.getKontostand());
    }

    @Test
    void abhebenMehrfachsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        assertTrue(sb.abheben(1000));
        assertTrue(sb.abheben(1000));
        assertEquals(8000.0, sb.getKontostand());
    }

    @Test
    void abhebenMehrfachUeberschrittensbTest() throws GesperrtException {
        sb.einzahlen(10000);
        assertTrue(sb.abheben(1000));
        assertTrue(sb.abheben(1000));
        assertFalse(sb.abheben(2000));
        assertEquals(8000.0, sb.getKontostand());
    }

    @Test
    void abhebenEinzelFremdwaehrungsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        assertTrue(sb.abheben(2000, Waehrung.BGN));
        assertEquals(8977.42, sb.getKontostand());
    }

    @Test
    void abhebenMehrfachFremdwaehrungsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        assertTrue(sb.abheben(1000, Waehrung.BGN));
        assertTrue(sb.abheben(1000, Waehrung.BGN));
        assertEquals(8977.42, sb.getKontostand(),0.001);
    }

    @Test
    void abhebenFalscherBetragsbTest() throws GesperrtException {
        assertThrows(IllegalArgumentException.class, () -> sb.abheben(-1000, Waehrung.BGN));
        assertThrows(IllegalArgumentException.class, () -> sb.abheben(Double.NaN, Waehrung.BGN));
    }

    @Test
    void gesperrtsbTest() throws GesperrtException {
        sb.sperren();
        assertThrows(GesperrtException.class, () -> sb.abheben(1000));
    }

    @Test
    void wahrungsWechselEinzelsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        sb.waehrungsWechsel(Waehrung.BGN);
        assertEquals(Waehrung.BGN, sb.getAktuelleWaehrung());
        assertEquals(19558.3, sb.getKontostand(),0.001);
    }

    @Test
    void wahrungsWechselMehrfachsbTest() throws GesperrtException {
        sb.einzahlen(10000);
        sb.waehrungsWechsel(Waehrung.BGN);
        assertEquals(Waehrung.BGN, sb.getAktuelleWaehrung());
        assertEquals(19558.3, sb.getKontostand(),0.001);
        sb.waehrungsWechsel(Waehrung.LTL);
        assertEquals(Waehrung.LTL, sb.getAktuelleWaehrung());
        assertEquals(34528, sb.getKontostand(),0.001); // Tolle Methode um gratis Geld zu bekommen ^^
    }

    @Test
    void einzahlenFremwaehrungEinzelsbTest() throws GesperrtException {
        sb.einzahlen(10000,Waehrung.LTL);
        assertEquals(2896.2001, sb.getKontostand(),0.001);
    }

    @Test
    void einzahlenFremwaehrungMehrfachsbTest() throws GesperrtException {
        sb.einzahlen(10000,Waehrung.LTL);
        sb.einzahlen(10000,Waehrung.KM);
        assertEquals(8009.12, sb.getKontostand(),0.001);
    }

    @Test
    void waehrungInEuroTest() throws GesperrtException {
        assertEquals(0.51,Waehrung.BGN.waehrungInEuroUmrechnen(1),0.001);
    }

    @Test
    void euroInWaehrungTest() throws GesperrtException {
        assertEquals(1.96,Waehrung.BGN.euroInWaehrungUmrechnen(1),0.001);
    }

    @Test
    void wertEinesEuroKMTest() throws GesperrtException {
        assertEquals(1.95583,Waehrung.KM.getWertEinesEuro());
    }

    @Test
    void wertEinesEuroLTLTest() throws GesperrtException {
        assertEquals(3.4528,Waehrung.LTL.getWertEinesEuro());
    }

    @Test
    void wertEinesEuroEURTest() throws GesperrtException {
        assertEquals(1,Waehrung.EUR.getWertEinesEuro());
    }

    @Test
    void wertEinesEuroBGNTest() throws GesperrtException {
        assertEquals(1.95583,Waehrung.BGN.getWertEinesEuro());
    }
    @Test
    void entsperrenTest(){
        sb.entsperren();
        assertFalse(sb.isGesperrt());
    }
}