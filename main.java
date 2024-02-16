public class Main {
    
    public static void main(String[] args) {
        System.out.println("Two or three dimensions?");
        int dimensionNumber = BoardThreeD.scanner.nextInt();
        
        while (dimensionNumber != 2 && dimensionNumber != 3) {
            System.out.println("Please type either 2 or 3.");
            dimensionNumber = BoardThreeD.scanner.nextInt();
        }

        System.out.println("Please enter board width:");
        int boardwidth = BoardThreeD.scanner.nextInt();
        while (boardwidth < 0) {
            System.out.println("Please enter a positive number.");
            boardwidth = BoardThreeD.scanner.nextInt();
        }

        BoardIO playBoard = dimensionNumber == 3 ? new BoardThreeD(boardwidth) : new BoardTwoD(boardwidth);

        BoardTwoD.Status winner = playBoard.play();

        if (winner == BoardTwoD.Status.X || winner == BoardTwoD.Status.O) {
            System.out.println("Player " + BoardThreeD.statusToString(winner) + " won! Congratulations!");
        }
        else {
            System.out.println("It's a draw!");
        }
        playBoard.printBoard();
    }
    
    
}
