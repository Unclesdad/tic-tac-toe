import java.util.Scanner;

public class Board {
    public static enum Status {X, O, NONE};
    public static enum Column {left, middle, right};
    public static enum Row {top, middle, bottom};

    Scanner scanner = new Scanner(System.in);

    public static final int BOARDWIDTH = 3;
    public static final int SETBOARDWIDTH = BOARDWIDTH - 1;

    int turnCount = 0;

    Status[][] board = new Status[BOARDWIDTH][BOARDWIDTH];

    public Board() {
        System.out.print("Let's play Tic Tac Toe! \n");
        for (int column = 0; column < BOARDWIDTH; column++) {
            for (int row = 0; row < BOARDWIDTH; row++) {
                board[column][row] = Status.NONE;
            }
        }
    }

    public static int enumToInt(Column col) {
        switch(col) {
            case left: return 0;
            case middle: return 1;
            case right: return 2;
            default: return -1;
        }
    }
    public static int enumToInt(Row ro) {
        switch(ro) {
            case top: return 0;
            case middle: return 1;
            case bottom: return 2;
            default: return -1;
        }
    }

    public static String statusToString(Status s) {
        switch(s) {
            case X: return "X";
            case O: return "O";
            case NONE: return "n";
            default: return "ERROR";
        }
    }
    
    /* only in use for three-wide boards
    public void place(Column targColumn, Row targRow, Status piece) {
        board[enumToInt(targColumn)][enumToInt(targRow)] = piece;
    }
    */

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
    /* only in use for three-wide boards
    public Status getStatus(Column targColumn, Row targRow) {
        return board[enumToInt(targColumn)][enumToInt(targRow)];
    } */

    public void printBoard() {
        String fullRow;
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
        boolean negSet = board[0][0] != Status.NONE;
        for (int dia = 1; dia < BOARDWIDTH; dia++) {
            negSet = negSet && board[dia][dia] == board[0][0];
        }
        
        // positive slope

        boolean posSet = board[SETBOARDWIDTH][0] != Status.NONE;
        for (int dia = 0; dia < BOARDWIDTH; dia++) {
            posSet = posSet && board[SETBOARDWIDTH - dia][dia] == board[SETBOARDWIDTH][0];
        }
        return posSet || negSet;
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
        /* testing shit
        if (checkHorizontals()) {
            System.out.println("HORIZONTALS");
        }
        if (checkVerticals()) {
            System.out.println("VERTICALS");
        }
        if (checkDiagonals()) {
            System.out.println("DIAGONALS");
        } */
        return checkVerticals() || checkHorizontals() || checkDiagonals();
        
    }

/* only in use for three-wide boards
    public void inputThenEdit(String position, Status changer) {
        Column inputColumn = Column.middle;
        Row inputRow = Row.middle;
        boolean recursion = false;

        switch(position) {
            case "top left":
                inputColumn = Column.left;
                inputRow = Row.top;
                break;
            case "top":
                inputColumn = Column.middle;
                inputRow = Row.top;
                break;
            case "top right":
                inputColumn = Column.right;
                inputRow = Row.top;
                break;
            case "left":
                inputColumn = Column.left;
                inputRow = Row.middle;
                break;
            case "middle":
                inputColumn = Column.middle;
                inputRow = Row.middle;
                break;
            case "right":
                inputColumn = Column.right;
                inputRow = Row.middle;
                break;
            case "bottom left":
                inputColumn = Column.left;
                inputRow = Row.bottom;
                break;
            case "bottom":
                inputColumn = Column.middle;
                inputRow = Row.bottom;
                break;
            case "bottom right":
                inputColumn = Column.right;
                inputRow = Row.bottom;
                break;
            case "help":
                System.out.println("Command list:\ntop\nleft\nbottom\nright\nmiddle\ntop left\nbottom left\nbottom right\ntop right\npress enter to continue.");
                while (scanner.nextLine() != "") {
                }
                recursion = true;
                turnModule(changer);
                break;

            default:
                System.out.println("Unfortunately, that's not a valid input. for a list of valid inputs, type 'help'");
                turnModule(changer);
                recursion = true;
                break;
        }
        if (getStatus(inputColumn, inputRow) == Status.NONE && recursion == false) {
            place(inputColumn, inputRow, changer);
        }
        else if (recursion == false){
            System.out.println("Sorry, that spot is taken.");
            turnModule(changer);
        }
    }
*/ 

/* only in use for three-wide boards
    public void turnModuleThree(Status player) {
        printBoard();
            System.out.println("Where would you like to place the " + statusToString(player) + "? \n");
            String placer = scanner.nextLine();
            inputThenEdit(placer, player);
    }
*/

    public void turnModule(Status player) {
        printBoard();
            System.out.println("In which column would you like to place the " + statusToString(player) + "? \n");
            int placerColumn = scanner.nextInt();
            printBoard();
            System.out.println("In which row would you like to place the " + statusToString(player) + "? \n");
            int placerRow = scanner.nextInt();
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