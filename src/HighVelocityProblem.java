import java.util.ArrayList;

public class HighVelocityProblem {
    //Here we will put all the methods to solve the problem with all the different approaches

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

}
