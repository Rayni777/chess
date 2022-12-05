package Chess;

public class Rook extends ChessFigure {

    public Rook(int x, int y, boolean colorIsWhite) {
        super(x, y, colorIsWhite ? '\u2656' : '\u265c', colorIsWhite);

    }

    @Override
    public boolean canMove(int toX, int toY) {
        if (checkCellToGo(toX, toY)) {
            return canStep(toX, toY);
        }
        return false;//if it is non-valid move
    }

    @Override
    protected boolean canStep(int toX, int toY) {
        if (isToEqualsThis(toX, toY)) return false;

        int x = this.getX(), y = this.getY();//save changes in case not to call ger every time

        if ((x == toX || y == toY)) {//Rook can go only straight x or y

            if (x == toX) {//go straight x, i.e. y is changing
                if (y > toY) {//go down
                    for (int i = y - 1; i > toY; i--)
                        if (Board.board[i][x] != null) return false;
                } else if (y < toY) {//go up
                    for (int i = y + 1; i < toY; i++)
                        if (Board.board[i][x] != null) return false;
                }
            } else if (y == toY) {//go straight y, i.e. x is changing
                if (x > toX) {//go left
                    for (int i = x - 1; i > toX; i--)
                        if (Board.board[y][i] != null) return false;
                } else if (x < toX) {//go right
                    for (int i = x + 1; i < toX; i++)
                        if (Board.board[y][i] != null) return false;
                }
            }
            return true;//if it is valid move
        }
        return false;//if it is non-valid move
    }
}
