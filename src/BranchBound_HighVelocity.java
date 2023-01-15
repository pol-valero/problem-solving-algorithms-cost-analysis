import java.awt.image.DataBufferInt;
import java.util.*;

public class BranchBound_HighVelocity implements Comparable<BranchBound_HighVelocity>{

        // La configuració (representa l'ordre en que visitem les ciutats)
        public final int[] config;
        // L'array de marcatge per ciutats visitades
        public final boolean[] sailor_picked;
        // El marcatge per la distància total viatjada
        public float sailor_impact;
        // El nivell en el que ens trobem
        public int level;
        public ArrayList<Sailor> sailors;
        public Vessel vessel;



        // Constructor per defecte, inicialitzarà la primera configuració
        // Generalment, tot estaria buit (nivell = 0), però el TSP ens permet saltar el primer nivell
        public BranchBound_HighVelocity(ArrayList<Sailor> sailors, Vessel vessel, ArrayList<Vessel> vessels) {
            findMinVesselWeight(vessels);
            // Creem els arrays

            config = new int[vessel.capacity];
            sailor_picked = new boolean[sailors.size()];
            this.sailors = (ArrayList<Sailor>) sailors.clone();
            this.vessel = vessel;

            // No hem acumulat distància encara
            level = 0;
            sailor_impact = 0;
        }

        // Constructor auxiliar (privat) per "clonar" una configuració, fet servir per generar successors al "expandir"
        // Tinguem present que això té cost linear, pel que esperem podar més que el que afegim fent això
        public BranchBound_HighVelocity(BranchBound_HighVelocity that) {
            this.config = that.config.clone();
            this.sailor_picked = that.sailor_picked.clone();
            this.level = that.level;
            this.sailor_impact = that.sailor_impact;
            this.sailors = (ArrayList<Sailor>) that.sailors.clone();
            this.vessel = that.vessel;
        }

        // Funció auxiliar per saber si la configuració és plena (per com generem el valor k, en aquest cas comparem amb N i no N-1)
        public boolean isFull() {
            return level == config.length;
        }

        // Funció que "expandeix" la configuració actual, retornant els seus successors com una arrayList
        public List<BranchBound_HighVelocity> expand() {
            List<BranchBound_HighVelocity> children = new ArrayList<>();

            // Per cada possible ciutat a visitar com a successor
            for (int city = 0; city < sailors.size(); city++) {
                // Ignorant les ciutats que ja hem visitat
                if (!sailor_picked[city]) {
                    // Clonem la configuració actual amb el constructor vist prèviament
                    BranchBound_HighVelocity next = new BranchBound_HighVelocity(this);

                    // Actualitzem la configuració + marcatge segons la ciutat escollida
                    next.config[level] = city;
                    next.sailor_picked[city] = true;
                    next.sailor_impact += sailorImpact(sailors.get(city),vessel);

                    // Incrementem el nivell (ja que és un successor)
                    next.level++;

                    // Afegim el successor a la llista
                    children.add(next);
                }
            }
            return children;
        }

        // Funció auxiliar que retorna el cost per la configuració actual (tenint en compte que un cop escollit l'ordre de les N ciutats cal tornar a l'inici)
        public float getCost() {
            return sailor_impact * vessel.speed;
        }

        // Funció auxiliar que estima (aproxima) el cost total d'una configuració parcial
        public float estimate() {
            ArrayList<Float> temp = new ArrayList<>();
            for (int i = 0; i < sailors.size(); i++) {
                if (!sailor_picked[i]){
                    temp.add(sailorImpact(sailors.get(i),vessel));
                }
            }
            Collections.sort(temp);
            float best_sailor_impact;

            best_sailor_impact = sailor_impact;
            for (int i = 0; i < vessel.capacity - level; i++) {
                best_sailor_impact += temp.get(i);
            }
            float realSpeed = 0;

            realSpeed = vessel.speed * best_sailor_impact;

            return realSpeed;
        }

        // Funció compareTo de la interfície Comparable, que la priority queue de Java fa servir internament per ordenar els elements
        @Override
        public int compareTo(BranchBound_HighVelocity that) {
            float total = this.estimate() - that.estimate();
            if (total > 0){
                return -1;
            }
            if (total < 0){
                return 1;
            }
            return 0;
        }

        // Per debugar
        @Override
        public String toString() {
            return "Config: " + Arrays.toString(config) + " - Level: " + level + " - Cost: " + getCost();
        }

    private static float minVesselWeight;

    public static void findMinVesselWeight(ArrayList<Vessel> vessels){
        float minWeight = Float.MAX_VALUE;
        for (Vessel vessel : vessels) {
            if (vessel.weight < minWeight) {
                minWeight = vessel.weight;
            }
        }
        minVesselWeight = minWeight;
    }


    //Normalizes a variable so that its value is always between 0 and 1.
    private static float normalize(float value, float minValue, float maxValue) {
        return 1 - ( (value - maxValue) / (minValue - maxValue) );
    }

    private static float abilityImpact(Sailor sailor, Vessel vessel) {

        float abilityImpact;

        float normAbilityWithVessel;
        float normVictoryRatio;

        normAbilityWithVessel = normalize(sailor.getVesselAbility(vessel.type), 0, 10);
        normVictoryRatio = normalize(sailor.WL, 0, 100);

        abilityImpact = (normAbilityWithVessel + normVictoryRatio) / 2;

        return abilityImpact;

    }

    private static float weightImpact(Sailor sailor, Vessel vessel) {

        float weightImpact;
        float normWeightImpact;

        float maxWeightImpact;
        float minWeightImpact;

        //If we want to normalize the weightImpact we need to know the maximum weightImpact possible
        //In the best case, the sailor's weight will be 40.00kg (as indicated in the project statement)
        maxWeightImpact = (100 - 40.00f) / minVesselWeight;

        //If we want to normalize the weightImpact we need to know the smallest weightImpact possible
        //In the worst case, the sailor's weight will be 99.99kg and the vessel's weight will be 100.00kg
        //(as the sailor's weight is always lower than the vessel's weight, as indicated in the project statement)
        minWeightImpact = (100 - 99.99f) / 100.00f; //It is basically 0

        weightImpact = (100 - sailor.weight) / vessel.weight;

        normWeightImpact = normalize(weightImpact, minWeightImpact, maxWeightImpact);

        return normWeightImpact;

    }

    private static float sailorImpact(Sailor sailor, Vessel vessel) {

        float sailorImpact;

        float abilityImpact;
        float weightImpact;

        weightImpact = weightImpact(sailor, vessel);
        abilityImpact = abilityImpact(sailor, vessel);

        sailorImpact = (weightImpact + abilityImpact) / 2;

        return sailorImpact;

    }

}
