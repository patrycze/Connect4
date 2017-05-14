import sac.game.GameState;
import sac.game.GameStateImpl;

import java.util.List;

public class Connect4 extends GameStateImpl {

    byte O = 0;
    byte X = 1;

    public byte[][] board = null;

    Connect4(int wiersze, int kolumny) {
        this.board = new byte[wiersze][kolumny];

        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                //        System.out.print("| X ");
            }
            //    System.out.println("|\n");
        }
    }

    // KONSTRUKTOR KOPIUJĄCY

    Connect4(Connect4 Parent) {
        this.board = new byte[Parent.board.length][Parent.board[0].length];
        for (int i = 0; i < Parent.board.length; i++) {
            for (int j = 0; j < Parent.board[0].length; j++) {
                this.board[i][j] = Parent.board[i][j];
            }
        }
    }


    public static void main(String[] argv) {
        byte X = 1;
        byte Y = 2;
        Connect4 Con = new Connect4(6, 10);
        Con.Move(2, X);
        Con.Move(2, X);
        Con.Move(2, X);
        Con.Move(2, X);
        Con.Move(2, Y);

        Con.Move(2, Y);
        Con.Display();
    }

    public void Move(int kolumna, byte gracz) {

        int i;

        for (i=0 ; i < board.length; i++) {
            if(board[i][kolumna] == 1 || board[i][kolumna] == 2) {
                board[i - 1][kolumna] = gracz;
                return;
            }
        }
        board[i-1][kolumna] = gracz;
    }

    // METODA DO WYŚWIETLANIA

    public void Display() {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] != 0) {
                    if(board[i][j] == 1)
                        System.out.print("| 1 ");
                    else if (board[i][j] == 2)
                        System.out.print("| 2 ");
                } else {
                    System.out.print("| _ ");
                }
            }
            System.out.println("|\n");

        }

    }

    @Override
    public List<GameState> generateChildren() {
        return null;
    }
}