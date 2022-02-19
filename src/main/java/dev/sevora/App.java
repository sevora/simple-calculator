package dev.sevora;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    private Calculator calculator = new Calculator();   
    private Layout layout = new Layout(400, 600);
    
    boolean hasTyped = false;
    
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Layout layout = this.layout;
        Button[] buttons = this.layout.getButtons();

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

    public void buttonHandler(ActionEvent event) {
        Button button = (Button) event.getSource();
        Calculator calculator = this.calculator;
        Label label = this.layout.getLabel();

        calculator.punch(button.getText());
        label.setText(calculator.display());
    }

}
