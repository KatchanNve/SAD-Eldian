import java.util.ArrayList;
import java.util.List;

public class State {

    private String playerOne;
    private String playerTwo;
    private String currentPlayer;
    private int pieces;
    private int piecesPlayerOne;
    private int piecesPlayerTwo;
    private String[][] board;
    private ArrayList<String[][]>listBoard;

    public State(String playerOne, String playerTwo){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentPlayer = playerOne;
        this.board = new String[7][7];
        this.listBoard = new ArrayList<String[][]>();
    }

    public State(String playerOne, String playerTwo, ArrayList<String[][]>listBoard, String currentPlayer){
        this(playerOne,playerTwo);
        this.listBoard = listBoard;
        this.currentPlayer = currentPlayer;
    }

    public void initGame(){
        board[0][0] = playerOne;
        board[6][6] = playerOne;
        board[0][6] = playerTwo;
        board[6][0] = playerTwo;
        pieces = 4;
        piecesPlayerOne = 2;
        piecesPlayerTwo = 2;
    }

    public List<Move> getMove(String player){
        List<Move> listMove = new ArrayList<Move>();
        for(int i = 0; i <= 6; i++){
            for(int j = 0; j <= 6; j++){
                int[] actualposition = {i,j};
                if(board[i][j] == player){
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            if(caseisValid(i,j,k,l)){
                                if(board[i + k][j + l] == null){
                                    int[] delta = {k,l};
                                    Move newMove = new Move(0,actualposition,delta);
                                    listMove.add(newMove);
                                }
                            }
                        }
                    }
                    for (int m = -2; m <= 2; m+=2) {
                        for (int n = -2; n <= 2; n+=2) {
                            if(caseisValid(i,j,m,n)){
                                if(board[i + m][j + n] == null){
                                    int[] delta = {m,n};
                                    Move newMove = new Move(1,actualposition,delta);
                                    listMove.add(newMove);
                                }
                            }
                        }
                    }
                }
            }
        }
        return listMove;
    }



    private boolean caseisValid(int row, int column, int deltaRow, int deltaColumn){
        return row + deltaRow >= 0 && row + deltaRow <= 6 && column + deltaColumn >= 0 && column + deltaColumn <= 6;
    }

    public int getScore(String player) {
        if(player.equals(playerOne)){
            return piecesPlayerOne / pieces;
        }
        return piecesPlayerTwo / pieces;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getOppenent() {
        if(currentPlayer.equals(playerOne)){
            return playerTwo;
        }
        return playerOne;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public String[][] getBoard() {
        return board;
    }

    public void addBoardListElement(String[][] board){
        listBoard.add(board);
    }



    public State play(Move move) {
        if(move.getName() == 0){
            board[move.getActualPosition(0) + move.getDelta(0)][move.getActualPosition(1) + move.getDelta(1)] = currentPlayer;
            pieces++;
            if(currentPlayer.equals(playerOne)){
                piecesPlayerOne++;
            }
            else{
                piecesPlayerTwo++;
            }
        }
        else{
            board[move.getActualPosition(0) + move.getDelta(0)][move.getActualPosition(1) + move.getDelta(1)] = currentPlayer;
            board[move.getActualPosition(0)][move.getActualPosition(1)] = null;

        }
        State newState = null;
        if(currentPlayer.equals(playerOne)){
            newState = new State(playerOne,playerTwo,listBoard,playerTwo);
        }
        else{
            newState = new State(playerOne,playerTwo,listBoard,playerOne);
        }
        return newState;
    }

    public String printBoard(){
        String boardStr = "";
        for(String[] line : this.board) {
            boardStr += System.lineSeparator();
            boardStr+= "| ";
            for (String player : line) {
                if (player == null) {
                    boardStr += ". | ";
                }
                else if (player == playerOne) {
                    boardStr += "\033[0;33m೦\033[0m | ";
                }
                else {
                    boardStr += "\033[0;36m೦\033[0m | ";
                }
            }
        }
        return boardStr;
    }


    public boolean isOver(){
        if((getMove(playerOne) == null && getMove(playerTwo) == null) || (piecesPlayerOne == 0 || piecesPlayerTwo == 0)){
            return true;
        }
        else return listBoard.contains(board);
    }
}
