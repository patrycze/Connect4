import sac.State;
import sac.StateFunction;
import static jdk.nashorn.internal.runtime.JSType.toInteger;

public class HFunctionManhattan extends StateFunction {

    @Override
    public double calculate(State state){
        Puzzle P1 = (Puzzle) state;
        double h = 0.0;
        for (int i = 0; i < P1.n; i++) {
            for (int j = 0; j < P1.n; j++) {
                byte w = P1.board[i][j];
                if(w == 0)
                    continue;
                int x = w / P1.n;
                int y = w % P1.n;
                h += Math.abs(x-i) + Math.abs(y-j);
            }
        }
        //System.out.println(h);
        return h;
    }
}
