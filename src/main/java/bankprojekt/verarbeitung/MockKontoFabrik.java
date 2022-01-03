package bankprojekt.verarbeitung;

public class MockKontoFabrik extends Kontofabrik{
    Konto konto;

    /**
     * Konstruktor für ein MockKonten Fabrik
     * @param konto das gemockte Konto, dieses wird in der konto Erzeugen Methode returned
     */
    public  MockKontoFabrik(Konto konto){
        this.konto = konto;
    }
    @Override
    public Konto kontoErzeugen(Kunde inhaber, long kontonummer) {
        return konto;
    }

    /**
     * setter für das MockKonto um ggf später die Fabrik wieder verwenden zu können
     * @param konto Mockkonto
     */
    public void setKonto(Konto konto) {
        this.konto = konto;
    }

}
