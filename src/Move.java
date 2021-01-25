public class Move {

    private int name; //0 = clone / 1 = jump
    private int[] actualPosition;
    private int[] futurePosition;

    public Move(int name, int[] actualPosition, int[] futurePosition){
        this.name = name;
        this.actualPosition = actualPosition;
        this.futurePosition = futurePosition;
    }

    public int getName() {
        return name;
    }

    public int[] getActualPosition() {
        return actualPosition;
    }

    public int[] getFuturePosition() {
        return futurePosition;
    }
}
