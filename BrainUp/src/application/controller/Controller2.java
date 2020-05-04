package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.Random;

import application.model.Login;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller2 {
	@FXML private TextField firstN;
	@FXML private TextField lastN;
	@FXML private TextField userN;
	@FXML private TextField passW;
	@FXML private TextField retype;
	@FXML private TextField classCode;
	@FXML private TextField classN;
	@FXML private TextField quiz;
	@FXML private TextField test;
	@FXML private TextField homework;
	@FXML private TextField finalE;
	@FXML private TextField labs;
	@FXML private TextField other;
	@FXML private Button signUp;
	Alert a = new Alert(AlertType.NONE);
	
	@FXML protected void handleStuSignUp(ActionEvent e) throws Exception{
		Login log = new Login();
		if(userN.getText().equals("") || passW.getText().equals("") || retype.getText().equals("") || classCode.getText().equals("") || firstN.getText().equals("") || lastN.getText().equals("")) {
			a.setAlertType(AlertType.ERROR);
			a.setContentText("Please fill out all spaces");
			a.show();
		}
		else {
			if(!passW.getText().equals(retype.getText())){
				a.setAlertType(AlertType.ERROR);
				a.setContentText("The passwords do not match");
				a.show();
			}
			else {
				if(log.alreadyExists(userN.getText())) {
					a.setAlertType(AlertType.ERROR);
					a.setContentText("That username already exists");
					a.show();
				}
				else {
					if(log.findClass(Integer.valueOf(classCode.getText())) == -1) {
						a.setAlertType(AlertType.ERROR);
						a.setContentText("Class Code invalid");
						a.show();
					}
					else {
						int i = log.findClass(Integer.valueOf(classCode.getText()));
						String full = firstN.getText() + " " + lastN.getText();
						log.addStudent(full, userN.getText(), i, Integer.valueOf(classCode.getText()));
						log.addUser(full, userN.getText(), passW.getText(), true);
						log.update();
						Stage stage;
						Parent root;
						
						stage = (Stage) signUp.getScene().getWindow();
						root = FXMLLoader.load(getClass().getResource("Login.fxml"));
						Scene scene = new Scene(root);
						stage.setScene(scene);
						stage.show();
						
						
					}
				}
			}
		}
	}
	
	@FXML protected void handleProfSignUp(ActionEvent e) throws Exception{
		Login log = new Login();
		if(quiz.getText().equals("") || test.getText().equals("") || homework.getText().equals("") || finalE.getText().equals("") || labs.getText().equals("") || other.getText().equals("")) {
			a.setAlertType(AlertType.ERROR);
			a.setContentText("Please fill out all spaces");
			a.show();
		}
		else {
			try {
				int q = Integer.valueOf(quiz.getText());
				int t = Integer.valueOf(test.getText());
				int hw = Integer.valueOf(homework.getText());
				int f = Integer.valueOf(finalE.getText());
				int l = Integer.valueOf(labs.getText());
				int o = Integer.valueOf(other.getText());
				if(userN.getText().equals("") || passW.getText().equals("") || retype.getText().equals("") || firstN.getText().equals("") || lastN.getText().equals("")) {
					a.setAlertType(AlertType.ERROR);
					a.setContentText("Please fill out all spaces");
					a.show();
				}
				else {
					if(!passW.getText().equals(retype.getText())){
						a.setAlertType(AlertType.ERROR);
						a.setContentText("The passwords do not match");
						a.show();
					}
					else {
						if(log.alreadyExists(userN.getText())) {
							a.setAlertType(AlertType.ERROR);
							a.setContentText("That username already exists");
							a.show();
						}
						else {
							String full = firstN.getText() + " " + lastN.getText();
							Random r = new Random();
							int i = r.nextInt((100000 - 10000) + 1) + 10000;
							System.out.println(i);
							log.addClass(classN.getText(), userN.getText(), i);
							log.editSyllabus(i, f, t, q, hw, l, o);
							log.addUser(full, userN.getText(), passW.getText(), false);
							log.update();
							Stage stage;
							Parent root;
								
							stage = (Stage) signUp.getScene().getWindow();
							root = FXMLLoader.load(getClass().getResource("Login.fxml"));
							Scene scene = new Scene(root);
							stage.setScene(scene);
							stage.show();
						}
					}
				}
			}
			catch (NumberFormatException n) {
				a.setAlertType(AlertType.ERROR);
				a.setContentText("Please use numbers for the weights");
				a.show();
			}
		
			
	}
	}
}
