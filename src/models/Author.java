/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import Audit_Trail.AuditEntry;
import SqL.AuthorTableGateway;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author ultimaq
 */
public class Author {
	
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private LocalDateTime lastMod;
	private String gender;
	private String webSite;

	//empty constructor
	public Author(){
		firstName = "";
		lastName = "";
		dob = null;
		gender = "";
		webSite = "";
		lastMod = null;
	}

	/**
	 * This is what you call when the date-busters are not around
	 * @param firstName
	 * @param lastName
	 * @param dob
	 * @param gender
	 * @param webSite
	 * @param id
	 * @param lastMod
	 */
	public Author(String firstName, String lastName, Date dob, String gender, String webSite, int id, LocalDateTime lastMod){
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.webSite = webSite;
		this.id = id;
		this.lastMod = lastMod;
		if(dob == null){
			this.dob = null;
		}else{
			this.dob = new Date(dob.getTime()).toLocalDate();
		}
	}

	/**
	 * this is going to be the one to call if date is perfect
	 * @param firstName
	 * @param lastName
	 * @param dob
	 * @param gender
	 * @param webSite
	 * @param id
	 * @param lastMod
	 */
	public Author(String firstName, String lastName, LocalDate dob, String gender, String webSite, int id, LocalDateTime lastMod){
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.webSite = webSite;
		this.id = id;
		this.lastMod = lastMod;
		this.dob = dob;
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formatter = formatter.withLocale(Locale.US);
		LocalDate date = LocalDate.parse(thing, formatter);
		this.dob = date;
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
	
	public Date getDoB() {
	    if(this.dob == null){
	    	return null;
		}
		Date dob = java.sql.Date.valueOf(this.dob);
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

	public LocalDateTime getLastMod() {
		return lastMod;
	}

	public void setLastMod(LocalDateTime lastMod) {
		this.lastMod = lastMod;
	}
}
