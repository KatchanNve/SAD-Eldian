public class AlphaBeta implements Algorithm{

    String player;
    int deepness;
    float alpha;
    float beta;
    int count;


    public AlphaBeta(String player, int deepness, float alpha, float beta){
        this.player = player;
        this.deepness = deepness;
        this.alpha = alpha;
        this.beta = beta;
        this.count = 0;
    }

    @Override
    public Move getAlgoMove(State state, int deepness, float alpha, float beta) {
        return getBestMove(state,deepness,alpha,beta);
    }


    public Move getBestMove(State state,int deepness,float alpha, float beta){
        Move bestMove = null;
        float bestValue = Integer.MIN_VALUE;
        for(Move move : state.getMove(state.getCurrentPlayer())){
            State nextstate = state.play(move);
            float value = alphabeta(nextstate,alpha,beta,deepness);
            if(value > bestValue){
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }


    private float alphabeta(State state,float alpha, float beta, int deepness){
        if(deepness == 0 || state.isOver()){
            return state.getScore(this.player);
        }
        else{
            if(state.getCurrentPlayer().equals(this.player)){
                for(Move move : state.getMove(state.getCurrentPlayer())){
                    this.count += 1;
                    State nextstate = state.play(move);
                    alpha = Math.max(alpha,alphabeta(nextstate,alpha,beta,deepness -1));
                    if(alpha >= beta){
                        return alpha;
                    }
                }
                return alpha;
            }
            else{
                for(Move move : state.getMove(state.getCurrentPlayer())){
                    this.count += 1;
                    State nextstate = state.play(move);
                    beta = Math.min(beta,alphabeta(nextstate,alpha,beta,deepness -1));
                    if(alpha >= beta){
                        return beta;
                    }
                }
                return beta;
            }
        }
    }

    @Override
    public float getAlpha() {
        return alpha;
    }

    @Override
    public float getBeta() {
        return beta;
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
