package Controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ultimaq
 */
import assignment2.Assignment2;
import assignment2.Master_Controller;
import assignment2.ViewType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import models.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MenuController {
	private static Logger logger = LogManager.getLogger();
	
	@FXML private MenuItem menuQuit;
	@FXML private MenuItem menuAuthorList;
	@FXML private MenuItem menuNew;
	
	public MenuController() {
		
	}
	
	@FXML
	private void handleMenuItem(ActionEvent action) throws IOException, SQLException {
		Object source = action.getSource(); //tell us what menu item was clicked
		//AuthorListController cont = new AuthorListController();
		if(source == menuQuit){
			logger.entry("Quitting...");
			Platform.exit();
		}
		if(source == menuAuthorList){
			logger.entry("Author list has been clicked");
			/*List<Author> auths = Assignment2.authorGate.getAuthors();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AuthorListView.fxml"));
			loader.setController(new AuthorListController(auths));

			Parent view = loader.load();
			Assignment2.root.setCenter(view);*/
			Master_Controller.getInstance().changeView(ViewType.AUTHOR_LIST, null);
			return;
		}
		if (source == menuNew){
			logger.error("New Author in the making...");
			Master_Controller.getInstance().changeView(ViewType.AUTHOR_DETAIL, new Author());

			/*FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AuthorObjectDetail.fxml"));
			loader.setController(new AuthorDetailController());

			Parent view = loader.load();
			Assignment2.root.setCenter(view);*/
			return;
		}
	}
	
	public void initialize() {
		logger.error("Controller init has been called");
	}
}
