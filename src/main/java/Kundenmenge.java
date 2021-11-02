import java.time.LocalDate;
import java.util.*;

import bankprojekt.verarbeitung.Kunde;

/**
 * verwaltet eine Menge von Kunden
 * @author Doro
 *
 */
public class Kundenmenge {

	/**
	 * erstellt eine Menge von Kunden und loescht die unnoetigen
	 * wieder
	 * @param args
	 */
	public static void main(String[] args) {
		Kunde anna = new Kunde("Anna", "Anna", "hier", LocalDate.now());
		Kunde berta = new Kunde("Berta", "Berta", "hier", LocalDate.now());
		Kunde chris = new Kunde("Chris", "Chris", "hier", LocalDate.now());
		Kunde anton = new Kunde("Anton", "Anton", "hier", LocalDate.now());
		Kunde adalbert = new Kunde("Bert", "Adal", "hier", LocalDate.now());

		class KundenGeburtstagVergleicher implements Comparator<Kunde>{

			@Override
			public int compare(Kunde o1, Kunde o2) {
				return o1.getGeburtstag().compareTo(o2.getGeburtstag());
			}
		}
		Comparator<Kunde> kundenGeburtstagVergleicherVergleicher = new KundenGeburtstagVergleicher();

		Set<Kunde> kunden = new TreeSet<>();

		kunden.add(anna);
		kunden.add(berta);
		kunden.add(chris);
		kunden.add(anton);
		kunden.add(adalbert);

		for (Kunde k:kunden) {
			System.out.println("----------------------");
			System.out.println(k);
		}
		Iterator<Kunde> iter = kunden.iterator();
		while (iter.hasNext()){
			Kunde element = iter.next();
			if(element.getName().startsWith("A") || element.getVorname().startsWith("A")){
				iter.remove();
			}
		}
		System.out.println("Kunden die nicht mit A beginnen:");
		for (Kunde k:kunden) {
			System.out.println("----------------------");
			System.out.println(k);
		}
		System.out.println("Kunde "+chris.getName()+ " kommt "+Collections.frequency(kunden,chris)+" mal in der Menge vor");

		Map<Long, Kunde> bank = new TreeMap<>();
		bank.put(3L,anna);
		bank.put(1L,berta);
		bank.put(6L,chris);
		bank.put(2L,anton);
		bank.put(10L,adalbert);

		System.out.println("Kundenmap:");
		for (Kunde k:bank.values()) {
			System.out.println("----------------------");
			System.out.println(k);
		}




	}

}
