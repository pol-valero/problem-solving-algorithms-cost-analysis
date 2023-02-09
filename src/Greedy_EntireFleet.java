import java.util.*;

public class Greedy_EntireFleet{
        public static void greedy (ArrayList<Center> centros){
            ArrayList<Center> temp = (ArrayList<Center>) centros.clone();
            ArrayList<Center> Solution = new ArrayList<>();
            int found = 0;

            for (int i = centros.size(); i > 0; i--) {
                Collections.sort(temp);
                Solution.add(temp.get(0));
                if (temp.size() != 0) {
                    found += temp.get(0).setFound_types();
                }
                temp.remove(0);
                if (found == 6){
                    Menu.finished();
                    System.out.println("\n--- Best Configuration ---");
                    for (Center a: Solution) {
                        System.out.println("Center: " + a.name +", Types: " + a.types);
                    }
                    System.out.println("--- Total centers: " + Solution.size() + " ---");
                    return;
                }
            }
        }
}
