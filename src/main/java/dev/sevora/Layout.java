package dev.sevora;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Layout {
    private Label label;
    private GridPane gridPane;
    private Scene scene;
    private Button[] buttons = new Button[20];
    private String[] buttonKeys = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", "", ".", "="
    };

    public Layout(int width, int height) {
        gridPane = createGridPaneOfSize(4, 6);
        gridPane.setId("root");
        gridPane.setPrefSize(width, height);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //
        label = new Label("Hello World");
        label.setId("label");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.TOP_RIGHT);
        label.setPadding(new Insets(20, 5, 0, 0));
        gridPane.add(label, 0, 0, 4, 1);

        //
        fillGridPane(gridPane, 4, 6);

        scene = new Scene(gridPane);
        scene.getStylesheets().add(getClass().getResource("resources/stylesheet.css").toExternalForm());
    }

    private GridPane createGridPaneOfSize(final int columns, final int rows) {
        GridPane root = new GridPane();

        for (int column = 0; column < columns; ++column) {
            ColumnConstraints constraint = new ColumnConstraints();
            constraint.setPercentWidth(100.0 / columns);
            root.getColumnConstraints().add(constraint);
        }

        for (int row = 0; row < rows; ++row) {
            RowConstraints constraint = new RowConstraints();
            constraint.setPercentHeight(100.0 / rows);
            root.getRowConstraints().add(constraint);
        }

        return root;
    }

    private void fillGridPane(GridPane root, int columns, int rows) {
        loop:
        for (int y = 0; y < rows; ++y) {
            for (int x = 0; x < columns; ++x) {
                int index = y * columns + x;

                // there's no 17th button
                if (index == 17) continue;
                if (index > 19) break loop;

                buttons[index] = new Button(buttonKeys[index]);
                Button button = buttons[index];
                button.setId(String.format("button-%d", index));
                button.getStyleClass().add("button");
                System.out.println(button.getId());

                // maximizes the size of each grid for the buttons
                GridPane.setHgrow(button, Priority.ALWAYS);
                GridPane.setVgrow(button, Priority.ALWAYS);
                
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                
                // 16th element is for the 0 button which should take two spaces 
                // horizontally instead of one
                if (index == 16) {
                    root.add(button, x, y + 1, 2, 1);
                } else {
                    root.add(button, x, y + 1);
                }

            }
        }
    }

    public Scene getScene() {
        return scene;
    }

}
