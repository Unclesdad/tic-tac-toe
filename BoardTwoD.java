
    import java.util.Scanner;

public class BoardTwoD implements BoardIO{

    static Scanner scanner = new Scanner(System.in);

    int turnCount = 0;

    Status[][] board;

    final int BOARDWIDTH;
    final int SETBOARDWIDTH;

    public BoardTwoD() {
        BOARDWIDTH = scanner.nextInt();
        SETBOARDWIDTH = BOARDWIDTH - 1;
        board = new Status[BOARDWIDTH][BOARDWIDTH];
        System.out.print("Alright then, let's play Tic Tac Toe!\n");
        for (int column = 0; column < BOARDWIDTH; column++) {
            for (int row = 0; row < BOARDWIDTH; row++) {
                    board[column][row] = Status.NONE;
            }
        }
    }

    public static String statusToString(Status s) {
        switch(s) {
            case X: return "X";
            case O: return "O";
            case NONE: return "-";
            default: return "ERROR";
        }
    }

    public void place(int targColumn, int targRow, Status piece) {
        board[targColumn][targRow] = piece;
    }

    public void controlledPlace(int targColumn, int targRow, Status piece) {
        if (board[targColumn][targRow] == Status.NONE) {
            place(targColumn, targRow, piece);
        }
        else {
            System.out.println("Sorry, that spot is taken.");
            turnModule(piece);
        }
    }

    public void printBoard() {
        String fullRow = "";

        for (int row = 0; row < BOARDWIDTH; row++) {
            fullRow = Integer.toString(BOARDWIDTH - row) + " ";
                for (int col = 0; col < BOARDWIDTH; col++) {
                    fullRow += statusToString(board[col][row]) + " ";
                }
            System.out.println(fullRow);
        }
        fullRow = "  ";
            for (int col = 0; col < BOARDWIDTH; col++) {
                fullRow += Integer.toString(col + 1) + " ";
            }
        System.out.println(fullRow);
    }

    public boolean checkVerticals() {
        boolean win = false;
            for (int col = 0; col < BOARDWIDTH; col++) {
                boolean set = board[col][0] != Status.NONE;
                for (int g = 0; g < BOARDWIDTH; g++) {
                    set = set && board[col][0] == board[col][g];
                }
                win =  win || set;
            }
        return win;
    }

    public boolean checkHorizontals() {
        boolean win = false;
            for (int ro = 0; ro < BOARDWIDTH; ro++) {
                boolean set = board[0][ro] != Status.NONE;
                for (int g = 0; g < BOARDWIDTH; g++) {
                    set = set && board[0][ro] == board[g][ro];
                }  
                win =  win || set;
            }   
        return win;
    }

    public boolean checkDiagonals() {
        //negative slope
        boolean win = false;
            boolean negSet = board[0][0] != Status.NONE;
            for (int dia = 1; dia < BOARDWIDTH; dia++) {
                negSet = negSet && board[dia][dia] == board[0][0];
            }
            win = win || negSet;
        
        // positive slope
            boolean posSet = board[SETBOARDWIDTH][0] != Status.NONE;
            for (int dia = 1; dia < BOARDWIDTH; dia++) {
                posSet = posSet && board[SETBOARDWIDTH - dia][dia] == board[SETBOARDWIDTH][0];
            }
            win = win || posSet;
        return win;
    }

    public boolean checkDraw() {
        boolean noDraw = false;
            for (int column = 0; column < BOARDWIDTH; column++) {
                for (int row = 0; row < BOARDWIDTH; row++) {
                    noDraw = noDraw || board[column][row] == Status.NONE;
                }
            }
        return !(noDraw);
    }

    public boolean checkWin() {
        return checkVerticals() || checkHorizontals() || checkDiagonals();
    }

// ai !!!!!

    public boolean aiOneTurnWin(Status aiPlayer) {
        
        // verticals!!!
        for(int missingValue = 0; missingValue < BOARDWIDTH; missingValue++) {
            Status baseBoard = missingValue != 0 ? board[0][0] : board[0][1];

            for (int col = 0; col < BOARDWIDTH; col++) {
                boolean set = board[col][missingValue] == Status.NONE;

                for (int ro = 0; ro < BOARDWIDTH; ro++) {
                    if (ro != missingValue) {
                       set = set && board[col][ro] == baseBoard;
                    }
                }
                if (set) {
                    board[col][missingValue] = aiPlayer;
                    return true;
                }

            }
        }

        // horizontals!!!

        return false;
    }







    public int accurateIntScan() {
        int scan = scanner.nextInt();
        if (scan > 0 && scan <= BOARDWIDTH) {
            return scan;
        }
        else {
            System.out.println("That is an invalid value. Please type a valid coordinate.");
            return accurateIntScan();
        }
    }

    public void turnModule(Status player) {
        printBoard();
        System.out.println("In which column would you like to place the " + statusToString(player) + "? \n");
        int placerColumn = accurateIntScan();
        printBoard();
        System.out.println("In which row would you like to place the " + statusToString(player) + "? \n");
        int placerRow = accurateIntScan();
        System.out.println("\n\n");
        controlledPlace(placerColumn - 1, BOARDWIDTH - placerRow, player);
    }

    public Status play() {
        Status winner = Status.NONE;
        while (winner == Status.NONE) {
            turnModule(Status.X);
            if (checkWin()) {
                winner = Status.X;
                return winner;
            } else if (checkDraw()) {
                return winner;
            }

            turnModule(Status.O);
            if (checkWin()) {
                winner = Status.O;
                return winner;
            } else if (checkDraw()) {
                return winner;
            }
        }
        return winner;
    }
}
