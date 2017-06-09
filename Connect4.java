import sac.game.AlphaBetaPruning;
import sac.game.GameSearchAlgorithm;
import sac.game.GameState;
import sac.game.GameStateImpl;
import sac.graph.GraphState;

import java.io.Console;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

public class Connect4 extends GameStateImpl {

    byte O = 2;
    byte X = 1;
    byte E = 0;
    public static int wiersze;
    public static int kolumny;
    public byte[][] board = null;

    static {
        setHFunction(new HFunctionKwiatek());
    }

    Connect4(int wiersze, int kolumny) {
        this.board = new byte[wiersze][kolumny];
        this.wiersze = wiersze;
        this.kolumny = kolumny;
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                board[i][j] = E;
            }

        }
    }

    // KONSTRUKTOR KOPIUJÄ„CY

    public Connect4(Connect4 Parent) {


        board = new byte[wiersze][kolumny];
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                board[i][j] = Parent.board[i][j];
            }
        }
        setMaximizingTurnNow(Parent.isMaximizingTurnNow());
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        StringBuilder hr = new StringBuilder();

        for (int i = 0; i < kolumny * 4 + 1; i++) {
            hr.append("-");
        }

        s.append(hr + "\n");
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                s.append("| " + board[i][j] + " "); // moze byc tutaj blad
            }
            s.append("|\n");
            s.append(hr + "\n");
        }

        for (int i = 0; i < kolumny; i++) {
            s.append("  " + i + " ");
        }

        return s.toString();
    }

    public static void main(String[] argv) {

        Scanner input = new Scanner( System.in );
        byte X = 1;
        byte Y = 2;
        int kolumna;


        //Console cnsl = null;

        Connect4 Con = new Connect4(7, 7);
        GameSearchAlgorithm algorithm = new AlphaBetaPruning(Con);
        System.out.println(Con);

        boolean whoStart = true;
        int makeMoveValue = 0;
        Con.setMaximizingTurnNow(whoStart);

        while(Con.IsOver() == 0) {
            algorithm.execute();

            System.out.println(algorithm.getMovesScores());
            if (Con.isMaximizingTurnNow()) {
                System.out.println("Podaj nr kolumny: ");
                makeMoveValue = input.nextInt();
                Con.Move(makeMoveValue, Con.isMaximizingTurnNow());

            } else {
                makeMoveValue = Integer.parseInt(algorithm.getFirstBestMove());
                Con.Move(makeMoveValue, Con.isMaximizingTurnNow());
            }
            Con.refresh();
            Con.Display();

            Con.setMaximizingTurnNow(!Con.isMaximizingTurnNow());
        }

        System.out.println("Wygral gracz " + Con.IsOver());
    }

    public byte ThisIsTheEnd()
    {
        Console cnsl = null;
        int kolumna;

        while(true) {
            System.out.print("PODAJ KOLUMNE KOLEGO");
            kolumna = Integer.parseInt(cnsl.readLine());
            if (board[0][kolumna] != 0) {
                System.out.print("RUCH NIEDOZWOLONY");

            }


        }
    }


    public boolean Move(int kolumna, boolean bgracz) {
        byte gracz;
        if (bgracz == true)
            gracz = 1;
        else
            gracz = 2;

        int i;

        for (i=0 ; i < board.length; i++) {
            if(board[i][kolumna] == 1 || board[i][kolumna] == 2) {
                if (i == 0)
                    return false;
                board[i - 1][kolumna] = gracz;
                return true;
            }
        }
        board[i-1][kolumna] = gracz;
        return true;
    }

    // METODA DO WYĹšWIETLANIA

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
    public int IsOver()
    {
        for (int i = 0; i < board.length; i++) {        //wiersze
            int player1 = 0;
            int player2 = 0;
            for (int j = 0; j < board[0].length; j++) {     //kolumny w wierszu
                if (board[i][j] == 0)
                {
                    player1 = 0;
                    player2 = 0;
                }
                if (board[i][j] == 1)
                {
                    player1++;
                    player2 = 0;
                }
                if (board[i][j] == 2)
                {
                    player2++;
                    player1 = 0;
                }
                if (player1 >= 4)
                    return 1;
                if (player2 >= 4)
                    return 2;
            }
        }

        for (int j = 0; j < board[0].length; j++) {     //kolumny
            int player1 = 0;
            int player2 = 0;
            for (int i = 0; i < board.length; i++) {        //wiersze w kolumnie
                if (board[i][j] == 0)
                {
                    player1 = 0;
                    player2 = 0;
                }
                if (board[i][j] == 1)
                {
                    player1++;
                    player2 = 0;
                }
                if (board[i][j] == 2)
                {
                    player2++;
                    player1 = 0;
                }
                if (player1 >= 4)
                    return 1;
                if (player2 >= 4)
                    return 2;
            }
        }

        //dodać skosy
        int slants = board.length + board[0].length -1;     //linie skosne gora
        for (int i=0; i<slants;i++){
            int x = i;
            int y = 0;
            int player1 = 0;
            int player2 = 0;
            while(x >= 0){
                if ( x > board.length-1 || y > board[0].length-1){
                    x--;
                    y++;
                    continue;

                }
                if (board[x][y] == 0)
                {
                    player1 = 0;
                    player2 = 0;
                }
                if (board[x][y] == 1)
                {
                    player1++;
                    player2 = 0;
                }
                if (board[x][y] == 2)
                {
                    player2++;
                    player1 = 0;
                }
                if (player1 >= 4)
                    return 1;
                if (player2 >= 4)
                    return 2;
                //
                //
                x--;
                y++;
            }
            //for(int j=0; j<)
        }

        for (int i=0; i<slants;i++){        //linie skosne dol
            int x = board.length-1-i;
            int y = 0;
            int player1 = 0;
            int player2 = 0;
            while(x < board.length){
                if ( x < 0 || y > board[0].length-1){
                    x++;
                    y++;
                    continue;

                }
                if (board[x][y] == 0)
                {
                    player1 = 0;
                    player2 = 0;
                }
                if (board[x][y] == 1)
                {
                    player1++;
                    player2 = 0;
                }
                if (board[x][y] == 2)
                {
                    player2++;
                    player1 = 0;
                }
                if (player1 >= 4)
                    return 1;
                if (player2 >= 4)
                    return 2;
                //
                //
                x++;
                y++;
            }
            //for(int j=0; j<)
        }
        return 0;
    }

 /*   @Override
    public List<GameState> generateChildren() {

        List<GameState> children = new LinkedList<GameState>();

        for (int i = 0; i < kolumny; i++) {
            Connect4 child = new Connect4(this);
                if(board[0][i] == 0)
            child.Move(i,O);
            child.setMaximizingTurnNow(!isMaximizingTurnNow());
            child.setMoveName(Integer.toString(i));
            children.add(child);
        }
        return children;
    }*/

    @Override
    public List<GameState> generateChildren() {
        List<GameState> children = new LinkedList<GameState>();
        for (int i = 0; i < kolumny; i++) {
            Connect4 child = new Connect4(this);
            if (child.Move(i, isMaximizingTurnNow()));
            {
                //child.Display();
                child.setMaximizingTurnNow(!isMaximizingTurnNow());
                child.setMoveName(Integer.toString(i));
                children.add(child);
            }
        }
        return children;
    }

    @Override
    public int hashCode() {

        int k = 0;
        byte[] linear = new byte[wiersze*kolumny];
        for(int i =0; i<wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                linear[k++] = board[i][j];
            }
        }
        return Arrays.hashCode(linear);
    }

}
