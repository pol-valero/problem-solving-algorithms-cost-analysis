

public class Sailor {
    public int id;
    public String name;
    public float weight;
    public int[] abilities;
    public int WL;

    public Sailor(int id, String name, float weight, int[] abilities, int WL){
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.abilities = abilities;
        this.WL = WL;
    }

    public int getVesselAbility (String vesselType) {

        int vesselAbility = 0;

        switch (vesselType) {

            case Const.WINDSURF:
                vesselAbility = abilities[0];
                break;
            case Const.OPTIMIST:
                vesselAbility = abilities[1];
                break;
            case Const.LASER:
                vesselAbility = abilities[2];
                break;
            case Const.PATI_CATALA:
                vesselAbility = abilities[3];
                break;
            case Const.HOBBIE_DRAGOON:
                vesselAbility = abilities[4];
                break;
            case Const.HOBIE_CAT:
                vesselAbility = abilities[5];
                break;
        }

        return vesselAbility;
    }

}
