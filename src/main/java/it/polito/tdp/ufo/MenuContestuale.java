package it.polito.tdp.ufo;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MenuContestuale {
	private Stage stage;
	private ImageView imageView;

	public MenuContestuale(Stage st, ImageView im) {
		this.stage = st;
		this.imageView = im;
	}

	public ContextMenu getMenuContestuale() {
		final ContextMenu cm = new ContextMenu();
		final String Menu1="Download and view";

		cm.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.SECONDARY) {
					System.out.println("consuming right release button in cm filter");
					event.consume();
				}
			}
		});
		cm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String Selez =((MenuItem)event.getTarget()).getText();
				System.out.println("right gets consumed so this must be left on "+
						((MenuItem)event.getTarget()).getText());
				if (Selez.equals(Menu1) ) DownloadAndView();
			}
		});

		MenuItem menuItem1 = new MenuItem(Menu1);
		MenuItem menuItem2 = new MenuItem("line 2");
		MenuItem menuItem3 = new MenuItem("line 3");

		cm.getItems().addAll(menuItem1, menuItem2, menuItem3);
		return cm;
	}

	private void DownloadAndView() {
		File SavedFile=null;
		if (imageView.getImage()==null) {
			System.out.println("Non c'è alcuna immagine");
			return;
		}
		try
		{
			MenuBar menuBar = new MenuBar();
			Group root = new Group(menuBar);
			Scene scene = new Scene(root, 595, 355);  
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("file jpg", "*.jpg"));
			SavedFile=fileChooser.showSaveDialog(stage);
			if (SavedFile==null) {
				System.out.println("Nessun file indicato");
				return;
			}
			// qui estraggo il file e lo registro sul pc
		    BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
		    try {
		      ImageIO.write(bImage, "jpg", SavedFile);
		    } catch (IOException e) {
		      throw new RuntimeException(e);
		    }
			
			
			
			
			// Command to create an external process
			//String command = "C:\\Program Files (x86)"+
			//     "\\Google\\Chrome\\Application\\chrome.exe";
			String command = "C:\\Program Files\\paint.net\\paintdotnet.exe "+SavedFile;


			// Running the above command
			Runtime run  = Runtime.getRuntime();
			Process proc = run.exec(command);
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
