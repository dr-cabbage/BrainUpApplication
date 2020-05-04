package application.model;
import java.util.ArrayList;

public class Classes {
	String name;
	ArrayList<Students> stu;
	String prof;
	int numStu;
	int classCode;
	ArrayList<Assignments> assignments = new ArrayList<Assignments>();
	Syllabus syllabus;
	
	public Classes(String n, String p, int s, int cl) {
		this.name = n;
		this.prof = p;
		this.numStu = s;
		this.classCode = cl;
		this.stu = new ArrayList<Students>();
	}
	public void gradeAssignment(String studentID, String assignmentName, int grade) {
		int i = getStudent(studentID);
		int j = getAssignment(assignmentName);
		if(i == -1 || j == -1) {
			return;
		}
		stu.get(i).assignments.get(j).graded = true;
		stu.get(i).assignments.get(j).grade = grade;
	}
	public String toCsv() {
		String str = name + "," + Integer.toString(classCode) + "," + prof + "," + Integer.toString(stu.size());
		for(int i = 0; i < stu.size(); i++) {
			str += "," + stu.get(i).username;
		}
		str += "\n";
		return str;
	}
	public int getStudent(String usr) {
		for(int i = 0; i < stu.size(); i++) {
			if(stu.get(i).username.equals(usr)) {
				return i;
			}
		}
		return -1;
	}
	public int getAssignment(String assignmentName) {
		for(int i = 0; i < assignments.size(); i++) {
			if(assignments.get(i).name.equals(assignmentName)) {
				return i;
			}
		}
		return -1;
	}
	public String getStuGrade(String username) {
		int i = getStudent(username);
		double finalGrade = 0, exams = 0, quizzes = 0, HW = 0, labs = 0, other = 0;
		int numExams = 0, numQuizzes = 0, numHW = 0, numLabs = 0, numOther = 0, numFinal = 0;
		for(int j = 0; j < stu.get(i).assignments.size(); j++) {
			if(stu.get(i).assignments.get(j).graded) {
				switch(stu.get(i).assignments.get(j).type) {
				case "F":
					finalGrade += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
					numFinal++;
					continue;
				case "E":
					exams += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
					numExams++;
					continue;
				case "Q":
					quizzes += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
					numQuizzes++;
					continue;
				case "L":
					labs += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
					numLabs++;
					continue;
				case "H":
					HW += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
					numHW++;
					continue;
				case "O":
					other += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
					numOther++;
				}
			}
		}
		if(numExams != 0) {
			exams = (exams * syllabus.examWeight) / numExams;
		}
		else {
			exams = syllabus.examWeight;
		}
		if(numQuizzes != 0) {
			quizzes = (quizzes * syllabus.quizWeight) / numQuizzes;
		}
		else {
			quizzes = syllabus.quizWeight;
		}
		if(numHW != 0) {
			HW = (HW * syllabus.HWWeight) / numHW;
		}
		else {
			HW = syllabus.HWWeight;
		}
		if(numLabs != 0) {
			labs = (labs * syllabus.labWeight) / numLabs;
		}
		else {
			labs = syllabus.labWeight;
		}
		if(numOther != 0) {
			other = (other * syllabus.otherWeight) / numOther; 
		}
		else {
			other = syllabus.otherWeight;
		}
		if(numFinal == 0) {
			finalGrade = syllabus.finalWeight;
		}
		else {
			finalGrade = finalGrade * (syllabus.finalWeight);
		}
		double grade = exams + quizzes + HW + labs + other + finalGrade;
		return Double.toString(grade);
	}
	public ArrayList<String> listStudents(){
		ArrayList<String> str = new ArrayList<String>();
		for(int i = 0; i < stu.size(); i++) {
			str.add(stu.get(i).name);
		}
		return str;
	}
	public void changeGrade(String assignName, String usr, int i, int gr) {
		for(int j = 0; j < stu.get(i).assignments.size(); j++) {
			if(stu.get(i).assignments.get(j).name.equals(assignName)) {
				stu.get(i).assignments.get(j).grade = gr;
				stu.get(i).assignments.get(j).graded = true;
				return;
			}
		}
	}
	public String assignAvgGrade(String assignName) {
		double avg = 0.0;
		double numStu = stu.size();
		int j = getAssignment(assignName);
		for(int i = 0; i < numStu; i++) {
			avg += stu.get(i).assignments.get(j).grade;
		}
		return Double.toString(avg/numStu);
	}
	public String classAvg() {
		double avg = 0.0;
		for(int i = 0; i < stu.size(); i++) {
			avg += Double.valueOf(getStuGrade(stu.get(i).username));
		}
		double j = stu.size();
		return Double.toString(avg / j);
	}
	public void dropStudent(String username) {
		int i = getStudent(username);
		stu.remove(i);
	}
}
