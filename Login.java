package application.model;
import java.io.*;
import java.util.ArrayList;

public class Login {
	ArrayList<Classes> classes;
	public Login() throws IOException {
		this.classes = new ArrayList<Classes>();
		importClasses();
		update();
	}
	//makes sure all information is up to date any time you use any functions in login
	//don't mess with this one if you want to keep your data cause its a jerk
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
								classes.get(i).assignments.add(new Assignments(str2[2], str2[3], Integer.valueOf(str2[4]), Integer.valueOf(str2[0]), str2[1], Integer.valueOf(str2[5]), true));
							}
							else {
								classes.get(i).assignments.add(new Assignments(str2[2], str2[3], Integer.valueOf(str2[4]), Integer.valueOf(str2[0]), str2[1], Integer.valueOf(str2[5]), false));
							}
						}
						else {
							for(int j = 0; j < classes.get(i).stu.size(); j++) {
								if(classes.get(i).stu.get(j).username.equals(str2[1])){
									if(str2[6].equals("yes")) {
										addAssignment(str2[2], str2[3], Integer.valueOf(str2[4]), Integer.valueOf(str2[0]), str2[1], Integer.valueOf(str2[5]), true, i, j);
									}
									else {
										addAssignment(str2[2], str2[3], Integer.valueOf(str2[4]), Integer.valueOf(str2[0]), str2[1], Integer.valueOf(str2[5]), false, i, j);
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
	
	//This function checks username and password to login
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
	//this makes sure a username does not already exist, use this if you want to change the username
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
	//checks if student is already in class before adding them
	public boolean stuInClass(int cl, String u) {
		int k = findClass(cl);
		for(int i = 0; i < classes.get(k).stu.size(); i++) {
			if(classes.get(k).stu.get(i).username.equals(u)) {
				return true;
			}
		}
		return false;
	}
	//finds a specific class... intended to be used once per function and saved to a variable
	public int findClass(int cl) {
		for(int i =0; i < this.classes.size(); i++) {
			if(classes.get(i).classCode == cl) {
				return i;
			}
		}
		return -1;
	}
	//finds student in class so you can call this once and set it equal to a variable 
	public int findStu(int i, String usr) {
		for(int j = 0; j < this.classes.get(i).stu.size(); j++) {
			if(classes.get(i).stu.get(j).username.equals(usr)) {
				return j;
			}
		}
		return -1;
	}
	//adds a student to a class and gives them all assignments
	public void addStudent(String name, String username, int i, int cl) throws IOException {
		classes.get(i).stu.add(new Students(name, username, cl));
		for(int j = 0; j < classes.get(i).assignments.size();j++){
			classes.get(i).stu.get(findStu(i, username)).assignments.add(new Assignments(classes.get(i).assignments.get(j).type, classes.get(i).assignments.get(j).name, classes.get(i).assignments.get(j).possible, cl, username, classes.get(i).assignments.get(j).grade, classes.get(i).assignments.get(j).graded));
		}
	}
	//for new assignments being created by professors... distributes assignment to all students
	public void newAssignment(String type, String name, int possible, int classcode, String username) throws IOException {
		int k = findClass(classcode);
		//think of the classes array of assignments as the professors copy of the assignments
		classes.get(k).assignments.add(new Assignments(type, name, possible, classcode, username, 0, false));
		for(int i = 0; i < classes.get(k).stu.size(); i++) {
			//the student ArrayList of assignments lets each student have a specific assignment rather than it being all bunched up together
			classes.get(k).stu.get(i).assignments.add(new Assignments(type, name, possible, classcode, classes.get(k).stu.get(i).username, 0, false));
		}
		update();
	}
	//gets a specific students grade for a class
	public String getGrade(int cl, String user) {
		int i = findClass(cl);
		return classes.get(i).getStuGrade(user);
	}
	//for adding assignments from csv files
	public void addAssignment(String type, String name, int possible, int classcode, String username, int grade, boolean graded, int i, int j) throws IOException {
		classes.get(i).stu.get(j).assignments.add(new Assignments(type, name, possible, classcode, username, grade, graded));
	}
	//checks if user is professor or student, 1 if student, 0 if professor, and -1 if they do not exist
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
	//returns "name username" of all students in a class in an ArrayList
	public ArrayList<String> getStudents(int cl){
		ArrayList<String> str = new ArrayList<String>();
		int k = findClass(cl);
		for(int i = 0; i < classes.get(k).stu.size(); i++) {
			str.add(classes.get(k).stu.get(i).name + " " + classes.get(k).stu.get(i).username);
		}
		return str;
	}
	//adds a user when they sign up
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
	//this returns the users grades... it'll be useful for separating the assignment from the grade in order to edit it
	public ArrayList<String> getUserGrades(int cl, String user) throws IOException{
		ArrayList<String> str = new ArrayList<String>();
		int i = findClass(cl);
		int j = findStu(i, user);
		for(int k = 0; k < classes.get(i).stu.get(j).assignments.size(); k++) {
			str.add(Integer.toString(classes.get(i).stu.get(j).assignments.get(k).grade) + "/" + Integer.toString(classes.get(i).stu.get(j).assignments.get(k).possible));
		}
		return str;
	}
	//checks if the assignment exists
	public boolean assignmentExists(int cl, String assignName) {
		int i = findClass(cl);
		for(int j = 0; j < classes.get(i).assignments.size(); j++) {
			if(classes.get(i).assignments.get(j).name.equals(assignName)) {
				return true;
			}
		}
		return false;
	}
	//gets the grade of a singular assignment
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
	//this returns all assignments for a specific user... for students it is automatically the user signed in.
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
	//this returns all grades for a specific type of grade (like quizzes) for a specific user...
	public ArrayList<String> getUserGrades(int cl, String user, String assignmentType) throws IOException {
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
	
	// this returns the name and class code of all classes a specific student is in... also checks if user is a professor
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
	// this is really important as it will keep everything up to date as it is used
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
	//adds a class when the professor makes one
	public void addClass(String name, String prof, int cl) throws IOException {
		classes.add(new Classes(name, prof, 0, cl));
	}
	//this saves the current user when they log in so we can access the getUser() function whenever we want
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
	//gets first name, this will be used in the profile part of our project
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
	//adds student to class when student is already in one class 
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
	//returns the current user
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
	//works when creating and editing the syllabus
	public void editSyllabus(int cl, double finalW, double examW, double quizW, double HWW, double labW, double otherW) throws IOException {
		classes.get(findClass(cl)).syllabus = new Syllabus(cl, finalW, examW, quizW, HWW, labW, otherW);
	}
	//changes the grade of one assignment
	public void changeGrade(int cl, String usr, String assignName, int grade) throws IOException {
		int i = findClass(cl);
		int j = findStu(i, usr);
		classes.get(i).changeGrade(assignName, usr, j, grade);
		update();
	}
	//returns the average for a single assignment in the whole class
	public String assignAvgGrade(int cl, String assignName) {
		return classes.get(findClass(cl)).assignAvgGrade(assignName);
	}
	//returns the whole class average
	public String classAvg(int cl) {
		return classes.get(findClass(cl)).classAvg();
	}
	//drops student from course
	public void dropStudent(int cl, String usr) throws IOException {
		int i = findClass(cl);
		classes.get(i).dropStudent(usr);
		update();
	}
}
