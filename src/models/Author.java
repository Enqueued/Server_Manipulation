/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import Audit_Trail.AuditEntry;
import SqL.AuthorTableGateway;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ultimaq
 */
public class Author {
	
	private int id;
	private String firstName;
	private String lastName;
	private String dob;
	private String gender;
	private String webSite;
	
	//empty constructor
	public Author(){
		firstName = "";
		lastName = "";
		dob = "";
		gender = "";
		webSite = "";
	}
	
	public Author(String firstName, String lastName, String dob, String gender, String webSite){
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.gender = gender;
		this.webSite = webSite;
	}
	
	//Setters:
	public void setId(int thing){
		this.id = thing;
	}
	
	public void setFirstName(String thing){
		this.firstName = thing;
	}
	
	public void setLastName(String thing){
		this.lastName = thing;
	}
	
	public void setDoB (String thing){
		this.dob = thing;
	}
	
	public void setGender(String thing){
		this.gender = thing;
	}
	
	public void setWebSite(String thing){
		this.webSite = thing;
	}
	
	//Getters:
	public int getId(){
		return id;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getDoB(){
		return dob;
	}
	
	public String getGender(){
		return gender;
	}
	
	public String getWebSite(){
		return webSite;
	}
	
	@Override
	public String toString(){
		return id + " : " + firstName + " " + lastName + " : Born: "
			+ dob + " : " + gender + " : " + webSite;
	}

	public List<AuditEntry> getAuditTrail(Author auth, AuthorTableGateway gate) throws SQLException{
		return gate.auditTrail(auth);
	}
	
}
