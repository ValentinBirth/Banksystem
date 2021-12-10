package bankprojekt.verarbeitung;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Klasse für eine Aktie
 */
public class Aktie {
    /**
     * Name der Aktie
     */
    private String name;
    /**
     * Kennung der Aktie
     */
    private String wertpapierNummer;
    /**
     * Kurs der Aktie
     */
    private double kurs;

    /**
     * Condition um in anderen Threads auf Änderungen der Aktie zu warten
     */
    private Condition kursVerändert;
    /**
     * Lock für synchronisation
     */
    private Lock aktienLock;

    /**
     * Konstruktor einer Aktie die sekündlich ihren Kurs zufällig neu berechnet
     * @param name Name der Aktie
     * @param wertpapierNummer Nummer der Aktie
     * @param kurs Anfangswert der Aktie
     */
    public Aktie(String name, String wertpapierNummer, double kurs) {
        this.name = name;
        this.wertpapierNummer = wertpapierNummer;
        this.kurs = kurs;
        this.aktienLock = new ReentrantLock();
        this.kursVerändert = this.aktienLock.newCondition();

        Runnable wertberechnung = () -> {
            double veraenderung = Math.random()*6-3;
            aktienLock.lock();
            setKurs(getKurs()*(100+veraenderung)/100);
            kursVerändert.signalAll();
            aktienLock.unlock();
        };

        ScheduledExecutorService preisberechnung = Executors.newSingleThreadScheduledExecutor();
        preisberechnung.scheduleAtFixedRate(wertberechnung,0,1,TimeUnit.SECONDS);
    }

    /**
     * getter für den Aktienkurs
     * @return Aktienkurs als double
     */
    public double getKurs() {
        return kurs;
    }

    /**
     * setter für den Aktienkurs
     * @param kurs Aktienkurs als double
     */
    private void setKurs(double kurs) {
        this.kurs = kurs;
    }

    /**
     * getter für den Aktienname
     * @return Aktienname
     */
    public String getName() {
        return name;
    }

    /**
     * getter für die Wertpapiernummer
     * @return Wertpapiernummer als String
     */
    public String getWertpapierNummer() {
        return wertpapierNummer;
    }

    /**
     * getter für Condition
     * @return Condition für Kurs Verändert
     */
    public Condition getKursVerändert() {
        return kursVerändert;
    }

    /**
     * getter für Lock
     * @return Lock
     */
    public Lock getAktienLock() {
        return aktienLock;
    }
}
