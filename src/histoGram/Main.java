package histoGram;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class Main extends Application{
    // boundaries of window size
    final int WIDTH = 1100;
    final int HEIGHT = 800;
    
    // function to set up stage
    @Override
    public void start(Stage primaryStage) {
        try{
            // Setting up primary stage, canvas, and graphics context
            primaryStage.setTitle("Jacob Onbreyt Assignment 3 CSC221"); 
            Canvas canvas = new Canvas(WIDTH,HEIGHT); 
            // Putting graphics into canvas
            GraphicsContext graphics = canvas.getGraphicsContext2D();
            graphics.setLineWidth(1);
            MyPoint center = new MyPoint(WIDTH/2.0, HEIGHT/2.0); // canvas center
            // GUI COMPONENT:
            // Function for dialog popup which takes user input
            // https://code.makery.ch/blog/javafx-dialogs-official/
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Piechart Program Input");
            dialog.setHeaderText("Enter Number of Events");
            dialog.setContentText("Enter an integer");
            Optional<String> result = dialog.showAndWait();
            int N = 3; // default amount of inputs needed to slices to be shown
            if(result.isPresent()){
                N = Integer.parseInt(result.get());
            }
            
            // taking text file from local drive and storing it within the program
            String fileLocation = "/Users/jonbreyt/Desktop/221/Alice in Wonderland.txt";
            // file is taken by program and all upper case letters switched to lower case
            Scanner input = new Scanner(Paths.get(fileLocation));
            String content = "";
            while(input.hasNext()){
                content += input.nextLine().replaceAll("^[a-zA-z]", "").toLowerCase();
            }
            input.close(); // close file
            HistogramAlphabet text = new HistogramAlphabet(content);
            Map<String, Integer> sortedFrequency = text.sortDecreasing();
            sortedFrequency.forEach((K, V) -> System.out.println(K + ": " + V)); // freq of characters
            HistogramAlphabet.MyPieChart pieChart = text.new MyPieChart(N, center, 250, 0.0);
            
            // Creating Histogram with N elements
            System.out.println(pieChart.getProbability());
            System.out.println("\nSum of Probabilities: " + pieChart.getSumOfProbability() + "\n");
            Map<String, Slice> slices = pieChart.createPieChart();
            slices.forEach((K, V) -> System.out.println(K + ": " + slices.get(K)));
            
            double angleSum = 0.0;
            for (String key : slices.keySet()){
                angleSum += slices.get(key).getAngle();
            }
            System.out.println("\nSum of angles: " + angleSum);
            pieChart.draw(graphics);
            
            // To setup stack pane and scene
            StackPane sp = new StackPane(canvas);
            Scene scene = new Scene(sp, WIDTH, HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        // Catch error exceptions for invalid file and invalid input
        catch(IOException ioException){
            System.err.println("FILE NOT FOUND!");
        }catch(NoSuchElementException elementException){
            System.err.println("INPUT INVALID! Please try a different input");
        }catch(IllegalStateException stateException){
            System.err.println("ERROR PROCESSING FILE! Please try again");
        }catch(Exception e){
            e.printStackTrace();       
        }
    }
    
    public static void main(String[] args) { 
        launch(args);
    }
}