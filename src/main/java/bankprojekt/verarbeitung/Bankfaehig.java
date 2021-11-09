package bankprojekt.verarbeitung;

import java.util.List;

/**
 * Interface für Banken
 */
public interface Bankfaehig {

     /**
      * Getter für die Bankleitzahl
      *
      * @return bankleitzahl
      */
     long getBankleitzahl();

     /**
      * Konstruktor für ein Girokonto
      *
      * @param inhaber Inhaber des Konto
      * @return Kontonummer des erstellen Kontos
      * @throws IllegalArgumentException Wenn Inhaber nicht existiert
      */
     long girokontoErstellen(Kunde inhaber)throws IllegalArgumentException;

     /**
      * Konsturktor für ein Sparbuch
      *
      * @param inhaber Inhaber des Konto
      * @return Kontonummer des erstellten Kontos
      * @throws IllegalArgumentException Wenn Inhaber nicht existiert
      */
     long sparbuchErstellen(Kunde inhaber)throws IllegalArgumentException;

     /**
      * Gibt alle Konten im lesbaren Format wieder
      *
      * @return Auflistung aller Konten im lesbaren Format
      */
     String getAlleKonten();

     /**
      * Gibt eine Liste aller Kontonummern wieder, welche durch die Bank vergeben wurden
      *
      * @return Liste aller Kontonummern
      */
     List<Long> getAlleKontonummern();

     /**
      * Methode um Geld von einem Konto abzuheben
      *
      * @param von    Kontonummer von dem Konto von dem man Geld abheben will
      * @param betrag Abzuhebender Betrag
      * @return True, wenn erfolgreich, false, wenn nicht
      * @throws GesperrtException wenn das Konto gesperrt ist
      */
     boolean geldAbheben(long von, double betrag) throws GesperrtException;

     /**
      * Methode um Geld auf ein Konto einzuzahlen
      *
      * @param auf    Kontonummer von dem Konto auf das Geld eingezahlt werden soll
      * @param betrag Einzuzahlender Betrag
      * @return True, wenn erfolgreich, false, wenn nicht
      */
     boolean geldEinzahlen(long auf, double betrag);

     /**
      *Löscht ein Konto mit gegebener Kontonummer
      *
      * @param nummer Kontonummer des zulöschenden Kontos
      * @return True, wenn erfolgreich, false, wenn nicht
      */
     boolean kontoLoeschen(long nummer);

     /**
      * Getter für den Kontostand
      *
      * @param nummer Kontonummer des Kontos
      * @return Kontostand des Kontos
      * @throws IllegalArgumentException wenn die Kontonummer nicht existiert
      */
     double getKontostand(long nummer) throws IllegalArgumentException;

     /**
      * Methode zum überweisen von Geld auf ein anderes Konto
      *
      * @param vonKontoNummer   Kontonummer des Konto von dem das Geld kommt
      * @param nachKontoNummer  Kontonummer des Konto auf dem das Geld kommen soll
      * @param betrag           Betrag der überwiesen werden soll
      * @param verwendungszweck Verwendungszweck der Überweisung
      * @return True, wenn erfolgreich, false, wenn nicht
      * @throws GesperrtException Wenn das Konto welches überweisen will gesperrt ist
      */
     boolean geldUeberweisen(long vonKontoNummer, long nachKontoNummer, double betrag, String verwendungszweck) throws GesperrtException;



}
