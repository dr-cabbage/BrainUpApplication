package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class ProfileController implements Initializable{
	
	@FXML private Parent root;
	@FXML private Label firstName;
	@FXML private Label lastName;
	@FXML private Label userName;
	@FXML private Label password;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Login log;
		String[] userInfo;
		String[] fullName;
		try {
			log = new Login();
			userInfo = log.getInfo();
			if(userInfo == null) {
				return;
			}
			fullName = userInfo[0].split(" ");
			firstName.setText(fullName[0]);
			lastName.setText(fullName[1]);
			userName.setText(userInfo[1]);
			password.setText(userInfo[2]);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML protected void handleBack(ActionEvent e) throws IOException {
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
	
	
	@FXML protected void handleEditFirst(ActionEvent e) throws IOException {
		
	}
	
	
	
}
