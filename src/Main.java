import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        State game = new State("Eren", "Mikasa");
        game.initGame();
        System.out.println(game.printBoard());
        do {
            //Random rand = new Random();
            List<Move> listMove = game.getMove(game.getCurrentPlayer());
            if(listMove.size() == 0){
                game.setCurrentPlayer(game.getOppenent());
                continue;
            }
            //Move move = listMove.get(rand.nextInt(listMove.size()));
            State newState = game.play(Algorithm.getBestMove(game,5));
            System.out.println(game.printBoard());
            System.out.println(game.getPieces() + " J1 : " + game.getPiecesPlayerOne() + " J2 : " +game.getPiecesPlayerTwo());
            game.setCurrentPlayer(game.getOppenent());
            game.addBoardListElement(newState.getBoard());
        }
        while (!game.isOver());
        System.out.println(game.getScore(game.getPlayerOne()));
        System.out.println(game.getScore(game.getPlayerTwo()));
        System.out.print("partie terminée : ");
        if(game.getScore(game.getPlayerOne()) > game.getScore(game.getPlayerTwo())){
            System.out.print(game.getPlayerOne() + " gagne la partie");
        }
        else if (game.getScore(game.getPlayerOne()) < game.getScore(game.getPlayerTwo())){
            System.out.print(game.getPlayerTwo() + " gagne la partie");
        }
        else{
            System.out.print("personne ne gagne la partie");
        }
    }
}
