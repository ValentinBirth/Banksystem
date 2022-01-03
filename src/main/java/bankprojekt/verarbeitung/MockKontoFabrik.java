package bankprojekt.verarbeitung;

public class MockKontoFabrik extends Kontofabrik{
    Konto konto;

    public  MockKontoFabrik(Konto konto){
        this.konto = konto;
    }
    @Override
    public Konto kontoErzeugen(Kunde inhaber, long kontonummer) {
        return konto;
    }
}
