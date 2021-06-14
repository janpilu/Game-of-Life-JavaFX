package gol;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The First Window's Controller responsible for inputs
 * concerning events regarding setting the game fields size, and the games difficulty
 */
public class SizeController implements Initializable {

    @FXML
    private ComboBox<Integer> sizeSelect;

    @FXML
    private Text show;

    @FXML
    private Button submitSize;

    @FXML
    private GridPane root;

    @FXML
    private ComboBox<String> difficultySelect;

    /**
     * Called when th Scene is initialized
     * Fills the ComboBoxes containing the size and difficulty with options
     * @param fxml
     * @param resources
     */
    @Override
    public void initialize(URL fxml, ResourceBundle resources){
        assert sizeSelect != null : "ups";
        assert difficultySelect != null : "ups";
        sizeSelect.getItems().setAll(10,15,20,25,30,35,50);
        difficultySelect.getItems().setAll("Easy","Medium","Hard");
        difficultySelect.setValue("Medium");
    }

    /**
     * Mirrors the Users size input into the Text field next to it
     * and enables the submit Button
     * @param event
     */
    public void setSize(ActionEvent event) {
        this.show.setText(this.sizeSelect.getValue()+"");
        this.submitSize.setDisable(false);
    }

    /**
     * Closes the current Stage and calls
     * the gameStage method
     */
    @FXML
    private void submitSize() {
        Stage stage = (Stage) submitSize.getScene().getWindow();
        stage.close();
        gameStage(sizeSelect.getValue(),difficultySelect.getValue());
    }

    /**
     * Initializes the gameStage and passes the map's size and the games difficulty
     * @param size
     * @param diff
     */
    public void gameStage(int size, String diff) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/game.fxml"
                )
        );

        Stage stage = new Stage();
        try {
            stage.setScene(
                    new Scene(
                            loader.load()
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameController controller =
                loader.getController();
        controller.setup(size,diff,stage);

        stage.setTitle("Minesweeper "+size+"x"+size+" / "+diff);
        stage.show();
    }
}
