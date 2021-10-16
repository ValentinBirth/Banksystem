package bankprojekt.verarbeitung;

import java.time.LocalDate;

/**
 * ein Sparbuch
 * @author Birth
 *
 */
public class Sparbuch extends Konto {
	/**
	 * Zinssatz, mit dem das Sparbuch verzinst wird. 0,03 entspricht 3%
	 */
	private double zinssatz;
	
	/**
	 * Monatlich erlaubter Gesamtbetrag für Abhebungen
	 */
	public static final double ABHEBESUMMEEURO = 2000;
	
	/**
	 * Betrag, der im aktuellen Monat bereits abgehoben wurde
	 */
	private double bereitsAbgehoben = 0;
	/**
	 * Monat und Jahr der letzten Abhebung
	 */
	private LocalDate zeitpunkt = LocalDate.now();
	
	/**
	* ein Standard-Sparbuch
	*/
	public Sparbuch() {
		zinssatz = 0.03;
	}

	/**
	* ein Standard-Sparbuch, das inhaber gehört und die angegebene Kontonummer hat
	* @param inhaber der Kontoinhaber
	* @param kontonummer die Wunsch-Kontonummer
	* @throws IllegalArgumentException wenn inhaber null ist
	*/
	public Sparbuch(Kunde inhaber, long kontonummer) {
		super(inhaber, kontonummer);
		zinssatz = 0.03;
	}

	@Override
	public void waehrungsWechsel(Waehrung neu) {
		if (neu != getAktuelleWaehrung()){
			double kontostandInEuro = getAktuelleWaehrung().waehrungInEuroUmrechnen(getKontostand());
			double bereitsAbgehobenInEuro = getAktuelleWaehrung().waehrungInEuroUmrechnen(bereitsAbgehoben);
			setKontostand(neu.euroInWaehrungUmrechnen(kontostandInEuro));
			bereitsAbgehoben = neu.euroInWaehrungUmrechnen(bereitsAbgehobenInEuro);
			setAktuelleWaehrung(neu);
		}
	}

	@Override
	public boolean abheben (double betrag) throws GesperrtException{
		if (betrag < 0 || Double.isNaN(betrag)) {
			throw new IllegalArgumentException("Betrag ungültig");
		}
		if(this.isGesperrt())
		{
			throw new GesperrtException(this.getKontonummer());
		}
		LocalDate heute = LocalDate.now();
		if(heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear())
		{
			this.bereitsAbgehoben = 0;
		}
		if (getKontostand() - betrag >= 0.50 && 
				 bereitsAbgehoben + betrag <= getAktuelleWaehrung().euroInWaehrungUmrechnen(Sparbuch.ABHEBESUMMEEURO))
		{
			setKontostand(getKontostand() - betrag);
			bereitsAbgehoben += betrag;
			this.zeitpunkt = LocalDate.now();
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean abheben(double betrag, Waehrung w) throws GesperrtException {
		if (betrag < 0 || Double.isNaN(betrag)) {
			throw new IllegalArgumentException("Betrag ungültig");
		}
		double betragInEuro = w.waehrungInEuroUmrechnen(betrag);
		double betragInKontoWaehrung = getAktuelleWaehrung().euroInWaehrungUmrechnen(betragInEuro);
		return abheben(betragInKontoWaehrung);
	}

	@Override
	public String toString()
	{
		String ausgabe = "-- SPARBUCH --" + System.lineSeparator() +
				super.toString()
				+ "Zinssatz: " + this.zinssatz * 100 +"%" + System.lineSeparator();
		return ausgabe;
	}

}
