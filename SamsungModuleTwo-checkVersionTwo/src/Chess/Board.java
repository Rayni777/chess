/**
 * Java Console Chess
 * Welcome to this java project! It is Java Console Chess. It is main class.
 * To start game session call Board.startGame() in your psvm method.
 * <p>
 * What this game can:
 * Move figures by its rules
 * Check check
 * check-mate
 * pawn became any possible figure
 * <p>
 * To do:
 * en passant
 * castling (move King and Rook)
 * <p>
 * Branch peculiarities:
 * there is canStep method call every time player want to move this figure
 * also there is many step-checks for check-mate
 * <p>
 * last update 07.11.2021
 */

package Chess;

import java.util.ArrayList;

public class Board {


    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private static Reader reader;
    static boolean isWhitePlayerMove;
    private static boolean isGameContinuing;
    protected static boolean castling;

    private static ArrayList<ChessFigure> whiteFigures;
    private static ArrayList<ChessFigure> blackFigures;
    static ChessFigure[][] board;
    private static ChessFigure killedFigure;

    static {
        castling = false;
        killedFigure = null;
        whiteFigures = new ArrayList<>();
        blackFigures = new ArrayList<>();
        board = new ChessFigure[8][8];
    }

    public static void startGame() {
        System.out.println("Команды:");
        System.out.println("h1 h4        -Ход из клетки h1 в клетку h4");
        reader = new Reader();
        isWhitePlayerMove = true;
        isGameContinuing = true;
        putFiguresToBoard();
        printBoard();

        while (isGameContinuing) {
            makeMove();
            printBoard();
        }

        System.out.printf("Игра заканчивается победой %s!!\n", !isWhitePlayerMove ? "БЕЛЫХ" : "ЧЕРНЫХ");

    }

    /*
    81 82 83 84 85 86 87 88  70 71 72 73 74 75 76 77
    71 72 73 74 75 76 77 78  60 61 62 63 64 65 66 67
    61 62 63 64 65 66 67 68
    51 52 53 54 55 56 57 58
    41 42 43 44 45 46 47 48
    31 32 33 34 35 36 37 38
    21 22 23 24 25 26 27 28  10 11 12 13 14 15 16 17
    11 12 13 14 15 16 17 18  00 01 02 03 04 05 06 07
    */

    protected static void putFiguresToBoard() {
        putRooks();
        putKnights();
        putBishops();
        putQueens();
        putKings();
        putPawns();
    }

    private static void putRooks() {
        board[0][0] = new Rook(0, 0, true);//white Rook 1
        board[0][7] = new Rook(7, 0, true);//white Rook 2
        whiteFigures.add(board[0][0]);
        whiteFigures.add(board[0][7]);
        board[7][0] = new Rook(0, 7, false);//black Rook 1
        board[7][7] = new Rook(7, 7, false);//black Rook 2
        blackFigures.add(board[7][0]);
        blackFigures.add(board[7][7]);
    }

    private static void putKnights() {
        board[0][1] = new Knight(1, 0, true);//white Knight 1
        board[0][6] = new Knight(6, 0, true);//white Knight 2
        whiteFigures.add(board[0][1]);
        whiteFigures.add(board[0][6]);
        board[7][1] = new Knight(1, 7, false);//black Knight 1
        board[7][6] = new Knight(6, 7, false);//black Knight 2
        blackFigures.add(board[7][1]);
        blackFigures.add(board[7][6]);
    }

    private static void putBishops() {
        board[0][2] = new Bishop(2, 0, true);//white Bishop 1
        board[0][5] = new Bishop(5, 0, true);//white Bishop 2
        whiteFigures.add(board[0][2]);
        whiteFigures.add(board[0][5]);
        board[7][2] = new Bishop(2, 7, false);//black Bishop 1
        board[7][5] = new Bishop(5, 7, false);//black Bishop 2
        blackFigures.add(board[7][2]);
        blackFigures.add(board[7][5]);
    }

    private static void putQueens() {
        board[0][3] = new Queen(3, 0, true);//white Queen
        whiteFigures.add(board[0][3]);
        board[7][3] = new Queen(3, 7, false);//black Queen
        blackFigures.add(board[7][3]);
    }

    private static void putKings() {
        board[0][4] = new King(4, 0, true);//white King
        whiteFigures.add(board[0][4]);
        board[7][4] = new King(4, 7, false);//black King
        blackFigures.add(board[7][4]);
    }

