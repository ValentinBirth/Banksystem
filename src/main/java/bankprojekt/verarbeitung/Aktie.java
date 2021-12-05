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
     * Konstruktor einer Aktie die sekündlich ihren Kurs neu berechnet
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

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public String getName() {
        return name;
    }

    public String getWertpapierNummer() {
        return wertpapierNummer;
    }
}
