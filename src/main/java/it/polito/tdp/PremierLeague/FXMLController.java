/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.GiocatoreTitolarita;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	String goals = this.txtGoals.getText();
    	try {
    		Double minGoals = Double.parseDouble(goals);
    		if(minGoals < 0) {
    			this.txtResult.appendText("Inserire un numero maggiore di zero!");
        		return;
    		}
    		String msg = this.model.creaGrafo(minGoals);
    		this.txtResult.appendText(msg);
    	}
    	catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserire un numero!");
    		return;
    	}
    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	this.txtResult.clear();
    	
    	if(this.model.getGrafo() == null) {
    		this.txtResult.appendText("Creare prima il grafo!");
    		return;
    	}
    	String kS = this.txtK.getText();
    	try {
    		int k = Integer.parseInt(kS);
    		if(k < 0) {
    			this.txtResult.appendText("Inserire un numero maggiore di zero!");
        		return;
    		}
    		List<GiocatoreTitolarita> dreamTeam = this.model.getDreamTeam(k);
    		this.txtResult.appendText("DREAM TEAM:\n");
    		for(GiocatoreTitolarita g : dreamTeam) {
    			this.txtResult.appendText(g.getP() + "\n");
    		}
    		this.txtResult.appendText("\nGRADO DI TITOLARITA' TOTALE: " + this.model.getGradoTitolarita());
    	}
    	catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserire un numero!");
    		return;
    	}
    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	this.txtResult.clear();
    	
    	if(this.model.getGrafo() == null) {
    		this.txtResult.appendText("Creare prima il grafo!");
    		return;
    	}
    	
    	List<Adiacenza> battuti = this.model.getTopPlayerList();
    	this.txtResult.appendText("TOP PLAYER: " + this.model.getTop() + "\n");
    	this.txtResult.appendText("\nAVVERSARI BATTUTI:\n");
    	for(Adiacenza battuto : battuti) {
    		this.txtResult.appendText(battuto.getP2() + " | " + battuto.getPeso() + "\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
