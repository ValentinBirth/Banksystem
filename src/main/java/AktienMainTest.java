import bankprojekt.verarbeitung.Aktie;
import bankprojekt.verarbeitung.Girokonto;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AktienMainTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Aktie htw = new Aktie("htw","1",1);
        Aktie tu = new Aktie("tu","2",1);
        Aktie hu = new Aktie("hu","3",1);

        Girokonto gk = new Girokonto();
        gk.einzahlen(10);

        Runnable boersenausgabe = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.interrupted()){
                        return;
                    }else {
                        System.out.println(new Date()+"| Boerse");
                        System.out.println(htw.getName() + ": " + htw.getKurs());
                        System.out.println(tu.getName() + ": " + tu.getKurs());
                        System.out.println(hu.getName() + ": " + hu.getKurs());
                        System.out.println("-----------------------------");
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            System.exit(0);
                        }
                    }
                }
            }
        };
        Thread boerse = new Thread(boersenausgabe);
        boerse.start();
        System.out.println(new Date()+"| Aktienkauf | HTW | "+gk.kaufauftrag(htw,3,2).get());
        System.out.println(new Date()+"| Aktienkauf | TU  | "+gk.kaufauftrag(tu,3,2).get());
        System.out.println(new Date()+"| Aktienkauf | HU  | "+gk.kaufauftrag(hu,3,2).get());
        System.out.println(new Date()+"| Kontostand | "+gk.getKontostand());
        System.out.println(new Date()+"| Aktienverkauf | HTW | "+gk.verkaufauftrag("1",0.5).get());
        System.out.println(new Date()+"| Aktienverkauf | TU  | "+gk.verkaufauftrag("2",0.5).get());
        System.out.println(new Date()+"| Aktienverkauf | HU  | "+gk.verkaufauftrag("3",0.5).get());
        System.out.println(new Date()+"| Kontostand | "+gk.getKontostand());

        boerse.interrupt();

    }
}
