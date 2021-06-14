package gol;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.List;

/**
 * The Object Type of a single field
 */
public class Field extends StackPane {
    private int x;
    private int y;
    private int w=10;
    private  int h=10 ;
    private boolean alive;
    private List<Field> neighbors;
    private int aliveNeigbours;

    private Rectangle border = new Rectangle(w,h);
    private Text content = new Text("");
    private Text mark = new Text("");

    private Model m;

    /**
     * Initializes the passed x and y coordinates, whether it contains a Mine
     *
     * @param x
     * @param y
     * @param alive
     */
    public Field(int x, int y, boolean alive, Model m){
        this.x = x;
        this.y = y;
        this.m = m;
        this.alive=alive;

        this.border.setStroke(Color.BLACK);
        setColor();
        this.content.setVisible(false);
        getChildren().addAll(border,content,mark);
    }

    public Field(Field field){
        this(field.getX(), field.getY(), field.getIsAlive(), field.getModel());
    }

    /**
     * Called when the Field is clicked with the left mouse button
     * If the Field is Marked or already activated the method stops
     * Otherwise the Field is colored in White and displays the amount of neighboring mines
     * or calls the end Method should it contain amine itself
     * If the Field doesnt contain a mine and none of its neighoring fields does the neighbors activat
     * methods are called.
     */
    public void toggle() {
        if(alive){
            alive=false;
        }else{
            alive = true;
        }
        setColor();
    }

    public void setColor(){
        if(alive){
            border.setFill(Color.WHITE);
        }else{
            border.setFill(Color.BLACK);
        }
    }

    /**
     * changes the fields width and height
     * @param s new size
     */
    public void setSize(double s){
        this.border.setHeight(s);
        this.border.setWidth(s);
    }

    /**
     * Returns the Fields x Coordinate
     * @return
     */
    public int getX(){
        return this.x;
    }

    /**
     * Returns the Fields y Coordinate
     * @return
     */
    public int getY(){
        return this.y;
    }

    /**
     * Returns whether the Field contains a mine or not
     * @return
     */
    public boolean getIsAlive(){
        return this.alive;
    }

    public void setIsAlive(boolean alive){
        this.alive=alive;
        setColor();
    }

    /**
     * Passes a list containing the Fields neighboring Fields
     * @param neighbors
     */
    public void setNeighbors(List<Field> neighbors) {
        this.neighbors = neighbors;
    }

    public int getAliveNeigbours() {
        return aliveNeigbours;
    }

    public void setAliveNeigbours(int aliveNeigbours) {
        this.aliveNeigbours = aliveNeigbours;
    }

    public Rectangle getRect() {
        return border;
    }
    public Model getModel() {
        return m;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Field cloned = (Field) super.clone();
        return cloned;
    }
}
