public interface Algorithm {

    abstract public Move getAlgoMove(State state, int deepness, float alpha, float beta);
    //la methode getAlgoMove permet une universalité des getBestMove pour tous les algorithmes.
    //les algorithmes n'ayants pas besoin d'alpha / beta ou de la profondeur vont juste set 0 à cette argument.
    abstract public int getDeepness();
    abstract public float getAlpha();
    abstract public float getBeta();
    abstract public int getCount();
    abstract public void setCount(int count);
    //le getCount et le SetCount permet de compter le nombre de neoeuds pour un joueur dans un tours.
    //par defaut à chaque tours on le set à 0 dans le main.
}
