import java.util.Scanner;

public class BoardThreeD implements BoardIO{

    static Scanner scanner = new Scanner(System.in);

    int turnCount = 0;

    final int BOARDWIDTH;
    final int SETBOARDWIDTH;

    Status[][][] board;

    public BoardThreeD(int boardwidth) {
        BOARDWIDTH = boardwidth;
        SETBOARDWIDTH = BOARDWIDTH - 1;
        board = new Status[BOARDWIDTH][BOARDWIDTH][BOARDWIDTH];
        System.out.print("Alright then, let's play Tic Tac Toe!\n");
        for (int column = 0; column < BOARDWIDTH; column++) {
            for (int row = 0; row < BOARDWIDTH; row++) {
                for (int depth = 0; depth < BOARDWIDTH; depth++) {
                    board[column][row][depth] = Status.NONE;
                }
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

    public void place(int targColumn, int targRow, int targDepth, Status piece) {
        board[targColumn][targRow][targDepth] = piece;
    }

    public void controlledPlace(int targColumn, int targRow, int targDepth, Status piece) {
        if (board[targColumn][targRow][targDepth] == Status.NONE) {
            place(targColumn, targRow, targDepth, piece);
        }
        else {
            System.out.println("Sorry, that spot is taken.");
            turnModule(piece);
        }
    }

    public void printBoard() {
        String fullRow = "  ";
        for (int boardnum = 1; boardnum < BOARDWIDTH + 1; boardnum++) {
            fullRow += Integer.toString(boardnum) + " ".repeat(BOARDWIDTH * 2 + 1);
        }
        System.out.println(fullRow);

        for (int row = 0; row < BOARDWIDTH; row++) {
            fullRow = Integer.toString(BOARDWIDTH - row) + " ";
            for (int dep = 0; dep < BOARDWIDTH; dep++) {
                for (int col = 0; col < BOARDWIDTH; col++) {
                    fullRow += statusToString(board[col][row][dep]) + " ";
                }
                fullRow += "  ";
            }
            System.out.println(fullRow);
        }
        fullRow = "  ";
        for (int layer = 0; layer < BOARDWIDTH; layer++) {
            for (int col = 0; col < BOARDWIDTH; col++) {
                fullRow += Integer.toString(col + 1) + " ";
            }
            fullRow += "  ";
        }
        System.out.println(fullRow);
    }

    public boolean checkVerticals() {
        boolean win = false;
        for (int dep = 0; dep < BOARDWIDTH; dep++) {
            for (int col = 0; col < BOARDWIDTH; col++) {
                boolean set = board[col][0][dep] != Status.NONE;
                for (int g = 0; g < BOARDWIDTH; g++) {
                    set = set && board[col][0][dep] == board[col][g][dep];
                }
                win =  win || set;
            }
        }
        return win;
    }

    public boolean checkHorizontals() {
        boolean win = false;
        for (int dep = 0; dep < BOARDWIDTH; dep++) {
            for (int ro = 0; ro < BOARDWIDTH; ro++) {
                boolean set = board[0][ro][dep] != Status.NONE;
                for (int g = 0; g < BOARDWIDTH; g++) {
                    set = set && board[0][ro][dep] == board[g][ro][dep];
                }  
                win =  win || set;
            }
    }   
        return win;
    }

    public boolean checkDepthals() {
        boolean win = false;
        for (int ro = 0; ro < BOARDWIDTH; ro++) {
            for (int col = 0; col < BOARDWIDTH; col++) {
                boolean set = board[col][ro][0] != Status.NONE;
                for (int g = 1; g < BOARDWIDTH; g++) {
                    set = set && board[col][ro][0] == board[col][ro][g];
                }
                win =  win || set;
            }
        }
        return win;
    }

    public boolean checkDiagonals() {
        //negative slope
        boolean win = false;

        for (int layer = 0; layer < BOARDWIDTH; layer++) {
            boolean negSet = board[0][0][layer] != Status.NONE;
            for (int dia = 1; dia < BOARDWIDTH; dia++) {
                negSet = negSet && board[dia][dia][layer] == board[0][0][layer];
            }
            win = win || negSet;
        }
        
        // positive slope
        for (int layer = 0; layer < BOARDWIDTH; layer++) {
            boolean posSet = board[SETBOARDWIDTH][0][layer] != Status.NONE;
            for (int dia = 1; dia < BOARDWIDTH; dia++) {
                posSet = posSet && board[SETBOARDWIDTH - dia][dia][layer] == board[SETBOARDWIDTH][0][layer];
            }
            win = win || posSet;
        }
        
        //third dimension
        for (int layer = 0; layer < BOARDWIDTH; layer++) {
            boolean setOne = board[layer][0][0] != Status.NONE;
            for (int dia = 1; dia < BOARDWIDTH; dia++) {
                setOne = setOne && board[layer][dia][dia] == board[layer][0][0];
            }
            win = win || setOne;
        }
        
        for (int layer = 0; layer < BOARDWIDTH; layer++) {
            boolean setTwo = board[layer][SETBOARDWIDTH][0] != Status.NONE;
            for (int dia = 1; dia < BOARDWIDTH; dia++) {
                setTwo = setTwo && board[layer][SETBOARDWIDTH - dia][dia] == board[layer][SETBOARDWIDTH][0];
            }
            win = win || setTwo;
        }

        
        for (int layer = 0; layer < BOARDWIDTH; layer++) {
            boolean setThree = board[0][layer][0] != Status.NONE;
            for (int dia = 1; dia < BOARDWIDTH; dia++) {
                setThree = setThree && board[dia][layer][dia] == board[0][layer][0];
            }
            win = win || setThree;
        }

        
        for (int layer = 0; layer < BOARDWIDTH; layer++) {
            boolean setFour = board[0][layer][SETBOARDWIDTH] != Status.NONE;
            for (int dia = 1; dia < BOARDWIDTH; dia++) {
                setFour = setFour && board[SETBOARDWIDTH - dia][layer][dia] == board[SETBOARDWIDTH][layer][0];
            }
            win = win || setFour;
        }

        return win;
    }

    public boolean checkCrosses() {
        boolean setOne = board[0][0][0] != Status.NONE;
        for (int dia = 1; dia < BOARDWIDTH; dia++) {
            setOne = setOne && board[dia][dia][dia] == board[0][0][0];
        }

        boolean setTwo = board[0][SETBOARDWIDTH][0] != Status.NONE;
        for (int dia = 1; dia < BOARDWIDTH; dia++) {
            setTwo = setTwo && board[dia][SETBOARDWIDTH - dia][dia] == board[0][SETBOARDWIDTH][0];
        }

        boolean setThree = board[SETBOARDWIDTH][0][0] != Status.NONE;
        for (int dia = 1; dia < BOARDWIDTH; dia++) {
            setThree = setThree && board[SETBOARDWIDTH - dia][dia][dia] == board[SETBOARDWIDTH][0][0];
        }

        boolean setFour = board[SETBOARDWIDTH][SETBOARDWIDTH][0] != Status.NONE;
        for (int dia = 1; dia < BOARDWIDTH; dia++) {
            setFour = setFour && board[SETBOARDWIDTH - dia][SETBOARDWIDTH - dia][dia] == board[SETBOARDWIDTH][SETBOARDWIDTH][0];
        }
        return setOne || setTwo || setThree || setFour;
    }

    public boolean checkDraw() {
        boolean noDraw = false;
        for (int depth = 0; depth < BOARDWIDTH; depth++) {
            for (int column = 0; column < BOARDWIDTH; column++) {
                for (int row = 0; row < BOARDWIDTH; row++) {
                    noDraw = noDraw || board[column][row][depth] == Status.NONE;
                }
            }
        }
        return !(noDraw);
    }

    public boolean checkWin() {
        return checkVerticals() || checkHorizontals() || checkDiagonals() || checkCrosses() || checkDepthals();
        
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
            System.out.println("In which layer would you like to place the " + statusToString(player) + "? \n");
            int placerDepth = accurateIntScan();
            printBoard();
            System.out.println("In which column would you like to place the " + statusToString(player) + "? \n");
            int placerColumn = accurateIntScan();
            printBoard();
            System.out.println("In which row would you like to place the " + statusToString(player) + "? \n");
            int placerRow = accurateIntScan();
            System.out.println("\n\n");
            controlledPlace(placerColumn - 1, BOARDWIDTH - placerRow, placerDepth - 1, player);
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