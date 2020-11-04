package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
class Bil{
    public String bilNummer;
    public Date startTid;
    public int plass;
    public boolean kortTid; // true = korttids parkering

    public Bil(String bilNummer, Date startTid, boolean kortTid) {
        this.bilNummer = bilNummer;
        this.startTid = startTid;
        this.kortTid = kortTid;
    }

    public String formaterKvittering(){
        // formater kvitteringen etter oppgitt format
        String ut = "";
        ut += "kvittering for bilnr: " + this.bilNummer + "`\n";
        Date now = new Date();
        String starttid = "fra " + new SimpleDateFormat("dd.MM.YYYY HH:mm").format(this.startTid) + " ";
        String slut = "til" + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(now);
        ut += starttid + slut;
        ut += " Betalt: " + getPris() + " kr";
         return ut;
    }

    public double getPris(){
        // returner 10 eller 20 kroner avhengig av kortTid eller ikke

        if (kortTid){
            return 10;
        }
        else {
            return 20;
        }
    }
    public double avgift(){
        // regner ut tiden som er gått i timer og ganger med prisen
        Date nå = new Date();
        String startDatoTid = new SimpleDateFormat("dd.MM.YYYY HH:mm").format(nå);
        long varighet = nå.getTime() - startTid.getTime();
        int timer = (int) (varighet/3600000);

        return timer*getPris();
    }
}

class Parkeringshus{
    // opprett arrayet av biler
    ArrayList<Bil> biler = new ArrayList<Bil>();

    public void reserverPlass(Bil enBil){
        biler.add(enBil);


    }

    public String frigjørPlass(String bilNummeret){
        /*
         ** må finne bilen i arrayet
         ** når den er funnet slett den fra arrayet
         ** og formater kvitteringen som returneres
         ** dersom bilen ikke finnes skal man returnere en passenede tekst
         */

        for (Bil i: biler){
            String kvittering = "";
            if (i.bilNummer.equals(bilNummeret)){
                 kvittering +=  " " +i.formaterKvittering() + " ";
                biler.remove(i);
                return kvittering;

            }

        }
        return "Bilen finnes ikke";
    }
}

public class Controller {

    // opprett parkeringshuset
    Parkeringshus amfi = new Parkeringshus();

    @FXML
    private Label lblAvgift;

    @FXML
    private TextField txtBilnummer;

    @FXML
    void kjørUt(ActionEvent event) {
        // kall frigjør plass og legg ut kvitteringen i lblAvgift
        amfi.frigjørPlass(txtBilnummer.getText());
        lblAvgift.setText(amfi.frigjørPlass(txtBilnummer.getText()));
    }

    @FXML
    void regKorttid(ActionEvent event) {
        Date start = new Date();
        Bil nybil = new Bil(txtBilnummer.getText(),start, true);
        amfi.reserverPlass(nybil);
        lblAvgift.setText("Vellykket");
        // opprett en bil
        // og kall på reserver plass
    }

    @FXML
    void regLangtid(ActionEvent event) {
        // opprett en bil
        // og kall på reserver plass
        Date start = new Date();
        Bil nybil = new Bil(txtBilnummer.getText(),start,false);
        amfi.reserverPlass(nybil);
        lblAvgift.setText("Vellykket");
    }
}


