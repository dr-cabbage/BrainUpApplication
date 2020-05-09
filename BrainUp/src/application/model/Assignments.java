package application.model;

/**
 * Holds Assignment Information
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

public class Assignments {
	String type;
	String name;
	int possible;
	int classCode;
	int grade;
	String student;
	boolean graded;
	int fakeGrade;
	
	/**
	 * Assignment constructor
	 * 
	 * @param ty : String
	 * @param n : String
	 * @param p : Int
	 * @param cl : Int
	 * @param user : String
	 * @param gr : Int
	 * @param grad : boolean
	 * @param fake : Int
	 */
	public Assignments(String ty, String n, int p, int cl, String user, int gr, boolean grad, int fake) {
		this.type = ty;
		this.name = n;
		this.possible = p;
		this.classCode = cl;
		this.student = user;
		this.grade = gr;
		this.graded = grad;
		this.fakeGrade = fake;
	}
	
	/**
	 * formats assigment information for csv file
	 * 
	 * @return : String
	 */
	public String toCsv() {
		String str = Integer.toString(classCode) + "," + student + "," + type + "," + name + "," + Integer.toString(possible) + "," + Integer.toString(grade) + ",";
		if(graded) {
			str += "yes,";
		}
		else {
			str += "no,";
		}
		str += Integer.toString(fakeGrade) + "\n";
		return str;
	}
}
