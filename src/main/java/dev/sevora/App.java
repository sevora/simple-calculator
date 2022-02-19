package dev.sevora;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * App --- calculator program with GUI that uses Java 11 and JavaFX .
 * @author Ralph Louis Gopez
 */
public class App extends Application
{
    private Calculator calculator = new Calculator(); // This is what is used for the functionality in terms of logic of the app.
    private Layout layout = new Layout(400, 600); // JavaFX Objects
    
    /**
     * Launches the JavaFX application.
     * @param args
     */
    public static void main( String[] args )
    {
        launch(args);
    }

    /**
     * Starts the application.
     * @param primaryStage A JavaFX Stage Instance.
     */
    @Override
    public void start(Stage primaryStage) {
        // The GUI and the Logic is separated by design.
        Layout layout = this.layout;
        Button[] buttons = this.layout.getButtons();

        // This binds the event handler on all the buttons
        for (Button button : buttons) {
            if (button != null) {
                button.setOnAction(event -> buttonHandler(event));
            }
        }

        primaryStage.setTitle("Simple Calculator");
        primaryStage.setScene( layout.getScene() );
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Used as the callback for a button click.
     * @param event This is the event passed from the button.
     */
    public void buttonHandler(ActionEvent event) {
        Button button = (Button) event.getSource();
        Calculator calculator = this.calculator;
        Label label = this.layout.getLabel();

        calculator.punch(button.getText());
        label.setText(calculator.display());
    }

}
