/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Controllers;

import assignment2.Master_Controller;
import assignment2.ViewType;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import models.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ultimaq
 */
public class AuthorListController {

	private static Logger logger = LogManager.getLogger();
	@FXML private ListView<Author> AuthorList;
	private List<Author> authors;
	final Label label = new Label();

	public AuthorListController(List<Author> authors) {
		//listProperty.set(FXCollections.observableArrayList(authorList));
		this.authors = authors;
	}
	
	public void initialize() {
		ObservableList<Author> peeps = AuthorList.getItems();
		for (Author author : authors) {
			peeps.add(author);
		}
		AuthorList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				if (click.getClickCount() == 2) {
					Author selected = AuthorList.getSelectionModel().getSelectedItem();
					logger.info(selected + "has been selected...");
					try {
						Master_Controller.getInstance().changeView(ViewType.AUTHOR_DETAIL, selected);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}

	

