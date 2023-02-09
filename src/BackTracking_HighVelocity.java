import java.util.ArrayList;
import java.util.Arrays;

public class BackTracking_HighVelocity {
    public static double bestConfig = Integer.MIN_VALUE;
    public static int[] config_win;
    private static boolean[] sailor_picked;


    public static void Backtracking(ArrayList<Sailor> sailors, Vessel vessel, ArrayList<Vessel> vessels, boolean[] picked) {
        int[] config = new int[sailors.size()];
        config_win = new int[sailors.size()];
        sailor_picked = picked.clone();
        // Just for illustration purposes when debugging step by step
        Arrays.fill(config, -1);
        bestConfig = 0;

        bruteForce(config, 0, sailors, vessel, vessels);
    }

    private static void bruteForce(int[] config, int k,ArrayList<Sailor> sailors, Vessel vessel, ArrayList<Vessel> vessels) {
        config[k] = 0;

        while (config[k] <= 1) {
            if (k < config.length - 1) {
                // Poda (Backtracking)
                if (checkValid(config, k, vessel, sailors)) {
                    bruteForce(config, k + 1, sailors, vessel, vessels);
                }
                else {
                   // System.out.println("Podado!");    //---For debbug
                }
            } else {
                // Solution case
                checkSolution(config,sailors,vessel, vessels);
            }

            // Next successor
            config[k]++;
        }
    }

    private static boolean checkValid(int[] config, int k, Vessel vessel, ArrayList<Sailor> sailors) {
        int num_sailors = 0;
        ArrayList<Sailor> Tripulation = new ArrayList<Sailor>();

        for (int i = 0; i <= k; i++) {
            num_sailors += config[i];
            if (config[i] == 1){
                Tripulation.add(sailors.get(i));
            }
        }
        if (config[k] == 1 && sailor_picked[k]){
            return false;
        }

        double actual_speed = getRealSpeed(vessel, Tripulation);

        if (actual_speed <= bestConfig) {
            return false;
        }
        return (num_sailors <= vessel.capacity);
    }

    private static void checkSolution(int[] config, ArrayList<Sailor> sailors, Vessel vessel, ArrayList<Vessel> vessels) {
        findMinVesselWeight(vessels);
        ArrayList<Sailor> Tripulation = new ArrayList<Sailor>();
        int num_sailors = 0;
        for (int i = 0; i < config.length; i++) {
            num_sailors += config[i];
            if (config[i] == 1){
                Tripulation.add(sailors.get(i));
            }
        }
        if (num_sailors != vessel.capacity){
            return;
        }
        double actual_speed = getRealSpeed(vessel, Tripulation);
        if (config[sailors.size() - 1] == 1 && sailor_picked[sailors.size() - 1]){
            return;
        }


        if (bestConfig < actual_speed) {
            bestConfig = actual_speed;
            config_win = config.clone();
        }
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

    private static double getRealSpeed(Vessel vessel, ArrayList<Sailor> sailors) {

        double sailorsFactor = 1.0f;
        double realSpeed;

        for (Sailor sailor: sailors) {
            sailorsFactor = sailorsFactor * sailorImpact(sailor, vessel);
        }

        realSpeed = vessel.speed * sailorsFactor;

        return realSpeed;

    }
}
