package application.model;

public class Assignments {
	String type;
	String name;
	int possible;
	int classCode;
	int grade;
	String student;
	boolean graded;
	int fakeGrade;
	
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
