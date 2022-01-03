package bankprojekt.verarbeitung;

public abstract class Kontofabrik {
    /**
     * Konstruktor für ein Konto
     *
     * @param inhaber Inhaber des Konto
     * @param kontonummer Kontonummer des Kontos
     * @return Erzeugtes Konto
     */
    public abstract Konto kontoErzeugen(Kunde inhaber, long kontonummer);
}
