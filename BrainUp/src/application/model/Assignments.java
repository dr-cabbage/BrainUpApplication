package application.model;

public class Assignments {
	String type;
	String name;
	int possible;
	int classCode;
	int grade;
	String student;
	boolean graded;
	
	public Assignments(String ty, String n, int p, int cl, String user, int gr, boolean grad) {
		this.type = ty;
		this.name = n;
		this.possible = p;
		this.classCode = cl;
		this.student = user;
		this.grade = gr;
		this.graded = grad;
	}
	
	public String toCsv() {
		String str = Integer.toString(classCode) + "," + student + "," + type + "," + name + "," + Integer.toString(possible) + "," + Integer.toString(grade) + ",";
		if(graded) {
			str += "yes";
		}
		else {
			str += "no";
		}
		str += "\n";
		return str;
	}
}
