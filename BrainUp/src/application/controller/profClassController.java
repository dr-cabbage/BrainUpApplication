package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class profClassController implements Initializable{
	ObservableList<String> observe = FXCollections.observableArrayList();
	ObservableList<String> observe1 = FXCollections.observableArrayList();
	ObservableList<String> observe2 = FXCollections.observableArrayList();
	@FXML private ListView<String> list1;
	@FXML private ListView<String> list2;
	@FXML private ListView<String> list3;
	@FXML private ListView<String> list4;
	@FXML private MenuItem home;
	@FXML private MenuItem classes;
	@FXML private MenuItem signO;
	@FXML private MenuItem about;
	@FXML private MenuItem help;
	@FXML private Button findButt;
	@FXML private TextField searchA;
	@FXML private Parent root;
	Alert a = new Alert(AlertType.NONE);
	
	@Override 
	public void initialize(URL url, ResourceBundle rb) {
		try {
			loadData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadData() throws IOException {
		observe.removeAll(observe);
		Login log = new Login();
		ArrayList<String> str = log.getUserClasses();
		if(str.size() == 0) {
			observe.add("No classes available");
		}
		else {
			for(int i = 0; i < str.size(); i++) {
				observe.add(str.get(i));
			}
			log.saveUser(log.getUser(), str.get(0).substring(str.get(0).lastIndexOf(" ") + 1), "Doesnt matter");
		}
		list1.getItems().addAll(observe);
	}
	
	@FXML protected void handleFindStu(MouseEvent e) throws IOException {
		String cl = list1.getSelectionModel().getSelectedItem();
		list2.getItems().clear();
		list3.getItems().clear();
		list4.getItems().clear();
		observe.removeAll(observe);
		Login log = new Login();
		if(cl == null || cl.equals("") || cl.equals("No classes available")) {
			observe.add("No class was selected");
		}
		else {
			ArrayList<String> str = log.getStudents(Integer.valueOf(cl.substring(cl.lastIndexOf(" ") +1)));
			if(str.size() == 0) {
				observe.add("You have no students in this class");
			}
			else {
				observe.add("Class Avg: " + log.classAvg(Integer.valueOf(cl.substring(cl.lastIndexOf(" ") +1))));
				for(int i = 0; i < str.size();i++) {
					observe.add(str.get(i));
				}
			}
		}
		log.saveUser(log.getUser(), cl.substring(cl.lastIndexOf(" ") +1), "Doesnt matter");
		list2.getItems().addAll(observe);
	}
	@FXML protected void handleFindAssign(ActionEvent e) throws IOException {
		String c = list1.getSelectionModel().getSelectedItem();
		list2.getItems().clear();
		list3.getItems().clear();
		list4.getItems().clear();
		observe.removeAll(observe);
		observe1.removeAll(observe1);
		observe2.removeAll(observe2);
		c = c.trim();
		String[] wordList = c.split("\\s+");
		Login log = new Login();
		if(c == null || c == "") {
			a.setAlertType(AlertType.ERROR);
			a.setContentText("Please select a class before searching for an assignment");
			a.show();
		}
		else {
			if(!log.assignmentExists(Integer.valueOf(wordList[wordList.length - 1]), searchA.getText())) {
				a.setAlertType(AlertType.ERROR);
				a.setContentText("Assignment does not exist");
				a.show();
			}
			else {
				ArrayList<String> str = log.getStudents(Integer.valueOf(wordList[wordList.length - 1]));
				if(str.size() == 0) {
					observe.add("No students in class");
				}
				else {
					observe.add("Number of students: " + str.size());
					observe1.add("Assignment");
					observe2.add("Assign Avg: " + log.assignAvgGrade(Integer.valueOf(wordList[wordList.length - 1]), searchA.getText()));
					for(int i = 0; i < str.size();i++) {
						observe.add(str.get(i));
						observe1.add(searchA.getText());
						observe2.add(log.getAssignGrade(Integer.valueOf(wordList[wordList.length -1]), str.get(i).substring(str.get(i).lastIndexOf(" ") + 1), searchA.getText()));
					}
				}
			}
		}
		list2.getItems().addAll(observe);
		list3.getItems().addAll(observe1);
		list4.getItems().addAll(observe2);
	}
	@SuppressWarnings("unused")
	@FXML protected void handleFindStuAssignments(MouseEvent e) throws IOException {
		String c = list1.getSelectionModel().getSelectedItem();
		String cl = list2.getSelectionModel().getSelectedItem();
		cl = cl.trim();
		String[] wordList = cl.split("\\s+");
		list3.getItems().clear();
		list4.getItems().clear();
		observe.removeAll(observe);
		observe1.removeAll(observe1);
		Login log = new Login();
		if(cl == null) {
			observe.add("No class was selected");
		}
		else {
			if(cl == "" || cl.equals("You have no students in this class")) {
				observe.add("No assignments for this class chief");
			}
			else {
				ArrayList<String> str = log.getUserAssignments(Integer.valueOf(c.substring(c.lastIndexOf(" ") + 1)), wordList[wordList.length - 1]);
				if(str.size() == 0) {
					observe.add("No assignments for this bous");
				}
				else {
					ArrayList<String> str1 = log.getUserGrades(Integer.valueOf(c.substring(c.lastIndexOf(" ") + 1)), wordList[wordList.length - 1]);
					observe1.add("Total Grade: " + log.getGrade(Integer.valueOf(c.substring(c.lastIndexOf(" ") + 1)), wordList[wordList.length - 1]));
					for(int i = 0; i < str1.size(); i++) {
						observe1.add(str1.get(i));
					}
					observe.add("Num of Assignments: " + Integer.toString(str.size()));
					for(int j = 0; j< str.size();j++) {
						observe.add(str.get(j));
					}
				}
			}
		}
		list3.getItems().addAll(observe);
		list4.getItems().addAll(observe1);
	}
	@FXML protected void handleChangeGrade(MouseEvent e) throws IOException {
		int m = list3.getSelectionModel().getSelectedIndex();
		String cl = list4.getItems().get(m);
		String cl1 = list3.getSelectionModel().getSelectedItem();
		String cl2 = list2.getSelectionModel().getSelectedItem();
		if(cl2 == null) {
			cl2 = list2.getItems().get(list3.getSelectionModel().getSelectedIndex());
			String cl3 = list1.getSelectionModel().getSelectedItem();
			cl2  = cl2.trim();
			String[] wordList = cl2.split("\\s+");
			cl3 = cl3.substring(cl3.lastIndexOf(" ") + 1);
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Change Grade");
			dialog.setHeaderText("Change the grade, current grade is : "  + cl);
			dialog.setContentText("Grade:");
			Optional<String> result = dialog.showAndWait();
			Login log = new Login();
			log.changeGrade(Integer.valueOf(cl3), wordList[wordList.length - 1], cl1, Integer.valueOf(result.get()));
			Stage stage;
			Parent r;
			
			stage = (Stage) root.getScene().getWindow();
			r = FXMLLoader.load(getClass().getResource("profClass.fxml"));
			Scene scene = new Scene(r);
			stage.setScene(scene);
			stage.show();
		}
		else {
			String cl3 = list1.getSelectionModel().getSelectedItem();
			int i = cl1.indexOf(" Type:");
			cl1 = cl1.substring(0,i);
			cl2 = cl2.substring(cl2.lastIndexOf(" ") + 1);
			cl3 = cl3.substring(cl3.lastIndexOf(" ") + 1);
			int k = cl.indexOf('/');
			String currGrade = cl.substring(0,k);
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Change Grade");
			dialog.setHeaderText("Change the grade, current grade is : "  + currGrade);
			dialog.setContentText("Grade:");
			Optional<String> result = dialog.showAndWait();
			Login log = new Login();
			log.changeGrade(Integer.valueOf(cl3), cl2, cl1, Integer.valueOf(result.get()));
			handleFindStuAssignments(e);
		}
	}
	@FXML protected void handleSignOut(ActionEvent e) throws IOException {
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
		
	}
	@FXML protected void handleHome(ActionEvent e) throws IOException {
		Stage stage;
		Parent r;
		
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("home.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
		
	}
	
	@FXML protected void handleAbout(ActionEvent e) throws IOException {
		Stage stage;
		Parent r;
		
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("aboutUs.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void handleHelp(ActionEvent e) throws IOException{
		Stage stage;
		Parent r;
		
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("help.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	@FXML protected void handleNewAssign(ActionEvent e) throws IOException{
		Stage stage;
		Parent r;
		
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("createAssignment.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	@FXML protected void handleClasses(ActionEvent e) throws IOException{
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		Login log = new Login();
		if(log.isStudent(log.getUser()) == 1) {
			r = FXMLLoader.load(getClass().getResource("classes.fxml"));
		}
		else {
			r = FXMLLoader.load(getClass().getResource("profClass.fxml"));
		}
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	@FXML protected void handleCreateClass(ActionEvent e) throws IOException{
		Stage stage;
		Parent r;
		
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("createClass.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void handleEditClass(ActionEvent e) throws IOException{
		Stage stage;
		Parent r;
		
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("editClass.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
}
