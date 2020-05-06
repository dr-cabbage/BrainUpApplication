package application;
	
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Parent;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		music();
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		
		Scene scene = new Scene(root, 600, 600);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();
		
	}
	
	MediaPlayer mediaPlayer;
	public void music() {
		String s = "data/Wii.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.seek(Duration.ZERO);
		mediaPlayer.play();
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}