import Model.*;
import View.MyViewController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


import java.net.URL;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MyModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);

        //Loading Main Windows
        primaryStage.setTitle("Maze Project");
        primaryStage.setWidth(900);
        primaryStage.setHeight(600);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View/MyView.fxml").openStream());
        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("View/MyStyle.css").toExternalForm());
        primaryStage.setScene(scene);

        //View
        MyViewController view = fxmlLoader.getController();
        view.initialize(viewModel,primaryStage,scene);
        viewModel.addObserver(view);
        //--------------
        setStageCloseEvent(primaryStage, model);
        //
        //Show the Main Window
        primaryStage.show();
    }

    private void setStageCloseEvent(Stage primaryStage, MyModel model) {
        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                // Close the program properly
                model.Stop();
            } else {
                // ... user chose CANCEL or closed the dialog
                event.consume();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}




//package View;
//import Model.MyModel;
//import ViewModel.MyViewModel;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//import javafx.stage.WindowEvent;
//
//import javax.swing.*;
//import java.util.Optional;
//
//public class Main extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("/View/MyView.fxml"));
////        Button b = new Button();
////        b.setText("click");
////        StackPane layout = new StackPane();
////        layout.getChildren().add(b);
////        controller.setVisible(true);
////        controller.setSize(640,480);
////        controller.setLocationRelativeTo(null);
////        controller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        primaryStage.setTitle("Maze Project");
//        Scene scene = new Scene(root,700,600);
//        scene.getStylesheets().add(getClass().getResource("MyStyle.css").toExternalForm());
//        primaryStage.setScene(scene);
//        primaryStage.setOnCloseRequest(event ->  {
//                ;
//
//        });
//        primaryStage.show();
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
////@Override
////public void start(Stage primaryStage) throws Exception {
/////*
////        final URL music = getClass().getResource("./View/resources/GOT/......."); // insert song
////        media = new Media(music.toString());
////        MediaPlayer mediaPlayer = new MediaPlayer(music);
////  */
////    //ViewModel -> Model
////
////    MyModel model = new MyModel();
////    model.startServers();
////    MyViewModel viewModel = new MyViewModel(model);
////    model.addObserver(viewModel);
////
////    //Loading Main Windows
////    primaryStage.setTitle("GOT MAZE");
////    primaryStage.setWidth(700);
////    primaryStage.setHeight(600);
////    FXMLLoader fxmlLoader = new FXMLLoader();
////    Parent root = FXMLLoader.load(getClass().getResource("/View/MyView.fxml"));
////    Scene scene = new Scene(root, 800, 700);
////    scene.getStylesheets().add(getClass().getResource("MyStyle.css").toExternalForm());
////    primaryStage.setScene(scene);
////
////    //View -> ViewModel
////    MyViewController view = fxmlLoader.getController();
////    view.initialize(viewModel,primaryStage,scene);
////    viewModel.addObserver(view);
////    //--------------
//////    setStageCloseEvent(primaryStage, model);
////    //
////    //Show the Main Window
////    primaryStage.show();
////}
////
//////    private void setStageCloseEvent(Stage primaryStage, MyModel model) {
//////        primaryStage.setOnCloseRequest(event -> {
//////            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?");
//////            Optional<ButtonType> result = alert.showAndWait();
//////            if (result.get() == ButtonType.OK){
//////                // ... user chose OK
//////                // Close the program properly
//////                model.close();
//////            } else {
//////                // ... user chose CANCEL or closed the dialog
//////                event.consume();
//////            }
//////        });
//////    }
////
////    public static void main(String[] args) {
////        launch(args);
////    }
////}