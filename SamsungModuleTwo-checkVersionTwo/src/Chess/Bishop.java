package Chess;

public class Bishop extends ChessFigure {

    public Bishop(int x, int y, boolean colorIsWhite) {
        super(x, y, colorIsWhite ? '\u2657' : '\u265d', colorIsWhite);

    }

    @Override
    public boolean canMove(int toX, int toY) {
        if (checkCellToGo(toX, toY)) {
            return canStep(toX, toY);

        }
        return false;

    }

    @Override
    protected boolean canStep(int toX, int toY) {
        if (isToEqualsThis(toX, toY)) return false;

        int x = this.getX(), y = this.getY();

        if (Math.abs(toX - x) == Math.abs(toY - y)) {//diagonal move

            if (Math.abs(x - toX) == 1) return true;//if it is one_step move

            else if (toX < x) {//go left from figure
                if (toY > y) {//go left and up from figure
                    for (int i = x - 1; i > toX; i--)
                        if (Board.board[y - i + x][i] != null) return false;
                } else if (toY < y)//go left and down from figure
                    for (int i = x - 1; i > toX; i--)
                        if (Board.board[y - x + i][i] != null) return false;
            } else if (toX > x) {//fo right from figure
                if (toY > y) {//for right and up from figure
                    for (int i = x + 1; i < toX; i++)
                        if (Board.board[y - x + i][i] != null) return false;
                } else if (toY < y) {//go right and down from figure
                    for (int i = x + 1; i < toX; i++)
                        if (Board.board[y - i + x][i] != null) return false;
                }
            }

            return true;
        }

        return false;
    }
}