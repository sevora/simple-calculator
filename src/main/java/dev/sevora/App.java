package dev.sevora;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Layout layout = new Layout(400, 600);
        primaryStage.setScene(layout.getScene());
        primaryStage.setTitle("Simple Calculator");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
