import java.util.ArrayList;
import java.util.Arrays;

public class HighVelocityProblem {
    public static void BackTracking_Problem(ArrayList<Vessel> vessels, ArrayList<Sailor> sailors){
        boolean[] sailor_picked = new boolean[sailors.size()];
        Arrays.fill(sailor_picked, false);

        for (Vessel a:vessels) {
            BackTracking_HighVelocity.Backtracking(sailors,a,vessels,sailor_picked);
            for (int i = 0; i < sailors.size(); i++) {
                if ( BackTracking_HighVelocity.config_win[i] == 1) {
                    sailor_picked[i] = true;
                    System.out.println("Tripulacion: " + sailors.get(i).name);
                }
            }
            System.out.println("--- Vessel name: " + a.name + " : Real Speed: " + BackTracking_HighVelocity.bestConfig + " ---");
            System.out.println();
        }
    }
}
