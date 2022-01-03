package bankprojekt.verarbeitung;

public class SparbuchFabrik extends Kontofabrik{
    @Override
    public Konto kontoErzeugen(Kunde inhaber, long kontonummer) {
        return new Sparbuch(inhaber,kontonummer);
    }
}
