import bankprojekt.verarbeitung.Bank;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.GirokontoFabrik;
import bankprojekt.verarbeitung.Kunde;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StreamTest {
    Bank bank;
    GirokontoFabrik girokontoFabrik;
    Long gk1,gk2,gk3,gk4,gk5;
    Kunde anna = new Kunde("Anna", "Anna", "hier", LocalDate.now());
    Kunde berta = new Kunde("Berta", "Berta", "hier", LocalDate.now());
    Kunde chris = new Kunde("Chris", "Chris", "hier", LocalDate.now());
    Kunde anton = new Kunde("Anton", "Anton", "hier", LocalDate.now());
    Kunde adalbert = new Kunde("Bert", "Adal", "hier", LocalDate.now());

    @BeforeEach
    public void setUp(){
        bank = new Bank(1);
        girokontoFabrik = new GirokontoFabrik();
        gk1 = bank.kontoErstellen(girokontoFabrik,anna);
        gk2 = bank.kontoErstellen(girokontoFabrik,berta);
        gk3 = bank.kontoErstellen(girokontoFabrik,chris);
        gk4 = bank.kontoErstellen(girokontoFabrik,anton);
        gk5 = bank.kontoErstellen(girokontoFabrik,adalbert);
    }

    @AfterEach
    public void tearDown(){
        bank = null;
        gk1 = null;
        gk2 = null;
        gk3 = null;
        gk4 = null;
        gk5 = null;
    }

    @Test
    public void pleitegeierTest() throws GesperrtException {
        bank.geldAbheben(gk1,100);
        bank.geldAbheben(gk2,100);
        bank.geldAbheben(gk3,100);
        bank.pleiteGeierSperren();
        assertThrows(GesperrtException.class,() ->bank.geldAbheben(gk1,100));
        assertThrows(GesperrtException.class,() ->bank.geldAbheben(gk2,100));
        assertThrows(GesperrtException.class,() ->bank.geldAbheben(gk3,100));
        assertTrue(bank.geldAbheben(gk4,100));
        assertTrue(bank.geldAbheben(gk5,100));
    }

    @Test
    public void getKundenMitVollemKontoTest(){
        bank.geldEinzahlen(gk1,100);
        bank.geldEinzahlen(gk2,100);
        bank.geldEinzahlen(gk3,100);
        List<Kunde> kunden = new ArrayList<>();
        kunden.add(anna);
        kunden.add(berta);
        kunden.add(chris);
        assertEquals(kunden,bank.getKundenMitVollemKonto(10));
    }

    @Test
    public void getKontonummernLueckenTest(){
        bank.kontoLoeschen(gk3);
        List<Long> freieNummern = new ArrayList<>();
        freieNummern.add(2L);
        assertEquals(freieNummern,bank.getKontonummernLuecken());
    }
}
