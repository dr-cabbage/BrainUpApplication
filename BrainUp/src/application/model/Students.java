package application.model;
import java.util.ArrayList;

public class Students {
	String name;
	String username;
	int classCode;
	ArrayList<Assignments> assignments;
	
	public Students(String n, String u, int cl) {
		this.name = n;
		this.username = u;
		this.classCode = cl;
		this.assignments = new ArrayList<Assignments>();
	}
	
	public String toCsv() {
		String str = name + "," + username + "," + classCode + "\n";
		return str;
		
	}
}
