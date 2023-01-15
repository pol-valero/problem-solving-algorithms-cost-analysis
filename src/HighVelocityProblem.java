import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class HighVelocityProblem {
    public static void BackTracking_Problem(ArrayList<Vessel> vessels, ArrayList<Sailor> sailors){
        boolean[] sailor_picked = new boolean[sailors.size()];
        Arrays.fill(sailor_picked, false);
        float total_speed = 0;

        for (Vessel a:vessels) {
            BackTracking_HighVelocity.Backtracking(sailors,a,vessels,sailor_picked);
            for (int i = 0; i < sailors.size(); i++) {
                if ( BackTracking_HighVelocity.config_win[i] == 1) {
                    sailor_picked[i] = true;
                    System.out.println("Tripulacion: " + sailors.get(i).name);
                }
            }
            System.out.println("--- Vessel name: " + a.name + " : Real Speed: " + BackTracking_HighVelocity.bestConfig +" ---");
            System.out.println();
            total_speed += BackTracking_HighVelocity.bestConfig;
        }
        System.out.println("Total Speed:" + total_speed);
    }
    public static void BranchBound_Problem(ArrayList<Sailor> sailors, ArrayList<Vessel> vessels){
// Iniciaització de l'array de distàncies mínimes
        float best = Integer.MIN_VALUE;

        // Priority Queue (Cua de prioritat) que farem servir per navegar l'espai de solucions de forma "dinàmica"
        // A Java, la PriorityQueue fa servir la interfície Comparable per ordenar els seus elements
        PriorityQueue<BranchBound_HighVelocity> cua = new PriorityQueue<>();
        PriorityQueue<BranchBound_HighVelocity> queue = new PriorityQueue<>();

        // Creem la configuració inicial i l'encuem
        BranchBound_HighVelocity first = new BranchBound_HighVelocity(sailors, vessels.get(0),vessels);
        queue.add(first);

        BranchBound_HighVelocity best_child;
        // Repetim el següent procés fins que no quedin més configuracions a considerar
        while(!queue.isEmpty()) {
            // Agafem la primera configuració de la cua (major prioritat / menor cost estimat)
            BranchBound_HighVelocity config = queue.poll();

            // L'expandim, obtenint els successors
            List<BranchBound_HighVelocity> children = config.expand();

            // Per cadascun dels successors
            for (BranchBound_HighVelocity child : children) {
                // Si és solució (hem pres totes les decisions)
                if (child.isFull()) {

                    // Procés d'optimització
                    if (child.getCost() < best) {
                        best_child = child;
                        best = child.getCost();
                        // Printem per debugar
                        System.out.println(child);
                        System.out.println("Best one!");
                    }
                } else {
                    // Si no és solució (encara queden decisions per prendre)
                    // PBMSC (depenent de l'heurística, podríem fer servir el cost estimat aquí)
                    if (child.getCost() < best) {
                        // Afegim el successor a la cua, que farà servir el seu cost estimat per determinar quan explorar-lo
                        queue.offer(child);
                    } else {
                        // Debugging comentat per evitar gastar més recursos del compte
                        //System.out.println(child);
                        //System.out.println("PBCBS");
                    }
                }
            }
        }
    }
}
