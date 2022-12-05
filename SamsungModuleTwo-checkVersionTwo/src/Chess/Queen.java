package Chess;

public class Queen extends ChessFigure {


    public Queen(int x, int y, boolean colorIsWhite) {
        super(x, y, colorIsWhite ? '\u2655' : '\u265b', colorIsWhite);

    }

    @Override
    public boolean canMove(int toX, int toY) {
        if (checkCellToGo(toX, toY)){
            return canStep(toX, toY);
        }

        return false;//if it is non-valid move
    }

    @Override
    protected boolean canStep(int toX, int toY) {
        if (isToEqualsThis(toX, toY)) return false;

        int x = this.getX(), y = this.getY();//save changes in case not to call getter every time

        //king move
        if ((Math.abs(toX - this.getX()) <= 1 && Math.abs(toY - this.getY()) <= 1))//one_step move
            return true;

        //rook move
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

        //bishop move
        if (Math.abs(toX - x) == Math.abs(toY - y)) {//diagonal move

            if (toX < x) {//go left from figure
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

            return true;//if it is valid move
        }
        return false;//if it is non-valid move
    }

}
