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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
	@FXML private MenuItem Labs;
	@FXML private MenuItem Homeworks;
	@FXML private MenuItem Quizzes;
	@FXML private MenuItem All;
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
	//this creates a line chart for the quizzes
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML protected void handleQuizzes(ActionEvent e) throws IOException{
		Stage stage = new Stage();

		String cl = list.getSelectionModel().getSelectedItem();
		int classcode = Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1));
		
		Login log = new Login();

		ArrayList<String> quizGrades = new ArrayList<String>();
		quizGrades = log.getUserGrades(classcode, log.getUser(), "Q");
		
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

		String cl = list.getSelectionModel().getSelectedItem();
		int classcode = Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1));
		
		Login log = new Login();

		ArrayList<String> labGrades = new ArrayList<String>();
		labGrades = log.getUserGrades(classcode, log.getUser(), "L");
		
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
		String cl = list.getSelectionModel().getSelectedItem();
		int classcode = Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1));
		
		Login log = new Login();
		ArrayList<String> hwGrades = new ArrayList<String>();
		hwGrades = log.getUserGrades(classcode, log.getUser(), "H");
				
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
		String cl = list.getSelectionModel().getSelectedItem();
		int classcode = Integer.valueOf(cl.substring(cl.lastIndexOf(" ") + 1));
		
		Login log = new Login();
		ArrayList<String> hwGrades = new ArrayList<String>();
		ArrayList<String> labGrades = new ArrayList<String>();
		ArrayList<String> quizGrades = new ArrayList<String>();

		hwGrades = log.getUserGrades(classcode, log.getUser(), "H");
		labGrades = log.getUserGrades(classcode, log.getUser(), "L");
		quizGrades = log.getUserGrades(classcode, log.getUser(), "Q");
		
		int hwNum = hwGrades.size();
		int labNum = labGrades.size();
		int quizNum = quizGrades.size();
		int max = hwNum;
		if(labNum >= max) {
			if(labNum >= quizNum) 
				max = labNum;
			else
				max = quizNum;
		}
		if(quizNum >= max)
			max = quizNum;
		
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
        
        lineChart.getData().add(series1);  
        lineChart.getData().add(series2);
        lineChart.getData().add(series);  
        Scene scene  = new Scene(lineChart,700,500);
        stage.setScene(scene);
        stage.show();        
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
