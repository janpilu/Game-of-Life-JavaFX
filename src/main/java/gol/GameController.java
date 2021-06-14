package gol;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController implements EventHandler<KeyEvent> {

    private Stage callback;
    private Field[][] map;
    private int size;
    private String diffStr;

    private Model m;

    ChangeListener<Number> resize = (observable, oldValue, newValue) ->
            resizeListen();

    @FXML
    private GridPane grid;

    @FXML
    private GridPane root;

    @FXML
    private MenuBar menu;

    @FXML
    private MenuItem resetItem;


    public void setup(int size, String diff, Stage callback){
        this.callback = callback;
        this.callback.heightProperty().addListener(resize);
        this.callback.widthProperty().addListener(resize);
        this.callback.setMinWidth(600);
        this.callback.setMinHeight(600);
        this.callback.getScene().setOnKeyPressed(this);
        this.menu.prefWidthProperty().bind(callback.widthProperty());
        //this.resetItem.setAccelerator());
        this.size=size;
        this.diffStr = diff;

        this.m = new Model(size);

        try {
            this.m.setDiff(diff);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("setup");
        this.map = m.setupBoard();
        this.addMouseEvent(map);

        initBoard(this.map);
    }

    public void initBoard(Field[][] map){
        for(int x = 0;x<size;x++){
            for(int y = 0; y<size;y++){
                this.grid.add(map[x][y],x,y);
            }
        }
    }

    public void resizeListen(){
        for (Field[] f:map) {
            for (Field temp:f) {
                if(callback.getHeight()>callback.getWidth())
                    temp.setSize((callback.getWidth()*0.9)/size);
                else
                    temp.setSize((callback.getHeight()*0.8)/size);
            }
        }
        this.root.setMinHeight((map[0][0].getHeight())*size);

        this.root.setMinWidth((map[0][0].getWidth())*size);
    }

    public void addMouseEvent(Field[][] map){
        for (Field[] f:map) {
            for (Field field:f) {
                field.setOnMouseClicked(this::onFieldClick);
            }
        }
    }

    public void onFieldClick(MouseEvent mouseEvent) {
        Field f = (Field) mouseEvent.getSource();
        if(mouseEvent.getButton()== MouseButton.PRIMARY){
            this.m.leftClick(f.getX(),f.getY());
        }
    }

    public void reset(ActionEvent event) {
        res();

    }

    public void res(){
        this.callback.close();
        try {
            setup(this.size, this.diffStr, this.callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.callback.show();
    }

    public void resize(ActionEvent event) {
        callback.close();
        sizeStage();
    }

    public void sizeStage() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/size.fxml"
                )
        );

        Stage stage = new Stage();
        try {
            stage.setScene(
                    new Scene(
                            (Pane) loader.load()
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Resize");
        stage.show();
    }

    @Override
    public void handle(KeyEvent event) {
        if(event.getCode() == KeyCode.R && event.isControlDown())
            res();
        if(event.getCode() == KeyCode.C && event.isControlDown())
            m.clear();
        if(event.getCode() == KeyCode.A && event.isControlDown())
            m.fill();
        if(event.getCode() == KeyCode.SPACE){
            m.play();
        }

    }

    public Model getM() {
        return m;
    }

    public void setM(Model m) {
        this.m = m;
    }

}
