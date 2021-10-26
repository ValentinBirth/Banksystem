package bankprojekt.verarbeitung;

/**
 * alle Wärhungen mit der die Bank umgeht
 * @author Birth
 */
public enum Waehrung {
    EUR(1),
    BGN(1.95583),
    LTL(3.4528),
    KM(1.95583);

    private final double wertEinesEuro;

    /**
     * Gibt den Wert eines Euros für die jeweilige Währung aus
     *
     * @return Wert eines Euro in der jeweiligen Währung
     */
    public double getWertEinesEuro() {
        return wertEinesEuro;
    }

    Waehrung(double wertEinesEuro) {
        this.wertEinesEuro = wertEinesEuro;
    }

    /**
     * Rechnet den in Euro angegebenen Betrag in die jeweilige Währung um
     *
     * @param betrag Betrag in Euro
     * @return Umgerechneter Betrag in der jeweiligen Währung
     */
    public double euroInWaehrungUmrechnen(double betrag){
        return this.wertEinesEuro*betrag;
    }

    /**
     * Rechnet den in der this-Währung angegebenen Betrag in Euro um
     *
     * @param betrag Betrag in der jeweiligen Währung
     * @return Umgerechneter Betrag in Euro
     */
    public double waehrungInEuroUmrechnen(double betrag){
        return betrag/this.wertEinesEuro;
    }

    public double waehrungInWaehrungUmrechnen(double betrag, Waehrung neu){
        return neu.euroInWaehrungUmrechnen(this.waehrungInEuroUmrechnen(betrag));
    }
}
