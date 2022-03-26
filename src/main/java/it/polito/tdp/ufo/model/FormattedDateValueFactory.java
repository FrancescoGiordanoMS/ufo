package it.polito.tdp.ufo.model;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.beans.value.ObservableValueBase;


public class FormattedDateValueFactory<EntityType> implements Callback<TableColumn.CellDataFeatures<EntityType, String>, ObservableValue<String>> {
	private String getterName;
	private SimpleDateFormat formatter;

	public FormattedDateValueFactory(String fieldName, String dateFormatPattern) {
		this.getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
		this.formatter = new SimpleDateFormat(dateFormatPattern);
	}

	public ObservableValue<String> call(CellDataFeatures<EntityType, String> features) {
		try {
			EntityType entity = features.getValue();
			Method m = entity.getClass().getMethod(getterName);
			Date date = (Date) m.invoke(entity);
			String formattedDate = formatter.format(date);
			return new SimpleObservableValue<String>(formattedDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

