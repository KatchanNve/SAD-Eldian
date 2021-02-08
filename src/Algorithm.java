public class Algorithm {


    public static Move getBestMove(State state, int deepness){
        Move bestMove = null;
        float bestValue = Integer.MIN_VALUE;
        for(Move move : state.getMove(state.getCurrentPlayer())){
            State nextstate = state.play(move);
            float value = minimax(nextstate,deepness, state.getCurrentPlayer());
            System.out.println(value + " " + bestValue);
            if(value > bestValue){
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }


    private static float minimax(State state, int deepness, String currentPlayer){
        if(deepness == 0 || state.isOver()){
            return state.getScore(currentPlayer);
        }
        else{
            if(state.getCurrentPlayer().equals(currentPlayer)){
                System.out.println("je entre dans le if");
                float b = Integer.MIN_VALUE;
                for(Move move : state.getMove(state.getCurrentPlayer())){
                    State nextstate = state.play(move);
                    float m = minimax(nextstate,deepness - 1,state.getOppenent());
                    if(b < m){
                        b = m;
                    }
                }
                System.out.println(b);
                return b;
            }
            else{
                float b = Integer.MAX_VALUE;
                for(Move move : state.getMove(state.getOppenent())){
                    State nextstate = state.play(move);
                    float m = minimax(nextstate,deepness - 1, state.getCurrentPlayer());
                    if(b > m){
                        b = m;
                    }
                }
                System.out.println(b);
                return b;
            }
        }
    }
}


