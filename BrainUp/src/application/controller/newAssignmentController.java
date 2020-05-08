package application.controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class newAssignmentController implements Initializable{
	ObservableList<String> observe2 = FXCollections.observableArrayList("Quiz","Lab","Homework","Exam","Final Exam","Other");
	@FXML private Parent root;
	@FXML private TextField assignName;
	@FXML private TextField possible;
	@FXML private TextField classCode;
	@FXML private Button create;
	@FXML private ChoiceBox<String> choice = new ChoiceBox<String>();
	Alert a = new Alert(AlertType.NONE);
	
	@Override 
	public void initialize(URL url, ResourceBundle rb) {
		choice.getItems().addAll(observe2);
	}
	@FXML protected void cancel(ActionEvent e) throws IOException {
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
	@FXML protected void handleCreateAssign(ActionEvent e) throws IOException{
		String result = (String) choice.getValue();
		if(result == "Quiz") {
			result = "Q";
		}
		else if(result == "Lab") {
			result = "L";
		}
		else if(result == "Homework") {
			result = "H";
		}
		else if(result == "Exam") {
			result = "E";
		}
		else if(result == "Final Exam") {
			result = "F";
		}
		else if(result == "Other") {
			result = "O";
		}
		if(result == null) {
			a.setAlertType(AlertType.ERROR);
			a.setContentText("Please select a assignment type");
			a.show();
		}
		else {
			Login log = new Login();
			log.newAssignment(result, assignName.getText(), Integer.valueOf(possible.getText()), Integer.valueOf(classCode.getText()), log.getUser());
			log.update();
			Stage stage;
			Parent r;
			stage = (Stage) root.getScene().getWindow();
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
	}
}
