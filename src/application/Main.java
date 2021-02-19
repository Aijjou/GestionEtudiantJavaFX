package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage primaryStage2;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage2 = primaryStage;
		try {

			Parent root = FXMLLoader.load(getClass().getResource("/vue/Scene.fxml"));
			primaryStage2.setTitle("Gestion Etudiant JavaFx");
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/vue/application.css");
			primaryStage2.setScene(scene);
			primaryStage2.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
