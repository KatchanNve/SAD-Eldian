import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        game(scanner);
    }

    private static void game(Scanner scanner) throws IOException {
        //System.out.println("Quel est le nom du joueur n°1 ?");
        //String joueur1 = scanner.next();
        String joueur1 = "BLUE";
        System.out.println("Quel type d'IA veux tu faire jouer pour \033[0;34mBLUE\033[0m et \033[0;31mRED\033[0m ? (random / minimax / alphabeta)");
        String typeAlgo1 = correctEntry(scanner.next(), scanner);
        Algorithm algorithm1 = inputAlgorithm(typeAlgo1, joueur1, scanner,true,null);
        //System.out.println("Quel est le nom du joueur n°2 ?");
        //String joueur2 = scanner.next();
        String joueur2 = "RED";
        //System.out.println("Quel type d'IA veux tu faire jouer pour \033[0;31m" + joueur2 + "\033[0m ? (random / minimax / alphabeta)");

        //String typeAlgo2 = correctEntry(scanner.next(), scanner);
        //je set typeAlgo1 dans l'input pour ne pas redemander le type d'algo pour le joueur 2 (quand on testait notre programme
        //on pouvait faire affronter deux joueurs avec des algo differents
        Algorithm algorithm2 = inputAlgorithm(typeAlgo1, joueur2, scanner,false,algorithm1);
        State game = new State(joueur1, joueur2);
        game.initGame();
        System.out.println(game.printBoard());
        //BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt")); -> c'etait pour stock les noeuds.

        while (!game.isOver()){
            game.addBoardListElement(game.getBoard());
            //System.out.println("size : " + game.getListBoard().size());
            //System.out.println(game.getPieces() + " "+ game.getPiecesPlayerOne() + " " + game.getPiecesPlayerTwo());
            List<Move> listMove = game.getMove(game.getCurrentPlayer());
            if (listMove.size() == 0) { //ce if est une sécurité au cas où la listmove du joueur est vide.
                game.setCurrentPlayer(game.getOppenent());
                continue;
            }
            if (game.getCurrentPlayer().equals(game.getPlayerOne())) {
                //System.out.println(algorithm1.getDeepness() + " " + algorithm1.getAlpha() + " " + algorithm1.getBeta());
                algorithm1.setCount(0);
                game = game.play(algorithm1.getAlgoMove(game, algorithm1.getDeepness(), algorithm1.getAlpha(), algorithm1.getBeta()));
                System.out.println("Nbr Noeuds " + algorithm1.getCount());
                //bw.write(String.valueOf(algorithm1.getCount()));
                //bw.newLine();
            } else {
                //System.out.println(algorithm2.getDeepness() + " " + algorithm2.getAlpha() + " " + algorithm2.getBeta());
                algorithm2.setCount(0);
                game = game.play(algorithm2.getAlgoMove(game, algorithm2.getDeepness(), algorithm2.getAlpha(), algorithm2.getBeta()));
            }
            System.out.println(game.printBoard());
        }
        //bw.flush();
        //bw.close();
        System.out.println("score player1 :" + game.getScore(game.getPlayerOne()));
        System.out.println("score player2 :" + game.getScore(game.getPlayerTwo()));
        System.out.print("partie terminée : ");
        if (game.getScore(game.getPlayerOne()) > game.getScore(game.getPlayerTwo())) {
            System.out.print("\033[0;34m" + game.getPlayerOne() + "\033[0m gagne la partie");
        } else if (game.getScore(game.getPlayerOne()) < game.getScore(game.getPlayerTwo())) {
            System.out.print("\033[0;31m" + game.getPlayerTwo() + "\033[0m gagne la partie");
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
    public static Algorithm inputAlgorithm(String type, String player, Scanner scanner, Boolean message, Algorithm algorithmOppenent){
        Algorithm algorithm;
        if(type.equals("random")){
            algorithm = new RandomMove(player);
        }
        else{
            int deepness;
            if(message){
                System.out.println("Quelle profondeur ?");
                deepness = testInteger(scanner.next(),scanner);
            }
            else{
                deepness = algorithmOppenent.getDeepness();
            }
            if(type.equals("minimax")){
                algorithm = new Minimax(player, deepness);
            }
            else{
                float alpha;
                float beta;
                if(message){
                    System.out.println("quel alpha ('-' si l'infini) ?");
                    String entry = scanner.next();
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
                }
                else{
                    alpha = algorithmOppenent.getAlpha();
                    beta = algorithmOppenent.getBeta();
                }
                algorithm = new AlphaBeta(player,deepness,alpha, beta);
            }
        }
        return algorithm;
    }
}
