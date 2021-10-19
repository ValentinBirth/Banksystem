package bankprojekt.verarbeitung;

/**
 * stellt ein allgemeines Konto dar
 *@author Birth
 */
public abstract class Konto implements Comparable<Konto>
{
	/** 
	 * der Kontoinhaber
	 */
	private Kunde inhaber;

	/**
	 * die Kontonummer
	 */
	private final long nummer;

	/**
	 * der aktuelle Kontostand
	 */
	private double kontostand;

	/**
	 * Art der Währung mit der das Konto arbeitet
	 */
	private Waehrung waehrung;

	/**
	 * Wenn das Konto gesperrt ist (gesperrt = true), können keine Aktionen daran mehr vorgenommen werden,
	 * die zum Schaden des Kontoinhabers wären (abheben, Inhaberwechsel)
	 */
	private boolean gesperrt;

	/**
	 * setzt den Kontoinhaber
	 * @param kinh   neuer Kontoinhaber
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn kinh null ist
	 */
	public final void setInhaber(Kunde kinh) throws GesperrtException{
		if (kinh == null) {
			throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
		}
		if(this.gesperrt) {
			throw new GesperrtException(this.nummer);
		}
		this.inhaber = kinh;

	}

	/**
	 * liefert den Kontoinhaber zurück
	 * @return   der Inhaber
	 */
	public final Kunde getInhaber() {
		return this.inhaber;
	}

	/**
	 * liefert die Kontonummer zurück
	 * @return   long
	 */
	public final long getKontonummer() {
		return nummer;
	}

	/**
	 * setzt den aktuellen Kontostand
	 * @param kontostand neuer Kontostand
	 */
	protected void setKontostand(double kontostand) {
		this.kontostand = kontostand;
	}

	/**
	 * liefert den aktuellen Kontostand
	 * @return   double
	 */
	public final double getKontostand() {
		return kontostand;
	}

	/**
	 * TODO Überarbeiten -> Kontostand muss mit geändert werden
	 * setzt die Wärung
	 * @param w Waehrung
	 */
	public void setAktuelleWaehrung(Waehrung w){
		waehrung =w;
	}

	/**
	 * liefert die aktuelle Währung des Konto zurück
	 * @return Waehrung
	 */
	public Waehrung getAktuelleWaehrung(){
		return waehrung;
	}

	/**
	 * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
	 */
	public final void sperren() {
		this.gesperrt = true;
	}

	/**
	 * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
	 */
	public final void entsperren() {
		this.gesperrt = false;
	}

	/**
	 * liefert zurück, ob das Konto gesperrt ist oder nicht
	 * @return true, wenn das Konto gesperrt ist
	 */
	public final boolean isGesperrt() {
		return gesperrt;
	}

	/**
	 * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
	 * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
	 */
	public final String getGesperrtText()
	{
		if (this.gesperrt)
		{
			return "GESPERRT";
		}
		else
		{
			return "";
		}
	}

	/**
	 * setzt alle Eigenschaften des Kontos auf Standardwerte
	 */
	public Konto() {
		this(Kunde.MUSTERMANN, 1234567);
	}

	/**
	 * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
	 * der anfängliche Kontostand wird auf 0 gesetzt.
	 *
	 * @param inhaber der Inhaber
	 * @param kontonummer die gewünschte Kontonummer
	 * @throws IllegalArgumentException wenn der Inhaber null
	 */
	public Konto(Kunde inhaber, long kontonummer) {
		this.waehrung = Waehrung.EUR;
		if(inhaber == null) {
			throw new IllegalArgumentException("Inhaber darf nicht null sein!");
		}
		this.inhaber = inhaber;
		this.nummer = kontonummer;
		this.kontostand = 0;
		this.gesperrt = false;
	}
	
	/**
	 * Erhöht den Kontostand um den eingezahlten Betrag.
	 *
	 * @param betrag double
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 */
	public void einzahlen(double betrag) {
		if (betrag < 0 || Double.isNaN(betrag)) {
			throw new IllegalArgumentException("Falscher Betrag");
		}
		setKontostand(getKontostand() + betrag);
	}

