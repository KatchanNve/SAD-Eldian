import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        State game = new State("Eren", "Mikasa");
        game.initGame();
        System.out.println(game.printBoard());
        do {
            Random rand = new Random();
            List<Move> listMove = game.getMove(game.getCurrentPlayer());
            Move move = listMove.get(rand.nextInt(listMove.size()));
            State newState = game.play(move);
            System.out.println(game.printBoard());
            game.setCurrentPlayer(game.getOppenent());
            game.addBoardListElement(newState.getBoard());
        }
        while (!game.isOver());
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
