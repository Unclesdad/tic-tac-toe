public class Board {
    public static enum Status {X, O, NONE};
    public static enum Column {left, middle, right};
    public static enum Row {top, middle, bottom};

    public Status[][] board = new Status[3][3];

    public Board() {
        System.out.print("Let's play Tic Tac Toe!");
        for (int column = 0; column < 3; column++) {
            for (int row = 0; row < 3; row++) {
                board[column][row] = Status.NONE;
            }
        }
        printBoard();
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
    
    public void place(Column targColumn, Row targRow, Status piece) {
        board[enumToInt(targColumn)][enumToInt(targRow)] = piece;
    }
    
    public Status getStatus(Column targColumn, Row targRow) {
        return board[enumToInt(targColumn)][enumToInt(targRow)];
    }

    public void printBoard() {
        for (int row = 0; row < 3; row++) {
            String fullRow = "";
            for (int col = 0; col < 3; col++) {
                fullRow += statusToString(board[col][row]) + " ";
            }
            System.out.println(fullRow);
        }
    }

    public boolean checkVerticals() {
        boolean win = false;
        for (int col = 0; col < 3; col++) {
            win =  win || (board[col][0] == board[col][1]) && (board[col][0] == board[col][2]);
        }
        return win;
    }

    public boolean checkHorizontals() {
        boolean win = false;
        for (int ro = 0; ro < 3; ro++) {
            win =  win || (board[ro][0] == board[ro][1]) && (board[ro][0] == board[ro][2]);
        }
        return win;
    }

    public boolean checkDiagonals() {
        return (board[1][1] == board[0][0] && board[1][1] == board[2][2]) 
        || (board[1][1] == board[2][0] && board[1][1] == board[0][2]);
    }

    public boolean checkWin() {
        return checkVerticals() || checkHorizontals() || checkDiagonals();
    }

    public void inputThenEdit(String position, Status changer) {
        Column inputColumn;
        Row inputRow;
        switch(position) {
            case "top left":
                inputColumn = Column.left;
                inputRow = Row.top;
            case "top middle":
                inputColumn = Column.middle;
                inputRow = Row.top;
            case "top right":
                inputColumn = Column.right;
                inputRow = Row.top;
            case "middle left":
                inputColumn = Column.left;
                inputRow = Row.middle;
            case "middle":
                inputColumn = Column.middle;
                inputRow = Row.middle;
            case "middle right":
                inputColumn = Column.right;
                inputRow = Row.middle;
            case "bottom left":
                inputColumn = Column.left;
                inputRow = Row.bottom;
            case "bottom middle":
                inputColumn = Column.middle;
                inputRow = Row.bottom;
            case "bottom right":
                inputColumn = Column.right;
                inputRow = Row.bottom;
            default:
            inputColumn = Column.left;
            inputRow = Row.bottom;
        }
        board[enumToInt(inputColumn)][enumToInt(inputRow)] = changer;
    }

    public void play() {
        printBoard();
        System.out.println("Where would you like to place the X?");
        String placex = System.console().readLine();
        inputThenEdit(placex, Status.X);
        if (checkWin()) {
            System.out.println("Player X won!");
        }

        printBoard();
        System.out.println("Where would you like to place the O?");
        String placeo = System.console().readLine();
        inputThenEdit(placeo, Status.O);
        if (checkWin()) {
            System.out.println("Player O won!");
        }

    }
}