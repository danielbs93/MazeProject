package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.*;
import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MyViewController implements Observer, IView, Initializable {
//public class MyViewController<pane> implements Observer, IView {
    //---MVP---
    private AppController appController;
    private MyViewModel viewModel;
    private Scene mainScene;
    private Stage mainStage;
    public BorderPane board;
    public Pane pane;
    public Maze2DDisplayer mazeDisplayer;


    //---MEDIA---

    public MediaPlayer mediaPlayer;
    public ImageView ImgFileWall;
    public ImageView ImgFileWalk;
    public ImageView ImgFilePlayer;
    public ImageView ImgFileStart;
    public ImageView ImgFileGoal;
    public ImageView ImgFileSolution;

    //---FXML FIELDS---
    @FXML
    public javafx.scene.control.TextField textField_mazeRows;
    public javafx.scene.control.TextField textField_mazeColumns;
    public javafx.scene.control.TextField textField_PlayerName;
//    public javafx.scene.control.TextField textField_mazeHeight;

    //---CHECK BOXES---

    public javafx.scene.control.CheckBox Maze3D_CBX;
    public javafx.scene.control.CheckBox Mute_CBX;
    public MenuItem load;

    //---INDICATORS---

    private static Boolean isGenerated = false;
    private static Boolean isSolved = false;
    private static Boolean isMoved = false;
    private static Boolean isLoaded = false;

    //---OBSERVER & OBSERVABLE FUNCTIONS---
    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {

            //change the maze - generate was pressed again
            if (isGenerated) {
                appController.GenerateMaze();
                isGenerated = false;
            }
            //solve was pressed
            if (isSolved) {
                appController.SolveMazeFunctionCall();
                isSolved = false;
            }
            //movement key pressed
            if (isMoved) {
                appController.move();
                isMoved = false;
            }
        }
    }

    public void initialize(MyViewModel vm, Stage stage, Scene scene) {
        setViewModel(vm);
        this.mainStage = stage;
        this.mainScene = scene;
        Music();


//        ArrayList<String> pics = new ArrayList<>();
//        ArrayList<ImageView> imgs = new ArrayList<>();
//
//        pics.add("ImageFileWall1.jpg");
//        pics.add("ImageFileWalk1.jpg");
//        pics.add("ImageFilePlayer.jpg");
//        pics.add("ImageFileGoal.jpg");
//            pics.add("ImageFileSolution.jpg");
//            pics.add("ImageFileStart.jpg");

//        ImgFileGoal = new ImageView();
//        ImgFileWall = new ImageView();
//        ImgFileWalk = new ImageView();
//        ImgFileStart = new ImageView();
//        ImgFilePlayer = new ImageView();
//        ImgFileSolution = new ImageView();
//
//        imgs.add(this.ImgFileWall);
//        imgs.add(this.ImgFileWalk);
//        imgs.add(this.ImgFilePlayer);
//        imgs.add(this.ImgFileGoal);
//            imgs.add(this.ImgFileSolution);
//            imgs.add(this.ImgFileStart);

//        for (int i = 0; i < 4 ; i++) {
//            Image img = new Image(this.getClass().getClassLoader().getResourceAsStream(pics.get(i)));
////            imgs.get(i) = new ImageView(img);
//            imgs.get(i).setImage(img);
//        }
    }

    //---NECESSARY MVVM SETTERS---

    public void setAppController(AppController apc) {
        this.appController = apc;
    }

    public void setViewModel(MyViewModel mvm) {
        this.viewModel = mvm;
    }


    @Override
    public void MazeDisplayer(Maze maze) {
        appController.MazeDisplayer(maze);
    }

    @Override
    public void SolutionDisplayer(Solution solution, Maze maze) {
        appController.SolutionDisplayer(solution, maze);
    }

    @Override
    public void PlayerDisplayer(int playerrow, int playercolumn, Maze maze) {
        appController.PlayerDisplayer(playerrow, playercolumn, maze);
    }


    private void generate2DMaze(int rows, int columns) {
        viewModel.generate2DMaze(rows, columns);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        appController.GenerateMaze();
    }

    /**
     * FUNCTION FOR 3D MAZES
     */
