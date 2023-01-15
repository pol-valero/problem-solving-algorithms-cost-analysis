import java.util.ArrayList;
import java.util.Arrays;

public class BackTracking_EntrieFleet {
    private static String[] all_types = {"Windsurf", "Optimist", "Laser", "Patí Català", "HobieDragoon", "HobieCat"};
    private static int bestConfig;

    public static void Backtracking(ArrayList<Center> centro) {
        int[] config = new int[centro.size()];
        bestConfig = Integer.MAX_VALUE;

        // Just for illustration purposes when debugging step by step
        Arrays.fill(config, -1);

        bruteForce(config, 0, centro);
        if (bestConfig == Integer.MAX_VALUE){
            System.out.println("No hay solucion");
        }
    }

    private static void bruteForce(int[] config, int k, ArrayList<Center> centro) {
        config[k] = 0;

        while (config[k] <= 1) {
            if (k < config.length - 1) {
                // Poda (Backtracking)
                if (checkValid(config, k, centro)) {
                    bruteForce(config, k + 1, centro);
                }
                else {
                    //System.out.println("Podado!");    ---For debbug
                }
            } else {
                // Solution case
                checkSolution(config,centro);
            }

            // Next successor
            config[k]++;
        }
    }

    private static boolean checkValid(int[] config, int k, ArrayList<Center> centro) {
        int total_centers = 0;
        for (int i = 0; i <= k; i++) {
            total_centers += config[i];
        }
        return total_centers <= bestConfig;
    }

    private static void checkSolution(int[] config, ArrayList<Center> centro) {

        boolean[] found = new boolean[6];
        Arrays.fill(found, false);

        for (int i = 0; i < config.length; i++) {
            if (config[i] == 1){
                for (String a : centro.get(i).types){
                    for (int j = 0; j < all_types.length; j++) {
                        if (all_types[j].equals(a)){
                            found[j] = true;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < found.length; i++) {
            if (found[i] == false){
                return;
            }
        }
        int total_centers = 0;
        for (int i = 0; i < config.length; i++) {
            total_centers += config[i];
        }
        if (bestConfig > total_centers) {
            bestConfig = total_centers;

            System.out.println("--- A better configuration was found ---");

            for (int i = 0; i < config.length; i++) {
                if (config[i] == 1) {
                    System.out.println("Center: " + centro.get(i).name + ", Types: " + centro.get(i).types);
                }
            }
            System.out.println("--- Total centers: " + total_centers + " ---");

            System.out.println();
        }
    }
}
