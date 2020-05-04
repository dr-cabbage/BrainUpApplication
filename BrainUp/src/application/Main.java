package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		
		Scene scene = new Scene(root, 600, 600);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();
		
	}
	

	public static void main(String[] args) {
		
		launch(args);
	}
}