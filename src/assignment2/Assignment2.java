/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import Controllers.MenuController;
import SqL.AuthorTableGateway;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author ultimaq
 */
public class Assignment2 extends Application {
	
	public static BorderPane root;
	ListView<String> listview;
	/*
	* this is going to be where the beginning loads of the app start
	* should start with copy paste then expand from that */
	
	public static AuthorTableGateway authorGate;
	
	@Override
	public void init() throws Exception{
		super.init();
		authorGate = new AuthorTableGateway();
	}
	
	@Override
	public void stop() throws Exception{
		super.stop();
		authorGate.close();
	}
	
	@Override
	public void start (Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/view_master.fxml"));
		
		MenuController controller = new MenuController();
		loader.setController(controller);
		
		Parent view = loader.load();
		//root = (BorderPane) view;
		Master_Controller.getInstance().setRootPane((BorderPane) view);
		Scene scene = new Scene(view);
		stage.setScene(scene);
		stage.setTitle("Assignment 2 : Author List Library");
		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		//CS 4743 Assignment 2 by George Wilborn
		launch(args);
	}
	
}
