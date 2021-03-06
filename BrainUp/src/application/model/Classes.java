package application.model;
import java.util.ArrayList;

/**
 * Holds classes and respective students
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

public class Classes {
	String name;
	ArrayList<Students> stu;
	String prof;
	int numStu;
	int classCode;
	ArrayList<Assignments> assignments = new ArrayList<Assignments>();
	Syllabus syllabus;
	
	/**
	 * classes constructor
	 * 
	 * @param n : String
	 * @param p : String
	 * @param s : Int
	 * @param cl : Int
	 */
	public Classes(String n, String p, int s, int cl) {
		this.name = n;
		this.prof = p;
		this.numStu = s;
		this.classCode = cl;
		this.stu = new ArrayList<Students>();
	}
	
	/**
	 * grades a specific assignment for specific student
	 * 
	 * @param studentID : String
	 * @param assignmentName : String
	 * @param grade : Int
	 */
	public void gradeAssignment(String studentID, String assignmentName, int grade) {
		int i = getStudent(studentID);
		int j = getAssignment(assignmentName);
		if(i == -1 || j == -1) {
			return;
		}
		stu.get(i).assignments.get(j).graded = true;
		stu.get(i).assignments.get(j).grade = grade;
	}
	
	/**
	 * formats class information for csv files
	 * 
	 * @return : String
	 */
	public String toCsv() {
		String str = name + "," + Integer.toString(classCode) + "," + prof + "," + Integer.toString(stu.size());
		for(int i = 0; i < stu.size(); i++) {
			str += "," + stu.get(i).username;
		}
		str += "\n";
		return str;
	}
	
	/**
	 * returns index of a student
	 * 
	 * @param usr : String
	 * @return : Int
	 */
	public int getStudent(String usr) {
		for(int i = 0; i < stu.size(); i++) {
			if(stu.get(i).username.equals(usr)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * returns index of an assignment
	 * 
	 * @param assignmentName : String
	 * @return : Int
	 */
	public int getAssignment(String assignmentName) {
		for(int i = 0; i < assignments.size(); i++) {
			if(assignments.get(i).name.equals(assignmentName)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * establishes information for graph for student
	 * 
	 * @param username : String
	 * @return : ArrayList<String>
	 */
	public ArrayList<String> studentGraph(String username){
		ArrayList<String> arr = new ArrayList<String>();
		int i = getStudent(username);
		double finalGrade = 0, exams = 0, quizzes = 0, HW = 0, labs = 0, other = 0;
		double finalPoints = 0, examPoints = 0, quizPoints = 0, homeworkPoints = 0, labPoints = 0, otherPoints = 0;
		int numExams = 0, numQuizzes = 0, numHW = 0, numLabs = 0, numOther = 0, numFinal = 0;
		for(int j = 0; j < stu.get(i).assignments.size(); j++) {
			if(stu.get(i).assignments.get(j).graded || stu.get(i).assignments.get(j).fakeGrade != 0) {
				if(stu.get(i).assignments.get(j).graded) {
					switch(stu.get(i).assignments.get(j).type) {
					case "F":
						finalGrade += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numFinal++;
					case "E":
						exams += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numExams++;
					case "Q":
						quizzes += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numQuizzes++;
					case "L":
						labs += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numLabs++;
					case "H":
						HW += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numHW++;
					case "O":
						other += Double.valueOf(stu.get(i).assignments.get(j).grade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numOther++;
					}
				}
				else if(stu.get(i).assignments.get(j).fakeGrade != 0) {
					switch(stu.get(i).assignments.get(j).type) {
					case "F":
						finalGrade += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numFinal++;
					case "E":
						exams += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numExams++;
					case "Q":
						quizzes += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numQuizzes++;
					case "L":
						labs += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numLabs++;
					case "H":
						HW += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numHW++;
					case "O":
						other += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numOther++;
					}
				}
			}
		
			if(numExams != 0) {
				examPoints = (exams * syllabus.examWeight) / numExams;
			}
			else {
				examPoints = syllabus.examWeight;
			}
			if(numQuizzes != 0) {
				quizPoints = (quizzes * syllabus.quizWeight) / numQuizzes;
			}
			else {
				quizPoints = syllabus.quizWeight;
			}
			if(numHW != 0) {
				homeworkPoints = (HW * syllabus.HWWeight) / numHW;
			}
			else {
				homeworkPoints = syllabus.HWWeight;
			}
			if(numLabs != 0) {
				labPoints = (labs * syllabus.labWeight) / numLabs;
			}
			else {
				labPoints = syllabus.labWeight;
			}
			if(numOther != 0) {
				otherPoints = (other * syllabus.otherWeight) / numOther; 
			}
			else {
				otherPoints = syllabus.otherWeight;
			}
			if(numFinal == 0) {
				finalPoints = syllabus.finalWeight;
			}
			else {
				finalPoints = finalGrade * (syllabus.finalWeight);
			}
			int grade = (int) (examPoints + quizPoints + homeworkPoints + labPoints + otherPoints + finalPoints);
			arr.add(Integer.toString(grade));
		}
		return arr;
	}
	
	/**
	 * returns students grades for class
	 * 
	 * @param username : String
	 * @return : String
	 */
	public String getStuGrade(String username) {
		int i = getStudent(username);
		double finalGrade = 0, exams = 0, quizzes = 0, HW = 0, labs = 0, other = 0;
		int numExams = 0, numQuizzes = 0, numHW = 0, numLabs = 0, numOther = 0, numFinal = 0;
		for(int j = 0; j < stu.get(i).assignments.size(); j++) {
			if(stu.get(i).assignments.get(j).graded || stu.get(i).assignments.get(j).fakeGrade != 0) {
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
				else if(stu.get(i).assignments.get(j).fakeGrade != 0) {
					switch(stu.get(i).assignments.get(j).type) {
					case "F":
						finalGrade += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numFinal++;
						continue;
					case "E":
						exams += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numExams++;
						continue;
					case "Q":
						quizzes += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numQuizzes++;
						continue;
					case "L":
						labs += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numLabs++;
						continue;
					case "H":
						HW += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numHW++;
						continue;
					case "O":
						other += Double.valueOf(stu.get(i).assignments.get(j).fakeGrade)/Double.valueOf(stu.get(i).assignments.get(j).possible);
						numOther++;
					}
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
	
	/**
	 * returns list of students
	 * 
	 * @return : ArrayList<String>
	 */
	public ArrayList<String> listStudents(){
		ArrayList<String> str = new ArrayList<String>();
		for(int i = 0; i < stu.size(); i++) {
			str.add(stu.get(i).name);
		}
		return str;
	}
	
	/**
	 * assigns grade for assignment
	 * 
	 * @param assignName : String
	 * @param usr : String
	 * @param i : Int
	 * @param gr : Int
	 */
	public void changeGrade(String assignName, String usr, int i, int gr) {
		for(int j = 0; j < stu.get(i).assignments.size(); j++) {
			if(stu.get(i).assignments.get(j).name.equals(assignName)) {
				stu.get(i).assignments.get(j).grade = gr;
				stu.get(i).assignments.get(j).graded = true;
				return;
			}
		}
	}
	
	/**
	 * changes the fake grade to real grade
	 * 
	 * @param assignName : String
	 * @param usr : String
	 * @param i : Int
	 * @param gr : Int
	 */
	public void changeFakeGrade(String assignName, String usr, int i, int gr) {
		for(int j = 0; j < stu.get(i).assignments.size(); j++) {
			if(stu.get(i).assignments.get(j).name.equals(assignName)) {
				stu.get(i).assignments.get(j).fakeGrade = gr;
				return;
			}
		}
	}
	
	/**
	 * assigns avg grade for an assignment
	 * 
	 * @param assignName : String
	 * @return : String
	 */
	public String assignAvgGrade(String assignName) {
		double avg = 0.0;
		double numStu = stu.size();
		int j = getAssignment(assignName);
		for(int i = 0; i < numStu; i++) {
			avg += stu.get(i).assignments.get(j).grade;
		}
		return Double.toString(avg/numStu);
	}
	
	/**
	 * returns class average
	 * 
	 * @return : String
	 */
	public String classAvg() {
		double avg = 0.0;
		for(int i = 0; i < stu.size(); i++) {
			avg += Double.valueOf(getStuGrade(stu.get(i).username));
		}
		double j = stu.size();
		return Double.toString(avg / j);
	}
	
	/**
	 * drops a student from class
	 * 
	 * @param username : String
	 */
	public void dropStudent(String username) {
		int i = getStudent(username);
		stu.remove(i);
	}
	
	/**
	 * shows weights for assignments
	 * 
	 * @return : String[]
	 */
	public String[] editClassPage() {
		String[] str = new String[10];
		str[0] = this.name;
		str[1] = Integer.toString(this.classCode);
		str[2] = Integer.toString(stu.size());
		str[3] = Integer.toString(assignments.size());
		str[4] = Double.toString(syllabus.quizWeight) + "%";
		str[5] = Double.toString(syllabus.labWeight) + "%";
		str[6] = Double.toString(syllabus.examWeight) + "%";
		str[7] = Double.toString(syllabus.HWWeight) + "%";
		str[8] = Double.toString(syllabus.finalWeight) + "%";
		str[9] = Double.toString(syllabus.otherWeight) + "%";
		return str;
	}
}
