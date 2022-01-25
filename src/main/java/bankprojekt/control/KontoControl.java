package bankprojekt.control;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class KontoControl extends Application {
    @FXML private TextArea adresse;
    @FXML private TextField betrag;
    @FXML private Text meldung;
    @FXML private Girokonto konto;
    @FXML private Text stand;
    @FXML private CheckBox gesperrt;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../KontoOberflaeche.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,300,300);
        stage.setScene(scene);
        stage.setTitle("Konto");
        stage.show();
    }

    @FXML public void initialize(){
        stand.textProperty().bind(konto.kontostandProperty().asString());
        gesperrt.selectedProperty().bindBidirectional(konto.gesperrtProperty());
        adresse.textProperty().bind(konto.getInhaber().adresseProperty());
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Zahlt Betrag auf Konto ein
     * @param betrag Betrag
     */
    private void einzahlen(double betrag) {
        try {
            konto.einzahlen(betrag);
        }catch (IllegalArgumentException e){
            meldung.setText("Betrag ist negativ");
        }
        meldung.setText("Einzahlung erfolgreich");

    }

    /**
     * Hebt Betrag vom Konto ab
     * @param betrag Betrag
     */
    private void abheben(double betrag) {
        try{
            if (konto.abheben(betrag)){
                meldung.setText("Abheben erfolgreich");
            }else{
                meldung.setText("Kontostand zu gering");
            }
        } catch (GesperrtException e) {
            meldung.setText("Fehler bei Abhebung");
        }
    }

    public void einzahlen() {
        try {
            einzahlen(Double.parseDouble(betrag.getText()));
        }catch (NumberFormatException exception){
            meldung.setText("Betrag muss eine Dezimalzahl sein!");
        }
    }

    public void abheben() {
        try {
            abheben(Double.parseDouble(betrag.getText()));
        }catch (NumberFormatException exception){
            meldung.setText("Betrag muss eine Dezimalzahl sein!");
        }
    }
}
