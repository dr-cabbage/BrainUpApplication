package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import application.model.Login;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MainController {
	@FXML private Button signup;
	@FXML private Button login;
	@FXML private TextField username;
	@FXML private PasswordField pass;
	@FXML private Button Student;
	@FXML private Button Prof;
	Alert a = new Alert(AlertType.NONE);
	public String user;
	
	@FXML protected void handleSignUp(ActionEvent event) throws Exception {
		Stage stage;
		Parent root;
		
		stage = (Stage) signup.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("profstu.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	@FXML protected void handleSignin(ActionEvent event) throws Exception{
		Login log = new Login();
		Stage stage;
		Parent root;
		log.signOut();
		if(log.userPassCheck(username.getText(), pass.getText())) {
			log.saveUser(username.getText(), "00000", log.getName(username.getText()));
			stage = (Stage) login.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("home.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else {
			a.setAlertType(AlertType.ERROR);
			a.setContentText("Invalid Username or Password");
			a.show();
		}
	}
	
	@FXML protected void stuSign(ActionEvent event) throws Exception{
		Stage stage;
		Parent root;
		
		stage = (Stage) Student.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void profSign(ActionEvent event) throws Exception{
		Stage stage;
		Parent root;
		
		stage = (Stage) Prof.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("profSignup.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}
