package it.polito.tdp.ufo;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.Model;
import it.polito.tdp.ufo.model.Sighting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxForma;

    @FXML
    private Label txtMessaggio;
    
    @FXML
    private TableView<Sighting> TVUfo;

    @FXML
    private TableColumn<Sighting, Integer> col_id;

    @FXML
    private TableColumn<Sighting, String> col_city;
       
    @FXML
    private TableColumn<Sighting, String> col_shape;
    
    @FXML
    private Button PBCaricaDati;
  
    
    @FXML
    void handleConta(ActionEvent event) {
    	String forma = boxForma.getValue();
    	int count = model.getCountByForma(forma);
    	txtMessaggio.setText("Gli UFO della forma "+forma+" sono: "+count);

    }
    
    public void setModel(Model m) {
    	this.model = m ;
    	boxForma.getItems().addAll(this.model.getFormeUFO()) ;
    }

    @FXML
    void CaricaDati(ActionEvent event) {
    	TableView();
    }
    
    ObservableList<Sighting> obs = FXCollections.observableArrayList();
    
    public void TableView() {
        obs=model.getRighe();
        

        
        this.TVUfo.setItems(obs);
    }
    
    @FXML
    void initialize() {
        assert boxForma != null : "fx:id=\"boxForma\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMessaggio != null : "fx:id=\"txtMessaggio\" was not injected: check your FXML file 'Scene.fxml'.";

        col_id.setCellValueFactory(new PropertyValueFactory<Sighting,Integer>("id"));
        col_city.setCellValueFactory(new PropertyValueFactory<Sighting,String>("city"));
        col_shape.setCellValueFactory(new PropertyValueFactory<Sighting,String>("shape"));
           
    }

	
}
