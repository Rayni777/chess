package Chess;

public class King extends ChessFigure {

    public King(int x, int y, boolean colorIsWhite) {
        super(x, y, colorIsWhite ? '\u2654' : '\u265a', colorIsWhite);

    }

    @Override
    public boolean canMove(int toX, int toY) {
        return canStep(toX, toY);
    }

    @Override
    protected boolean canStep(int toX, int toY) {
        if (isToEqualsThis(toX, toY)) return false;

        return ((Math.abs(toX - this.getX()) <= 1 && Math.abs(toY - this.getY()) <= 1)//one_step move
                && checkCellToGo(toX, toY) && !Board.isCellUnderAttack(toX, toY, !this.colorIsWhite));

    }
}
