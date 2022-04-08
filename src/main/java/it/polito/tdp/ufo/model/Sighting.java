package it.polito.tdp.ufo.model;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Sighting {
	
	
	String city, shape;
	Integer id;
	LocalDate datetime;
	StringProperty formattedDate;
	String TypeBinary;
	Blob BinaryField;
	private Image Image;

	public Sighting() {	}

	
	public String getTypeBinary() {
		return TypeBinary;
	}

	public Image getImage() {
	return Image;
}

	public void setImage(Image image) {
		Image = image;
		
		// write data to in-memory stream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedImage bi = SwingFXUtils.fromFXImage(image, null);
        try {
			ImageIO.write(bi, "jpg", bos);
	        this.BinaryField = new SerialBlob(bos.toByteArray());
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

	public void setTypeBinary(String typeBinary) {
		TypeBinary = typeBinary;
	}

	public Blob getBinaryField() {
		return BinaryField;
	}

	public void setBinaryField(Blob binaryField) {
		BinaryField = binaryField;
		// qui sotto carico anche il campo image
		if (binaryField == null) {
			Image = null;
		} 
		else {
		InputStream is;
		try {
			is = new BufferedInputStream(binaryField.getBinaryStream());
			BufferedImage bi = ImageIO.read(is);
			Image image = SwingFXUtils.toFXImage(bi,null);
			Image=image;
			is.close();
		}
		catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}

	//	public Sighting(int id, String city, String shape, LocalDate date) {
//		// TODO Auto-generated constructor stub
//		this.id = id;
//		this.city=city;
//		this.shape=shape;	
//		this.datetime=date;
//				
//	}
	
	public StringProperty FormattedDateProperty() { 
       	if (formattedDate == null) { 
       		String dd;
       		formattedDate = new SimpleStringProperty(this, "datetime");	
       		dd=datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       		formattedDate.setValue(dd);
       	}
        return formattedDate; 
    } 
	
	public String getFormattedDate() {
		return datetime.toString();
	}
	
	public LocalDate getDatetime() {
		return datetime;
	}


	public void setDatetime(LocalDate datetime) {
		this.datetime = datetime;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getShape() {
		return shape;
	}


	public void setShape(String shape) {
		this.shape = shape;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sighting other = (Sighting) obj;
		return id == other.id;
	}


	
}
