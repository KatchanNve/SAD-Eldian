public interface Algorithm {

    abstract public Move getAlgoMove(State state, int deepness, float alpha, float beta);
    abstract public int getDeepness();
    abstract public float getAlpha();
    abstract public float getBeta();

    
}
