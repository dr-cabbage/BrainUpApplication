package application.controller;

import javafx.scene.control.Button;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class ProfileController implements Initializable{
	
	@FXML private Parent root;
	@FXML private Label firstName;
	@FXML private Label lastName;
	@FXML private Label userName;
	@FXML private Label password;
	@FXML private Button back;
	@FXML private Button editFirst;
	@FXML private Button editLast;
	@FXML private Button editUser;
	@FXML private Button editPass;
	
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
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change First Name");
		dialog.setHeaderText("Change your first name, current first name is : "  + firstName.getText());
		dialog.setContentText("New First Name: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		String name = result.get() + " " + lastName.getText();
		log.editInfo(userName.getText(), name, userName.getText(), password.getText());
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("profile.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void handleEditLast(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Last Name");
		dialog.setHeaderText("Change your last name, current last name is : "  + lastName.getText());
		dialog.setContentText("New Last Name: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		String name = firstName.getText() + " " + result.get();
		log.editInfo(userName.getText(), name, userName.getText(), password.getText());
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("profile.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void handleEditUser(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Username");
		dialog.setHeaderText("Change your username, current username is : " + userName.getText());
		dialog.setContentText("New Username: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		String name = firstName.getText() + " " + lastName.getText();
		log.editInfo(userName.getText(), name, result.get(), password.getText());
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("profile.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void handleEditPass(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Password");
		dialog.setHeaderText("Change your password, current password is : " + password.getText());
		dialog.setContentText("New Password: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		String name = firstName.getText() + " " + lastName.getText();
		log.editInfo(userName.getText(), name, userName.getText(), result.get());
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("profile.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}

}
