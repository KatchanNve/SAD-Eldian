import java.util.ArrayList;
import java.util.List;

public class State {

    private final String player1;
    private final String player2;
    private String currentPlayer;
    private int pieces;
    private String[][] board;

    public State(String player1, String player2){
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.board = new String[7][7];

    }

    public List<Move> getMove(String player){
        List<Move> listMove = new ArrayList<Move>();
        for(int i = 0; i <= 6; i++){
            for(int j = 0; j <= 6; j++){
                if(board[i][j] == player){

                    caseisValid(i,j,-1,-1);
                    caseisValid(i,j,-1,0);
                    caseisValid(i,j,-1,1);
                    caseisValid(i,j,0,-1);
                    caseisValid(i,j,0,1);
                    caseisValid(i,j,1,-1);
                    caseisValid(i,j,1,0);
                    caseisValid(i,j,1,1);
                }
            }
        }
        return listMove;
    }



    private boolean caseisValid(int row, int column, int deltaRow, int deltaColumn){
        return row + deltaRow >= 0 && row + deltaRow <= 6 && column + deltaColumn >= 0 && column + deltaColumn <= 6;
    }

    public int getScore(String player) {

    }

    public void play(Move move) {
    }

    public boolean isOver(){
        return true;
    }

}
