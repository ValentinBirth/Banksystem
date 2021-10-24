import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestKlasse{

    Girokonto gk;
    Sparbuch sb;

    @BeforeEach
    void aufbauDerTests(){
        gk = new Girokonto();
        sb = new Sparbuch();
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
    void mehrfachAbheben()throws GesperrtException{
        gk.abheben(100);
        gk.einzahlen(500);
        gk.abheben(100);
        gk.abheben(100);
        assertEquals(200.0, gk.getKontostand());
    }
    @Test
    void ueberDispoAbheben()throws GesperrtException{
        gk.abheben(100);
        gk.einzahlen(500);
        gk.abheben(100);
        gk.abheben(100);
        assertFalse(gk.abheben(1100));
        assertEquals(200, gk.getKontostand());
    }

    @Test
    void entsperren(){
        gk.entsperren();
        assertFalse(gk.isGesperrt());
    }

    @Test
    void waehrungswechel(){
        sb.einzahlen(1000);
        sb.waehrungsWechsel(Waehrung.LTL);
        assertEquals(3452.8, sb.getKontostand(),0.001);

    }
    @Test
    void aktuelleWaehrung(){
        sb.waehrungsWechsel(Waehrung.LTL);
       assertEquals(Waehrung.LTL, sb.getAktuelleWaehrung());
    }
    @Test
    void abhebenInAndererWaehrung() throws GesperrtException {
        sb.abheben(1000, Waehrung.EUR);
        assertEquals(0.0, sb.getKontostand());

    }
    @Test
    void waherungswechselErneut(){
        sb.einzahlen(1000);
        sb.waehrungsWechsel(Waehrung.LTL);
        sb.waehrungsWechsel(Waehrung.BGN);
        assertEquals(1955.83 ,sb.getKontostand(),0.001);
    }
    @Test
    void einzahlenInAndererWaehrungErneut(){
        sb.einzahlen(1000, Waehrung.BGN);
        sb.einzahlen(1000, Waehrung.BGN);
        assertEquals(1022.583762392437, sb.getKontostand(),0.001);
    }
    @Test
    void abhebenInAndererWaehrungErneut() throws GesperrtException {
        sb.einzahlen(1000);
        sb.einzahlen(1000, Waehrung.BGN);
        sb.einzahlen(1000, Waehrung.BGN);
        sb.abheben(1000, Waehrung.BGN);
        sb.abheben(1000, Waehrung.BGN);
        assertEquals(1000.0, sb.getKontostand(),0.001);
    }


}