    private static void putPawns() {
        //white pawns
//        for (int i = 0; i < 8; i++) {
//            board[1][i] = new Pawn(i, 1, true);
//            whiteFigures.add(board[1][i]);
//        }

        //black pawns
        for (int i = 0; i < 8; i++) {
            if(i==4) continue;
            board[6][i] = new Pawn(i, 6, false);
            blackFigures.add(board[6][i]);
        }
    }

    protected static void printBoard() {

        System.out.println("   a  b  c d  e f g  h");
        System.out.println("  +-------------+");
        for (int i = 7; i >= 0; i--) {
            System.out.print((i + 1) + " |");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    if (i % 2 == 1) {
                        if (j % 2 == 0) System.out.print(ANSI_WHITE_BACKGROUND + ANSI_WHITE + '\u2659' + ANSI_RESET);
                        else System.out.print(ANSI_BLACK_BACKGROUND + ANSI_BLACK + '\u2659' + ANSI_RESET);
                    } else {
                        if (j % 2 == 1) System.out.print(ANSI_WHITE_BACKGROUND + ANSI_WHITE + '\u2659' + ANSI_RESET);
                        else System.out.print(ANSI_BLACK_BACKGROUND + ANSI_BLACK + '\u2659' + ANSI_RESET);
                    }
                } else {
//                    if (i%2==1){
//                        if (j%2==0) System.out.print(ANSI_WHITE_BACKGROUND+field[i][j].toString()+ANSI_RESET);
//                        else System.out.print(ANSI_BLACK_BACKGROUND+field[i][j].toString()+ANSI_RESET);
//                    }else{
//                        if (j%2==1) System.out.print(ANSI_WHITE_BACKGROUND+field[i][j].toString()+ANSI_RESET);
//                        else System.out.print(ANSI_BLACK_BACKGROUND+field[i][j].toString()+ANSI_RESET);
//                    }
                    System.out.print(board[i][j].toString());
                }
            }

