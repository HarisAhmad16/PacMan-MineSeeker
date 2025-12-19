package ca.cmpt276.as3.model;


/**
 * Class that stores the data of the rows, cols, and number of mines
 * Has singleton method to help other classes use the methods
 */
public class GameLogic {

    private static final GameLogic instance = new GameLogic();
    private int row;
    private int col;
    private int numOfMines;

    private GameLogic(){
        // Private to prevent anyone else from instantiating
    }

    public static GameLogic getInstance() { return instance; }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setNumOfMines(int numOfMines) {
        this.numOfMines = numOfMines;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getNumOfMines() {
        return this.numOfMines;
    }

    public void calculateRows(String rows){
        int row = 4;

        switch (rows) {
            case "4 rows x 6 columns":
                row = 4;
                break;
            case "5 rows x 10 columns":
                row = 5;
                break;
            case "6 rows x 15 columns":
                row = 6;
                break;
        }
        setRow(row);
    }

    public void calculateColumns(String cols){
        int col = 0;

        switch (cols) {
            case "4 rows x 6 columns":
                col = 6;
                break;
            case "5 rows x 10 columns":
                col = 10;
                break;
            case "6 rows x 15 columns":
                col = 15;
                break;
        }
        setCol(col);
    }

    public void calculateMines(String mines){
        int numOfMines = 0;

        switch (mines) {
            case "6 mines":
                numOfMines = 6;
                break;
            case "10 mines":
                numOfMines = 10;
                break;
            case "15 mines":
                numOfMines = 15;
                break;
            case "20 mines":
                numOfMines = 20;
                break;
        }

        setNumOfMines(numOfMines);
    }
}
