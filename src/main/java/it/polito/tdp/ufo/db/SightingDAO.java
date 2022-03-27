package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.Sighting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SightingDAO {


	public List<String> readShapes() {
		try {
			Connection conn = DBConnect.getConnection();

			String sql = "SELECT DISTINCT shape FROM sighting WHERE shape<>'' ORDER BY shape ASC";

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			List<String> formeUFO = new ArrayList<>();
			while (res.next()) {
				String forma = res.getString("shape");
				formeUFO.add(forma);
			}
			st.close();
			conn.close();
			return formeUFO;
		} catch (SQLException e) {
			throw new RuntimeException("Database error in readShapes", e) ;
		}

	}

	public int countByShape(String shape) {
		
		try {
			Connection conn = DBConnect.getConnection();

			String sql2 = "SELECT COUNT(*) AS cnt FROM sighting WHERE shape= ? ";
			
			PreparedStatement st2 = conn.prepareStatement(sql2);
			
			st2.setString(1, shape);
			ResultSet res2 = st2.executeQuery() ;
			
			res2.first();
			int count = res2.getInt("cnt");
			st2.close();
			conn.close();
			
			return count ;
			
		} catch(SQLException e) {
			throw new RuntimeException("Database Error in countByShape", e);
		}

	}

	public ObservableList<Sighting> getRighe() {
		// TODO Auto-generated method stub
		ObservableList<Sighting> obs = FXCollections.observableArrayList();
		try {
			Connection conn = DBConnect.getConnection();

			String sql2 = "SELECT id,city,shape, datetime FROM sighting";
			
			PreparedStatement st2 = conn.prepareStatement(sql2);
			ResultSet res2 = st2.executeQuery() ;
			
			while (res2.next()) {
				obs.add(new Sighting(res2.getInt("id"), res2.getString("city"),res2.getString("shape"), res2.getDate("datetime").toLocalDate()));
				}
				
			st2.close();
			conn.close();
			return(obs);
		
			
		} catch(SQLException e) {
			throw new RuntimeException("Database Error in countByShape", e);
		}
	}


	public ObservableList<String> getDistinctShape() {
		// TODO Auto-generated method stub
		ObservableList<String> obs = FXCollections.observableArrayList();
		try {
			Connection conn = DBConnect.getConnection();

			String sql2 = "	SELECT distinct shape FROM ufo_sightings.sighting order by shape asc";
			
			PreparedStatement st2 = conn.prepareStatement(sql2);
			ResultSet res3 = st2.executeQuery() ;
			
			while (res3.next()) {
				obs.add(res3.getString("shape"));
				}
				
			st2.close();
			conn.close();
			return(obs);
			
		} catch(SQLException e) {
			throw new RuntimeException("Database Error in countByShape", e);
		}
	}

	public boolean DBModify(Sighting Record) {
		boolean ret=true;
		try {
			Connection conn = DBConnect.getConnection();

			String sql2 = "update ufo_sightings.sighting set city = ? where id = ?";
			
			PreparedStatement st2 = conn.prepareStatement(sql2);
			st2.setString(1, Record.getCity());
			st2.setInt(2, Record.getId());
			ret = st2.execute() ;
				
			st2.close();
			conn.close();
			return(ret);
			
		} catch(SQLException e) {
			throw new RuntimeException("Database Error in countByShape", e);
			//return(false);
		}
		//return(false);
	}

	
	
}
