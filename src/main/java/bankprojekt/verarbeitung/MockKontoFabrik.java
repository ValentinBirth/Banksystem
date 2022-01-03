package bankprojekt.verarbeitung;

import org.mockito.Mockito;

public class MockKontoFabrik extends Kontofabrik{

    @Override
    public long kontoErzeugen(Kunde inhaber, Bank bank) {
        long kontonummer = bank.groesteKontoNummer;
        bank.konten.put(kontonummer, Mockito.mock(Konto.class));
        bank.kontoNummern.add(kontonummer);
        bank.groesteKontoNummer+=1;
        return kontonummer;
    }
}
