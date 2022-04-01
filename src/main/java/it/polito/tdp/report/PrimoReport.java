package it.polito.tdp.report;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

//import com.mysql.jdbc.Connection;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

import it.polito.tdp.ufo.db.DBConnect;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public abstract class PrimoReport {

	private static JasperReport jreport;
	private static JasperViewer jviewer;
	private static JasperPrint jprint;
	
//	public PrimoReport() {
//		// TODO Auto-generated constructor stub
//	}
		
	public static void createReport(Connection connect, Map<String, Object> map, InputStream by) {
		try {
			//JasperDesign by1 = JRXmlLoader.load("Wood.jrxml");
			//jreport = JasperCompileManager.compileReport(by);
			jreport = (JasperReport)JRLoader.loadObjectFromFile("Wood.jasper");
			//jreport = (JasperReport)JRLoader.loadObject(by);
			jprint = JasperFillManager.fillReport(jreport, map, connect);		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showReport() {
		jviewer = new JasperViewer(jprint, false); // false to avoid closing the main application and will only close the print preview
		jviewer.setVisible(true);
	}
	
	
}
