public class Main {
    
    public static void main(String[] args) {
        System.out.println("Please enter board width:");

        Board playBoard = new Board();

        Board.Status winner = playBoard.play();

        if (winner == Board.Status.X || winner == Board.Status.O) {
            System.out.println("Player " + Board.statusToString(winner) + " won! Congratulations!");
        }
        else {
            System.out.println("It's a draw!");
        }
        playBoard.printBoard();
    }
    
    
}
