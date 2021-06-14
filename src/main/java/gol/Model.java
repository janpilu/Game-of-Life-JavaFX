package gol;

public class Model {
    private Field[][] cells;
    private boolean[][] states;
    private int rows;
    private int cols;
    private double diff;
    private boolean init;
    private boolean paused;
    GameRunner gameRunner;

    public Model(int size) {
        this.init=false;
        this.paused=false;
        this.rows = size;
        this.cols = size;
        gameRunner=new GameRunner(this);
    }

    public Field[][] setupBoard(){
        this.cells = new Field[cols][rows];
        this.states = new boolean[cols][rows];
        Field currField;
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                boolean isAlive=false;
                if(Math.random()<0.2){
                    isAlive = true;
                }
                currField = new Field(i,j,isAlive,this);
                this.cells[i][j] = currField;
                this.states[i][j]=isAlive;
            }
        }
        return cells;
    }

    public void clear(){
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                states[i][j]=false;
            }
        }
        applyStates();
    }

    public void fill(){
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                if(i%2==0 && j%2!=0){
                    states[i][j]=true;
                }else{
                    states[i][j]=false;
                }
            }
        }
        applyStates();
    }

    private void applyStates() {
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                cells[i][j].setIsAlive(states[i][j]);
            }
        }
    }

    public void leftClick(int x, int y){
        if(states[x][y]){
            states[x][y]=false;
        }else {
            states[x][y]=true;
        }
        applyStates();
    }

    public int getAliveNeighbours(int x, int y){
        int amount = 0;
        for(int i = -1;i<=1;i++){
            for(int j = -1;j<=1;j++){
                int newX = x+i;
                int newY = y+j;
                if(newX<0||newX>=cols||newY<0||newY>=rows||(i==0&&j==0))
                    continue;
                if(states[newX][newY])
                    amount++;
            }
        }
        return amount;
    }

    public void play(){
        if(init){
            if(paused){
                gameRunner.resume();
                paused=false;
            }else{
                gameRunner.pause();
                paused=true;
            }
        }else{
            init=true;
            new Thread(gameRunner).start();
        }

    }

    public void setDiff(String diff) throws Exception{
        switch (diff){
            case "Easy":
                this.diff = 0.1;
                break;
            case "Medium":
                this.diff = 0.2;
                break;
            case "Hard":
                this.diff = 0.3;
                break;
            default:
                throw new Exception("Unknown Difficulty");
        }
    }

    public void update() {

        boolean[][] tempStates = deepClone(states);
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                int aliveNeighbours = getAliveNeighbours(i,j);
                if(states[i][j]){
                    if(aliveNeighbours<2||aliveNeighbours>3){
                        tempStates[i][j]=false;
                    }
                }else{
                     if(aliveNeighbours==3){
                        tempStates[i][j]=true;
                    }
                }
            }
        }
        states=tempStates;
        applyStates();
    }

    public static boolean[][] deepClone(boolean[][] src) {
        assert (src != null);
        boolean[][] dest = new boolean[src.length][];
        for (int i = 0; i < src.length; i++) {
            dest[i] = src[i].clone();
        }
        return dest;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}
