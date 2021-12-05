import java.time.LocalDate;

import bankprojekt.verarbeitung.*;

/**
 * Testprogramm für Konten
 * @author Doro
 *
 */
public class KontenMainTest {

	/**
	 * Testprogramm für Konten
	 * @param args wird nicht benutzt
	 */
	public static void main(String[] args) {
		Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));

		//Konto k = new Konto(ich, 47345);
		
		Girokonto meinGiro = new Girokonto(ich, 1234, 1000.0);
		meinGiro.einzahlen(50);
		System.out.println(meinGiro);
		
		Sparbuch meinSpar = new Sparbuch(ich, 9876);
		meinSpar.einzahlen(50);
		try
		{
			boolean hatGeklappt = meinSpar.abheben(70);
			System.out.println("Abhebung hat geklappt: " + hatGeklappt);
			System.out.println(meinSpar);
		}
		catch (GesperrtException e)
		{
			System.out.println("Zugriff auf gesperrtes Konto - Polizei rufen!");
		}
		
		Konto k = meinGiro; //Zuweisungskompatibilität
		Girokonto k2 = (Girokonto) k;
		k.waehrungsWechsel(Waehrung.BGN);
		System.out.println(k);
		
		int a = 100;
		int b = a;
		a +=50;
		System.out.println(b);
		
		Konto k1 = new Girokonto();
		Konto k3 = k1;
		k1.einzahlen(100);
		System.out.println(k3.getKontostand());
		
		Kontoart art;
		art = Kontoart.GIROKONTO;
		art = Kontoart.valueOf("SPARBUCH");
		art = Kontoart.FESTGELDKONTO;
		System.out.println(art.toString() + " " + art.name() + " " + art.ordinal());
		System.out.println(art.getWerbebotschaft());
		
		Kontoart[] alle = Kontoart.values();
		for(int i=0; i<alle.length; i++)
		{
			System.out.println(alle[i] + ": " + alle[i].getWerbebotschaft());
		}

		Waehrung [] W = Waehrung.values();
		for (Waehrung waehrung : W) {
			System.out.println(waehrung + ": " + waehrung.getWertEinesEuro());
		}
		
		
	}

}
