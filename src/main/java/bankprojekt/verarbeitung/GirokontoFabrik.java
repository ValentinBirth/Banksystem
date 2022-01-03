package bankprojekt.verarbeitung;

public class GirokontoFabrik extends Kontofabrik{
    @Override
    public long kontoErzeugen(Kunde inhaber, Bank bank) {
        long kontonummer = bank.groesteKontoNummer;
        bank.konten.put(kontonummer,new Girokonto(inhaber,kontonummer,500));
        bank.kontoNummern.add(kontonummer);
        bank.groesteKontoNummer+=1;
        return kontonummer;
    }
}
