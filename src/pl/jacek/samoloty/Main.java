package pl.jacek.samoloty;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class Main extends Application {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root, 1200, 600));
		primaryStage.show();
	}
}
