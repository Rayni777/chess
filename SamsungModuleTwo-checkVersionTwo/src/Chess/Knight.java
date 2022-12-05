package Chess;

public class Knight extends ChessFigure {

    public Knight(int x, int y, boolean colorIsWhite) {
        super(x, y, colorIsWhite ? '\u2658' : '\u265e', colorIsWhite);

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
        if ((toX == this.getX() + 1 || toX == this.getX() - 1)
                && (toY == this.getY() + 2 || toY == this.getY() - 2)) return true;
        else if ((toX == this.getX() + 2 || toX == this.getX() - 2)
                && (toY == this.getY() + 1 || toY == this.getY() - 1)) return true;
        return false;
    }
}
