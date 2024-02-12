public interface BoardIO {

    public static enum Status {X, O, NONE};

    public BoardIO.Status play();

    public void printBoard();
}
