package Audit_Trail;

import javafx.collections.ObservableList;
import models.Author;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ultimaq on 17/02/26.
 */
public class AuditController {
    private static Logger logger = LogManager.getLogger();
    @FXML private Label authorName;
    @FXML private ListView<AuditEntry> auditView;
    private Author author;
    private List<AuditEntry> trailEntryList;

    public AuditController(Author author, List<AuditEntry> trailEntryList){
        this.author = author;
        this.trailEntryList = trailEntryList;
    }

    public void initialize() throws SQLException{
        authorName.setText(author.toString());
        ObservableList<AuditEntry> items = auditView.getItems();
        for (AuditEntry c : trailEntryList){
            items.add(c);
        }
    }

}
