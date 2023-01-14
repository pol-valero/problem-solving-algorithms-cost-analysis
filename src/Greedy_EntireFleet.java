import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Greedy_EntireFleet implements Comparable<Greedy_EntireFleet>{

        // La configuració que guardem en la caixa
        public final int[] config;
        // L'array de marcatge pels tresors emmagatzemat
        private final String[] all_types = {"Windsurf", "Optimist", "Laser", "Patí Català", "HobieDragoon", "HobieCat"};
        private boolean[] found_types;
        private boolean[] center_picked;
        // El marcatge pel total de caixes utilitzades/caixa actual.
        private int num_centers;
        // El nivell en el qual ens trobem de la caixa
        private int level;
        private int num_found = 0;



        // Constructor per defecte, inicialitzarà la primera configuració
        // Generalment, tot estaria buit (nivell = 0), però el TSP ens permet saltar el primer nivell.
        public Greedy_EntireFleet(ArrayList<Center> centers) {
            // Creem els arrays
            config = new int[centers.size()];
            center_picked = new boolean[centers.size()];
            found_types = new boolean[6];
            Arrays.fill(found_types, false);

            // No hem acumulat distància encara
            num_centers = 0;
        }

        // Constructor auxiliar (privat) per "clonar" una configuració, fet servir per generar successors al "expandir"
        // Tinguem present que això té cost linear, pel que esperem podar més que el que afegim fent això
        public Greedy_EntireFleet(Greedy_EntireFleet that) {
            this.config = that.config.clone();
            this.center_picked = that.center_picked.clone();
            this.found_types = that.found_types.clone();
            this.level = that.level;
            this.num_centers = that.num_centers;
        }

        // Funció auxiliar per saber si la configuració és plena (per com generem el valor k, en aquest cas comparem amb N i no N-1)
        public boolean isFull() {
            return level == config.length || num_found == 6;
        }

        // Funció que "expandeix" la configuració actual, retornant els seus successors com una arrayList
        public List<Greedy_EntireFleet> expand(ArrayList<Center> centers) {
            List<Greedy_EntireFleet> children = new ArrayList<>();

            // Per cada possible ciutat a visitar com a successor
            for (int city = 0; city < centers.size(); city++) {
                // Ignorant les ciutats que ja hem visitat
                if (!center_picked[city]) {
                    Greedy_EntireFleet next = new Greedy_EntireFleet(this);

                    // Actualitzem la configuració + marcatge segons la ciutat escollida
                    next.config[level] = city;

                    for (String a : centers.get(city).types) {
                        for (int j = 0; j < all_types.length; j++) {
                            if (all_types[j].equals(a) && !found_types[j]) {
                                next.found_types[j] = true;
                                next.num_found++;
                            }
                        }
                    }
                    next.center_picked[city] = true;

                    next.num_centers++;


                    // Incrementem el nivell (ja que és un successor)
                    next.level++;

                    // Afegim el successor a la llista
                    children.add(next);
                }
            }
            return children;
        }
        // Funció auxiliar que retorna el cost per la configuració actual
        public int getCost() {
            return num_centers;
        }

        // Funció auxiliar que estima (aproxima) el cost total d'una configuració parcial
        public float estimate() {
            return num_found;
        }
        // Funció compareTo de la interfície Comparable, que la priority queue de Java fa servir internament per ordenar els elements
        @Override
        public int compareTo(Greedy_EntireFleet that) {
            // Comparem costos estimats
            return (int) (this.estimate() - that.estimate());
        }

        // Per debugar
        @Override
        public String toString() {
            //return "Config: " + Arrays.toString(config) + " - Level: " + level + " - Cost: " + getCost();     //Debug
            return "Config: " + Arrays.toString(config) + " - number centers: " + getCost();
        }
}
