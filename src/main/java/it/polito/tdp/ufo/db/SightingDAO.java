package it.polito.tdp.ufo.db;

import java.lang.reflect.*;
//import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.*;

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
		ObservableList<Sighting> obs = FXCollections.observableArrayList();
		try {
			String sql2 = "SELECT id,city,shape, datetime FROM sighting";
			Connection conn = DBConnect.getConnection();
			PreparedStatement st2 = conn.prepareStatement(sql2);
			ResultSet res2 = st2.executeQuery() ;

			while (res2.next()) {
				//Sig = new Sighting();
				//Sig.setCity(res2.getString("city"));
				//Sig.setShape(res2.getString("shape"));
				//Sig.setDatetime(res2.getDate("datetime").toLocalDate());
				//Sig.setId(res2.getInt("id"));
				//obs.add(getAutomatic(Sig,res2));
				obs.add(getAutomatic(res2));
			}	
			st2.close();
			conn.close();
		} catch(SQLException e) {
			throw new RuntimeException("Database Error in countByShape", e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(obs);
	}


	private Sighting getAutomatic(ResultSet res2) {
		Sighting sig = new Sighting();
		Class<?> c = Sighting.class;
		ResultSetMetaData rsmd;
		Object rv = null;

		try {
			Method m = null;
			rsmd = res2.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			String nameCol, setMethod="", typeField;
			Class[] cArg = new Class[1];

			for(int ix=1; ix <= columnsNumber; ix++) {
				nameCol=res2.getMetaData().getColumnName(ix);
				typeField=res2.getMetaData().getColumnTypeName(ix);
				setMethod = "set" + nameCol.substring(0, 1).toUpperCase() + nameCol.substring(1, nameCol.length());		

				switch(typeField) {
				case "VARCHAR":
					cArg[0] = String.class;
					m=c.getMethod(setMethod,cArg);
					rv = m.invoke(sig, res2.getString(ix));
					break;
				case "INTEGER":
					cArg[0] = int.class;
					m=c.getMethod(setMethod,cArg);
					rv = m.invoke(sig, res2.getInt(ix));
					break;
				case "DATETIME":
					cArg[0] = LocalDate.class;
					m=c.getMethod(setMethod,cArg);
					rv = m.invoke(sig, res2.getDate(ix).toLocalDate());
					break;
				}
 			} 
			//Class<Sighting> c = sig;
			//Class c = Class.forName("Sighting");
			//Class c = "it.polito.tdp.ufo.model.Sighting".getClass();

			//			String mname = "";
			//			Method[] allMethods = c.getDeclaredMethods();
			//			for (Method m : allMethods) {
			//				mname = m.getName();
			//				if (mname.equals("setId")) {
			//					//m2 = sig.getMethod(method, Integer.class);
			//					rv = m.invoke(sig, param);
			//					break;
			//				}
			//			}

//			Class[] cArg = new Class[1];
//			cArg[0] = int.class;
//			Method m1=c.getMethod(method,cArg);
//			rv = m1.invoke(sig, param);
		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return sig;
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
