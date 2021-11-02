import bankprojekt.verarbeitung.*;

import java.time.LocalDate;

/**
 * Testprogramm für Banken
 * @author Doro
 *
 */
public class Bankentest {

	/**
	 * Testprogramm für Banken
	 * @param args wird nicht benutzt
	 */
	public static void main(String[] args) {
		Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));

		Bank Sparkasse = new Bank(1005);

		System.out.println("Konten werden erstellt");
		System.out.println("Girokontonummer: "+ Sparkasse.girokontoErstellen(ich));
		System.out.println("Sparbuchkontonummer: "+Sparkasse.sparbuchErstellen(ich));
		System.out.println("------------------------------------------------------");

		System.out.println("bisher vergebene Kontonummern:");
		System.out.println(Sparkasse.getAlleKontonummern());
		System.out.println("------------------------------------------------------");

		System.out.println("Alle Konten der Bank");
		System.out.println(Sparkasse.getAlleKonten());


	}
}
