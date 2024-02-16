

public class Test {

    public static void assertB(boolean condition) {
        if (!condition) {
            throw new RuntimeException();
        }
    }
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        assertB(!false);

        BoardTwoD testBoardOne = new BoardTwoD(3);
        testBoardOne.controlledPlace(1, 0, BoardIO.Status.X, false);
        //testBoardOne.controlledPlace(1, 1, BoardIO.Status.X);
        testBoardOne.controlledPlace(1, 2, BoardIO.Status.X, false);
        assertB(testBoardOne.aiOneTurnWin(BoardIO.Status.X, true));
    }
}