//    private void generate3DMaze(int rows, int columns) {
//        int height = Integer.valueOf(textField_mazeHeight.getText());
//        viewModel.generate3DMaze(rows,columns,height);
//
//    }
    public void generateMaze() {
        int rows = Integer.valueOf(textField_mazeRows.getText());
        int columns = Integer.valueOf(textField_mazeColumns.getText());
        isGenerated = true;
        try {
            //openning new Stage to show in
            Stage stage = new Stage();
            stage.setTitle("Daniel & Eran Maze");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AppView.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 590, 650);
            stage.setScene(scene);
            //loading the controllers of the new stage:
            AppController app = fxmlLoader.getController();
            setAppController(app);
            if (viewModel == null)
                viewModel = new MyViewModel(new MyModel());
            app.setApp(stage, this.viewModel, this);
            //Lock the main window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isLoaded) {
            appController.GenerateMaze();
            isLoaded = false;
        } else {

//        if (Boolean.valueOf(Maze3D_CBX.getText()))
//            generate3DMaze(rows,columns);
//        else
//            generate2DMaze(rows,columns);
            generate2DMaze(rows, columns);
        }
    }

    public void AppisUp(ActionEvent actionEvent) {
        //blessing the player for starting the game with a message
        String PlayerName = textField_PlayerName.getText();
        int rows = Integer.valueOf(textField_mazeRows.getText());
        int columns = Integer.valueOf(textField_mazeColumns.getText());
        if (rows < 3 || columns < 3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Hi " + PlayerName + " !");
            alert.setContentText("Please insert values that bigger than 2");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Hi " + PlayerName + " !");
            alert.setContentText("Your Maze will be ready in a few seconds. Get ready we are getting started!");
            alert.showAndWait();
            generateMaze();
            /**
             * goes to view model controler and generate maze.
             */
        }

    }


    //---SETS IMAGES---

    public void setPlayerImg() {
        appController.setPlayerImg("resources/ImageFilePlayer.jpg");
    }

    public void setMazeImgs() {
        String wall = "resources/ImageFileWall.jpg";//--------------------------------NEED TO CHANGE INDEX OF PIC------------
        String walk = "resources/ImageFileWalk.jpg";
        String start = "resources/ImageFileStart.jpg";
        String goal = "resources/ImageFileGoal.jpg";
        appController.setMazeImg(wall, walk, start, goal);
    }

    public void AboutFunctionCall(ActionEvent actionEvent) {
        NewStage("About us", "About.fxml", 730, 550);
    }

    public void PropertiesFunctionCall(ActionEvent actionEvent) {
        try {

            Stage stage = new Stage();
            stage.setTitle("Properties");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PropertiesView.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 400, 120);
            stage.setScene(scene);
            stage.setResizable(false);
            //loading the controllers of the new stage:
            PropertiesController properties = fxmlLoader.getController();
            properties.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void NewFunctionCall(ActionEvent actionEvent) {
    }


    public void LoadFunctionCall(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your maze please");
        fileChooser.setInitialDirectory(new File("resources/Mazes"));
        File file = fileChooser.showOpenDialog(new PopupWindow() {
        });
        if (file != null && file.exists() && !file.isDirectory()) {
            if (viewModel == null)
                viewModel = new MyViewModel(new MyModel());
            viewModel.LoadMaze(file);
            isLoaded = true;
            generateMaze();

        }
        actionEvent.consume();

    }

    public void CloseFunctionCall(ActionEvent actionEvent) {
        if (viewModel != null)
            viewModel.Exit();
        else
            Platform.exit();
    }

    public void CloseFunctionCall(WindowEvent actionEvent) {
        if (viewModel != null)
            viewModel.Exit();
        else
            Platform.exit();
    }

    public void HelpFunctionCall(ActionEvent actionEvent) {
        NewStage("Game rules", "Help.fxml", 450, 425);
    }

    //---OPENING NEW STAGE---

    private void NewStage(String title, String fxml_File, int width, int height) {
        try {

            Stage stage = new Stage();
            stage.setTitle(title);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml_File));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void SolveMazeCall() {
        if (viewModel.getMaze() != null) {
            isSolved = true;
            viewModel.solve2DMaze();//get Solution
        }
    }

    public void KeyPressed(KeyEvent keyEvent) {
        if (!isSolved) {
            isMoved = true;
            viewModel.PlayerisMoving(keyEvent.getCode());
            keyEvent.consume();
        }
    }

    //---MUSIC---

    public void Music() {
        Media play = new Media(new File("resources/Soundtrack.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(play);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
    }

    public void mute() {
        mediaPlayer.setMute(true);
        if (!Mute_CBX.isSelected())
            mediaPlayer.setMute(false);
    }

    public void Scroll(ScrollEvent scrollEvent) {
        //this.appController.MouseScrolling(scrollEvent);
    }

    public void setResizeEvent() {
        this.mainScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            //mazeDisplayer.redraw();
            System.out.println("Width: " + newValue);
        });

        this.mainScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            //mazeDisplayer.redraw();
            System.out.println("Height: " + newValue);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


//    public void initialize(MyViewModel viewModel, Stage mainStage, Scene mainScene) {
//        Music();
//        this.viewModel = viewModel;
//        this.mainScene = mainScene;
//        this.mainStage = mainStage;
//
//        setResizeEvent();
//
//
//        pane.prefHeightProperty().bind(board.heightProperty());
//        pane.prefWidthProperty().bind(board.widthProperty());
//        mazeDisplayer.heightProperty().bind(pane.heightProperty());
//        mazeDisplayer.widthProperty().bind(pane.widthProperty());
//        mazeDisplayer.heightProperty().addListener((observable, oldValue, newValue) -> {
//            if(viewModel.getMaze() != null)
//                MazeDisplayer(viewModel.getMaze());
//        });
//        mazeDisplayer.widthProperty().addListener((observable, oldValue, newValue) -> {
//            if(viewModel.getMaze() != null)
//                MazeDisplayer(viewModel.getMaze());
//        });
//    }


}
