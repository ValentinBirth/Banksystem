package bankprojekt.verarbeitung;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

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

    public long mockEinfuegen(Konto k){
        long kontonummer = groesteKontoNummer;
        konten.put(kontonummer,k);
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
            kontoNummern.remove(nummer);
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
    public boolean geldUeberweisen(long vonKontoNummer, long nachKontoNummer, double betrag, String verwendungszweck) throws GesperrtException {
        if (konten.containsKey(vonKontoNummer) && konten.containsKey(nachKontoNummer)){
            if(Ueberweisungsfaehig.class.isAssignableFrom(konten.get(vonKontoNummer).getClass()) && Ueberweisungsfaehig.class.isAssignableFrom(konten.get(nachKontoNummer).getClass())){
                Ueberweisungsfaehig vonKonto = (Ueberweisungsfaehig) konten.get(vonKontoNummer);
                Ueberweisungsfaehig nachKonto = (Ueberweisungsfaehig) konten.get(nachKontoNummer);
                if(vonKonto.ueberweisungAbsenden(betrag,konten.get(nachKontoNummer).getInhaber().getName(),nachKontoNummer,bankleitzahl,verwendungszweck)){
                    nachKonto.ueberweisungEmpfangen(betrag,konten.get(vonKontoNummer).getInhaber().getName(),vonKontoNummer,bankleitzahl,verwendungszweck);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void pleiteGeierSperren() {
        Stream<Konto> kontenStream = konten.values().stream();
        kontenStream.filter(konto -> konto.getKontostand()<0).forEach(Konto::sperren);
    }

    @Override
    public List<Kunde> getKundenMitVollemKonto(double minimum) {
        Stream<Konto> kontenStream = konten.values().stream();
        return kontenStream.filter(konto -> konto.getKontostand()>minimum).map(Konto::getInhaber).collect(Collectors.toList());
    }

    @Override
    public String getKundenGeburtstage() {
        Stream<Konto> kontenStream = konten.values().stream();
        return kontenStream.map(Konto::getInhaber).distinct().sorted(Comparator.comparing(Kunde::getGeburtstag)).map(Kunde::toString).reduce("", (partialString, element) -> partialString + element);
    }

    @Override
    public List<Long> getKontonummernLuecken() {
        Stream<Long> nummernStream = LongStream.range(0,groesteKontoNummer).boxed();
        return nummernStream.filter(aLong -> !kontoNummern.contains(aLong)).collect(Collectors.toList());
    }

    @Override
    public List<Kunde> getAlleReichenKunden(double minimum) {
        return null;
    }
}
