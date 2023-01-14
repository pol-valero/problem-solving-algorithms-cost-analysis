import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class BranchBound_EntireFleet {

    public class Config implements Comparable<Config>{
        // La configuració que guardem en la caixa
        public final int[] config;
        // L'array de marcatge pels tresors emmagatzemat
        private final String[] all_types = {"Windsurf", "Optimist", "Laser", "Patí Català", "HobieDragoon", "HobieCat"};
        private final boolean[] found_types;
        // El marcatge pel total de caixes utilitzades/caixa actual.
        private int num_centers;
        // El nivell en el qual ens trobem de la caixa
        private int level;


        // Constructor per defecte, inicialitzarà la primera configuració
        // Generalment, tot estaria buit (nivell = 0), però el TSP ens permet saltar el primer nivell.
        public Config(ArrayList<Center> centers) {
            // Creem els arrays
            config = new int[centers.size()];
            found_types = new boolean[centers.size()];
            Arrays.fill(found_types, false);

            // No hem acumulat distància encara
            num_centers = Integer.MAX_VALUE;
        }

        // Constructor auxiliar (privat) per "clonar" una configuració, fet servir per generar successors al "expandir"
        // Tinguem present que això té cost linear, pel que esperem podar més que el que afegim fent això
        public Config(Config that) {
            this.config = that.config.clone();
            this.found_types = that.found_types.clone();
            this.level = that.level;
            this.num_centers = that.num_centers;
        }

        // Funció auxiliar per saber si la configuració és plena (per com generem el valor k, en aquest cas comparem amb N i no N-1)
        public boolean isFull() {
            return level == config.length;
        }

        // Funció que "expandeix" la configuració actual, retornant els seus successors com una arrayList
        public List<Config> expand(ArrayList<Center> centers) {
            List<Config> children = new ArrayList<>();

            // Per cada possible combinacio de objectes a guardar com a successor.
            for (int save = 0; save < centers.size(); save++) {
                // Ignorant els objectes que hem guardat
                if (!found_types[save]) {
                    // Clonem la configuració actual amb el constructor vist prèviament
                    Config next = new Config(this);

                    //Sumem el nou objecte a la caixa
                    next.num_centers++;

                    // Actualitzem la configuració + marcatge segons l'objecte escollits
                    if (next.box_volume > Global.volume_Max){
                        next.config[level] = save;
                        next.object[save] = true;
                        //Actualitzem el volum, ja que necessitem una caixa nova.
                        next.num_Box++;
                        next.box_volume = Global.objects_Volume[save];
                    }
                    else{
                        next.config[level] = save;
                        next.object[save] = true;
                    }

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
            return num_Box;
        }

        // Funció auxiliar que estima (aproxima) el cost total d'una configuració parcial
        public float estimate() {
            // Prenem un enfoc optimista, assumint que independentment del camí que escollim ens mourem sempre amb el mínim cost possible,
            // concretament completem el cost actual amb la suma dels costs mínims de les caixes estimades que s'espera que s'utilitza.
            float minRest = 0;

            // Per cada objecte no guardada
            for (int i = 0; i < Global.num_Object; i++) {
                if (!object[i]) {
                    minRest += Global.objects_Volume[i];
                }
            }

            //Suma del cost actual + el cost minim esperat segons aquest combinacio.
            return num_Box + (minRest/Global.volume_Max);
        }
        // Funció compareTo de la interfície Comparable, que la priority queue de Java fa servir internament per ordenar els elements
        @Override
        public int compareTo(Config that) {
            // Comparem costos estimats
            return (int) (this.estimate() - that.estimate());
        }

        //Funció per poder calcular el número d'objectes que estan emmagatzemats en cada caixa
        public int[] group(){
            int[] group = new int[getCost()];
            int j = 0;
            float trash = 0;

            for (int i = 0; i < Global.num_Object; i++){
                trash += Global.objects_Volume[config[i]];
                if (trash <= Global.volume_Max){
                    group[j]++;
                }
                else{
                    j++;
                    trash = Global.objects_Volume[config[i]];;
                    group[j]++;
                }
            }
            return group;
        }

        // Per debugar
        @Override
        public String toString() {
            //return "Config: " + Arrays.toString(config) + " - Level: " + level + " - Cost: " + getCost();     //Debug
            return "Config: " + Arrays.toString(config) + " - Boxes of carrying: " + getCost() + " - Group: " + Arrays.toString(group()) + " - Price: " + getCost() * Global.price_Box;
        }

}
