package application.controller;

import java.io.IOException;
import java.util.Random;

import application.model.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class createClassController {
	@FXML private Parent root;
	@FXML private TextField classN;
	@FXML private TextField quiz;
	@FXML private TextField lab;
	@FXML private TextField hw;
	@FXML private TextField exam;
	@FXML private TextField finalW;
	@FXML private TextField other;
	@FXML private Button cancel;
	@FXML private Button create;
	Alert a = new Alert(AlertType.NONE);
	
	@FXML protected void handleCancel(ActionEvent e) throws IOException {
		Stage stage;
		Parent r;
		
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("profClass.fxml"));
		Scene scene = new Scene(r);
		stage.setScene(scene);
		stage.show();
	}
	@FXML protected void handleCreateClass(ActionEvent e) throws IOException {
		Login log = new Login();
		if(quiz.getText().equals("") || exam.getText().equals("") || hw.getText().equals("") || finalW.getText().equals("") || lab.getText().equals("") || other.getText().equals("")) {
			a.setAlertType(AlertType.ERROR);
			a.setContentText("Please fill out all spaces");
			a.show();
		}
		else {
			try {
				int q = Integer.valueOf(quiz.getText());
				int t = Integer.valueOf(exam.getText());
				int h = Integer.valueOf(hw.getText());
				int f = Integer.valueOf(finalW.getText());
				int l = Integer.valueOf(lab.getText());
				int o = Integer.valueOf(other.getText());
				if(classN.getText().equals("")) {
					a.setAlertType(AlertType.ERROR);
					a.setContentText("Please fill out all spaces");
					a.show();
				}
				else {
					Random r = new Random();
					int i = r.nextInt((100000 - 10000) + 1) + 10000;
					System.out.println(i);
					log.addClass(classN.getText(), log.getUser(), i);
					log.editSyllabus(i, f, t, q, h, l, o);
					log.update();
					Stage stage;
					Parent re;
						
					stage = (Stage) root.getScene().getWindow();
					re = FXMLLoader.load(getClass().getResource("profClass.fxml"));
					Scene scene = new Scene(re);
					stage.setScene(scene);
					stage.show();
				}
			}	catch (NumberFormatException n) {
				a.setAlertType(AlertType.ERROR);
				a.setContentText("Please use numbers for the weights");
				a.show();
			}
		}
	}
}
