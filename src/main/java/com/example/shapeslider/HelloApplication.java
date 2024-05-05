package com.example.shapeslider;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Polygon;

import java.io.IOException;



public class HelloApplication extends Application {
    private Pane root;
    private Shape[] shapes;
    private int currentIndex;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        shapes = new Shape[4];
        shapes[0] = new Circle(100, 150, 50, Color.BLUE);
        shapes[1] = new Rectangle(50, 50, 100, 100);
        shapes[2] = new Polygon(150, 50, 250, 150, 50, 150);
        shapes[3] = createTriangle(3, 250, 100, 50);
        for (Shape shape : shapes) {
            setupShape(shape);
        }

        Button prevButton = new Button("Previous");
        prevButton.setLayoutX(50);
        prevButton.setLayoutY(250);
        prevButton.setOnAction(e -> showPreviousShape());

        Button nextButton = new Button("Next");
        nextButton.setLayoutX(150);
        nextButton.setLayoutY(250);
        nextButton.setOnAction(e -> showNextShape());

        Button bgButton = new Button("Change Background");
        bgButton.setLayoutX(250);
        bgButton.setLayoutY(250);
        bgButton.setOnAction(e -> changeBackgroundColor());

        root.getChildren().addAll(shapes);
        root.getChildren().addAll(prevButton, nextButton, bgButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shape Slider");
        primaryStage.show();
    }

    private void setupShape(Shape shape) {
        shape.setOnMouseDragged(e -> {
            shape.setLayoutX(e.getSceneX() - shape.getBoundsInLocal().getWidth() / 2);
            shape.setLayoutY(e.getSceneY() - shape.getBoundsInLocal().getHeight() / 2);
        });
    }

    private void showPreviousShape() {
        currentIndex = (currentIndex - 1 + shapes.length) % shapes.length;
        showShapeAtIndex(currentIndex);
    }

    private void showNextShape() {
        currentIndex = (currentIndex + 1) % shapes.length;
        showShapeAtIndex(currentIndex);
    }

    private void showShapeAtIndex(int index) {
        root.getChildren().removeAll(shapes);
        root.getChildren().add(shapes[index]);
    }

    private void changeBackgroundColor() {
        Color randomColor = Color.rgb((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
        root.setStyle("-fx-background-color: #" + randomColor.toString().substring(2));
    }

    private Shape createTriangle(int numPoints, double x, double y, double size) {
        Polygon triangle = new Polygon();
        for (int i = 0; i < numPoints * 2; i += 2) {
            double angle = Math.toRadians((360 / numPoints) * i);
            double xPos = x + size * Math.cos(angle);
            double yPos = y + size * Math.sin(angle);
            triangle.getPoints().addAll(xPos, yPos);
        }
        triangle.setFill(Color.GREEN);
        return triangle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