            System.out.println("| " + (i + 1));
        }
        System.out.println("  +-------------+");
        System.out.println("   a  b  c d  e f g  h");

    }

    private static void makeMove() {
        System.out.printf("Ход %s\n", isWhitePlayerMove ? "белых" : "черных");

        while (true) {
            String move = reader.readLine();
            int fromX = reader.getFromX(move) - 1,
                    fromY = reader.getFromY(move) - 1,
                    toX = reader.getToX(move) - 1,
                    toY = reader.getToY(move) - 1;

            if (board[fromY][fromX] == null || board[fromY][fromX].colorIsWhite != isWhitePlayerMove) {
                //player can't move opponents Figures, if colorIsWhite=true and isWhitePlayerMove=true or visa-versa he can do move
                System.out.printf("Ход %s не может быть выполнен! Попытайтесь снова.\n", move);
                continue;
            }

            boolean b = board[fromY][fromX].canMove(toX, toY);

            if (b) {//if it is rule move
                killedFigure = board[toY][toX];
                board[fromY][fromX].move(toX, toY);//make test move

                if (isCheckForKing(getKingByItsColor(isWhitePlayerMove))) {//if there was check for current player's king and there is still check it is invalid move
                    board[toY][toX].move(fromX, fromY);//test move was invalid

                    if (killedFigure != null) {//in case test move killed figure
                        if (killedFigure.colorIsWhite) whiteFigures.add(killedFigure);
                        else blackFigures.add(killedFigure);
                        killedFigure = null;//refresh the store
                    }

                    System.out.printf("Ход %s не может быть выполнен! Попытайтесь снова.\n", move);
                    continue;//next iteration of move
                }

                changePawn(toX, toY);

                //if it is new check or there is check after another check
                if (isCheckForKing(getKingByItsColor(!isWhitePlayerMove))) {
                    //check if there is check for opponent's king
                    //!isWhitePlayerMove means that player who took move could make check, we check opponent's king
                    //if isWhitePlayerMove=true -> check BLACK king in case he is under attack
                    //if isWhitePlayerMove=false -> check WHITE king in case he is under attack

                    //if there is check we check fo mate (there is no mate without check, it calls check-make)
                    if (isMate(getKingByItsColor(!isWhitePlayerMove))) {//check if we made mat to enemy

                        System.out.printf("%s СТАВЯТ ШАХ И МАТ ЧЕРНОМУ КОРОЛЮ\n", isWhitePlayerMove ? "БЕЛЫЕ" : "ЧЕРНЫЕ");

                        isGameContinuing = false;//stop the game loop
                    } else {
                        System.out.printf("%s СТАВЯТ ШАХ ЧЕРНОМУ КОРОЛЮ\n", isWhitePlayerMove ? "БЕЛЫЕ" : "ЧЕРНЫЕ");
                    }

                }


                isWhitePlayerMove = !isWhitePlayerMove;
                break;
            } else {
                System.out.printf("Ход %s не может быть выполнен! Попытайтесь снова.\n", move);
            }
        }
    }


    private static void changePawn(int toX, int toY) {
        if (board[toY][toX] instanceof Pawn && ((Pawn) board[toY][toX]).isChange) {
            String name = reader.changePawn();
            if (board[toY][toX].colorIsWhite) {
                whiteFigures.remove(Board.board[toY][toX]);
            } else {
                blackFigures.remove(Board.board[toY][toX]);
            }
            if (name.equals("ЛАДЬЯ")) board[toY][toX] = new Rook(toX, toY, isWhitePlayerMove);
            if (name.equals("СЛОН")) board[toY][toX] = new Bishop(toX, toY, isWhitePlayerMove);
            if (name.equals("КОНЬ")) board[toY][toX] = new Knight(toX, toY, isWhitePlayerMove);
            if (name.equals("КОРОЛЕВА")) board[toY][toX] = new Queen(toX, toY, isWhitePlayerMove);
            if (board[toY][toX].colorIsWhite) {
                whiteFigures.add(Board.board[toY][toX]);
            } else {
                blackFigures.add(Board.board[toY][toX]);
            }


        }
    }

    /**
     * finds king of one side, depends on argument isWhiteKing
     *
     * @param isWhiteKing true=whiteKing, false=blackKing
     * @return king as chess figure
     */
    private static King getKingByItsColor(boolean isWhiteKing) {
        if (isWhiteKing) {//search white king in whiteFigures
            for (ChessFigure e : whiteFigures) {
                if (e instanceof King)
                    return (King) e;
            }
        } else {//search black king in blackFigures
            for (ChessFigure e : blackFigures) {
                if (e instanceof King)
                    return (King) e;
            }
        }
        return null;
    }

    /**
     * method check if any of figures can move to this coordinate
     * for check:
     * if king.colorIsWhite=true it check if there is no check for white king from black army
     * if king.colorIsWhite=false it check if there is no check for black king from white army
     *
     * @param x                checked coordinate
     * @param y                checked coordinate
     * @param groupColor true=white, false=black
     * @return
     */
    protected static boolean isCellUnderAttack(int x, int y, boolean groupColor) {

        if (groupColor) {//check if there is check for white king from black army
            for (ChessFigure e : whiteFigures) {
                if (e.canStep(x, y)) return true;
            }
        } else {//check if there is check for black king from white army
            for (ChessFigure e : blackFigures) {
                if (e.canStep(x, y)) return true;
            }
        }

        return false;//if there is no check
    }

    private static boolean isCheckForKing(King king) {
        return isCellUnderAttack(king.getX(), king.getY(), !king.colorIsWhite);
    }

    private static ChessFigure getFigureAttackingCell(int x, int y, boolean colorOfFigure) {

        if (colorOfFigure) {//check if there is check for white king from black army
            for (ChessFigure e : whiteFigures) {
                if (e.canStep(x, y)) return e;
            }
        } else {//check if there is check for black king from white army
            for (ChessFigure e : blackFigures) {
                if (e.canStep(x, y)) return e;
            }
        }

        return null;//if there is no check
    }

    /**
     * check mat if there is check
     * check all cells near the king in case they are under attack
     * if all 9 cells are under attack, it is mate
     *Можно предложить очевидный линейный алгоритм.
     * 1. Атаковано ли поле с королём? Если нет, то не мат.
     * 2. Атакованы ли соседние с королём и свободные от его фигур поля? Если нет, то не мат.
     * 3. Сколько фигур атакуют короля? Если две, то мат.
     * 4. Можно ли съесть атакующую фигуру? Если да, то не мат.
     * 5. Атакует конь? Если да, то мат.
     * 6. Атакующая фигура на соседнем поле? Если да, то мат.
     * 7. Можно ли перекрыть линию атаки? Если да, то не мат. Иначе - мат.
     * @param king
     * @return
     */
    private static boolean isMate(King king) {
        int x = king.getX(), y = king.getY();

        //chek if there is free and save fields near the king
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == x && j == y) continue;
                if (isCorrectParams(x - i, y - j) && board[y - j][x - i] == null && !isCellUnderAttack(x - i, y - j, !king.colorIsWhite)) {
                    king.move(x - i, y - j);
                    if (isCheckForKing(king)) {
                        king.move(x, y);
                        continue;
                    }
                    king.move(x, y);
                    return false;
                }

            }
        }
        //if all near cells are under attack, check third algorithm step
        //check how many enemies can attack the king
        int numOfEnemiesReadyToKillKing = 0;
        if (king.colorIsWhite) {//check if there is check for white king from black army
            for (ChessFigure e : blackFigures) {
                if (e.canMove(x, y)) numOfEnemiesReadyToKillKing++;
            }
        } else {//check if there is check for black king from white army
            for (ChessFigure e : whiteFigures) {
                if (e.canMove(x, y)) numOfEnemiesReadyToKillKing++;
            }
        }
        //if there is more than two enemies it is mate
        if (numOfEnemiesReadyToKillKing >= 2) return true;
        //check fourth algorithm step
        //is it possible to eat figure attacking the king
        ChessFigure attackFigure = getFigureAttackingCell(x, y, !king.colorIsWhite);
        if (attackFigure != null && isCellUnderAttack(attackFigure.getX(), attackFigure.getY(), king.colorIsWhite)) {
            if (getFigureAttackingCell(attackFigure.getX(), attackFigure.getY(), king.colorIsWhite) instanceof King) {
                if (isCellUnderAttack(attackFigure.getX(), attackFigure.getY(), attackFigure.colorIsWhite)){//if someone covers attacking figure
                    return true;
                }

            } else return false;
        }
        //if it is Knight it is mate
        if (attackFigure instanceof Knight) return true;

        //is it possible to cover the king
        if (x ==attackFigure.getX() || y == attackFigure.getY()) {//if it is Rook attack

            if (x == attackFigure.getX()) {//go straight x, i.e. y is changing
                if (y > attackFigure.getY()) {//go down
                    for (int i = y - 1; i > attackFigure.getY(); i--)
                        if (isCellUnderAttack(x, i, king.colorIsWhite)) return false;
                } else if (y < attackFigure.getY()) {//go up
                    for (int i = y + 1; i < attackFigure.getY(); i++)
                        if (isCellUnderAttack(x, i, king.colorIsWhite)) return false;
                }
            } else if (y == attackFigure.getY()) {//go straight y, i.e. x is changing
                if (x > attackFigure.getX()) {//go left
                    for (int i = x - 1; i > attackFigure.getX(); i--)
                        if (isCellUnderAttack(i, y, king.colorIsWhite)) return false;
                } else if (x < attackFigure.getX()) {//go right
                    for (int i = x + 1; i < attackFigure.getX(); i++)
                        if (isCellUnderAttack(i, y, king.colorIsWhite)) return false;
                }
            }
        }else if (Math.abs(attackFigure.getX() - x) == Math.abs(attackFigure.getY() - y)) {//diagonal move

            if (attackFigure.getX() < x) {//go left from figure
                if (attackFigure.getY() > y) {//go left and up from figure
                    for (int i = x - 1; i > attackFigure.getX(); i--)
                        if (isCellUnderAttack(i, y - i + x, king.colorIsWhite)) return false;
                } else if (attackFigure.getY() < y)//go left and down from figure
                    for (int i = x - 1; i > attackFigure.getX(); i--)
                        if (isCellUnderAttack(i, y - x + i, king.colorIsWhite)) return false;
            } else if (attackFigure.getX() > x) {//fo right from figure
                if (attackFigure.getY() > y) {//for right and up from figure
                    for (int i = x + 1; i < attackFigure.getX(); i++)
                        if (isCellUnderAttack(i, y - x + i, king.colorIsWhite)) return false;
                } else if (attackFigure.getY() < y) {//go right and down from figure
                    for (int i = x + 1; i < attackFigure.getX(); i++)
                        if (isCellUnderAttack(i, y - i + x, king.colorIsWhite)) return false;
                }
            }
        }

        return true;
    }

    private static boolean isCorrectParams(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    public static ArrayList<ChessFigure> getWhiteFigures() {
        return whiteFigures;
    }

    public static ArrayList<ChessFigure> getBlackFigures() {
        return blackFigures;
    }

}
