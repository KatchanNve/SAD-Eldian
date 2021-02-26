import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //La fonction game permet de choisir le jeu de facon plus intuitif (c'est notre version)
        game(scanner);
        //Ah l'inverse la fonction cmd permet de lancer le programme dans le terminal afin de lancer directement la partie
        //comme le précise l'ennoncé.
    }

    private static void game(Scanner scanner){
        System.out.println("Quel est le nom du joueur n°1 ?");
        String joueur1 = scanner.next();
        System.out.println("Quel type d'IA veux tu faire jouer pour le joueur 1 ? (random / minimax / alphabeta)");
        String typeAlgo1 = correctEntry(scanner.next(), scanner);
        Algorithm algorithm1 = inputAlgorithm(typeAlgo1, joueur1, scanner);
        System.out.println("Quel est le nom du joueur n°2 ?");
        String joueur2 = scanner.next();
        System.out.println("Quel type d'IA veux tu faire jouer pour le joueur 2 ? (random / minimax / alphabeta)");
        String typeAlgo2 = correctEntry(scanner.next(), scanner);
        Algorithm algorithm2 = inputAlgorithm(typeAlgo2, joueur2, scanner);
        State game = new State(joueur1, joueur2);
        game.initGame();
        System.out.println(game.printBoard());
        do {
            game.addBoardListElement(game.getBoard());
            List<Move> listMove = game.getMove(game.getCurrentPlayer());
            if (listMove.size() == 0) {
                game.setCurrentPlayer(game.getOppenent());
                continue;
            }
            if (game.getCurrentPlayer().equals(game.getPlayerOne())) {
                //System.out.println(algorithm1.getDeepness() + " " + algorithm1.getAlpha() + " " + algorithm1.getBeta());
                game = game.play(algorithm1.getAlgoMove(game, algorithm1.getDeepness(), algorithm1.getAlpha(), algorithm1.getBeta()));
            } else {
                //System.out.println(algorithm2.getDeepness() + " " + algorithm2.getAlpha() + " " + algorithm2.getBeta());
                game = game.play(algorithm2.getAlgoMove(game, algorithm2.getDeepness(), algorithm2.getAlpha(), algorithm2.getBeta()));
            }
            System.out.println(game.printBoard());
        }
        while (!game.isOver());
        //System.out.println(game.printBoard());
        System.out.println("score player1 :" + game.getScore(game.getPlayerOne()));
        System.out.println("score player2 :" + game.getScore(game.getPlayerTwo()));
        System.out.print("partie terminée : ");
        if (game.getScore(game.getPlayerOne()) > game.getScore(game.getPlayerTwo())) {
            System.out.print(game.getPlayerOne() + " gagne la partie");
        } else if (game.getScore(game.getPlayerOne()) < game.getScore(game.getPlayerTwo())) {
            System.out.print(game.getPlayerTwo() + " gagne la partie");
        } else {
            System.out.print("personne ne gagne la partie");
        }
    }

    //La methode correctEntry nous permet de verifier que nous recevons bien le bon argument dans le choix de l'algorithme
    public static String correctEntry(String entry, Scanner scanner) {
        while (!entry.equals("random") && !entry.equals("minimax") && !entry.equals("alphabeta")) {
            System.out.println("\033[0;31mErreur de saisie\033[0m : choisis dans la liste suivante (random / minimax / alphabeta)");
            entry = scanner.next();
        }
        return entry;
    }

    //les methodes testInteger et testFloat testent réspectivement la présence dans l'entrée scanner d'un entier ou
    //d'un float afin d'eviter d'eventuelles erreurs de compilation.
    public static int testInteger(String entry, Scanner scanner) {
        while (!isNumeric(entry)) {
            System.out.println("\033[0;31mErreur de saisie\033[0m : Veuillez rentrer une valeur correcte !");
            entry = scanner.next();
        }
        return Integer.parseInt(entry);
    }

    public static float testFloat(String entry, Scanner scanner) {
        while (!isFloat(entry)) {
            System.out.println("\033[0;31mErreur de saisie\033[0m : Veuillez rentrer une valeur correcte !");
            entry = scanner.next();
        }
        return Integer.parseInt(entry);
    }

    public static boolean isFloat(String strNum) {
        try {
            float d = Float.parseFloat(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String strNum) {
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }


    //Cette méthode permet de créer le bon algorithme en fonction de l'argument d'entrée du scanner.
    //Pour cela nous passons par une Interface Algorithm qui represente un algorithme en général.
    //Chaque algorithmes implemente cette interface qui possede une méthode universelle de getMove.
    public static Algorithm inputAlgorithm(String type, String player, Scanner scanner){
        Algorithm algorithm;
        if(type.equals("random")){
            algorithm = new RandomMove(player);
        }
        else{
            System.out.println("Quelle profondeur ?");
            int deepness = testInteger(scanner.next(),scanner);
            if(type.equals("minimax")){
                algorithm = new Minimax(player, deepness);
            }
            else{
                System.out.println("quel alpha ('-' si l'infini) ?");
                String entry = scanner.next();
                float alpha;
                float beta;
                if(entry.equals("-")){
                    alpha = Float.MIN_VALUE;
                }
                else{
                    alpha = testFloat(entry,scanner);
                }
                System.out.println("quel beta ('+' si l'infini) ?");
                entry = scanner.next();
                if(entry.equals("+")){
                    beta = Float.MAX_VALUE;
                }
                else{
                    beta = testFloat(entry,scanner);
                }
                algorithm = new AlphaBeta(player,deepness,alpha, beta);
            }
        }
        return algorithm;
    }
}
