package bankprojekt.verarbeitung;

public class GirokontoFabrik extends Kontofabrik{
    @Override
    public Konto kontoErzeugen(Kunde inhaber, long kontonummer) {
        return new Girokonto(inhaber,kontonummer,500);
    }
}
