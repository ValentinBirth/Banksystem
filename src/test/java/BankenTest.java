import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class BankenTest {

    @Test
    public void geldAbhebenEinzelTest() throws GesperrtException {

        Konto k1 = Mockito.mock(Konto.class);
        Konto k2 = Mockito.mock(Konto.class);
        Bank bank = new Bank(111);
        long kontonummer1 = bank.mockEinfuegen(k1);
        long kontonummer2 = bank.mockEinfuegen(k2);
        Mockito.when(k1.abheben(100)).thenReturn(true);
        bank.geldAbheben(kontonummer1,100);
        Mockito.verify(k1,Mockito.times(1)).abheben(ArgumentMatchers.anyDouble());
        Mockito.verifyNoInteractions(k2);
    }

    @Test
    public void geldAbhebenMehrfachTest() throws GesperrtException {

        Konto k1 = Mockito.mock(Konto.class);
        Konto k2 = Mockito.mock(Konto.class);
        Bank bank = new Bank(111);
        long kontonummer1 = bank.mockEinfuegen(k1);
        long kontonummer2 = bank.mockEinfuegen(k2);
        Mockito.when(k1.abheben(100)).thenReturn(true);
        Mockito.when(k2.abheben(100)).thenReturn(true);
        bank.geldAbheben(kontonummer1,100);
        bank.geldAbheben(kontonummer2,100);
        Mockito.verify(k1,Mockito.times(1)).abheben(ArgumentMatchers.anyDouble());
        Mockito.verify(k2,Mockito.times(1)).abheben(ArgumentMatchers.anyDouble());
    }

    @Test
    public void geldEinzahlenEinzelTest() throws GesperrtException {

        Konto k1 = Mockito.mock(Konto.class);
        Konto k2 = Mockito.mock(Konto.class);
        Bank bank = new Bank(111);
        long kontonummer1 = bank.mockEinfuegen(k1);
        long kontonummer2 = bank.mockEinfuegen(k2);
        bank.geldEinzahlen(kontonummer1,100);
        Mockito.verify(k1,Mockito.times(1)).einzahlen(ArgumentMatchers.anyDouble());
        Mockito.verifyNoInteractions(k2);
    }

    @Test
    public void geldEinzahlenMehrfachTest() throws GesperrtException {

        Konto k1 = Mockito.mock(Konto.class);
        Konto k2 = Mockito.mock(Konto.class);
        Bank bank = new Bank(111);
        long kontonummer1 = bank.mockEinfuegen(k1);
        long kontonummer2 = bank.mockEinfuegen(k2);
        bank.geldEinzahlen(kontonummer1,100);
        bank.geldEinzahlen(kontonummer2,100);
        Mockito.verify(k1,Mockito.times(1)).einzahlen(ArgumentMatchers.anyDouble());
        Mockito.verify(k2,Mockito.times(1)).einzahlen(ArgumentMatchers.anyDouble());
    }

    @Test
    public void KontoGesperrtTest() throws GesperrtException {
        Konto k1 = Mockito.mock(Konto.class);
        Konto k2 = Mockito.mock(Konto.class);
        Bank bank = new Bank(111);
        long kontonummer1 = bank.mockEinfuegen(k1);
        long kontonummer2 = bank.mockEinfuegen(k2);
        Mockito.when(k1.abheben(ArgumentMatchers.anyDouble())).thenThrow(GesperrtException.class);
        assertThrows(GesperrtException.class, () -> bank.geldAbheben(kontonummer1,100));
        Mockito.verifyNoInteractions(k2);
    }

    @Test
    public void DispoUberzogenTest() throws GesperrtException {
        Girokonto k1 = Mockito.mock(Girokonto.class);
        Girokonto k2 = Mockito.mock(Girokonto.class);
        Bank bank = new Bank(111);
        long kontonummer1 = bank.mockEinfuegen(k1);
        long kontonummer2 = bank.mockEinfuegen(k2);
        Mockito.when(k1.abheben(ArgumentMatchers.anyDouble())).thenReturn(false);
        assertFalse(bank.geldAbheben(kontonummer1,100000));
        Mockito.verifyNoInteractions(k2);
    }

    @Test
    public void KontoLoeschen(){
        Girokonto k1 = Mockito.mock(Girokonto.class);
        Girokonto k2 = Mockito.mock(Girokonto.class);
        Bank bank = new Bank(111);
        long kontonummer1 = bank.mockEinfuegen(k1);
        long kontonummer2 = bank.mockEinfuegen(k2);
        assertTrue(bank.kontoLoeschen(kontonummer1));
        assertEquals(kontonummer2,bank.getAlleKontonummern().get(0));
        Mockito.verifyNoInteractions(k1);
        Mockito.verifyNoInteractions(k2);
    }

    @Test
    public void ueberweisenTest() throws GesperrtException {

        Kunde kunde1 = Mockito.mock(Kunde.class);
        Kunde kunde2 = Mockito.mock(Kunde.class);
        Girokonto k1 = Mockito.mock(Girokonto.class);
        Girokonto k2 = Mockito.mock(Girokonto.class);
        k1.setInhaber(kunde1);
        k2.setInhaber(kunde2);
        Bank bank = new Bank(111);
        long kontonummer1 = bank.mockEinfuegen(k1);
        long kontonummer2 = bank.mockEinfuegen(k2);
        Mockito.when(kunde1.getName()).thenReturn("Kunde1");
        Mockito.when(kunde2.getName()).thenReturn("Kunde2");
        bank.geldUeberweisen(kontonummer1,kontonummer2,100,"Test");
        Mockito.verify(k1,Mockito.times(1)).ueberweisungAbsenden(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString());
        Mockito.verify(k2,Mockito.times(1)).ueberweisungEmpfangen(ArgumentMatchers.anyDouble(),ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString());
    }
}
