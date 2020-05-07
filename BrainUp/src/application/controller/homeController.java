package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

import application.model.Login;

public class homeController {
	@FXML private MenuItem home;
	@FXML private MenuItem classes;
	@FXML private MenuItem signO;
	@FXML private MenuItem about;
	@FXML private MenuItem help;
	@FXML private Parent root;
	

	
	@FXML protected void handleSignOut(ActionEvent e) throws IOException {
		Login log = new Login();
		log.signOut();
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
	
	@FXML protected void handleProfile(ActionEvent e) throws IOException {
		Stage stage;
		Parent r;
		
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("profile.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
}
