public class Minimax {

    String player;
    int deepness;

    public Minimax(String player, int deepness){
        this.deepness = deepness;
        this.player = player;
    }


    public Move getBestMove(State state){
        Move bestMove = null;
        float bestValue = Integer.MIN_VALUE;
        for(Move move : state.getMove(state.getCurrentPlayer())){
            System.out.println("state :" + state.getPieces() + " J1 : " + state.getPiecesPlayerOne() + " J2 : " +state.getPiecesPlayerTwo());
            State nextstate = state.play(move);
            System.out.println("newstate :" + state.getPieces() + " J1 : " + state.getPiecesPlayerOne() + " J2 : " +state.getPiecesPlayerTwo());
            float value = minimax(nextstate,deepness);
            System.out.println(value + " " + bestValue);
            if(value > bestValue){
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }



    @SuppressWarnings("all")
    private float minimax(State state, int deepness){
        if(deepness == 0 || state.isOver()){
            System.out.println(state.getScore(this.player));
            return state.getScore(this.player);
        }
        else{
            if(state.getCurrentPlayer().equals(this.player)){
                System.out.println("je rentre dans le if");
                float b = Integer.MIN_VALUE;
                for(Move move : state.getMove(state.getCurrentPlayer())){
                    State nextstate = state.play(move);
                    float m = minimax(nextstate,deepness - 1);
                    if(b < m){
                        b = m;
                    }
                }
                System.out.println(b);
                return b;
            }
            else{
                float b = Integer.MAX_VALUE;
                for(Move move : state.getMove(state.getCurrentPlayer())){
                    State nextstate = state.play(move);
                    float m = minimax(nextstate,deepness - 1);
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


