package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.Model;
import it.polito.tdp.ufo.model.Sighting;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class FXMLController {

	private Model model;
	Integer index = -1;

	@FXML
    private TableView<Sighting> TVUfo;
    
    @FXML
    private TableColumn<Sighting, Integer> col_id;

    @FXML
    private TableColumn<Sighting, String> col_city;

    @FXML
    private Button PBMod;

    @FXML
    private Label txtMessaggio;

    @FXML
    private ComboBox<String> boxForma;

    @FXML
    private Button PBAdd;

    @FXML
    private TextField TFId;

    @FXML
    private TextField TFCity;

    @FXML
    private TextField TFCity1;

    @FXML
    private Button PBCaricaDati;

    @FXML
    private TableColumn<Sighting, String> col_shape;

    @FXML
    private TextField TFShape;


    
    @FXML
    void handleAdd(ActionEvent event) {

    }

    @FXML
    void HMod(ActionEvent event) {

    }

   private final StringProperty twoWayInput = new SimpleStringProperty();
    public StringProperty twoWayInputProperty() {return twoWayInput;}
    @FXML
    void getSelected(MouseEvent event) {
    	index = TVUfo.getSelectionModel().getSelectedIndex();
    	if (index <= -1)
    		return;
    	TFId.setText(col_id.getCellData(index).toString());
    	TFCity.setText(col_city.getCellData(index).toString());
    	TFShape.setText(col_shape.getCellData(index).toString());
    	
    	//TFCity.textProperty().bind(twoWayInputProperty());
    	Bindings.bindBidirectional(twoWayInputProperty(), TFCity1.textProperty());
    	Bindings.bindBidirectional(twoWayInputProperty(), TFCity.textProperty());
    	//Bindings.bindBidirectional(twoWayInputProperty(), col_city.textProperty());
    	//TVUfo.getSelectionModel().selectedItemProperty().addListener(observableValue, authorProps, authorProps2);
    }
    
    
    
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

