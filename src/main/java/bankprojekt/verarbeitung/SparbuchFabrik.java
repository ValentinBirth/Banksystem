package bankprojekt.verarbeitung;

public class SparbuchFabrik extends Kontofabrik{
    @Override
    public long kontoErzeugen(Kunde inhaber, Bank bank) {
        long kontonummer = bank.groesteKontoNummer;
        bank.konten.put(kontonummer,new Sparbuch(inhaber,kontonummer));
        bank.kontoNummern.add(kontonummer);
        bank.groesteKontoNummer+=1;
        return kontonummer;
    }
}
