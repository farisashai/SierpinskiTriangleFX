package FX.Graphics.SierpinskiTriangle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.net.URL;
import java.util.*;
public class Controller implements Initializable {
    @FXML private AnchorPane pane;
    @FXML private Button restart, color;
    @FXML private TextField iter, rad;
    private int index = 0, iterations = 1000;
    private boolean rainbow = true;
    private String bgColor = "rgb(200,255,200)";
    private double radius = 4;
    private List<Coordinate> corners = new LinkedList<>();
    @Override public void initialize(URL location, ResourceBundle resources) {
        pane.setStyle("-fx-background-color: "+bgColor);
        iter.setText(iterations+"");
        rad.setText(radius+"");
        color.setText("COLOR: " + (rainbow ? "ON" : "OFF"));
        color.setOnMouseClicked(event -> {
            rainbow = !rainbow;
            color.setText("COLOR: " + (rainbow ? "ON" : "OFF"));
        });
        pane.setOnMouseClicked(this::handle);
        restart.setOnMouseClicked(event -> {
            index = 0;
            pane.getChildren().clear();
            pane.getChildren().addAll(restart, iter, rad,color);
            corners.clear();
            try {
                iterations = Integer.parseInt(iter.getText());
            } catch (NumberFormatException ignored) {}
            try {
                radius = Double.parseDouble(rad.getText());
            } catch (NumberFormatException ignored) {}
            pane.setOnMouseClicked(this::handle);
        });
    }
    private Coordinate randSpotInTriangle(List<Coordinate> corners) {
        double r1 = Math.random(), r2 = Math.random();
        return new Coordinate((int)((1 - Math.sqrt(r1)) * corners.get(0).x + (Math.sqrt(r1) * (1 - r2)) * corners.get(1).x + (Math.sqrt(r1) * r2) * corners.get(2).x),(int)((1 - Math.sqrt(r1)) * corners.get(0).y + (Math.sqrt(r1) * (1 - r2)) * corners.get(1).y + (Math.sqrt(r1) * r2) * corners.get(2).y));
    }
    private Circle spot (Coordinate c) {
        Circle circle = new Circle(c.x,c.y,radius);
        circle.setFill(!rainbow ? Color.BLACK : new Color(Math.random(),Math.random(),Math.random(),1));
        return circle;
    }
    private void handle(MouseEvent event) {
        if (index++ < 2) {
            corners.add(new Coordinate((int) event.getX(), (int) event.getY()));
            pane.getChildren().add(spot(corners.get(corners.size()-1)));
        } else {
            corners.add(new Coordinate((int) event.getX(), (int) event.getY()));
            pane.getChildren().add(spot(corners.get(2)));
            Coordinate currentSpot = randSpotInTriangle(corners);
            for (int i = 0; i < iterations; i++) {
                Coordinate corner = corners.get((int) (Math.random() * corners.size()));
                currentSpot = new Coordinate((corner.x + currentSpot.x) / 2,(corner.y + currentSpot.y) / 2);
                pane.getChildren().add(spot(currentSpot));
            }
            pane.setOnMouseClicked(event1 -> {});
        }
    }
    private class Coordinate {
        private Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
        private int x, y;
    }
}