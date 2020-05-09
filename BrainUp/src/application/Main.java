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

/**
 * Launches BrainUp
 * 
 * @author GroupUs
 * @author Kale King
 * @author Ian Solis
 * @author Jose Morales
 * @author Seyma Oz
 * @author Claudio Ordaz
 * 
 * UTSA CS 3443 - Group Project
 * Spring 2020
 */

public class Main extends Application {
	
	/**
	 * Sets up stage
	 */
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
	
	/**
	 * plays our music
	 */
	public void music() {
		mediaPlayer.seek(Duration.ZERO);
		mediaPlayer.setVolume(0.1);
		mediaPlayer.play();
		ClassController.media = mediaPlayer;
	}
	
	/**
	 * stops the music
	 * @param media : MediaPlayer
	 */
	public void stop(MediaPlayer media) {
		media.stop();
	}
	
	/**
	 * launches the BrainUp
	 * @param args
	 */
	public static void main(String[] args) {
		
		launch(args);
	}
}