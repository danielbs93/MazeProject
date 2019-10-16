package View;

import Server.Configurations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class PropertiesController implements Initializable {

    private String AlgoToUse;
    private Stage stage;
    @FXML
    public ChoiceBox<String> AlgorithmChoiceBox;

    public void setStage(Stage s) {
        stage = s;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AlgorithmChoiceBox = new ChoiceBox<String>();
        AlgorithmChoiceBox.getItems().addAll("BreadthFirstSearch","BestFirstSearch","DepthFirstSearch");
        AlgorithmChoiceBox.setValue("BestFirstSearch");
    }

    //---USER CHOOSING SEARCH ALGORITHM---

    public void ChoiceHasBeenMade() {
        AlgoToUse = (String) AlgorithmChoiceBox.getValue();
        if (AlgoToUse != null) {
            if (AlgoToUse.equals("BreadthFirstSearch"))
                ChangeAlgoProperties("BFS");
            else if (AlgoToUse.equals("BestFirstSearch"))
                ChangeAlgoProperties("Best");
            else if (AlgoToUse.equals("DepthFirstSearch"))
                ChangeAlgoProperties("DFS");

            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An algorithm must to be chosen before we start the game, please try again :)");
            alert.showAndWait();
        }
    }

    //---CLOSING STAGE---

    public void Close() {
        stage.close();
    }
    private void ChangeAlgoProperties(String AlgoSearch) {
        Configurations config = null;
        try {
            config = new Configurations();
            config.setSearchAlgorithm(AlgoSearch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
