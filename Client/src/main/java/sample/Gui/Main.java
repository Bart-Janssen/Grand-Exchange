package sample.Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Main extends Application implements Observer
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        primaryStage.setTitle("Grand Exchange");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }

    @Override
    public void stop()
    {
        System.exit(-1);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg)
    {
    }
}