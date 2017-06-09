
import sac.State;
import sac.StateFunction;

public class HFunctionMisplacedTiles extends StateFunction {

    @Override
    public double calculate(State state)
    {
        Puzzle puz = (Puzzle) state;
        double h = 0.0;

        int k = 0;
        for (int i= 0; i < puz.n; i++) {
            for (int j = 0; j < puz.n; j++) {
                if (puz.board[i][j] != k && puz.board[i][j] != 0)
                    h += 1.0;
                k++;
            }
        }

      //  System.out.println(h);
      //  System.out.println(puz.toString());
        return h;
    }
}
