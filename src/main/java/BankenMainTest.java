import bankprojekt.verarbeitung.*;

import java.time.LocalDate;

/**
 * Testprogramm für Banken
 * @author Doro
 *
 */
public class BankenMainTest {

	/**
	 * Testprogramm für Banken
	 * @param args wird nicht benutzt
	 */
	public static void main(String[] args) throws GesperrtException {
		Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));

		Bank Sparkasse = new Bank(1005);

		System.out.println("Konten werden erstellt");
		System.out.println("Girokontonummer: "+ Sparkasse.girokontoErstellen(ich));
		System.out.println("Girokontonummer: "+ Sparkasse.girokontoErstellen(ich));
		System.out.println("Sparbuchkontonummer: "+Sparkasse.sparbuchErstellen(ich));
		System.out.println("------------------------------------------------------");

		System.out.println("bisher vergebene Kontonummern:");
		System.out.println(Sparkasse.getAlleKontonummern());
		System.out.println("------------------------------------------------------");

		System.out.println("Alle Konten der Bank");
		System.out.println(Sparkasse.getAlleKonten());
		System.out.println("------------------------------------------------------");

		System.out.println(Sparkasse.geldEinzahlen(0,1000));
		System.out.println(Sparkasse.geldUeberweisen(0,1,500,"Test"));
		System.out.println(Sparkasse.getAlleKonten());
		System.out.println("------------------------------------------------------");
		System.out.println(Sparkasse.getKundenGeburtstage());
	}
}
