package application.model;
import java.util.ArrayList;

/**
 * Holds Student Information
 * 
 * @author GroupUs
 * @author Kale King
 * @author Ian Solis
 * @author Jose Morales
 * @author Seyma Oz
 * @author Claudio Ordaz
 * 
 * UTSA CS 3443 - Group Project
 * Spring 2020
 */

public class Students {
	String name;
	String username;
	int classCode;
	ArrayList<Assignments> assignments;
	
	/**
	 * Student constructor
	 * 
	 * @param n : String
	 * @param u : String
	 * @param cl : Int
	 */
	public Students(String n, String u, int cl) {
		this.name = n;
		this.username = u;
		this.classCode = cl;
		this.assignments = new ArrayList<Assignments>();
	}
	
	/**
	 * formats student information for csv file
	 * 
	 * @return : String
	 */
	public String toCsv() {
		String str = name + "," + username + "," + classCode + "\n";
		return str;
		
	}
}
