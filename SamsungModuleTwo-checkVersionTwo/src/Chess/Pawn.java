package Chess;


public class Pawn extends ChessFigure {

    private boolean isFirstStep;
    protected boolean isChange = false;

    public Pawn(int x, int y, boolean colorIsWhite) {

        super(x, y, colorIsWhite ? '\u2659' : '\u265f', colorIsWhite);
        this.isFirstStep = true;
    }

    @Override
    public boolean canMove(int toX, int toY) {

        if (this.isFirstStep) {//if it is first step Pawn can do two steps forward
            if (toX == this.getX() && Math.abs(toY - this.getY()) <= 2 && Board.board[toY][toX] == null) {
                if (this.colorIsWhite && toY == 0) return false;
                else if (!this.colorIsWhite && toY == 7) return false;
                this.isFirstStep = false;
                return true;
            } else {
                if (canMoveAndKill(toX, toY)) {
                    this.isFirstStep = false;
                    return true;
                }
            }
        } else {
            if (this.colorIsWhite) {//figures can go only forward
                if (toY==7) isChange = true;
                if (toX == this.getX() && toY == this.getY() + 1 && Board.board[toY][toX] == null)
                    //if it is forward_step &&  it is one_cell step && cell_to_go is free
                    return true;//step forward
                else return canMoveAndKill(toX, toY);//step to kill
            } else {
                if (toY==0) isChange = true;
                if (toX == this.getX() && toY == this.getY() - 1 && Board.board[toY][toX] == null)
                    //if it is forward_step &&  it is one_cell step && cell_to_go is free
                    return true;//step forward
                else return canMoveAndKill(toX, toY);//step to kill
            }
        }
        return false;//theoretically it is impossible to go to this step
    }

    protected boolean canMoveAndKill(int toX, int toY) {
        if (this.colorIsWhite) {
            return (Math.abs(toX - this.getX()) == 1 && toY - this.getY() == 1
                    && Board.board[toY][toX] != null && Board.board[toY][toX].colorIsWhite != this.colorIsWhite);//white figure's step to kill
        } else return (Math.abs(toX - this.getX()) == 1 && toY - this.getY() == -1
                && Board.board[toY][toX] != null && Board.board[toY][toX].colorIsWhite != this.colorIsWhite);//black figure's step to kill

    }

    @Override
    protected boolean canStep(int toX, int toY) {
        if (this.colorIsWhite) {
            return (Math.abs(toX - this.getX()) == 1 && toY - this.getY() == 1);//white figure's step to kill
        } else return (Math.abs(toX - this.getX()) == 1 && toY - this.getY() == -1);//black figure's step to kill
    }
}
