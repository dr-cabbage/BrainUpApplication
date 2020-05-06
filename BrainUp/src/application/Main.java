package application;
	
import java.nio.file.Paths;

import application.controller.ClassController;
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
	public final Media h = new Media(Paths.get("data/Wii.mp3").toUri().toString());
	public final MediaPlayer mediaPlayer = new MediaPlayer(h);
	public void music() {
		mediaPlayer.seek(Duration.ZERO);
		mediaPlayer.setVolume(0.1);
		mediaPlayer.play();
		ClassController.media = mediaPlayer;
	}
	
	public void stop(MediaPlayer media) {
		media.stop();
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}