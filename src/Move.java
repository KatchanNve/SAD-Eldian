public class Move {

    private int name; //0 = clone / 1 = jump / 2 = pass
    private int[] actualPosition;
    private int[] delta;

    public Move(int name, int[] actualPosition, int[] delta){
        this.name = name;
        this.actualPosition = actualPosition;
        this.delta = delta;
    }


    public int getName() {
        return name;
    }

    public int getActualPosition(int i) {
        return actualPosition[i];
    }

    public int getDelta(int i) {
        return delta[i];

    }
}
