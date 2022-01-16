import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class BankenTest {

    /**
     * Auskommentierte Tests sind nicht möglich so lange abheben Final ist.
     */

    Konto k1;
    Konto k2;
    Girokonto gk1;
    Girokonto gk2;
    Sparbuch sb1;
    Bank bank;
    Kunde kunde1;
    Kunde kunde2;

    @BeforeEach
    public void setUp(){
        bank = new Bank(111);
        k1 = Mockito.mock(Konto.class);
        k2 = Mockito.mock(Konto.class);
        gk1 = Mockito.mock(Girokonto.class);
        gk2 = Mockito.mock(Girokonto.class);
        sb1 = Mockito.mock(Sparbuch.class);
        kunde1 = Mockito.mock(Kunde.class);
        kunde2 = Mockito.mock(Kunde.class);
    }

    @AfterEach
    public void tearDown(){
        k1 = null;
        k2 = null;
        gk1 = null;
        gk2 = null;
        sb1 = null;
        bank = null;
        kunde1 = null;
        kunde2 = null;
    }
    /**
    @Test
    public void geldAbhebenEinzelTest() throws GesperrtException {

        long kontonummer1 = bank.mockEinfuegen(k1);
        bank.mockEinfuegen(gk1);
        Mockito.when(k1.abheben(100)).thenReturn(true);
        bank.geldAbheben(kontonummer1,100);
        Mockito.verify(k1,Mockito.times(1)).abheben(ArgumentMatchers.anyDouble());
        Mockito.verifyNoInteractions(gk1);
    }**/
    /**
    @Test
    public void geldAbhebenMehrfachTest() throws GesperrtException {

        long kontonummer1 = bank.mockEinfuegen(k1);
        long kontonummer2 = bank.mockEinfuegen(sb1);
        Mockito.when(k1.abheben(100)).thenReturn(true);
        Mockito.when(k2.abheben(100)).thenReturn(true);
        bank.geldAbheben(kontonummer1,100);
        bank.geldAbheben(kontonummer2,100);
        Mockito.verify(k1,Mockito.times(1)).abheben(ArgumentMatchers.anyDouble());
        Mockito.verify(sb1,Mockito.times(1)).abheben(ArgumentMatchers.anyDouble());
    }**/

    @Test
    public void geldEinzahlenEinzelTest() throws GesperrtException {
        long kontonummergk1 = bank.kontoErstellen(new MockKontoFabrik(gk1), kunde1);
        bank.kontoErstellen(new MockKontoFabrik(gk2), kunde1);
        bank.geldEinzahlen(kontonummergk1,100);
        Mockito.verify(gk1,Mockito.times(1)).einzahlen(ArgumentMatchers.anyDouble());
        Mockito.verifyNoInteractions(gk2);
    }

    @Test
    public void geldEinzahlenMehrfachTest() throws GesperrtException {

        long kontonummer1 = bank.kontoErstellen(new MockKontoFabrik(sb1), kunde1);
        long kontonummer2 = bank.kontoErstellen(new MockKontoFabrik(k2), kunde1);
        bank.geldEinzahlen(kontonummer1,100);
        bank.geldEinzahlen(kontonummer2,100);
        Mockito.verify(k2,Mockito.times(1)).einzahlen(ArgumentMatchers.anyDouble());
        Mockito.verify(sb1,Mockito.times(1)).einzahlen(ArgumentMatchers.anyDouble());
    }
    /**
    @Test
    public void KontoGesperrtTest() throws GesperrtException {

        long kontonummer1 = bank.kontoErstellen(new MockKontoFabrik(k1), kunde1);
        bank.kontoErstellen(new MockKontoFabrik(k2), kunde1);
        Mockito.when(k1.abheben(ArgumentMatchers.anyDouble())).thenThrow(GesperrtException.class);
        assertThrows(GesperrtException.class, () -> bank.geldAbheben(kontonummer1,100));
        Mockito.verifyNoInteractions(k2);
    }**/
    /**
    @Test
    public void DispoUberzogenTest() throws GesperrtException {

        long kontonummer1 = bank.kontoErstellen(new MockKontoFabrik(gk1), kunde1);
    bank.kontoErstellen(new MockKontoFabrik(gk2), kunde1);
        Mockito.when(gk1.abheben(ArgumentMatchers.anyDouble())).thenReturn(false);
        assertFalse(bank.geldAbheben(kontonummer1,100000));
        Mockito.verifyNoInteractions(gk2);
    }**/

    @Test
    public void KontoLoeschen(){

        long kontonummer1 = bank.kontoErstellen(new MockKontoFabrik(k1), kunde1);
        long kontonummer2 = bank.kontoErstellen(new MockKontoFabrik(k2), kunde1);
        assertTrue(bank.kontoLoeschen(kontonummer1));
        assertEquals(kontonummer2,bank.getAlleKontonummern().get(0));
        Mockito.verifyNoInteractions(k1);
        Mockito.verifyNoInteractions(k2);
    }

    @Test
    public void ueberweisenTest() throws GesperrtException {

        gk1.setInhaber(kunde1);
        gk2.setInhaber(kunde2);
        long kontonummer1 = bank.kontoErstellen(new MockKontoFabrik(gk1), kunde1);
        long kontonummer2 = bank.kontoErstellen(new MockKontoFabrik(gk2), kunde1);
        Mockito.when(kunde1.getName()).thenReturn("Kunde1");
        Mockito.when(kunde2.getName()).thenReturn("Kunde2");
        Mockito.when(gk1.ueberweisungAbsenden(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString())).thenReturn(true);
        bank.geldUeberweisen(kontonummer1,kontonummer2,100,"Test");
        Mockito.verify(gk1).ueberweisungAbsenden(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString());
        Mockito.verify(gk2).ueberweisungEmpfangen(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString());
    }

    @Test
    void testClone() {
        Bank sparkasse = new Bank(111);
        Kunde bob = new Kunde();
        long bobnr = sparkasse.kontoErstellen(new GirokontoFabrik(),bob);
        Bank billigeKopie = sparkasse.clone();

        assertNotEquals(sparkasse,billigeKopie);
        assertEquals(sparkasse.getBankleitzahl(), billigeKopie.getBankleitzahl());
        assertEquals(sparkasse.getAlleKontonummern(),billigeKopie.getAlleKontonummern());
        assertEquals(sparkasse.getKontonummernLuecken(),billigeKopie.getKontonummernLuecken());
        //assertEquals(sparkasse.getAlleKonten(),billigeKopie.getAlleKonten());
        //assertEquals(sparkasse.getKundenGeburtstage(),billigeKopie.getKundenGeburtstage());
        sparkasse.geldEinzahlen(bobnr,100);
        assertNotEquals(sparkasse.getKontostand(bobnr),billigeKopie.getKontostand(bobnr));
        assertNotEquals(sparkasse.getKundenMitVollemKonto(50),billigeKopie.getKundenMitVollemKonto(50));
    }
}
