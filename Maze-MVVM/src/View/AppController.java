package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.scene.input.ScrollEvent;
import javafx.util.Duration;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AppController implements IView {

    //---VIEW OBJECTS---
    private Stage stage;
    private MyViewController ViewController;
    private MyViewModel ViewModel;

    //---VIEW DISPLAYERS---
    public Displayer mazeDisplayer;
    public PlayerDisplayer playerDisplayer;
    public SolutionDisplayer solutionDisplayer;
    private int PlayerRow, PlayerColumn;

    //---MEDIA---
    public MediaPlayer MusicPlayer;

    private static boolean Solved = false;

    //---BUTTONS---
    @FXML
    public javafx.scene.control.Button solve_BTN;
    public Button Return_BTN;

    //---CHECK BOX---
    public javafx.scene.control.CheckBox mute_CBX;



    //---INITIALIZING---
    public void setApp(Stage s, MyViewModel vm, MyViewController vc) {
        stage = s;
        ViewController = vc;
        ViewModel = vm;
    }

    public void MazeDisplayer(Maze m) {
        ((Maze2DDisplayer) mazeDisplayer).setMaze(m);
    }

    public void MazeDisplayer(Maze m, PlayerDisplayer p) {
        ((Maze2DDisplayer) mazeDisplayer).setMaze(m, p);
    }

    public void PlayerDisplayer(int row, int col, Maze m) {
        playerDisplayer.setPlayer(row, col, m);
    }

    public void SolutionDisplayer(Solution s, Maze m) {
        solutionDisplayer.setSolution(s, m);
    }

    //---Media SETTINGS---
    public void wallHit() {
        String path = "resources/Whistle.mp3";
        Media hit = new Media(new File(path).toURI().toString());
        MusicPlayer = new MediaPlayer(hit);
        MusicPlayer.play();
    }

    public void victoryMusic() {
        String path = "resources/Crowd.mp3";
        Media hit = new Media(new File(path).toURI().toString());
        MusicPlayer = new MediaPlayer(hit);
        MusicPlayer.setStopTime(new Duration(2600));
        MusicPlayer.play();
    }

    //---CLOSING THIS STAGE---
    public void closeStage() {
        stage.close();
    }

    //---SETS IMAGES---

    public void setPlayerImg(String img) {
        playerDisplayer.setImageFilePlayer(img);
        if (ViewModel.getMaze() != null)
            PlayerDisplayer(PlayerRow, PlayerColumn, ViewModel.getMaze());
    }

    public void setMazeImg(String wall, String walk, String start, String goal) {
        ((Maze2DDisplayer) mazeDisplayer).setImageFileWall(wall);
        ((Maze2DDisplayer) mazeDisplayer).setImageFileWalk(walk);
        ((Maze2DDisplayer) mazeDisplayer).setImageFileStart(start);
        ((Maze2DDisplayer) mazeDisplayer).setImageFileGoal(goal);
        if (ViewModel.getMaze() != null)
            MazeDisplayer(ViewModel.getMaze(), playerDisplayer); // check if we need a new one or just set the maze
    }

    //---Displaying Images---

    public void move() {
        PlayerRow = ViewModel.getPlayerRow();
        PlayerColumn = ViewModel.getPlayerColumn();

        //player hit wall music
        if (ViewModel.isWallPosition())
            wallHit();
        else {
            PlayerDisplayer(PlayerRow, PlayerColumn, ViewModel.getMaze());
            MazeDisplayer(ViewModel.getMaze(), this.playerDisplayer);
        }
        //PlayerDisplayer(PlayerRow,PlayerColumn,ViewModel.getMaze());

        //if player reached destination
        if (ViewModel.isGoalPosition(PlayerRow, PlayerColumn)) {
            //pop up message to try another game or exit
            Alert alert = new Alert(Alert.AlertType.NONE);
            //set victory music
            mute();
            victoryMusic();
            mute();
            alert.setTitle("Congratulations!");
            alert.setContentText("Excellent work ! ");
            ButtonType yesButton = new ButtonType("Lets do one more Maze", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("Maybe later", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yesButton, noButton);

            //checking what the player choose
            alert.showAndWait().ifPresent((buttonType) -> {
                if (buttonType == yesButton) {//generate another Maze
                    stage.close();
                    this.ViewController.generateMaze();
                } else {
                    closeStage();
                }
            });

        }
    }

    //---MENU ITEMS BAR---

    public void SaveFunctionCall(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a directory to save the maze");
        fileChooser.setInitialDirectory(new File("resources/Mazes"));

        //Display the window save
        File file = fileChooser.showSaveDialog(new PopupWindow() {
        });

        if (file != null)
            ViewModel.SaveMaze(file);
        actionEvent.consume();

    }

    public void ExitFunctionCall(ActionEvent actionEvent) {
        closeStage();
    }

    //---BUTTONS---

    public void GenerateMaze() {
        if (Solved)
            solve_BTN.setDisable(false);
        Solved = false;
        MazeDisplayer(ViewModel.getMaze());//display maze
        solutionDisplayer.clearSolution();//clear old solution
        PlayerRow = ViewModel.getPlayerRow();
        PlayerColumn = ViewModel.getPlayerColumn();
        PlayerDisplayer(PlayerRow, PlayerColumn, ViewModel.getMaze());
        //MazeDisplayer(ViewModel.getMaze(), this.playerDisplayer);
        //PlayerDisplayer(PlayerRow,PlayerColumn,ViewModel.getMaze());//char position


        //setting player and wall
        ViewController.setPlayerImg();
        ViewController.setMazeImgs();
    }

    public void SolveMazeFunctionCall() {
        if (!Solved) {
            solutionDisplayer.clearSolution();//clear old solution
            ViewController.SolveMazeCall();
            try {
                Thread.sleep(2000);
            SolutionDisplayer(ViewModel.getSolution(), ViewModel.getMaze());
            //            Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Solved = true;
            if (!solve_BTN.isDisable())
                solve_BTN.setDisable(true);
        }
    }

    public void mute() {
        ViewController.mediaPlayer.setMute(true);
        if (!mute_CBX.isSelected())
            ViewController.mediaPlayer.setMute(false);
    }

    public void MouseScrolling(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            double moovment = 1.05;
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY < 0) {
                moovment = 2.0 - moovment;
            }
            playerDisplayer.setScaleX(playerDisplayer.getScaleX() * moovment);
            playerDisplayer.setScaleX(playerDisplayer.getScaleY() * moovment);

            mazeDisplayer.setScaleX(mazeDisplayer.getScaleX() * moovment);
            mazeDisplayer.setScaleY(mazeDisplayer.getScaleY() * moovment);

            solutionDisplayer.setScaleX(solutionDisplayer.getScaleX() * moovment);
            solutionDisplayer.setScaleY(solutionDisplayer.getScaleY() * moovment);

        }
    }

    public void KeyPressed(KeyEvent keyEvent) {
        //this.ViewModel.PlayerisMoving(keyEvent.getCode());
        ViewController.KeyPressed(keyEvent);
    }

    public void NewFunctionCall(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Making new Maze");
        alert.setContentText("Are you sure you want to get a new maze? ");
        ButtonType yesButton = new ButtonType("Give me new one", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("I haven't finish yet!", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        //checking what the player choose
        alert.showAndWait().ifPresent((buttonType) -> {
            if (buttonType == yesButton) {//generate another Maze
                stage.close();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.ViewController.generateMaze();
            } else {
                alert.close();
            }
        });
    }


}
