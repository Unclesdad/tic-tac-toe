
import java.util.Random;
import java.util.Scanner;

public class BoardTwoD implements BoardIO{

    Random rand = new Random();

    static Scanner scanner = new Scanner(System.in);

    int turnCount = 0;

    Status[][] board;

    final int BOARDWIDTH;
    final int SETBOARDWIDTH;

    public BoardTwoD(int boardwidth) {
        BOARDWIDTH = boardwidth > 0 ? boardwidth : 3;
        SETBOARDWIDTH = BOARDWIDTH - 1;
        board = new Status[BOARDWIDTH][BOARDWIDTH];
        System.out.print("Alright then, let's play Tic Tac Toe!\n");
        for (int column = 0; column < BOARDWIDTH; column++) {
            for (int row = 0; row < BOARDWIDTH; row++) {
                    board[column][row] = Status.NONE;
            }
        }
    }

    public static Status oppositeStatus(Status s) {
        switch(s) {
            case X: return Status.O;
            case O: return Status.X;
            case NONE: 
                throw new RuntimeException("Status.NONE was passed through oppositeStatus(). that's not supposed to happen.");
            default: throw new RuntimeException();

        }
    }

    public static String statusToString(Status s) {
        switch(s) {
            case X: return "X";
            case O: return "O";
            case NONE: return "-";
            default: throw new RuntimeException();
        }
    }

    public void place(int targColumn, int targRow, Status piece) {
        board[targColumn][targRow] = piece;
    }

    public boolean controlledPlace(int targColumn, int targRow, Status piece, boolean retryIfTaken) {
        if (board[targColumn][targRow] == Status.NONE) {
            place(targColumn, targRow, piece);
            return true;
        }
        else if (retryIfTaken) {
            System.out.println("Sorry, that spot is taken.");
            turnModule(piece);
        }
        return false;
    }
    public boolean controlledPlace(int targColumn, int targRow, Status piece) {
        return controlledPlace(targColumn, targRow, piece, false);
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

    public int[][] possibleWinChecker(Status checker) {
        int count = 0;
        int[][] duos = new int[2][];

        // they call me vertical
        for (int col = 0; col < BOARDWIDTH; col++) {

            for(int missingValue = 0; missingValue < BOARDWIDTH; missingValue++) {
            //Status baseBoard = missingValue != 0 ? board[0][0] : board[0][1];  used previously. might use again

                boolean set = board[col][missingValue] == Status.NONE;

                for (int ro = 0; ro < BOARDWIDTH; ro++) {
                    if (ro != missingValue) {
                       set = set && board[col][ro] == checker;
                    }
                }
                if (set) {
                    duos[0][count] = col;
                    duos[1][count] = missingValue;
                    count++;
                }
            }
        }

        // they call me horizontal
        for (int ro = 0; ro < BOARDWIDTH; ro++) {

            for(int missingValue = 0; missingValue < BOARDWIDTH; missingValue++) {
            
                boolean set = board[missingValue][ro] == Status.NONE;

                for (int col = 0; col < BOARDWIDTH; col++) {
                    if (col != missingValue) {
                       set = set && board[col][ro] == checker;
                    }
                }
                if (set) {
                    duos[0][count] = missingValue;
                    duos[1][count] = ro;
                    count++;
                }
            }
        }

        // they call me negative slope diagonal
        for (int missingValue = 0; missingValue < BOARDWIDTH; missingValue++) {
            boolean set = board[missingValue][missingValue] == Status.NONE;
            for (int dia = 0; dia < BOARDWIDTH; dia++) {
                if (dia != missingValue) {
                    set = set && board[dia][dia] == checker;
                }
            }
            if (set) {
                duos[0][count] = missingValue;
                duos[1][count] = missingValue;
                count++;
            }
        }

        // they call me positive slope diagonal
        for (int missingValue = 0; missingValue < BOARDWIDTH; missingValue++) {
            boolean set = board[SETBOARDWIDTH - missingValue][missingValue] == Status.NONE;
            for (int dia = 0; dia < BOARDWIDTH; dia++) {
                if (dia != missingValue) {
                    set = set && board[SETBOARDWIDTH - dia][dia] == checker;
                }
            }
            if (set) {
                duos[0][count] = SETBOARDWIDTH - missingValue;
                duos[1][count] = missingValue;
            }
        }

        return duos;
    }

    public boolean aiOneTurnWin(Status aiPlayer, boolean self) {
        int[][] duos = self ? possibleWinChecker(aiPlayer) : possibleWinChecker(oppositeStatus(aiPlayer));
        if (duos[0].length != 0) {
            controlledPlace(duos[0][0], duos[1][0], aiPlayer);
            return true;
        }
        return false;
    }

    public void aiTurn(Status player) {
        if (aiOneTurnWin(player, true)) {
        }
        else if (aiOneTurnWin(player, false)) {
        }
        else if (controlledPlace(SETBOARDWIDTH / 2, SETBOARDWIDTH / 2, player)) {
        }
        else if (controlledPlace(0, 0, player)) {
        }
        else if (controlledPlace(0, SETBOARDWIDTH, player)) {
        }
        else if (controlledPlace(SETBOARDWIDTH, 0, player)) {
        }
        else if (controlledPlace(SETBOARDWIDTH, SETBOARDWIDTH, player)) {
        }
        else {
            while (!controlledPlace(rand.nextInt(BOARDWIDTH), rand.nextInt(BOARDWIDTH), player)) {}
        }
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
        controlledPlace(placerColumn - 1, BOARDWIDTH - placerRow, player, true);
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

            aiTurn(Status.O);
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