	/**
	 * Erhöht den Kontostand um den eingezahlten Betrag einer spezifischen Währung.
	 * // TODO Überarbeiten -> einzahlen(double) nutzen, analog wie abheben
	 * @param betrag double
	 * @param w Waehrung
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 */
	public void einzahlen(double betrag, Waehrung w) {
		if (betrag < 0 || Double.isNaN(betrag)) {
			throw new IllegalArgumentException("Falscher Betrag");
		}
		if(getAktuelleWaehrung() == w) {
			setKontostand(getKontostand() + betrag);
		}else{
			double betragInEuro = w.waehrungInEuroUmrechnen(betrag);
			switch (getAktuelleWaehrung()){

				case EUR -> {
					setKontostand(getKontostand() + betragInEuro);
				}
				case BGN -> {
					setKontostand(getKontostand() * Waehrung.BGN.euroInWaehrungUmrechnen(betragInEuro));
				}
				case LTL -> {
					setKontostand(getKontostand() * Waehrung.LTL.euroInWaehrungUmrechnen(betragInEuro));
				}
				case KM -> {
					setKontostand(getKontostand() * Waehrung.KM.euroInWaehrungUmrechnen(betragInEuro));
				}
			}
		}
	}

	/**
	 * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist.
	 *
	 * @param betrag double
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 * @return true, wenn die Abhebung geklappt hat,
	 * 		   false, wenn sie abgelehnt wurde
	 */
	public abstract boolean abheben(double betrag)
			throws GesperrtException;

	/**
	 * Mit dieser Methode wird der geforderte Betrag in einer spezifischen Währung
	 * vom Konto abgehoben, wenn es nicht gesperrt ist.
	 * // TODO Methode non abstract machen (allgemeingültig Implementieren)
	 *
	 * @param betrag double
	 * @param w Waehrung
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 * @return true, wenn die Abhebung geklappt hat,
	 * 		   false, wenn sie abgelehnt wurde
	 */
	public abstract boolean abheben(double betrag, Waehrung w)
			throws GesperrtException;

	/**
	 * Wechselt die Währung des des aktuellen Kontos
	 * // TODO allgemeingültig implementieren und per super von Kindklassen aufrufen lassen
	 * @param neu Waherung
	 */
	public abstract void waehrungsWechsel(Waehrung neu);
	
	/**
	 * Gibt eine Zeichenkettendarstellung der Kontodaten zurück.
	 */
	@Override
	public String toString() {
		String ausgabe;
		ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
				+ System.getProperty("line.separator");
		ausgabe += "Inhaber: " + this.inhaber;
		ausgabe += "Aktueller Kontostand: " + getKontostandFormatiert() + " ";
		ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
		return ausgabe;
	}
	
	/**
	 * liefert die ordentlich formatierte Kontonummer
	 * @return auf 10 Stellen formatierte Kontonummer
	 */
	public String getKontonummerFormatiert()
	{
		return String.format("%10d", this.nummer);
	}
	
	/**
	 * liefert den ordentlich formatierten Kontostand
	 * @return formatierter Kontostand mit 2 Nachkommastellen und Währungssymbol €
	 */
	public String getKontostandFormatiert()
	{
		// TODO ändern auf this.waehrung.name()
		switch (getAktuelleWaehrung()) {

			case BGN -> {
				return String.format("%10.2f BGN" , this.getKontostand());
			}
			case LTL -> {
				return String.format("%10.2f LTL" , this.getKontostand());
			}
			case KM -> {
				return String.format("%10.2f KM" , this.getKontostand());
			}
			default -> {
				return String.format("%10.2f Euro" , this.getKontostand());
			}
		}
	}
	
	/**
	 * Vergleich von this mit other; Zwei Konten gelten als gleich,
	 * wen sie die gleiche Kontonummer haben
	 * @param other das Vergleichskonto
	 * @return true, wenn beide Konten die gleiche Nummer haben
	 */
	@Override
	public boolean equals(Object other)
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		return this.nummer == ((Konto) other).nummer;
	}
	
	@Override
	public int hashCode()
	{
		return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
	}

	@Override
	public int compareTo(Konto other)
	{
		if(other.getKontonummer() > this.getKontonummer())
			return -1;
		if(other.getKontonummer() < this.getKontonummer())
			return 1;
		return 0;
	}
}
