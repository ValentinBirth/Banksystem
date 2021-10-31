import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GirokontoTest {

    Girokonto gk;

    @BeforeEach
    void setUp() {
        gk = new Girokonto();
        gk.entsperren();
    }

    @org.junit.jupiter.api.Test
    void abhebenEinzelgkTest() throws GesperrtException {
        gk.einzahlen(10000);
        assertTrue(gk.abheben(2000));
        assertEquals(8000.0, gk.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachgkTest() throws GesperrtException {
        gk.einzahlen(10000);
        assertTrue(gk.abheben(1000));
        assertTrue(gk.abheben(1000));
        assertEquals(8000.0, gk.getKontostand());
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
    void abhebenEinzelFremdwaehrunggkTest() throws GesperrtException {
        gk.einzahlen(10000);
        assertTrue(gk.abheben(2000, Waehrung.BGN));
        assertEquals(8977.42, gk.getKontostand());
    }

    @org.junit.jupiter.api.Test
    void abhebenMehrfachFremdwaehrunggkTest() throws GesperrtException {
        gk.einzahlen(10000);
        assertTrue(gk.abheben(1000, Waehrung.BGN));
        assertTrue(gk.abheben(1000, Waehrung.BGN));
        assertEquals(8977.42, gk.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void abhebenFalscherBetraggkTest() throws GesperrtException {
        assertThrows(IllegalArgumentException.class, () -> gk.abheben(-1000, Waehrung.BGN));
        assertThrows(IllegalArgumentException.class, () -> gk.abheben(Double.NaN, Waehrung.BGN));
    }

    @org.junit.jupiter.api.Test
    void gesperrtgkTest() throws GesperrtException {
        gk.sperren();
        assertThrows(GesperrtException.class, () -> gk.abheben(1000));
    }

    @org.junit.jupiter.api.Test
    void wahrungsWechselEinzelgkTest() throws GesperrtException {
        gk.einzahlen(10000);
        gk.waehrungsWechsel(Waehrung.BGN);
        assertEquals(Waehrung.BGN, gk.getAktuelleWaehrung());
        assertEquals(19558.3, gk.getKontostand(),0.001);
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
    void einzahlenFremwaehrungEinzelgkTest() throws GesperrtException {
        gk.einzahlen(10000,Waehrung.LTL);
        assertEquals(2896.2001, gk.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void einzahlenFremwaehrungMehrfachgkTest() throws GesperrtException {
        gk.einzahlen(10000,Waehrung.LTL);
        gk.einzahlen(10000,Waehrung.KM);
        assertEquals(8009.12, gk.getKontostand(),0.001);
    }

    @org.junit.jupiter.api.Test
    void waehrungInEuroTest() throws GesperrtException {
        assertEquals(0.51,Waehrung.BGN.waehrungInEuroUmrechnen(1),0.001);
    }

    @org.junit.jupiter.api.Test
    void euroInWaehrungTest() throws GesperrtException {
        assertEquals(1.96,Waehrung.BGN.euroInWaehrungUmrechnen(1),0.001);
    }

    @org.junit.jupiter.api.Test
    void wertEinesEuroKMTest() throws GesperrtException {
        assertEquals(1.95583,Waehrung.KM.getWertEinesEuro());
    }

    @org.junit.jupiter.api.Test
    void wertEinesEuroLTLTest() throws GesperrtException {
        assertEquals(3.4528,Waehrung.LTL.getWertEinesEuro());
    }

    @org.junit.jupiter.api.Test
    void wertEinesEuroEURTest() throws GesperrtException {
        assertEquals(1,Waehrung.EUR.getWertEinesEuro());
    }

    @org.junit.jupiter.api.Test
    void wertEinesEuroBGNTest() throws GesperrtException {
        assertEquals(1.95583,Waehrung.BGN.getWertEinesEuro());
    }

    @Test
    void abhebenTest() throws GesperrtException {
        gk.abheben(100);
        assertEquals(-100.0, gk.getKontostand());
    }
    @Test
    void einzahlenTest() throws GesperrtException {
        gk.abheben(100);
        gk.einzahlen(500);
        assertEquals(400.0, gk.getKontostand());
    }
    @Test
    void mehrfachAbhebenTest()throws GesperrtException{
        gk.abheben(100);
        gk.einzahlen(500);
        gk.abheben(100);
        gk.abheben(100);
        assertEquals(200.0, gk.getKontostand());
    }
    @Test
    void ueberDispoAbhebenTest()throws GesperrtException{
        gk.abheben(100);
        gk.einzahlen(500);
        gk.abheben(100);
        gk.abheben(100);
        assertFalse(gk.abheben(1100));
        assertEquals(200, gk.getKontostand());
    }

    @Test
    void setDispoNegativTest(){
        gk.entsperren();
        assertThrows(IllegalArgumentException.class, () -> gk.setDispo(-9));
    }

    @Test
    void entsperrenTet(){
        gk.entsperren();
        assertFalse(gk.isGesperrt());
    }

    @Test
    void setDispoNaNTest(){
        gk.entsperren();
        assertThrows(IllegalArgumentException.class, () -> gk.setDispo(Double.NaN));
    }
}