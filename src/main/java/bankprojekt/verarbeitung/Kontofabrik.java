package bankprojekt.verarbeitung;

public abstract class Kontofabrik {
    /**
     * Konstruktor für ein Konto
     *
     * @param inhaber Inhaber des Konto
     * @param kontonummer Kontonummer des Kontos
     * @return Kontonummer des erstellen Kontos
     * @throws IllegalArgumentException Wenn Inhaber nicht existiert
     */
    public abstract Konto kontoErzeugen(Kunde inhaber, long kontonummer);
}
