package bankprojekt.verarbeitung;

public abstract class Kontofabrik {
    /**
     * Konstruktor für ein Konto
     *
     * @param inhaber Inhaber des Konto
     * @param bank Aufrufende Bank
     * @return Kontonummer des erstellen Kontos
     * @throws IllegalArgumentException Wenn Inhaber nicht existiert
     */
    public abstract long kontoErzeugen(Kunde inhaber, Bank bank);
}
