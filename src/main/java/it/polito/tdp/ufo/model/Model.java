package it.polito.tdp.ufo.model;

import java.util.List;

import it.polito.tdp.ufo.db.SightingDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	
	ObservableList<Sighting> obs = FXCollections.observableArrayList();
	
	public ObservableList<Sighting> getRighe() {
		SightingDAO dao = new SightingDAO() ;
		this.obs = dao.getRighe();
		return this.obs;
	}
}
