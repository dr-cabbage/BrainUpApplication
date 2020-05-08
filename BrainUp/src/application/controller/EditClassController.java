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

public class EditClassController implements Initializable{
	@FXML private Button change;
	@FXML private Parent root;
	@FXML private Label name;
	@FXML private Label classcode;
	@FXML private Label numStu;
	@FXML private Label numAssign;
	@FXML private Label quiz;
	@FXML private Label lab;
	@FXML private Label exam;
	@FXML private Label hw;
	@FXML private Label finalE;
	@FXML private Label other;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Login log;
		try {
			log = new Login();
			String[] str = log.editClass();
			if(str[0] == "") {
				return;
			}
			name.setText(str[0]);
			classcode.setText(str[1]);
			numStu.setText(str[2]);
			numAssign.setText(str[3]);
			quiz.setText(str[4]);
			lab.setText(str[5]);
			exam.setText(str[6]);
			hw.setText(str[7]);
			finalE.setText(str[8]);
			other.setText(str[9]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
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
	
	@FXML protected void changeClassName(ActionEvent e) throws IOException{
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Name");
		dialog.setHeaderText("Change the class name, current name is : "  + name.getText());
		dialog.setContentText("New Name: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		log.changeClassName(Integer.valueOf(classcode.getText()), result.get());
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("editClass.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void changeQuizWeight(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Quiz Weight");
		dialog.setHeaderText("Change the quiz weight, current weight is : "  + quiz.getText());
		dialog.setContentText("New Weight: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		try {
			int i = Integer.valueOf(result.get());
			log.changeWeight(Integer.valueOf(classcode.getText()), i, "Q");
		}catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("editClass.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void changeLabWeight(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Lab Weight");
		dialog.setHeaderText("Change the lab weight, current weight is : "  + lab.getText());
		dialog.setContentText("New Weight: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		try {
			int i = Integer.valueOf(result.get());
			log.changeWeight(Integer.valueOf(classcode.getText()), i, "L");
		}catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("editClass.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void changeFinalWeight(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Final Weight");
		dialog.setHeaderText("Change the final weight, current weight is : "  + finalE.getText());
		dialog.setContentText("New Weight: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		try {
			int i = Integer.valueOf(result.get());
			log.changeWeight(Integer.valueOf(classcode.getText()), i, "F");
		}catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("editClass.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void changeExamWeight(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Exam Weight");
		dialog.setHeaderText("Change the exam weight, current weight is : "  + exam.getText());
		dialog.setContentText("New Weight: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		try {
			int i = Integer.valueOf(result.get());
			log.changeWeight(Integer.valueOf(classcode.getText()), i, "E");
		}catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("editClass.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void changeHWWeight(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Homework Weight");
		dialog.setHeaderText("Change the homework weight, current weight is : "  + hw.getText());
		dialog.setContentText("New Weight: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		try {
			int i = Integer.valueOf(result.get());
			log.changeWeight(Integer.valueOf(classcode.getText()), i, "H");
		}catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("editClass.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML protected void changeOtherWeight(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Other Weight");
		dialog.setHeaderText("Change the other weight, current weight is : "  + other.getText());
		dialog.setContentText("New Weight: ");
		Optional<String> result = dialog.showAndWait();
		Login log = new Login();
		try {
			int i = Integer.valueOf(result.get());
			log.changeWeight(Integer.valueOf(classcode.getText()), i, "O");
		}catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		Stage stage;
		Parent r;
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("editClass.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	
}
