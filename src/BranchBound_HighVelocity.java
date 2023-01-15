import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BranchBound_HighVelocity {
/*
    // La configuració (representa l'ordre en que visitem les ciutats)
    private final int[] config;
    // L'array de marcatge per ciutats visitades
    private final boolean[] full;
    // El marcatge per la distància total viatjada
    private float real_velocity;
    // El nivell en el que ens trobem
    private int level;
    private int[] capacity;
    private static float minVesselWeight;


    // Constructor per defecte, inicialitzarà la primera configuració
    // Generalment, tot estaria buit (nivell = 0), però el TSP ens permet saltar el primer nivell
    public BranchBound_HighVelocity(ArrayList<Sailor> sailors, ArrayList<Vessel> vessels) {
        // Creem els arrays
        config = new int[sailors.size()];
        full = new boolean[vessels.size()];
        Arrays.fill(full,false);
        level = 0;
        capacity = new int[vessels.size()];
        Arrays.fill(capacity,0);
        // No hem acumulat distància encara
        real_velocity = 0;
    }

    // Constructor auxiliar (privat) per "clonar" una configuració, fet servir per generar successors al "expandir"
    // Tinguem present que això té cost linear, pel que esperem podar més que el que afegim fent això
    public BranchBound_HighVelocity(BranchBound_HighVelocity that) {
        this.config = that.config.clone();
        this.full = that.full.clone();
        this.level = that.level;
        this.real_velocity = that.real_velocity;
    }

    public static float findMinVesselWeight(ArrayList<Vessel> vessels){
        float minWeight = Float.MAX_VALUE;
        for (Vessel vessel : vessels) {
            if (vessel.weight < minWeight) {
                minWeight = vessel.weight;
            }
        }
        minVesselWeight = minWeight;
        return minVesselWeight;
    }

    private float normalize(float value, float minValue, float maxValue) {
        return 1 - ( (value - maxValue) / (minValue - maxValue) );
    }

    private float abilityImpact (Sailor sailor, Vessel vessel) {

        float abilityImpact;

        float normAbilityWithVessel;
        float normVictoryRatio;

        normAbilityWithVessel = normalize(sailor.getVesselAbility(vessel.type), 0, 10);
        normVictoryRatio = normalize(sailor.WL, 0, 100);

        abilityImpact = (normAbilityWithVessel + normVictoryRatio) / 2;

        return abilityImpact;
    }

    private float weightImpact (Sailor sailor, Vessel vessel) {

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

    private float sailorImpact (Sailor sailor, Vessel vessel) {
        float sailorImpact;

        float abilityImpact;
        float weightImpact;

        weightImpact = weightImpact(sailor, vessel);
        abilityImpact = abilityImpact(sailor, vessel);

        sailorImpact = (weightImpact + abilityImpact) / 2;

        return sailorImpact;
    }

    private double getRealSpeed (Vessel vessel, ArrayList<Sailor> sailors) {

        double sailorsFactor = 1.0f;
        double realSpeed;

        for (Sailor sailor: sailors) {
            sailorsFactor = sailorsFactor * sailorImpact(sailor, vessel);
        }

        realSpeed = vessel.speed * sailorsFactor;

        return realSpeed;
    }

    // Funció auxiliar per saber si la configuració és plena (per com generem el valor k, en aquest cas comparem amb N i no N-1)
    public boolean isFull() {
        return level == config.length;
    }

    // Funció que "expandeix" la configuració actual, retornant els seus successors com una arrayList
    public List<BranchBound_HighVelocity> expand(ArrayList<Sailor> sailors, ArrayList<Vessel> vessels) {
        List<BranchBound_HighVelocity> children = new ArrayList<>();

        // Per cada possible ciutat a visitar com a successor
        for (int city = 0; city < vessels.size(); city++) {
            // Ignorant les ciutats que ja hem visitat
            if (!full[city]) {
                // Clonem la configuració actual amb el constructor vist prèviament
                BranchBound_HighVelocity next = new BranchBound_HighVelocity(this);

                // Actualitzem la configuració + marcatge segons la ciutat escollida
                next.config[level] = city;
                capacity[city]++;
                if (capacity[city] == vessels.get(city).capacity) {
                    next.full[city] = true;
                }
                next.real_velocity = getRealSpeed();

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
        return real_velocity;
    }

    // Funció auxiliar que estima (aproxima) el cost total d'una configuració parcial
    public int estimate() {
        // Prenem un enfoc optimista, assumint que independentment del camí que escollim ens mourem sempre amb el mínim cost possible,
        // concretament completem el cost actual amb la suma dels costs mínims de les ciutats que queden (incloent tornar a l'inici)
        // Hi ha molts enfocs/heurístiques disponibles, cadascun amb pros i contres
        float velocity_min = 0;

        // Per cada ciutat no-visitada (i tornant a l'inici) assumim que el cost serà el mínim
        for (int i = 0; i < ; i++) {
            if (!visited[i]) {
                minRest += Globals.MIN_DIST[i];
            }
        }
        minRest += Globals.MIN_DIST[Globals.START];

        return real_velocity + velocity_min;
    }

    // Funció compareTo de la interfície Comparable, que la priority queue de Java fa servir internament per ordenar els elements
    @Override
    public int compareTo(BranchBound_HighVelocity that) {
        // Comparem costos estimats
        return this.estimate() - that.estimate();
    }

    // Per debugar
    @Override
    public String toString() {
        return "Config: " + Arrays.toString(config) + " - Level: " + level + " - Cost: " + getCost();
    }


 */

}
