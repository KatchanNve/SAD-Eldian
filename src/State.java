import java.util.ArrayList;
import java.util.List;

public class State {

    private String playerOne;
    private String playerTwo;
    private String currentPlayer;
    private String[][] board;
    private int piecesPlayerOne;
    private int piecesPlayerTwo;
    private int pieces;
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
        System.out.println(board[0][0]);
        board[6][6] = playerOne;
        board[0][6] = playerTwo;
        System.out.println(board[0][6]);
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

    public float getScore(String player) {
        int piece1 = 0;
        int piece2 = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.println(board[i][j]);
                if(board[i][j] != null){
                    if (board[i][j].equals(player)) {
                        piece1++;
                    }
                    else{
                        piece2++;
                    }
                }
            }
        }
        this.pieces = piece1 + piece2;
        if (player.equals(getPlayerOne())) {
            this.piecesPlayerOne = piece1;
            this.piecesPlayerTwo = piece2;
        } else {
            this.piecesPlayerOne = piece2;
            this.piecesPlayerTwo = piece1;
        }
        float score = 0;
        score = piece1 / ((piece1 + piece2) * 1f);
        System.out.println("score :" + piece1 / ((piece1 + piece2) * 1f));
        System.out.println(piece1 + " " + piece2);
        return score;
    }

    public void setScore() {
        int piece1 = 0;
        int piece2 = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(board[i][j] != null){
                    if (board[i][j].equals(getPlayerOne())) {
                        piece1++;
                    }
                    else{
                        piece2++;
                    }
                }
            }
        }
        this.piecesPlayerOne = piece1;
        this.piecesPlayerTwo = piece2;
        this.pieces = piece1 + piece2;
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
        if(move.getName() == 0){ // 0 = clonage et 1 = saut
            board[move.getActualPosition(0) + move.getDelta(0)][move.getActualPosition(1) + move.getDelta(1)] = currentPlayer;
        }
        else{
            board[move.getActualPosition(0) + move.getDelta(0)][move.getActualPosition(1) + move.getDelta(1)] = currentPlayer;
            board[move.getActualPosition(0)][move.getActualPosition(1)] = null;
        }
        infection(move);
        State newState = null;
        if(currentPlayer.equals(playerOne)){
            newState = new State(playerOne,playerTwo,listBoard,playerTwo);
        }
        else{
            newState = new State(playerOne,playerTwo,listBoard,playerOne);
        }
        newState.setScore();
        return newState;
    }

    @SuppressWarnings("all")
    public void infection(Move move){
        int futurPositionX = move.getActualPosition(0) + move.getDelta(0);
        int futurPositionY = move.getActualPosition(1) + move.getDelta(1);
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if(caseisValid(futurPositionX,futurPositionY,k,l)){
                    if(board[futurPositionX + k][futurPositionY + l] == getOppenent()){
                        board[futurPositionX + k][futurPositionY + l] = getCurrentPlayer();
                    }
                }
            }
        }
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
                    boardStr += "\033[0;31m೦\033[0m | ";
                }
                else {
                    boardStr += "\033[0;34m೦\033[0m | ";
                }
            }
        }
        return boardStr;
    }


    public boolean isOver(){
        float sc= getScore(playerOne);
        if((getMove(playerOne).size() == 0 && getMove(playerTwo).size() == 0) || (piecesPlayerOne == 0 || piecesPlayerTwo == 0)){
            return true;
        }
        else return listBoard.contains(board);
    }

    public int getPieces() {
        return pieces;
    }

    public int getPiecesPlayerOne() {
        return piecesPlayerOne;
    }

    public int getPiecesPlayerTwo() {
        return piecesPlayerTwo;
    }

    public ArrayList<String[][]> getListBoard() {
        return listBoard;
    }
}
