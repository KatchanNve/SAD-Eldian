import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        State game = new State("Eren", "Mikasa");
        game.initGame();
        //System.out.println(game.getPieces() + " J1 : " + game.getPiecesPlayerOne() + " J2 : " +game.getPiecesPlayerTwo());
        System.out.println(game.printBoard());
        do {
            //Random rand = new Random();
            game.addBoardListElement(game.getBoard());
            List<Move> listMove = game.getMove(game.getCurrentPlayer());
            if(listMove.size() == 0){
                game.setCurrentPlayer(game.getOppenent());
                continue;
            }
            //Move move = listMove.get(rand.nextInt(listMove.size()));
            //game = game.play(move);
            Minimax minimax = new Minimax(game.getCurrentPlayer(),2);
            game = game.play(minimax.getBestMove(game,2));
            System.out.println(game.printBoard());
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
