package application.model;
import java.io.*;
import java.util.ArrayList;

/**
 * Heart of BrainUp
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

public class Login {
	ArrayList<Classes> classes;
	
	/**
	 * constructor for login
	 * 
	 * @throws IOException
	 */
	public Login() throws IOException {
		this.classes = new ArrayList<Classes>();
		importClasses();
		update();
	}
	
	/**
	 * makes sure all information is up to date any time you use any functions in login
	 * don't mess with this one if you want to keep your data 
	 * 
	 * @throws IOException
	 */
	public void importClasses() throws IOException{
		File f1 = new File("data/classes.csv");
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				String[] str = line.split(csvSplit);
				this.classes.add(new Classes(str[0], str[2], Integer.valueOf(str[3]), Integer.valueOf(str[1])));
			}
		}finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File f4 = new File("data/syllabus.csv");
		BufferedReader br3 = null;
		try {
			br3 = new BufferedReader(new FileReader(f4));
			while ((line = br3.readLine()) != null) {
				String[] str3 = line.split(csvSplit);
				for(int i = 0; i < classes.size(); i++) {
					if(Integer.valueOf(str3[0]) == classes.get(i).classCode) {
						classes.get(i).syllabus = new Syllabus(Integer.valueOf(str3[0]), Double.valueOf(str3[1]), Double.valueOf(str3[2]), Double.valueOf(str3[3]), Double.valueOf(str3[4]), Double.valueOf(str3[5]), Double.valueOf(str3[6]));
					}
				}
			}
		}finally {
			try {
				if(br3 != null) {
					br3.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		File f2 = new File("data/students.csv");
		BufferedReader br1 = null;
		try {
			br1 = new BufferedReader(new FileReader(f2));
			while ((line = br1.readLine()) != null) {
				String[] str1 = line.split(csvSplit);
				for(int i = 0; i < classes.size(); i++) {
					if(Integer.toString(classes.get(i).classCode).equals(str1[2])) {
						addStudent(str1[0], str1[1], i, Integer.valueOf(str1[2]));
					}
				}
			}
		}finally {
			try {
				if(br1 != null) {
					br1.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		File f3 = new File("data/assignments.csv");
		BufferedReader br2 = null;
		try {
			br2 = new BufferedReader(new FileReader(f3));
			while ((line = br2.readLine()) != null) {
				String[] str2 = line.split(csvSplit);
				for(int i = 0; i < classes.size();i++){
					if(Integer.toString(classes.get(i).classCode).equals(str2[0])) {
						if(classes.get(i).prof.equals(str2[1])) {
							if(str2[6].equals("yes")) {
								classes.get(i).assignments.add(new Assignments(str2[2], str2[3], Integer.valueOf(str2[4]), Integer.valueOf(str2[0]), str2[1], Integer.valueOf(str2[5]), true, Integer.valueOf(str2[7])));
							}
							else {
								classes.get(i).assignments.add(new Assignments(str2[2], str2[3], Integer.valueOf(str2[4]), Integer.valueOf(str2[0]), str2[1], Integer.valueOf(str2[5]), false, Integer.valueOf(str2[7])));
							}
						}
						else {
							for(int j = 0; j < classes.get(i).stu.size(); j++) {
								if(classes.get(i).stu.get(j).username.equals(str2[1])){
									if(str2[6].equals("yes")) {
										addAssignment(str2[2], str2[3], Integer.valueOf(str2[4]), Integer.valueOf(str2[0]), str2[1], Integer.valueOf(str2[5]), true, Integer.valueOf(str2[7]), i, j);
									}
									else {
										addAssignment(str2[2], str2[3], Integer.valueOf(str2[4]), Integer.valueOf(str2[0]), str2[1], Integer.valueOf(str2[5]), false, Integer.valueOf(str2[7]), i, j);
									}
								}
							}
						}
					}
				}
			}
		}finally {
			try {
				if(br2 != null) {
					br2.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This function checks username and password to login
	 * 
	 * @param user : String
	 * @param pass : String
	 * @return
	 * @throws IOException
	 */
	public boolean userPassCheck(String user, String pass) throws IOException{
		File f1 = new File("data/usrpass.csv");
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				String[] str = line.split(csvSplit);
				if(str[1].equals(user) && str[2].equals(pass)) {
					return true;
				}
			}
		}finally {
			if(br != null) {
				try {
					br.close();
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * returns the full name of the user
	 * 
	 * @param usr : String
	 * @return
	 * @throws IOException
	 */
	public String findFull(String usr) throws IOException {
		File f1 = new File("data/usrpass.csv");
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				String[] str = line.split(csvSplit);
				if(str[1].equals(usr)) {
					return str[0];
				}
			}
		}finally {
			if(br != null) {
				try {
					br.close();
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	/**
	 * this makes sure a username does not already exist, use this if you want to change the username
	 * 
	 * @param user : String
	 * @return
	 * @throws IOException
	 */
	public boolean alreadyExists(String user) throws IOException{
		File f1 = new File("data/usrpass.csv");
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				String[] str = line.split(csvSplit);
				if(str[1].equals(user)) {
					return true;
				}
			}
		}finally {
			if(br != null) {
				try {
					br.close();
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * checks if student is already in class before adding them
	 * 
	 * @param cl : Int
	 * @param u : String
	 * @return
	 */
	public boolean stuInClass(int cl, String u) {
		int k = findClass(cl);
		for(int i = 0; i < classes.get(k).stu.size(); i++) {
			if(classes.get(k).stu.get(i).username.equals(u)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * finds a specific class... intended to be used once per function and saved to a variable
	 * 
	 * @param cl : Int
	 * @return
	 */
	public int findClass(int cl) {
		for(int i =0; i < this.classes.size(); i++) {
			if(classes.get(i).classCode == cl) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * finds student in class so you can call this once and set it equal to a variable 
	 * 
	 * @param i : Int
	 * @param usr : String
	 * @return
	 */
	public int findStu(int i, String usr) {
		for(int j = 0; j < this.classes.get(i).stu.size(); j++) {
			if(classes.get(i).stu.get(j).username.equals(usr)) {
				return j;
			}
		}
		return -1;
	}
	
	/**
	 * adds a student to a class and gives them all assignments
	 * 
	 * @param name : String
	 * @param username : String
	 * @param i : Int
	 * @param cl : Int
	 * @throws IOException
	 */
	public void addStudent(String name, String username, int i, int cl) throws IOException {
		classes.get(i).stu.add(new Students(name, username, cl));
		for(int j = 0; j < classes.get(i).assignments.size();j++){
			classes.get(i).stu.get(findStu(i, username)).assignments.add(new Assignments(classes.get(i).assignments.get(j).type, classes.get(i).assignments.get(j).name, classes.get(i).assignments.get(j).possible, cl, username, classes.get(i).assignments.get(j).grade, classes.get(i).assignments.get(j).graded, classes.get(i).assignments.get(j).fakeGrade));
		}
	}
	
	/**
	 * for new assignments being created by professors... distributes assignment to all students
	 * 
	 * @param type : String
	 * @param name : String
	 * @param possible : Int
	 * @param classcode : Int
	 * @param username : String
	 * @throws IOException
	 */
	public void newAssignment(String type, String name, int possible, int classcode, String username) throws IOException {
		int k = findClass(classcode);
		//think of the classes array of assignments as the professors copy of the assignments
		classes.get(k).assignments.add(new Assignments(type, name, possible, classcode, username, 0, false, 0));
		for(int i = 0; i < classes.get(k).stu.size(); i++) {
			//the student ArrayList of assignments lets each student have a specific assignment rather than it being all bunched up together
			classes.get(k).stu.get(i).assignments.add(new Assignments(type, name, possible, classcode, classes.get(k).stu.get(i).username, 0, false, 0));
		}
		update();
	}
	
	/**
	 * gets a specific students grade for a class
	 * 
	 * @param cl : Int
	 * @param user : String
	 * @return
	 */
	public String getGrade(int cl, String user) {
		int i = findClass(cl);
		return classes.get(i).getStuGrade(user);
	}
	
	/**
	 * for adding assignments from csv files
	 * 
	 * @param type : String
	 * @param name : String
	 * @param possible : Int
	 * @param classcode : Int
	 * @param username : String
	 * @param grade : Int
	 * @param graded : boolean
	 * @param fake : Int
	 * @param i : Int
	 * @param j : Int
	 * @throws IOException
	 */
	public void addAssignment(String type, String name, int possible, int classcode, String username, int grade, boolean graded, int fake, int i, int j) throws IOException {
		classes.get(i).stu.get(j).assignments.add(new Assignments(type, name, possible, classcode, username, grade, graded, fake));
	}
	
	/**
	 * checks if user is professor or student, 1 if student, 0 if professor, and -1 if they do not exist
	 * 
	 * @param username : String
	 * @return : Int
	 * @throws IOException
	 */
	public int isStudent(String username) throws IOException {
		File f1 = new File("data/usrpass.csv");
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				String[] str = line.split(csvSplit);
				if(str[1].equals(username)) {
					if(str[3].equals("true")) {
						return 1;
					}
					else {
						return 0;
					}
		}
			}}finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	/**
	 * returns "name username" of all students in a class in an ArrayList
	 * 
	 * @param cl : Int
	 * @return : ArrayList<String>
	 */
	public ArrayList<String> getStudents(int cl){
		ArrayList<String> str = new ArrayList<String>();
		int k = findClass(cl);
		for(int i = 0; i < classes.get(k).stu.size(); i++) {
			str.add(classes.get(k).stu.get(i).name + " " + classes.get(k).stu.get(i).username);
		}
		return str;
	}
	
	/**
	 * adds a user when they sign up
	 * 
	 * @param full : String
	 * @param u : String
	 * @param p : String
	 * @param stu : boolean
	 * @throws IOException
	 */
	public void addUser(String full, String u, String p, boolean stu) throws IOException{
		FileWriter pw = new FileWriter("data/usrpass.csv", true);
		pw.append(full);
		pw.append(",");
		pw.append(u);
		pw.append(",");
		pw.append(p);
		pw.append(",");
		if(stu) {
			pw.append("true");
		}
		else {
			pw.append("false");
		}
		pw.append("\n");
		pw.flush();
		pw.close();
	}
	
	/**
	 * this returns the users grades... it'll be useful for separating the assignment from the grade in order to edit it
	 * 
	 * @param cl : Int
	 * @param user : String
	 * @return : ArrayList<String>
	 * @throws IOException
	 */
	public ArrayList<String> getUserGrades(int cl, String user) throws IOException{
		ArrayList<String> str = new ArrayList<String>();
		int i = findClass(cl);
		int j = findStu(i, user);
		for(int k = 0; k < classes.get(i).stu.get(j).assignments.size(); k++) {
			if(classes.get(i).stu.get(j).assignments.get(k).fakeGrade != 0) {
				str.add(Integer.toString(classes.get(i).stu.get(j).assignments.get(k).fakeGrade) + "/" + Integer.toString(classes.get(i).stu.get(j).assignments.get(k).possible));
			}
			else {
				str.add(Integer.toString(classes.get(i).stu.get(j).assignments.get(k).grade) + "/" + Integer.toString(classes.get(i).stu.get(j).assignments.get(k).possible));
			}
		}
		return str;
	}
	
	/**
	 * returns arraylist of grades of certain type
	 * 
	 * @param cl : Int
	 * @param user : String
	 * @param assignmentType : String
	 * @return : ArrayList<String>
	 * @throws IOException
	 */
	public ArrayList<String> getUserGradeType(int cl, String user, String assignmentType) throws IOException {
		ArrayList<String> str = new ArrayList<String>();
		for(int i = 0; i < classes.size(); i++) {
			if(classes.get(i).classCode == cl) {
				for(int j = 0; j < classes.get(i).stu.size(); j++) {
					if(classes.get(i).stu.get(j).username.equals(user)) {
						for(int k = 0; k < classes.get(i).stu.get(j).assignments.size(); k++) {
							if ((assignmentType.compareTo(classes.get(i).stu.get(j).assignments.get(k).type)) == 0)
								str.add(Integer.toString(classes.get(i).stu.get(j).assignments.get(k).grade));
						}
					}
				}
			}
		}
		return str;
	}
	
	/**
	 * checks if the assignment exists
	 * 
	 * @param cl : Int
	 * @param assignName : String
	 * @return : boolean
	 */
	public boolean assignmentExists(int cl, String assignName) {
		int i = findClass(cl);
		for(int j = 0; j < classes.get(i).assignments.size(); j++) {
			if(classes.get(i).assignments.get(j).name.equals(assignName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * gets the grade of a singular assignment
	 * 
	 * @param cl : Int
	 * @param usr : String
	 * @param assignName : String
	 * @return : String
	 */
	public String getAssignGrade(int cl, String usr, String assignName) {
		int i = findClass(cl);
		int j = findStu(i, usr);
		for(int k = 0; k < classes.get(i).stu.get(j).assignments.size(); k++) {
			if(classes.get(i).stu.get(j).assignments.get(k).name.equals(assignName)) {
				return Integer.toString(classes.get(i).stu.get(j).assignments.get(k).grade);
			}
		}
		return "";
	}
	
	/**
	 * this returns all assignments for a specific user... for students it is automatically the user signed in.
	 * 
	 * @param cl : Int
	 * @param user : String
	 * @return : ArrayList<String>
	 * @throws IOException
	 */
	public ArrayList<String> getUserAssignments(int cl, String user) throws IOException {
		ArrayList<String> str = new ArrayList<String>();
		for(int i = 0; i < classes.size(); i++) {
			if(classes.get(i).classCode == cl) {
				for(int j = 0; j < classes.get(i).stu.size(); j++) {
					if(classes.get(i).stu.get(j).username.equals(user)) {
						for(int k = 0; k < classes.get(i).stu.get(j).assignments.size(); k++) {
							str.add(classes.get(i).stu.get(j).assignments.get(k).name + " Type: " + classes.get(i).stu.get(j).assignments.get(k).type);
						}
					}
				}
			}
		}
		return str;
	}
	
	/**
	 * this returns the name and class code of all classes a specific student is in... also checks if user is a professor
	 * 
	 * @return : ArrayList<String>
	 * @throws IOException
	 */
	public ArrayList<String> getUserClasses() throws IOException {
		String user = getUser();
		ArrayList<String> str = new ArrayList<String>();
		ArrayList<Integer> cl = new ArrayList<Integer>();
		if(isStudent(user) == 0) {
			for(int i = 0; i < classes.size(); i++) {
				if(classes.get(i).prof.equals(user)) {
					str.add(classes.get(i).name + " " + classes.get(i).classCode);
				}
			}
		}

		File f1 = new File("data/students.csv");
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				String[] str1 = line.split(csvSplit);
				if(str1[1].equals(user)) {
					cl.add(Integer.valueOf(str1[2]));
				}
			}
		}finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		for(int i =0; i < classes.size(); i++) {
			for(int j = 0; j < cl.size(); j++) {
				if(classes.get(i).classCode == cl.get(j)) {
					str.add(classes.get(i).name + " " + classes.get(i).classCode);
				}
			}
		}
		return str;
		
	}
	
	/**
	 * this is really important as it will keep everything up to date as it is used
	 * 
	 * @throws IOException
	 */
	public void update() throws IOException{
		FileWriter pw = new FileWriter("data/students.csv", true);
		FileWriter pw1 = new FileWriter("data/classes.csv", true);
		FileWriter pw2 = new FileWriter("data/assignments.csv", true);
		FileWriter pw3 = new FileWriter("data/syllabus.csv", true);

		FileWriter pw4 = new FileWriter("data/syllabus.csv");
		FileWriter pw5 = new FileWriter("data/students.csv");
		FileWriter pw6 = new FileWriter("data/classes.csv");
		FileWriter pw7 = new FileWriter("data/assignments.csv");
		pw4.append("");
		pw5.append("");
		pw6.append("");
		pw7.append("");
		for(int i = 0; i < classes.size(); i++) {
			pw1.append(classes.get(i).toCsv());
			pw3.append(classes.get(i).syllabus.toCSV());
			for(int p = 0; p < classes.get(i).assignments.size();p++) {
				pw2.append(classes.get(i).assignments.get(p).toCsv());
			}
			for(int j = 0; j < classes.get(i).stu.size(); j++) {
				pw.append(classes.get(i).stu.get(j).toCsv());
				for(int k =0; k < classes.get(i).stu.get(j).assignments.size(); k++) {
					pw2.append(classes.get(i).stu.get(j).assignments.get(k).toCsv());
				}
			}
		}
		pw.flush();
		pw1.flush();
		pw2.flush();
		pw3.flush();
		pw4.flush();
		pw5.flush();
		pw6.flush();
		pw7.flush();
		pw.close();
		pw1.close();
		pw2.close();
		pw3.close();
		pw4.close();
		pw5.close();
		pw6.close();
		pw7.close();
	}
	
	/**
	 * adds a class when the professor makes one
	 * 
	 * @param name : String
	 * @param prof : String
	 * @param cl : Int
	 * @throws IOException
	 */
	public void addClass(String name, String prof, int cl) throws IOException {
		classes.add(new Classes(name, prof, 0, cl));
	}
	
	/**
	 * this saves the current user when they log in so we can access the getUser() function whenever we want
	 * 
	 * @param user : String
	 * @param classCode : String
	 * @param full : String
	 * @throws IOException
	 */
	public void saveUser(String user, String classCode, String full) throws IOException {
		FileWriter pw = new FileWriter("data/currUser.csv");
		pw.append(user);
		pw.append(",");
		pw.append(classCode);
		pw.append(",");
		pw.append(full);
		pw.flush();
		pw.close();
	}
	
	/**
	 * gets first name, this will be used in the profile part of our project
	 * 
	 * @param username : String
	 * @return : String
	 * @throws IOException
	 */
	public String getName(String username) throws IOException{
		File f1 = new File("data/usrpass.csv");
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				String[] str = line.split(csvSplit);
				if(str[1].equals(username)) {
					return str[0];
				}
			}
		}finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * adds student to class when student is already in one class
	 *  
	 * @param cl : String
	 * @throws IOException
	 */
	public void addToClass(String cl) throws IOException{
		File f1 = new File("data/currUser.csv");
		int k = findClass(Integer.valueOf(cl));
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				String[] str = line.split(csvSplit);
				addStudent(str[2], str[0], k, Integer.valueOf(cl));
		}
			}finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		update();
	}
	
	/**
	 * returns the current user
	 * 
	 * @return : String
	 * @throws IOException
	 */
	public String getUser() throws IOException {
		File f1 = new File("data/currUser.csv");
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				String[] str = line.split(csvSplit);
				return str[0];
		}
			}finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * works when creating and editing the syllabus
	 * 
	 * @param cl : Int
	 * @param finalW : Double
	 * @param examW : Double
	 * @param quizW : Double
	 * @param HWW : Double
	 * @param labW : Double
	 * @param otherW : Double
	 * @throws IOException
	 */
	public void editSyllabus(int cl, double finalW, double examW, double quizW, double HWW, double labW, double otherW) throws IOException {
		classes.get(findClass(cl)).syllabus = new Syllabus(cl, finalW, examW, quizW, HWW, labW, otherW);
	}
	
	/**
	 * changes the grade of one assignment
	 * 
	 * @param cl : Int
	 * @param usr : String
	 * @param assignName : String
	 * @param grade : Int
	 * @throws IOException
	 */
	public void changeGrade(int cl, String usr, String assignName, int grade) throws IOException {
		int i = findClass(cl);
		int j = findStu(i, usr);
		classes.get(i).changeGrade(assignName, usr, j, grade);
		update();
	}
	
	/**
	 * initializes a single grade
	 * 
	 * @param cl : Int
	 * @param usr : String
	 * @param assignName : String
	 * @param grade : Int
	 * @throws IOException
	 */
	public void changeFakeGrade(int cl, String usr, String assignName, int grade) throws IOException {
		int i = findClass(cl);
		int j = findStu(i, usr);
		classes.get(i).changeFakeGrade(assignName, usr, j, grade);
		update();
	}
	
	/**
	 * returns the average for a single assignment in the whole class
	 * 
	 * @param cl : Int
	 * @param assignName : String
	 * @return : String
	 */
	public String assignAvgGrade(int cl, String assignName) {
		return classes.get(findClass(cl)).assignAvgGrade(assignName);
	}
	
	/**
	 * returns the whole class average
	 * 
	 * @param cl : Int
	 * @return : String
	 */
	public String classAvg(int cl) {
		return classes.get(findClass(cl)).classAvg();
	}
	
	/**
	 * drops student from course
	 * 
	 * @param cl : Int
	 * @param usr : String
	 * @throws IOException
	 */
	public void dropStudent(int cl, String usr) throws IOException {
		int i = findClass(cl);
		classes.get(i).dropStudent(usr);
		update();
	}
	
	/**
	 * edits current class
	 * 
	 * @return : String[]
	 * @throws IOException
	 */
	public String[] editClass() throws IOException {
		String str = currClass();
		int i = findClass(Integer.valueOf(str));
		if(i == -1) {
			String[] str1 = {"",""};
			return str1;
		}
		return classes.get(i).editClassPage();
	}
	
	/**
	 * returns current class
	 * 
	 * @return : String
	 * @throws IOException
	 */
	public String currClass() throws IOException{
		File f1 = new File("data/currUser.csv");
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				String[] str = line.split(csvSplit);
				return str[1];
		}
			}finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * changes name of a certain class
	 * 
	 * @param cl : Int
	 * @param newName : String
	 * @throws IOException
	 */
	public void changeClassName(int cl, String newName) throws IOException {
		int i = findClass(cl);
		classes.get(i).name = newName;
		update();
		return;
	}
	
	/**
	 * changes weight of assignment type for class
	 * 
	 * @param cl : Int
	 * @param newWeight : Int
	 * @param type : String
	 * @throws IOException
	 */
	public void changeWeight(int cl, int newWeight, String type) throws IOException {
		int i = findClass(cl);
		if(type.equals("Q")) {
			classes.get(i).syllabus.quizWeight = newWeight;
		}
		else if(type.equals("H")) {
			classes.get(i).syllabus.HWWeight = newWeight;
		}
		else if(type.equals("E")) {
			classes.get(i).syllabus.examWeight = newWeight;
		}
		else if(type.equals("L")) {
			classes.get(i).syllabus.labWeight = newWeight;
		}
		else if(type.equals("F")) {
			classes.get(i).syllabus.finalWeight = newWeight;
		}
		else if(type.equals("O")) {
			classes.get(i).syllabus.otherWeight = newWeight;
		}
		update();
	}
	
	/**
	 * assigns all fake grades and updates
	 * 
	 * @throws IOException
	 */
	public void signOut() throws IOException {
		for(int i = 0; i < classes.size(); i++) {
			for(int j = 0; j < classes.get(i).stu.size(); j++) {
				for(int k = 0; k < classes.get(i).stu.get(j).assignments.size();k++) {
					classes.get(i).stu.get(j).assignments.get(k).fakeGrade = classes.get(i).stu.get(j).assignments.get(k).grade;
				}
			}
		}
		update();
	}

	/**
	 * returns graph information for a student
	 * 
	 * @param cl : Int
	 * @param usr : String
	 * @return
	 */
	public ArrayList<String> studentGraph(int cl, String usr){
		int i = findClass(cl);
		return classes.get(i).studentGraph(usr);
	}
	
	/**
	 * changes all instances of a users information to the new info. and updates the csv's
	 * 
	 * @param ogUser : String
	 * @param name : String
	 * @param userN : String
	 * @param password : String
	 * @throws IOException
	 */
	public void editInfo(String ogUser, String name, String userN, String password) throws IOException {
		int i, size, size1;
		File f1 = new File("data/usrpass.csv");
		File f2 = new File("data/currUser.csv");
		BufferedReader br = null;
		ArrayList<String> users = new ArrayList<String>();
		String[] str = null;
		String csvSplit = ",";
		String line = "";
		String type = "";
		String newInfo = "";
		String classCode = "";
		
		// changes all instances of the users info
		if(isStudent(ogUser) == 1) {
			for(int a=0;a<classes.size();a++) {
				i = findStu(a, ogUser);
				if(i != -1) {
					size = classes.get(a).stu.get(i).assignments.size();
					for(int b=0;b<size;b++) {
						classes.get(a).stu.get(i).assignments.get(b).student = userN;
					}
					size1 = classes.get(a).assignments.size();
					for(int c=0;c<size1;c++) {
						if(classes.get(a).assignments.get(c).student.equals(ogUser)) {
							classes.get(a).assignments.get(c).student = userN;
						}
					}
					classes.get(a).stu.get(i).username = userN;
					classes.get(a).stu.get(i).name = name;
				}
			}	
		}else if(isStudent(ogUser) == 0) {
			for(int d=0;d<classes.size();d++) {
				if(classes.get(d).prof.equals(ogUser)) {
					classes.get(d).prof = userN;
				}
			}
		}
		
		// saves all the other users information
		try {
			br = new BufferedReader(new FileReader(f1));
			while((line = br.readLine()) != null) {
				str = line.split(csvSplit);
				if(!(str[1].equals(ogUser))) {
					users.add(line + "\n");
				}else {
					type = str[3];
				}
			}
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		newInfo = name + "," + userN + "," + password + "," + type + "\n";
		users.add(newInfo);
		
		// updates the usrpass.csv information
		FileWriter fw = new FileWriter(f1);
		for(int e=0;e<users.size();e++) {
			fw.append(users.get(e));
		}
		fw.flush();
		fw.close();
		
		// accesses the users class code
		try {
			br = new BufferedReader(new FileReader(f2));
			while((line = br.readLine()) != null) {
				str = line.split(csvSplit);
				classCode = str[1];
			}
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// updates the currUser.csv info
		saveUser(userN, classCode, name);
		// updates all the other csv files
		update();
	
	}
	
	/**
	 * returns the information from usrpass.csv for current user
	 * 
	 * @return : String[]
	 * @throws IOException
	 */
	public String[] getInfo() throws IOException {
		String userN = getUser();
		String[] str = null;
		File f1 = new File("data/usrpass.csv");
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try {
			br = new BufferedReader(new FileReader(f1));
			while ((line = br.readLine()) != null) {
				str = line.split(csvSplit);
				if(str[1].equals(userN)) {
					return str;
				}
			}
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

}
	