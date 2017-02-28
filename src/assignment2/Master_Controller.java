package assignment2;

import Controllers.AuthorDetailController;
import Controllers.AuthorListController;
import SqL.AuthorTableGateway;
import SqL.GatewayException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import models.Author;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ultimaq on 17/02/23.
 */
public class Master_Controller {
    public static final Logger logger = LogManager.getLogger(Master_Controller.class);
    public static Master_Controller instance = null;
    private BorderPane rootPane;
    private AuthorTableGateway authorGate;
    private AuthorDetailController currentController;
    private Master_Controller(){
        try{
            authorGate = new AuthorTableGateway();
            currentController = null;
        } catch (GatewayException e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    /**
     * TODO: if current view has changed then prompt to save
     * @param v
     * @param data
     * @return
     */
    public boolean changeView(ViewType v, Object data) throws SQLException {
        if(currentController != null && currentController.hasChanged()){
            logger.error("*** Author Detail has changed.");
        }

        FXMLLoader loader = null;
        if(v == ViewType.AUTHOR_LIST){
            List<Author> authors = authorGate.getAuthors();
            loader = new FXMLLoader(getClass().getResource("../fxml/AuthorListView.fxml"));
            currentController = null;
            loader.setController(new AuthorListController(authors));
        } else if(v == ViewType.AUTHOR_DETAIL){
            loader = new FXMLLoader(getClass().getResource("../fxml/AuthorObjectDetail.fxml"));
            currentController = new AuthorDetailController((Author) data);
            loader.setController(currentController);
        }

        Parent view = null;
        try{
            view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        rootPane.setCenter(view);
        return true;
    }

    public void close(){
        authorGate.close();
    }

    public static Master_Controller getInstance(){
        if (instance == null) {
            instance = new Master_Controller();
        }
        return instance;
    }

    public BorderPane getRootPane(){
        return rootPane;
    }

    public void setRootPane(BorderPane rootPane){
        this.rootPane = rootPane;
    }

    public AuthorTableGateway getAuthorGate(){
        return authorGate;
    }

    public void setAuthorGate(AuthorTableGateway authorGate){
        this.authorGate = authorGate;
    }
}
