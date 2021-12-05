package bankprojekt.verarbeitung;

import java.util.concurrent.*;

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
     * Konstruktor einer Aktie die sekündlich ihren Kurs zufällig neu berechnet
     * @param name Name der Aktie
     * @param wertpapierNummer Nummer der Aktie
     * @param kurs Anfangswert der Aktie
     */
    public Aktie(String name, String wertpapierNummer, double kurs) {
        this.name = name;
        this.wertpapierNummer = wertpapierNummer;
        this.kurs = kurs;

        Runnable wertberechnung = new Runnable() {
            @Override
            public void run() {
                double zufallsZahl = ThreadLocalRandom.current().nextDouble(-3, 3.1);
                if(zufallsZahl<0){
                    synchronized (this) {
                        double newKurs = getKurs() * ((100 - Math.abs(zufallsZahl))/100);
                        setKurs(newKurs);
                    }
                }else{
                    synchronized (this) {
                        double newKurs = getKurs() * ((100 + Math.abs(zufallsZahl))/100);
                        setKurs(newKurs);
                    }
                }
            }
        };

        ScheduledExecutorService preisberechnung = Executors.newScheduledThreadPool(1);
        preisberechnung.scheduleWithFixedDelay(wertberechnung,0L,1L, TimeUnit.SECONDS);
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
    public void setKurs(double kurs) {
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
}
