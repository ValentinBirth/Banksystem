package bankprojekt.verarbeitung;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * stellt ein allgemeines Konto dar
 *@author Birth
 */
public abstract class Konto implements Comparable<Konto>, Serializable {
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
	 * Aktiendepot des Kontos
	 */
	private Map<Aktie,Integer> aktienDepot;

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
		this.aktienDepot = new HashMap<>();
	}

	/**
	 * Gibt einen Kaufauftrag für eine bestimmte Aktie auf
	 * @param a Aktie welche zu Kaufen ist
	 * @param anzahl Anzahl der zu kaufenden Aktien
	 * @param hoechstpreis Höchstpreis bis wann man kaufen will
	 * @return gesamtpreis des kaufes
	 */
	public Future<Double> kaufauftrag(Aktie a, int anzahl, double hoechstpreis){
		Callable<Double> aktieKaufen = new Callable<Double>() {
			@Override
			public Double call(){
				Lock aktienLock = a.getAktienLock();
				Condition kursVeraendert = a.getKursVerändert();
				aktienLock.lock();
				while (a.getKurs() > hoechstpreis) {
					try {
						kursVeraendert.await();
					} catch (InterruptedException e) {
					}
				}
				double preis = anzahl*a.getKurs();
				if (kontostand >= preis) {
					kontostand -= preis;
					if (aktienDepot.containsKey(a)){
						aktienDepot.put(a,aktienDepot.get(a)+anzahl);
					}else{
						aktienDepot.put(a,anzahl);
					}
				}else {
					preis = 0;
				}
				aktienLock.unlock();
				return preis;
			}
	};
		ScheduledExecutorService aktienkauf = Executors.newSingleThreadScheduledExecutor();
		return aktienkauf.schedule(aktieKaufen,0,TimeUnit.SECONDS);
	}

	/**
	 * Gibt einen Verkaufsauftrag für alle Aktien auf
	 * @param wkn Wertpapiernummer der zu verkaufenden Aktie
	 * @param minimalpreis minimalpreis zu dem die Aktie verkauft werden soll
	 * @return gesamt erlös des Verkaufs
	 */
	public Future<Double> verkaufauftrag (String wkn, double minimalpreis){
		Aktie dummyaktie = null;
		for(Aktie aktie: aktienDepot.keySet()){
			if(aktie.getWertpapierNummer().equals(wkn)){
				dummyaktie=aktie;
			}
		}
		Aktie a = dummyaktie;
		Callable<Double> aktieVerkaufen = new Callable<Double>() {
			@Override
			public Double call() {
				if (a!=null) {
					Lock aktienLock = a.getAktienLock();
					Condition kursVeraendert = a.getKursVerändert();
					aktienLock.lock();
					while (a.getKurs() < minimalpreis) {
						try {
							kursVeraendert.await();
						} catch (InterruptedException e) {
						}
					}
					double preis = aktienDepot.get(a)*a.getKurs();
					kontostand+=preis;
					aktienDepot.remove(a);
					aktienLock.unlock();
					return preis;
				}else{
					return 0D;
				}
			}
		};
		ScheduledExecutorService aktienverkauf = Executors.newSingleThreadScheduledExecutor();
		return aktienverkauf.schedule(aktieVerkaufen,0,TimeUnit.SECONDS);
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
	 *
	 * @param betrag double
	 * @param w Waehrung
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 */
	public void einzahlen(double betrag, Waehrung w) {
		einzahlen(w.waehrungInWaehrungUmrechnen(betrag,getAktuelleWaehrung()));
	}

	/**
	 * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist.
	 *
	 * @param betrag double
	 * @return true, wenn die Abhebung geklappt hat,
	 * 		   false, wenn sie abgelehnt wurde
	 */
	public final boolean abheben(double betrag) throws GesperrtException {
		if(!isGesperrt()){
			if(!Double.isNaN(betrag)&&betrag>0) {
				if (gesonderteBedingungen(betrag)) {
					setKontostand(getKontostand() - betrag);
					return true;
				}
			}else{
				throw new IllegalArgumentException("Betrag ungültig");
			}
		}else{
			throw new GesperrtException(getKontonummer());
		}
		return false;
	}

	/**
	 * prüft Kontospezisiche Bedingungen zum Abheben
	 * @param betrag double
	 * @return true, wenn bedingen erfüllt sind, false, wenn nicht
	 */
	protected abstract boolean gesonderteBedingungen(double betrag);

	/**
	 * Mit dieser Methode wird der geforderte Betrag in einer spezifischen Währung
	 * vom Konto abgehoben, wenn es nicht gesperrt ist.
	 *
	 * @param betrag double
	 * @param w Waehrung
	 * @return true, wenn die Abhebung geklappt hat,
	 * 		   false, wenn sie abgelehnt wurde
	 */
	public boolean abheben(double betrag, Waehrung w) throws GesperrtException {
		return abheben(w.waehrungInWaehrungUmrechnen(betrag,getAktuelleWaehrung()));
	}

	/**
	 * Wechselt die Währung des des aktuellen Kontos
	 *
	 * @param neu Waherung
	 */
	public void waehrungsWechsel(Waehrung neu){
		setKontostand(getAktuelleWaehrung().waehrungInWaehrungUmrechnen(getKontostand(),neu));
		waehrung = neu;
	}

	
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
		return String.format("%10.2f "+this.waehrung.name() , this.getKontostand());
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

	public void speichern(Statement stmt) throws Exception
	{
		ResultSet rs=null ;
		try
		{
			rs = stmt.executeQuery("SELECT * FROM konto WHERE kontonr="+this.nummer);
			if (rs.next())
			{
				rs.updateDouble("kontostand", this.kontostand);
				rs.updateBoolean("gesperrt", this.gesperrt);
				rs.updateRow();
			}
			else
			{
				rs.moveToInsertRow();
				rs.updateDouble("kontostand", this.kontostand);
				rs.updateBoolean("gesperrt", this.gesperrt);
				rs.updateLong("kontonr", this.nummer);
				int nummer = this.inhaber.speichern();
				rs.updateInt("kunde", nummer);
				rs.insertRow();
			}

		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null) rs.close();
			} catch (Exception ex)
			{
				throw ex;
			}
		}
	}
}
