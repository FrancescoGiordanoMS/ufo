package it.polito.tdp.ufo.model;

import java.sql.Date;
import java.time.*;
import java.util.Objects;

import javafx.beans.property.StringProperty;

public class Sighting {
	
	
	String city, shape;
	Integer id;
	LocalDate datetime;
	String formattedDate;

	public Sighting(int id, String city, String shape, LocalDate date) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.city=city;
		this.shape=shape;	
		this.datetime=date;
				
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
