import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class EntireFleetProblem {
        public static void BackTracking_Problem(ArrayList<Center> centro){
            BackTracking_EntireFleet.Backtracking(centro);
        }
        public static void Greedy_Problem(ArrayList<Center> centro){
            Greedy_EntireFleet.greedy(centro);
        }
}

