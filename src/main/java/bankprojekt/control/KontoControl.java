package bankprojekt.control;

import bankprojekt.oberflaeche.KontoOberflaeche;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Kunde;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KontoControl extends Application {
    private Girokonto girokonto = new Girokonto(Kunde.MUSTERMANN,100,500);

    @Override
    public void start(Stage stage) {
        Parent kontoUI = new KontoOberflaeche(this);
        Scene scene = new Scene(kontoUI,300,300);
        stage.setScene(scene);
        stage.setTitle("Konto");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Getter für gesperrtProperty
     * @return gesperrtProperty
     */
    public BooleanProperty gesperrtProperty() {
        return girokonto.gesperrtProperty();
    }

    /**
     * Getter für adresseProperty
     * @return adresseProperty
     */
    public SimpleStringProperty adresseProperty() {
        return girokonto.getInhaber().adresseProperty();
    }

    /**
     * Getter für Kontostandproterty
     * @return Kontostandproperty als SimpleStringProperty mit Vorzeichen
     */
    public SimpleStringProperty kontostandProperty() {
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
        simpleStringProperty.bind(girokonto.kontostandProperty().asString("%+,f"));
        return simpleStringProperty;
    }

    /**
     * Getter für Kontonummer
     * @return Kontonummer als String
     */
    public String getKontonummer() {
        return  Long.toString(girokonto.getKontonummer());
    }

    /**
     * Zahlt Betrag auf Konto ein
     * @param betrag Betrag
     */
    public void einzahlen(double betrag) {
        girokonto.einzahlen(betrag);
    }

    /**
     * Hebt Betrag vom Konto ab
     * @param betrag Betrag
     * @return true, wenn erfolgreich, sonst false
     */
    public boolean abheben(double betrag) {
        try {
            return girokonto.abheben(betrag);
        }catch (GesperrtException e){
            return false;
        }
    }
}
