package Chess;

public abstract class ChessFigure {

    private int x, y;
    private final char symbol; //'\u2654'..'\u265f'
    protected boolean colorIsWhite; // true = white, false = black

    public ChessFigure(int x, int y, char symbol, boolean color) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.colorIsWhite = color;
    }

    @Override
    public String toString() {

        if (this.colorIsWhite) return "\u001B[37m" + String.valueOf(symbol) + "\u001B[0m";//white color of text
        else return "\u001B[30m" + String.valueOf(symbol) + "\u001B[0m";//black color of text

//        return String.valueOf(symbol);
    }

    /**
     * method moves to figure to exact place if it can
     * ability to move is
     * <p>
     * this.y/x -1 in board means that we decrease by one as we count from 0
     *
     * @param toX x coordinate
     * @param toY y coordinate
     */
    public void move(int toX, int toY) {
        if (Board.board[toY][toX] != null)
            kill(toX, toY);
        Board.board[this.y][this.x] = null;
        this.x = toX;
        this.y = toY;
        Board.board[toY][toX] = ChessFigure.this;
    }

    private void kill(int toX, int toY) {
        if (Board.board[toY][toX].colorIsWhite) {
            Board.getWhiteFigures().remove(Board.board[toY][toX]);
        } else {
            Board.getBlackFigures().remove(Board.board[toY][toX]);
        }
    }


    /**
     * return (Board.board[toY][toX] == null || Board.board[toY][toX].colorIsWhite != this.colorIsWhite)
     * if cell_to_go is free or there is enemy figure
     *
     * @param toX x coordinate
     * @param toY y coordinate
     * @return boolean
     */
    protected boolean checkCellToGo(int toX, int toY) {
        return ((Board.board[toY][toX] == null || Board.board[toY][toX].colorIsWhite != this.colorIsWhite)
                && !isToEqualsThis(toX, toY));
    }

    protected boolean isToEqualsThis(int toX, int toY) {
        return (this.x == toX && this.y == toY);
    }

    public abstract boolean canMove(int toX, int toY);

    /**
     * if figure can move to this cell, doesn't matter it is free on there is figure, it can kill
     *
     * @param toX
     * @param toY
     * @return
     */
    protected abstract boolean canStep(int toX, int toY);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
