import java.util.List;
import java.util.Random;

public class RandomMove implements Algorithm{

    String player;


    public RandomMove(String player){
        this.player = player;
    }

    public Move getRandomMove(List<Move> listMove){
        Random rand = new Random();
        return listMove.get(rand.nextInt(listMove.size()));
    }

    @Override
    public Move getAlgoMove(State state, int deepness, float alpha, float beta) {
        return getRandomMove(state.getMove(state.getCurrentPlayer()));
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
    public int getDeepness() {
        return 0;
    }
}
