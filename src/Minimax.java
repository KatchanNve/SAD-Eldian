public class Minimax implements Algorithm{

    String player;
    int deepness;
    int count;

    public Minimax(String player, int deepness){
        this.deepness = deepness;
        this.player = player;
        this.count = 0;
    }



    @Override
    public Move getAlgoMove(State state, int deepness, float alpha, float beta) {
        return getBestMove(state,deepness);
    }

    public Move getBestMove(State state, int deepness){
        Move bestMove = null;
        float bestValue = Integer.MIN_VALUE;
        for(Move move : state.getMove(state.getCurrentPlayer())){
            State nextstate = state.play(move);
            float value = minimax(nextstate,deepness);
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
            return state.getScore(this.player);
        }
        else{
            if(state.getCurrentPlayer().equals(this.player)){
                float b = Integer.MIN_VALUE;
                for(Move move : state.getMove(state.getCurrentPlayer())){
                    this.count += 1;
                    State nextstate = state.play(move);
                    float m = minimax(nextstate,deepness - 1);
                    if(b < m){
                        b = m;
                    }
                }
                return b;
            }
            else{
                float b = Integer.MAX_VALUE;
                for(Move move : state.getMove(state.getCurrentPlayer())){
                    this.count += 1;
                    State nextstate = state.play(move);
                    float m = minimax(nextstate,deepness - 1);
                    if(b > m){
                        b = m;
                    }
                }
                return b;
            }
        }
    }

    @Override
    public float getAlpha() {
        return 0;
    }

    @Override
    public float getBeta() {
        return 0;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getDeepness() {
        return deepness;
    }
}


