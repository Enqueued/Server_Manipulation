/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import SqL.GatewayException;
import assignment2.Assignment2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;

import assignment2.Master_Controller;
import assignment2.ViewType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
//import javafx.stage.FileChooser;
import models.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ultimaq
 */
public class AuthorDetailController {

	private static Logger logger = LogManager.getLogger();
	@FXML private Button save_button, delete_button, newAuth;
	@FXML private TextField first_box, last_box, gen_box, web_box, date_box; // id_box;
	//should work as the author in question
	private Author it;

	public AuthorDetailController(Author item) {
		//reference item storage...
		it = item;
		logger.info(it.toString() + "should be selected now");
	}

	public AuthorDetailController(){
		it = new Author();
	}

	@FXML
	private void handleButton(ActionEvent action) throws IOException, SQLException, GatewayException {
		Object source = action.getSource();
		if (source == save_button) {

			logger.error("saved");
			it.setFirstName(first_box.getText());
			it.setLastName(last_box.getText());
			it.setDoB(date_box.getText());
			it.setWebSite(web_box.getText());
			it.setGender(gen_box.getText());
			if(it.getId()==0){
				Assignment2.authorGate.insert(it);
			}else{
				Assignment2.authorGate.update(it);
			}
		} else if(source == delete_button){
			Assignment2.authorGate.delete(it);
			logger.info("Shit has not been saved");
		} else if(source == newAuth) {
			Master_Controller.getInstance().changeView(ViewType.AUTHOR_DETAIL, new Author());
		}

	}

	public void initialize() throws SQLException, ParseException{
		String format = null;
		if(it.getDoB()!=null){
			format = it.getDoB().toString();
		}
		logger.error("Commence Initialization for:" + it.getFirstName());
		first_box.setText(it.getFirstName());
		last_box.setText(it.getLastName());
		gen_box.setText(it.getGender());
		web_box.setText(it.getWebSite());
		date_box.setText(format);
		//id_box.setText(it.getId()+"");
	}

	public boolean hasChanged(){
		if(!it.getDoB().equals(date_box) || !it.getFirstName().equals(first_box) || !it.getGender().equals(gen_box)
				|| !it.getLastName().equals(last_box) || !it.getWebSite().equals(web_box)){
			return true;
		}
		return false;
	}

}
