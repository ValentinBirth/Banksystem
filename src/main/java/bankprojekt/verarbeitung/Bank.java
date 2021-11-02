package bankprojekt.verarbeitung;

import java.util.*;

public class Bank implements Bankfaehig{
    private long bankleitzahl;
    private long groesteKontoNummer = 0;
    private List<Long> kontoNummern = new LinkedList<>();
    private Map<Long, Konto> konten = new HashMap<>();

    /**
     * Konstruktor für eine Bank
     * @param bankleitzahl Eindeutige Nummer zur identifikation einer Bank
     */
    public Bank(long bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
    }

    @Override
    public long getBankleitzahl() {
        return bankleitzahl;
    }

    @Override
    public long girokontoErstellen(Kunde inhaber) throws IllegalArgumentException{
        long kontonummer = groesteKontoNummer;
        if (inhaber==null){
            throw new IllegalArgumentException("Inhaber darf nicht Null sein!");
        }
        konten.put(kontonummer,new Girokonto(inhaber,kontonummer,500));
        kontoNummern.add(kontonummer);
        groesteKontoNummer+=1;
        return kontonummer;
    }

    @Override
    public long sparbuchErstellen(Kunde inhaber) throws IllegalArgumentException{
        long kontonummer = groesteKontoNummer;
        if (inhaber==null){
            throw new IllegalArgumentException("Inhaber darf nicht Null sein!");
        }
        konten.put(kontonummer,new Sparbuch(inhaber,kontonummer));
        kontoNummern.add(kontonummer);
        groesteKontoNummer+=1;
        return kontonummer;
    }

    @Override
    public String getAlleKonten() {
        String alleKonten="";
        for (Konto k:konten.values()) {
            alleKonten = alleKonten+k;
        }
        return alleKonten;
    }

    @Override
    public List<Long> getAlleKontonummern() {
        return kontoNummern;
    }

    @Override
    public boolean geldAbheben(long von, double betrag) throws GesperrtException {
        if (konten.containsKey(von)) {
           return konten.get(von).abheben(betrag);
        }else{
            return false;
        }
    }

    @Override
    public boolean geldEinzahlen(long auf, double betrag) {
        if (konten.containsKey(auf)) {
            konten.get(auf).einzahlen(betrag);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean kontoLoeschen(long nummer) {
        if(konten.containsKey(nummer)){
            konten.remove(nummer);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public double getKontostand(long nummer) throws IllegalArgumentException {
        if (konten.containsKey(nummer)) {
            return konten.get(nummer).getKontostand();
        }else{
            throw new IllegalArgumentException("Kontonummer nicht gefunden!");
        }
    }

    @Override
    public boolean geldUeberweisen(long vonKontoNummer, long nachKontoNummer, double betrag, String verwendungszweck) {
        if (konten.containsKey(vonKontoNummer) && konten.containsKey(nachKontoNummer)){
            if(Ueberweisungsfaehig.class.isAssignableFrom(konten.get(nachKontoNummer).getClass()) && Ueberweisungsfaehig.class.isAssignableFrom(konten.get(nachKontoNummer).getClass())){
               //TODO Überweisung not implemented
            }
        }
        return false;
    }
}
