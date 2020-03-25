package application;
	
import demineur.definitions.Definitions;
import demineur.game.Game;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class App extends Application {
	private Game game;
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{	
			primaryStage.setTitle("Demineur");
			primaryStage.setResizable(false);
			Definitions.stage=primaryStage;
			BorderPane root = new BorderPane();
			Definitions.root=root;
			Scene scene = new Scene(root,400,300);
			Definitions.stage.setHeight(600+38);
			Definitions.stage.setWidth(800+14);
			primaryStage.setScene(scene);
			primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth()-800)/2);
			primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight()-600)/2);
			game = new Game(800,600);
			primaryStage.show();
			game.start();
		} catch(Exception e) 
		{
			e.printStackTrace();
			stop();
		}
	}
	@Override
	public void stop(){
		System.exit(80);
	}

	
	public static void main(String[] args) {
		launch(args);

	}
}
