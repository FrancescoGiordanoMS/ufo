package it.polito.tdp.ufo;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MenuContestuale {

	public MenuContestuale() {
		// TODO Auto-generated constructor stub
	}
		
	public static ContextMenu getMenuContestuale() {
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

	private static void DownloadAndView() {
		 try
	        {
	            // Command to create an external process
	            //String command = "C:\\Program Files (x86)"+
	            //     "\\Google\\Chrome\\Application\\chrome.exe";
	            String command = "C:\\Program Files\\paint.net\\paintdotnet.exe 106_0627.jpg";

	  
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
