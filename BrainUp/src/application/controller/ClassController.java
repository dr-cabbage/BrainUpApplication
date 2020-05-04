package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClassController implements Initializable {
	ObservableList<String> observe = FXCollections.observableArrayList();
	ObservableList<String> observe1 = FXCollections.observableArrayList();
	@FXML private ListView<String> list;
	@FXML private ListView<String> list2;
	@FXML private ListView<String> list3;
	@FXML private MenuItem home;
	@FXML private MenuItem classes;
	@FXML private MenuItem signO;
	@FXML private MenuItem about;
	@FXML private MenuItem help;
	@FXML private Button addButton;
	@FXML private TextField addField;
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
		}
		list.getItems().addAll(observe);
	}
	
	@FXML private void handleAdd(ActionEvent e) throws NumberFormatException, IOException {
		Login log = new Login();
		if(log.findClass(Integer.valueOf(addField.getText())) == -1) {
			a.setAlertType(AlertType.ERROR);
			a.setContentText("The class code you entered does not exist");
			a.show();
		}
		else {
			if(log.stuInClass(Integer.valueOf(addField.getText()), log.getUser())){
				a.setAlertType(AlertType.ERROR);
				a.setContentText("You are already in the class ya dingus");
				a.show();
			}
			else {
				log.addToClass(addField.getText());
				Stage stage;
				Parent r;
				
				stage = (Stage) root.getScene().getWindow();
				r = FXMLLoader.load(getClass().getResource("classes.fxml"));
				Scene scene = new Scene(r);
				stage.setScene(scene);
				stage.show();
			}
		}
	}
	
	@FXML private void display(MouseEvent Event) throws NumberFormatException, IOException {
		String cl = list.getSelectionModel().getSelectedItem();
		list2.getItems().clear();
		list3.getItems().clear();
		observe.removeAll(observe);
		observe1.removeAll(observe1);
		Login log = new Login();
		if(cl == null) {
			observe.add("No class was selected");
		}
		else {
			if(cl == "" || cl.equals("No classes available")) {
				observe.add("This class has no assignments yet");
			}
			else {
				ArrayList<String> str = log.getUserAssignments(Integer.valueOf(cl.substring(cl.lastIndexOf(" ") +1)), log.getUser());
				if(str.size() == 0) {
					observe.add("You have no assignments for this class");
				}
				else {
					ArrayList<String> str2 = log.getUserGrades(Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1)), log.getUser());
					observe1.add("Total Grade: " + log.getGrade(Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1)), log.getUser()));
					for(int j = 0; j < str2.size(); j++) {
						observe1.add(str2.get(j));
					}
					observe.add("Num of Assignments: " + str.size());
					for(int i = 0; i< str.size(); i++) {
						observe.add(str.get(i));
					}
				}
			}
		}
		list2.getItems().addAll(observe);
		list3.getItems().addAll(observe1);
		
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
	@FXML protected void handleClasses(ActionEvent e) throws IOException{
		Stage stage;
		Parent r;
		
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("classes.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
}
