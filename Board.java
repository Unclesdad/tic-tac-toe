import java.util.Scanner;

public class Board {
    public static enum Status {X, O, NONE};
    public static enum Column {left, middle, right};
    public static enum Row {top, middle, bottom};

    Scanner scanner = new Scanner(System.in);

    int turnCount = 0;

    Status[][] board = new Status[3][3];

    public Board() {
        System.out.print("Let's play Tic Tac Toe! \n");
        for (int column = 0; column < 3; column++) {
            for (int row = 0; row < 3; row++) {
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
            win =  win || ((board[col][0] == board[col][1]) && (board[col][1] == board[col][2]) && (board[col][1] != Status.NONE));
        }
        return win;
    }

    public boolean checkHorizontals() {
        boolean win = false;
        for (int ro = 0; ro < 3; ro++) {
            win =  win || (board[0][ro] == board[1][ro] && (board[1][ro] == board[2][ro]) && (board[2][ro] != Status.NONE));
        }
        return win;
    }

    public boolean checkDiagonals() {
        return (board[1][1] == board[0][0] && board[1][1] == board[2][2] && board[1][1] != Status.NONE) 
        || (board[1][1] == board[2][0] && board[1][1] == board[0][2] && board[1][1] != Status.NONE);
        
    }

    public boolean checkDraw() {
        boolean noDraw = false;
        for (int column = 0; column < 3; column++) {
            for (int row = 0; row < 3; row++) {
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
                System.out.println("top\nleft\nbottom\nright\nmiddle\ntop left\nbottom left\nbottom right\ntop right\npress enter to continue.");
                while (scanner.nextLine() != "") {
                }
                recursion = true;
                turnModule(changer);
                break;

            default:
                System.out.println("Unfortunately, that's not a valid input. for a list of valid inputs, type 'help'");
                turnModule(changer);
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

    public void turnModule(Status player) {
        printBoard();
            System.out.println("Where would you like to place the " + statusToString(player) + "? \n");
            String placer = scanner.nextLine();
            inputThenEdit(placer, player);
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