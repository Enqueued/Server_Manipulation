package SqL;

import Audit_Trail.AuditEntry;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import models.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ultimaq
 */
public class AuthorTableGateway {

	private static Logger logger = LogManager.getLogger();
	private MysqlDataSource MsDs = null;
	Statement stmt = null;
	Connection conn = null;
	ResultSet res = null;
	List<Author> authors = new ArrayList<Author>();

	public List<Author> getAuthors() throws SQLException {
		/*List<Author> authors = new ArrayList<Author>();
		PreparedStatement st = null;
		ResultSet rs = null;*/
		conn = MsDs.getConnection();
		try {
			stmt = conn.createStatement();
			res = stmt.executeQuery("select * from AuthorTable");

			while(res.next()) {
				Author auth = new Author(
						res.getString("first_name"),
						res.getString("last_name"),
						res.getDate("dob"),
						res.getString("gender"),
						res.getString("web_site"),
						res.getInt("id"),
						res.getTimestamp("last_modified").toLocalDateTime());

				authors.add(auth);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(res != null)
					res.close();
				if(stmt != null)
					stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return authors;
	}

	public AuthorTableGateway()  throws GatewayException {
		//connect to data source and create a connection instance
		//read db credentials from properties file
		this.conn = null;
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("db.properties");
			props.load(fis);
			fis.close();
			this.MsDs = new MysqlDataSource();
			MsDs.setURL(props.getProperty("MYSQL_AUTHOR_DB_URL"));
			MsDs.setUser(props.getProperty("MYSQL_AUTHOR_DB_USERNAME"));
			MsDs.setPassword(props.getProperty("MYSQL_AUTHOR_DB_PASSWORD"));
			//create the connection
			conn = MsDs.getConnection();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new GatewayException(e);
		}

	}

	public void update(Author it) throws SQLException{
		List<Author> authors = new ArrayList<Author>();
		PreparedStatement st = null;
		ResultSet rs = null;

		try{
			st = conn.prepareStatement(
					"UPDATE AuthorTable SET first_name = ?, last_name = ?, dob = ?, gender = ?, web_site = ? WHERE ID = ?");
			st.setString(1, it.getFirstName());
			st.setString(2, it.getLastName());
			st.setString(3, String.valueOf((it.getDoB())));
			st.setString(4, it.getGender());
			st.setString(5, it.getWebSite());
			st.setInt(6, it.getId());
			st.executeUpdate();

		} catch (SQLException e){
			logger.error("Failed to Update Author: "+it.getFirstName() + "\n" + e);
		} finally {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
		}

	}

	public void insert(Author it) throws GatewayException{
		//List<Author> authors = new ArrayList<Author>();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"INSERT INTO AuthorTable (first_name, last_name, dob, gender, web_site) VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS); //WHERE ID = ?");
			st.setString(1, it.getFirstName());
			st.setString(2, it.getLastName());
			st.setString(3, String.valueOf((it.getDoB())));
			st.setString(4, it.getGender());
			st.setString(5, it.getWebSite());
			st.executeUpdate();
			rs = st.getGeneratedKeys();
			if(rs != null && rs.next()){
				it.setId(rs.getInt(1));
			}

		} catch (SQLException ex) {
			//Logger.getLogger(AuthorTableGateway.class.getName()).log(Level.SEVERE, null, ex);
			logger.error(ex);
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void close() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void delete(Author it) throws SQLException {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		List<Author> authors = new ArrayList<Author>();
		PreparedStatement st = null;
		ResultSet rs = null;

		try{
			st = conn.prepareStatement("DELETE FROM `AuthorTable` WHERE ID = ?");
			st.setInt(1, it.getId());
			st.executeUpdate();
			logger.error("post_execution");
		}catch (SQLException e){
			logger.error("Failure to execute: " + it.getFirstName() + "\n" + e.getMessage());
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<AuditEntry> auditTrail(Author author) throws SQLException {
		List<AuditEntry> list = new ArrayList<AuditEntry>();
		conn = MsDs.getConnection();

		try {
			stmt = conn.prepareStatement("SELECT * FROM `autdit_trail` WHERE `autdit_trail`.`record_id` = " + author.getId() + " ORDER BY `date_added` ASC");
			res = stmt.executeQuery("SELECT * FROM `autdit_trail` WHERE `autdit_trail`.`record_id` = " + author.getId() + " ORDER BY `date_added` ASC");
			while(res.next()) {
				//fetch the next record into rs
				list.add(new AuditEntry(res.getString("record_type"),res.getTimestamp("date_added"),
						res.getString("entry_msg")));
			}
		} catch(SQLException e) {
			logger.error("Failed reading database" + e);

			//handle the exception
		} finally {
			//be sure to close the objects
			if(res != null)
				res.close();
			if(stmt != null)
				stmt.close();
			if(conn != null) {
				conn.close();
			}
		}
		return list;
	}
}
