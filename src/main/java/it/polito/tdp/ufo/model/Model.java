package it.polito.tdp.ufo.model;

import java.util.List;

import it.polito.tdp.ufo.db.SightingDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionModel;

public class Model {

	private List<String> formeUFO = null ;
	
	public List<String> getFormeUFO() {
		if (this.formeUFO==null) {
			SightingDAO dao = new SightingDAO() ;
			this.formeUFO = dao.readShapes() ;
		}
		return this.formeUFO;
	}
	
	public int getCountByForma(String forma) {
		SightingDAO dao = new SightingDAO();
		return dao.countByShape(forma) ;
	}
	
	public ObservableList<String> getDistinctShape() {
		ObservableList<String> obsl= FXCollections.observableArrayList();
		obsl.clear();
		SightingDAO dao = new SightingDAO();
		obsl=dao.getDistinctShape();
		return( obsl);
		
	}
	
	ObservableList<Sighting> obs = FXCollections.observableArrayList();
	
	public ObservableList<Sighting> getRighe() {
		SightingDAO dao = new SightingDAO() ;
		this.obs = dao.getRighe();
		return this.obs;
	}


	public boolean DBModify(Sighting Record) {
		boolean ret = true;
		SightingDAO dao = new SightingDAO() ;
		ret = dao.DBModify(Record);
		return ret;
	}

}

