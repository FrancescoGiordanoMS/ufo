package it.polito.tdp.ufo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.report.DataAccessObject;
import it.polito.tdp.report.PrimoReport;
import it.polito.tdp.ufo.db.DBConnect;
import it.polito.tdp.ufo.jfxCellValueFactories.FormattedDateValueFactory;
import it.polito.tdp.ufo.model.Model;
import it.polito.tdp.ufo.model.Sighting;
import it.polito.tdp.ufo.model.TestClass;
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
    private Button PBStampa;

    @FXML
    private VBox VBoxButton;

    @FXML
    private VBox VBoxSalvaCancel;

 
    //----------------------------------------------------------------------------------------------------
	private Model model;
	private enum StatoButtonSave {
		INSERT,
		MODIFY,
		INDEFINITO
	}
	private StatoButtonSave ButtonSave;
	
	//private String MethodToRun = "";

	
	/*
	 * //private String SaveMethod; //EntityType entity = features.getValue();
	 * //Method m = getMethod(SaveMethod); //String date = (String)
	 * m.invoke(entity);
	 * 
	 * //TestClass tc = new TestClass(); TestClass obj = new TestClass();
	 * Class<TestClass> classObj = obj.getClass(); Method m1 =
	 * TestClass.class.getDeclaredMethod("test");
	 */
	
	Integer index = -1;
    ObservableList<Sighting> obs = FXCollections.observableArrayList();
    
    @FXML
    void handleAdd(ActionEvent event) {
		ButtonSave = StatoButtonSave.INSERT;
		
		
    }
    
    @FXML
    void handleStampa(ActionEvent event) {
		printReport();
    }

	//private DBConnection database;
	private Connection connect;
	private Map<String, Object> map;	
    DataAccessObject dao= new DataAccessObject();
    
	public void printReport() {
		Connection connect;
		try {
			connect = DBConnect.getConnection();
			map = new HashMap<String, Object>();		
			PrimoReport.createReport(connect, map, dao.getRep());
			//.createReport(connect, map, dao.getReport("account_report", "report_jasper"));
			PrimoReport.showReport();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//database = new DBConnect();
		//connect = database.getConnection();

	}
    /**
     * @param event
     * @param Object 
     * @param Object 
     */
    @FXML
    void handleSave(MouseEvent event) { 
    	if (ButtonSave== StatoButtonSave.INSERT) {
    		SaveInsert();
    	}
    	else if (ButtonSave == StatoButtonSave.MODIFY) {
    		SaveModify();		
    	}
//    	Class<?> c = null;
//    	boolean result=false;
//    	try {
//    		c = Class.forName("it.polito.tdp.ufo.FXMLController");
//    		//FXMLController obj = new FXMLController();        
//    		//Method method = null;
//    		//method = c.getDeclaredMethod(MethodToRun);
//    		String s = "";
//    		Method m1 = null;
//    		Class<?> obj1 = this.getClass();
//    		Class<?> obj = FXMLController.this.getClass();
// 		
//    		//m1 =obj.getDeclaredMethod(MethodToRun);
//    		m1 =this.getClass().getDeclaredMethod(MethodToRun);
//    		//m1.setAccessible(true);
//    		result=(boolean)m1.invoke(obj);
//    		//result = (boolean)method.invoke(obj);
//    	} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//    		// TODO Auto-generated catch block
//     		e.printStackTrace();
//    	}    
    }
    private boolean SaveInsert() {
    	//cerco max(id) nel tableview
    	//......
		Sighting Sig = new Sighting(9000,TFCity.getText(),TFShape.getText(),DPData.getValue());
		obs.add(Sig);

    	boolean ret = true;
    	return ret;
    }

    private boolean SaveModify() {
    	index = TVUfo.getSelectionModel().getSelectedIndex();
    	if (index >= 0) {
    		TVUfo.getSelectionModel().getSelectedItem().setCity(TFCity.getText());
    		Sighting sig = TVUfo.getSelectionModel().getSelectedItem();
    		model.DBModify(sig);
    		TVUfo.refresh();
    		SetButton(true);
    		ButtonSave = StatoButtonSave.INDEFINITO;
    	}
		return(true);
    }
    
    //true=disabilitato   false=abilitato
    private void SetButton(boolean v) {	
   		VBoxSalvaCancel.setDisable(v);
		VBoxButton.setDisable(!v);
		GP.setDisable(v);
		//TVUfo.refresh();
    }
    
    @FXML
    void handleCancel(ActionEvent event) {
		SetButton(true);
		ButtonSave = StatoButtonSave.INDEFINITO;
    }
    
    @FXML
    void HMod(ActionEvent event) {
    	index = TVUfo.getSelectionModel().getSelectedIndex();
    	if (index > -1) {
		VBoxSalvaCancel.setDisable(false);
		VBoxButton.setDisable(true);
		GP.setDisable(false);
		ButtonSave = StatoButtonSave.MODIFY;
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
    
	Method meth = null;
	
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
    
    public void test(String num1, String num2) {System.out.println("sum is:"+num1+num2);  }	// solo per prova, non serve a niente
    private void testInvoke() throws Exception {
    	
    	Class<?> c = null;
    	  String result="";
    	  try {
    	    // Getting Class instance
    	    c = Class.forName("it.polito.tdp.ufo.FXMLController");

    	    // Getting class object
    	    FXMLController obj = new FXMLController();        
    	    Method method = null;

    	    // getting method instance by passing method name and parameter types
    	    method = c.getDeclaredMethod("test", String.class, String.class);

    	    // invoking method
    	    result = (String)method.invoke(obj, "Hello", "World");
    	  } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
    	    // TODO Auto-generated catch block
    	    e.printStackTrace();
    	  }
    	  System.out.println("Returned result is- " + result);    
 
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
        col_FormattedDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sighting, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sighting, String> p) {
            	StringProperty str = p.getValue().FormattedDateProperty();
            	//String s;
            	//s=str.getName();
                // p.getValue() returns the Person instance for a particular TableView row
                return str;
            }
        });
            
            try {
				testInvoke();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
            //Method m1 = FXMLController.class.getDeclaredMethod("test");
			/*
			 * //FXMLController obj = new FXMLController(); //Class<?> classObj =
			 * obj.getClass(); TestClass obj = new TestClass(); //Class classObj =
			 * obj.getClass(); Method m1 = TestClass.class.getDeclaredMethod("test");
			 * //m1.invoke(); int x=1;
			 */
            
         
        
            
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

