

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ShrinkerMain extends Application {

    int shape;
    double percentage;
    boolean redSelected = false;
    boolean greenSelected = false;
    boolean blueSelected = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        Scene scene = new Scene(group, 400, 600);

        Canvas canvas = new Canvas(400, 300);


        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton rb_circle = new RadioButton("Circle");
        RadioButton rb_square = new RadioButton("Square");
        RadioButton rb_triangle = new RadioButton("Triangle");

        VBox vb_RadioButtons = new VBox();
        vb_RadioButtons.getChildren().addAll(rb_circle, rb_square, rb_triangle);
        vb_RadioButtons.setSpacing(10);
        rb_circle.setToggleGroup(toggleGroup);
        rb_circle.setSelected(true);
        rb_square.setToggleGroup(toggleGroup);
        rb_triangle.setToggleGroup(toggleGroup);

        CheckBox chb_red = new CheckBox("Red");
        CheckBox chb_green = new CheckBox("Green");
        CheckBox chb_blue = new CheckBox("Blue");

        VBox vb_CheckBoxes = new VBox();
        vb_CheckBoxes.getChildren().addAll(chb_red, chb_green, chb_blue);
        vb_CheckBoxes.setSpacing(10);

        TitledPane tp_shape = new TitledPane();
        tp_shape.setText("Shape");
        tp_shape.setCollapsible(false);
        tp_shape.setPrefSize(180, 120);
        tp_shape.setContent(vb_RadioButtons);

        TitledPane tp_color = new TitledPane();
        tp_color.setText("Color");
        tp_color.setCollapsible(false);
        tp_color.setPrefSize(180, 120);
        tp_color.setContent(vb_CheckBoxes);

        ComboBox<Integer> cb_percentage = new ComboBox<>();
        for (int x = 1; x <= 100; x++) {
            cb_percentage.getItems().add(x);
        }
        cb_percentage.getSelectionModel().select(9);

        TitledPane tp_shrinkPercentage = new TitledPane();
        tp_shrinkPercentage.setText("Shrink Percentage");
        tp_shrinkPercentage.setCollapsible(false);
        tp_shrinkPercentage.setPrefSize(380, 60);
        tp_shrinkPercentage.setContent(cb_percentage);

        Button create = new Button("Create");

        HBox hb_shrinkPercentage = new HBox();
        hb_shrinkPercentage.getChildren().addAll(tp_shrinkPercentage);
        hb_shrinkPercentage.setPadding(new Insets(10, 10, 10, 10));

        HBox hb_shape = new HBox();
        hb_shape.getChildren().addAll(tp_shape);

        HBox hb_color = new HBox();
        hb_color.getChildren().addAll(tp_color);

        HBox hb_shapesColors = new HBox();
        hb_shapesColors.getChildren().addAll(hb_shape, hb_color);
        hb_shapesColors.setSpacing(20);
        hb_shapesColors.setPadding(new Insets(0, 10, 10, 10));

        HBox hb_create = new HBox();
        hb_create.getChildren().addAll(create);
        hb_create.setAlignment(Pos.CENTER);

        HBox hb_canvas = new HBox();
        hb_canvas.getChildren().addAll(canvas);


        VBox vb_allHBoxes = new VBox();
        vb_allHBoxes.getChildren().addAll(hb_shapesColors, hb_shrinkPercentage, hb_create, hb_canvas);

        group.getChildren().addAll(vb_allHBoxes);

        primaryStage.setScene(scene);

        paint(canvas.getGraphicsContext2D());

        create.setOnAction(event -> {

            if (!chb_blue.isSelected() && !chb_green.isSelected() && !chb_red.isSelected()) {
                new Alert(Alert.AlertType.ERROR, "No Color is Selected").showAndWait();
            } else {
                percentage = (100 - cb_percentage.getSelectionModel().getSelectedItem()) / 100.0;

                if (rb_circle.isSelected()) {
                    shape = 1;
                } else if (rb_square.isSelected()) {
                    shape = 2;
                } else if (rb_triangle.isSelected()) {
                    shape = 3;
                }

                if (chb_blue.isSelected()) {
                    blueSelected = true;
                } else blueSelected = false;
                if (chb_green.isSelected()) {
                    greenSelected = true;
                } else greenSelected = false;
                if (chb_red.isSelected()) {
                    redSelected = true;
                } else redSelected = false;


            }

            paint(canvas.getGraphicsContext2D());

        });

        primaryStage.show();
    }

    public void paint(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.fillRect(20, 10, 360, 300);

        if (shape == 1) {
            double h = 200;
            double w = 200;
            double oldh;
            double oldw;
            double x = 100;
            double y = 45;
            double oldx;
            double oldy;

            while (h > 1.0 && w > 1.0) {

                if (redSelected && greenSelected && blueSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1));
                } else if (redSelected && greenSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1, 0));
                } else if (redSelected && blueSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, 0, (int) (Math.random() * 255) + 1));
                } else if (greenSelected && blueSelected) {
                    gc.setStroke(Color.rgb(0, (int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1));
                } else if (redSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, 0, 0));
                } else if (greenSelected) {
                    gc.setStroke(Color.rgb(0, (int) (Math.random() * 255) + 1, 0));
                } else if (blueSelected) {
                    gc.setStroke(Color.rgb(0, 0, (int) (Math.random() * 255) + 1));
                }

                gc.strokeOval(x, y, w, h);
                oldw = w;
                oldh = h;
                oldx = x;
                oldy = y;
                w *= percentage;
                h *= percentage;
                x = ((oldw - w) / 2) + oldx;
                y = ((oldh - h) / 2) + oldy;

            }
        } else if (shape == 2) {
            double h = 200;
            double w = 200;
            double oldh;
            double oldw;
            double x = 100;
            double y = 45;
            double oldx;
            double oldy;

            while (h > 1.0 && w > 1.0) {

                if (redSelected && greenSelected && blueSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1));
                } else if (redSelected && greenSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1, 0));
                } else if (redSelected && blueSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, 0, (int) (Math.random() * 255) + 1));
                } else if (greenSelected && blueSelected) {
                    gc.setStroke(Color.rgb(0, (int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1));
                } else if (redSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, 0, 0));
                } else if (greenSelected) {
                    gc.setStroke(Color.rgb(0, (int) (Math.random() * 255) + 1, 0));
                } else if (blueSelected) {
                    gc.setStroke(Color.rgb(0, 0, (int) (Math.random() * 255) + 1));
                }

                gc.strokeRect(x, y, w, h);
                oldw = w;
                oldh = h;
                oldx = x;
                oldy = y;
                w *= percentage;
                h *= percentage;
                x = ((oldw - w) / 2) + oldx;
                y = ((oldh - h) / 2) + oldy;

            }

        } else if (shape == 3) {

            double[] xpoints = {100, 200, 300};
            double[] ypoints = {250, 50, 250};

            double oldx2, oldx1, oldx3, oldy2, oldy13;

            int count = 0;
            double width = 200;
            double oldW;
            double height = 150;
            double oldH;
            while ((xpoints[2] - xpoints[0]) > 0) {


                if (redSelected && greenSelected && blueSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1));
                } else if (redSelected && greenSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1, 0));
                } else if (redSelected && blueSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, 0, (int) (Math.random() * 255) + 1));
                } else if (greenSelected && blueSelected) {
                    gc.setStroke(Color.rgb(0, (int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1));
                } else if (redSelected) {
                    gc.setStroke(Color.rgb((int) (Math.random() * 255) + 1, 0, 0));
                } else if (greenSelected) {
                    gc.setStroke(Color.rgb(0, (int) (Math.random() * 255) + 1, 0));
                } else if (blueSelected) {
                    gc.setStroke(Color.rgb(0, 0, (int) (Math.random() * 255) + 1));
                }


                gc.strokePolygon(xpoints, ypoints, 3);

                oldx2 = xpoints[1];
                oldx1 = xpoints[0];
                oldx3 = xpoints[2];
                oldW = oldx3 - oldx1;
                oldy2 = ypoints[1];
                oldy13 = ypoints[0];
                oldH = oldy13 - oldy2;
                width *= percentage;
                height *= percentage;

                xpoints[0] = oldx1 + ((oldW - width) / 2);
                xpoints[2] = oldx3 - ((oldW - width) / 2);

                ypoints[1] = oldy2 + ((oldH - height) / 2);
                ypoints[0] = oldy13 - ((oldH - height) / 6);
                ypoints[2] = oldy13 - ((oldH - height) / 6);

                count++;
                System.out.println("dist " + (xpoints[2] - xpoints[0]));
                System.out.println("xpoint 2 " + xpoints[2]);
                System.out.println("xpoint 0 " + xpoints[0]);

            }
            System.out.println("count " + count);
        }


    }

}













