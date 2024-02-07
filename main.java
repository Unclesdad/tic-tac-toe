public class Main {
    
    public static void main(String[] args) {
        Board playBoard = new Board();

        System.out.println("Player " + Board.statusToString(playBoard.play()) + " won! Congratulations!");
        playBoard.printBoard();
    }
    
    
}
