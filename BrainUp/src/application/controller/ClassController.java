package application.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import application.model.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	@FXML private MenuItem profile;
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
			log.saveUser(log.getUser(), str.get(0).substring(str.get(0).lastIndexOf(" ") + 1), "Doesnt matter");
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
	
	@FXML protected void handleProfile(ActionEvent e) throws IOException {
		Stage stage;
		Parent r;
		
		stage = (Stage) root.getScene().getWindow();
		r = FXMLLoader.load(getClass().getResource("profile.fxml"));
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
	@FXML protected void handleChangeFakeGrade(MouseEvent e) throws IOException {
		Login log = new Login();
		String cl1 = list2.getSelectionModel().getSelectedItem();
		if(cl1 == null || cl1 == "") {
			int i = list3.getSelectionModel().getSelectedIndex();
			cl1 = list2.getItems().get(i);
		}
		String cl = list3.getSelectionModel().getSelectedItem();
		String cl2 = log.getUser();
		
		String cl3 = list.getSelectionModel().getSelectedItem();
		int i = cl1.indexOf(" Type:");
		cl1 = cl1.substring(0,i);
		cl3 = cl3.substring(cl3.lastIndexOf(" ") + 1);
		int k = cl.indexOf('/');
		String currGrade = cl.substring(0,k);
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Grade");
		dialog.setHeaderText("Change the grade, current grade is : "  + currGrade);
		dialog.setContentText("Grade:");
		Optional<String> result = dialog.showAndWait();
		try {
			Integer.parseInt(result.get());
			log.changeFakeGrade(Integer.valueOf(cl3), cl2, cl1, Integer.valueOf(result.get()));
			display(e);
		}catch (NumberFormatException exc){
			a.setAlertType(AlertType.ERROR);
			a.setContentText("Please enter a number");
			a.show();
		}
		

	}
	public static MediaPlayer media;
	@FXML protected void handleRegret(ActionEvent e) {
		Main m = new Main();
		m.stop(media);
		String s = "data/Regret.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		media = new MediaPlayer(h);
		media.seek(Duration.ZERO);
		media.setVolume(0.1);
		media.play();
	}
	
	//this creates a line chart for the quizzes
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@FXML protected void handleQuizzes(ActionEvent e) throws IOException{
			Stage stage = new Stage();
			Login log = new Login();
			String cl = list.getSelectionModel().getSelectedItem();
			if(cl == null || cl == "") {
				cl = log.currClass();
			}
			int classcode = Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1));
			

			ArrayList<String> quizGrades = new ArrayList<String>();
			quizGrades = log.getUserGradeType(classcode, log.getUser(), "Q");
			
			stage.setTitle("Quiz Grades");
	        //defining the axes
	        NumberAxis xAxis = new NumberAxis(0.00, quizGrades.size()+1.00, 1.00);
	        NumberAxis yAxis = new NumberAxis(0.00, 125.00, 5.00);
	        //xAxis.setLabel("Assignments");
	        yAxis.setLabel("Grades");
	        //creating the chart
	        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis,yAxis);
	                
	        lineChart.setTitle("Line Chart of Quiz Grades");
	        //defining a series
	        XYChart.Series series = new XYChart.Series();
	        series.setName("Quiz Grades");
	        //populating the series with data
	        for (int i=1; i <= quizGrades.size(); i++) {
	        	series.getData().add(new XYChart.Data(i, Integer.valueOf(quizGrades.get(i-1))));
	        }
	        
	        lineChart.getData().add(series);       
	        Scene scene  = new Scene(lineChart,700,500);
	        stage.setScene(scene);
	        stage.show();        
		}
		
		//this creates a line chart for the labs
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@FXML protected void handleLabs(ActionEvent e) throws IOException{
			Stage stage = new Stage();
			Login log = new Login();
			String cl = list.getSelectionModel().getSelectedItem();
			if(cl == null || cl == "") {
				cl = log.currClass();
			}
			int classcode = Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1));
			

			ArrayList<String> labGrades = new ArrayList<String>();
			labGrades = log.getUserGradeType(classcode, log.getUser(), "L");
			
			stage.setTitle("Lab Grades");
	        //defining the axes
	        NumberAxis xAxis = new NumberAxis(0.00, labGrades.size()+1.00, 1.00);
	        NumberAxis yAxis = new NumberAxis(0.00, 125.00, 5.00);
	        //xAxis.setLabel("Labs");
	        yAxis.setLabel("Grades");
	        //creating the chart
	        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis,yAxis);
	                
	        lineChart.setTitle("Line Chart of Lab Grades");
	        //defining a series
			XYChart.Series series = new XYChart.Series();
	        series.setName("Lab Grades");
	        //populating the series with data
	        for (int i=1; i <= labGrades.size(); i++) {
	        	series.getData().add(new XYChart.Data(i, Integer.valueOf(labGrades.get(i-1))));
	        }
	        
	        lineChart.getData().add(series);       
	        Scene scene  = new Scene(lineChart,700,500);
	        stage.setScene(scene);
	        stage.show();        
		}
		
		//this creates a line chart for the homeworks
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@FXML protected void handleHomeworks(ActionEvent e) throws IOException{
			Stage stage = new Stage();
			Login log = new Login();
			String cl = list.getSelectionModel().getSelectedItem();
			if(cl == null || cl == "") {
				cl = log.currClass();
			}
			int classcode = Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1));
			ArrayList<String> hwGrades = new ArrayList<String>();
			hwGrades = log.getUserGradeType(classcode, log.getUser(), "H");
					
			stage.setTitle("Homework Grades");
	        //defining the axes
			NumberAxis xAxis = new NumberAxis(0.00, hwGrades.size()+1.00, 1.00);
			NumberAxis yAxis = new NumberAxis(0.00, 125.00, 5.00);
	        //xAxis.setLabel("Homeworks");
	        yAxis.setLabel("Grades");
	        //creating the chart
	        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis,yAxis);
			                
	        lineChart.setTitle("Line Chart of Homework Grades");
	        //defining a series
	        XYChart.Series series = new XYChart.Series();
	        series.setName("Homework Grades");
	        //populating the series with data
	        for (int i=1; i <= hwGrades.size(); i++) {
	        	series.getData().add(new XYChart.Data(i, Integer.valueOf(hwGrades.get(i-1))));
		    }
			        
	        lineChart.getData().add(series);       
	        Scene scene  = new Scene(lineChart,700,500);
	        stage.setScene(scene);
	        stage.show();        
	    }		
		
		//this creates a line chart for all type of assignment grades
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@FXML protected void handleAllGraphs(ActionEvent e) throws IOException{
			Stage stage = new Stage();
			Login log = new Login();
			String cl = list.getSelectionModel().getSelectedItem();
			if(cl == null || cl == "") {
				cl = log.currClass();
			}
			int classcode = Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1));
			

			ArrayList<String> hwGrades = new ArrayList<String>();
			ArrayList<String> labGrades = new ArrayList<String>();
			ArrayList<String> quizGrades = new ArrayList<String>();
			ArrayList<String> examGrades = new ArrayList<String>();
			ArrayList<String> finalExamGrades = new ArrayList<String>();
			ArrayList<String> otherGrades = new ArrayList<String>();
			
			hwGrades = log.getUserGradeType(classcode, log.getUser(), "H");
			labGrades = log.getUserGradeType(classcode, log.getUser(), "L");
			quizGrades = log.getUserGradeType(classcode, log.getUser(), "Q");
			examGrades = log.getUserGradeType(classcode, log.getUser(), "E");
			finalExamGrades = log.getUserGradeType(classcode, log.getUser(), "F");
			otherGrades = log.getUserGradeType(classcode, log.getUser(), "O");
			
			int hwNum = hwGrades.size();
			int labNum = labGrades.size();
			int quizNum = quizGrades.size();
			int examNum = examGrades.size();
			int finalNum = finalExamGrades.size();
			int otherNum = otherGrades.size();
			int[] arr = {hwNum, labNum, quizNum, examNum, finalNum, otherNum};
			int max = 0;
			for(int i = 0; i < arr.length; i++) {
				if(arr[i] > max) {
					max = arr[i];
				}
			}
			stage.setTitle("All Grades");
	        //defining the axes
			NumberAxis xAxis = new NumberAxis(0.00, max+1.00, 1.00);
			NumberAxis yAxis = new NumberAxis(0.00, 120.00, 5.00);
	        xAxis.setLabel("Assignments");
	        yAxis.setLabel("Grades");
	        //creating the chart
	        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis,yAxis);
			                
	        lineChart.setTitle("Line Chart of All Assignment Grades");
	        //defining a series
	        
	        XYChart.Series series1 = new XYChart.Series();
	        series1.setName("Lab Grades");
	        for (int i=1; i <= labGrades.size(); i++) {
	        	series1.getData().add(new XYChart.Data(i, Integer.valueOf(labGrades.get(i-1))));
		    }
	        XYChart.Series series2 = new XYChart.Series();
	        series2.setName("Quiz Grades");
	        for (int i=1; i <= quizGrades.size(); i++) {
	        	series2.getData().add(new XYChart.Data(i, Integer.valueOf(quizGrades.get(i-1))));
		    }
	        XYChart.Series series = new XYChart.Series();
	        series.setName("Homework Grades");
	        //populating the series with data
	        for (int i=1; i <= hwGrades.size(); i++) {
	        	series.getData().add(new XYChart.Data(i, Integer.valueOf(hwGrades.get(i-1))));
		    }  
	        XYChart.Series series3 = new XYChart.Series();
	        series3.setName("Exam Grades");
	        //populating the series with data
	        for (int i=1; i <= examGrades.size(); i++) {
	        	series3.getData().add(new XYChart.Data(i, Integer.valueOf(examGrades.get(i-1))));
		    }  
	        XYChart.Series series4 = new XYChart.Series();
	        series4.setName("Other Grades");
	        //populating the series with data
	        for (int i=1; i <= otherGrades.size(); i++) {
	        	series4.getData().add(new XYChart.Data(i, Integer.valueOf(otherGrades.get(i-1))));
		    }  
	        XYChart.Series series5 = new XYChart.Series();
	        series5.setName("Finals Grade");
	        //populating the series with data
	        for (int i=1; i <= finalExamGrades.size(); i++) {
	        	series5.getData().add(new XYChart.Data(i, Integer.valueOf(finalExamGrades.get(i-1))));
		    }  
	        lineChart.getData().add(series5);
	        lineChart.getData().add(series4);
	        lineChart.getData().add(series3);
	        lineChart.getData().add(series1);  
	        lineChart.getData().add(series2);
	        lineChart.getData().add(series);  
	        Scene scene  = new Scene(lineChart,700,500);
	        stage.setScene(scene);
	        stage.show();        
	    }		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML protected void handleGPA(ActionEvent e) throws IOException {
		Stage stage = new Stage();
		Login log = new Login();
		String cl = list.getSelectionModel().getSelectedItem();
		if(cl == null || cl == "") {
			cl = log.currClass();
		}
		int classcode = Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1));
		int max = 0;
		max = log.getUserAssignments(classcode, log.getUser()).size();
		stage.setTitle("All Grades");
        //defining the axes
		NumberAxis xAxis = new NumberAxis(0.00, max+1.00, 1.00);
		NumberAxis yAxis = new NumberAxis(0.00, 120.00, 5.00);
		xAxis.setLabel("Assignments");
        yAxis.setLabel("Grade");
        //creating the chart
        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis,yAxis);
		                
        lineChart.setTitle("Line Chart of Total Grade");
        XYChart.Series series = new XYChart.Series();
        ArrayList<String> grades;
        grades = log.studentGraph(Integer.valueOf(log.currClass()), log.getUser());
        series.setName(cl);
        for(int j = 1; j < grades.size() + 1; j++) {
        	series.getData().add(new XYChart.Data(j, Integer.valueOf(grades.get(j -1))));
        }
        lineChart.getData().add(series);
        Scene scene  = new Scene(lineChart,700,500);
        stage.setScene(scene);
        stage.show();
		
	}
		
}
