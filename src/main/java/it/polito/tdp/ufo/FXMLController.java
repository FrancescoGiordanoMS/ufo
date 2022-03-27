package it.polito.tdp.ufo;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.jfxCellValueFactories.FormattedDateValueFactory;
import it.polito.tdp.ufo.model.Model;
import it.polito.tdp.ufo.model.Sighting;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class FXMLController {

	 @FXML
	private GridPane GP;
	
	@FXML
    private TableView<Sighting> TVUfo;
    
    @FXML
    private TableColumn<Sighting, Integer> col_id;

    @FXML
    private TableColumn<Sighting, String> col_city;

    @FXML
    private ComboBox<String> CBShape;

    @FXML
    private Button PBMod;

    @FXML
    private Button PBCancel;

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
    private DatePicker DPData;

    @FXML
    private Button PBCaricaDati;

    @FXML
    private TableColumn<Sighting, String> col_shape;
    
    @FXML
    private TableColumn<Sighting, LocalDate> col_date;


    @FXML
    private TableColumn<Sighting, String> col_FormattedDate;
    
    @FXML
    private TextField TFShape;

    @FXML
    private Button PBSalva;

    @FXML
    private VBox VBoxButton;

    @FXML
    private VBox VBoxSalvaCancel;

 
    //----------------------------------------------------------------------------------------------------
	private Model model;
	Integer index = -1;
    ObservableList<Sighting> obs = FXCollections.observableArrayList();
    
    @FXML
    void handleAdd(ActionEvent event) {
    	//cerco max(id) nel tableview
    	//......
		Sighting Sig = new Sighting(9000,TFCity.getText(),TFShape.getText(),DPData.getValue());
		obs.add(Sig);
		
    }

    @FXML
    void handleSave(MouseEvent event) {
    	index = TVUfo.getSelectionModel().getSelectedIndex();
    	if (index >= 0) {
    		TVUfo.getSelectionModel().getSelectedItem().setCity(TFCity.getText());
    		TVUfo.refresh();
    		Sighting sig = TVUfo.getSelectionModel().getSelectedItem();
    		model.DBModify(sig);
    		VBoxSalvaCancel.setDisable(true);
    		VBoxButton.setDisable(false);
    		GP.setDisable(true);
    	}
    }
    
    @FXML
    void handleCancel(ActionEvent event) {
		VBoxSalvaCancel.setDisable(true);
		VBoxButton.setDisable(false);
		GP.setDisable(true);
    }
    
    @FXML
    void HMod(ActionEvent event) {
    	index = TVUfo.getSelectionModel().getSelectedIndex();
    	if (index > -1) {
		VBoxSalvaCancel.setDisable(false);
		VBoxButton.setDisable(true);
		GP.setDisable(false);
    	}	 	
    }

   
    @FXML
    void getSelected(MouseEvent event) {
		/*
		 * Questo funziona!! Commentato perchè ho introdotto il bind più generale
		 * che funziona anche con tastiera etc etc.. vedi sotto
		 * 
		 * index = TVUfo.getSelectionModel().getSelectedIndex(); if (index <= -1)
		 * return; TFId.setText(col_id.getCellData(index).toString());
		 * TFCity.setText(col_city.getCellData(index).toString());
		 * TFShape.setText(col_shape.getCellData(index).toString());
		 */
    	  	
    }
 
    @FXML
    void handleDistinctShape(MouseEvent event) {
		ObservableList<String> obsl= FXCollections.observableArrayList();
		obsl=model.getDistinctShape();
		CBShape.getItems().clear();
		CBShape.getItems().addAll(obsl);
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
        col_date.setCellValueFactory(new PropertyValueFactory<Sighting,LocalDate>("datetime"));
        
        // vedi http://dgimenes.com/blog/2014/03/06/javafx-formatting-data-in-tableview.html
        // Questo è il primo modo per formattare la colonna data
        //col_FormattedDate.setCellValueFactory(new FormattedDateValueFactory<Sighting>("FormattedDate","MM/dd/yyyy"));
        Bindings.bindBidirectional(TFCity.textProperty(), TFCity1.textProperty());     
     
        // Questo è il secondo modo che mi pare meno contorto
        col_FormattedDate.setCellValueFactory(new Callback<CellDataFeatures<Sighting, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Sighting, String> p) {
            	StringProperty str = p.getValue().FormattedDateProperty();
            	String s;
            	s=str.getName();
                // p.getValue() returns the Person instance for a particular TableView row
                return str;
            }
         });
        
            
        TVUfo.getSelectionModel().selectedItemProperty().addListener((ob, oldval, newVal) -> {
            if (newVal != null) {
                TFCity.setText(newVal.getCity());
                TFShape.setText(newVal.getShape());
                TFId.setText(String.valueOf(newVal.getId()));
                DPData.setValue(newVal.getDatetime());
                CBShape.setValue(newVal.getShape());
            }
        });
        
    }
  
}

