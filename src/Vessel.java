
public class Vessel {


    public int id;
    public String name;
    public String type;
    public float weight;
    public float length;
    public int capacity;
    public int n_competitions;
    public String status;
    public int speed;
    public String center;


    public Vessel (int id, String name, String type, float weight, float length, int capacity, int n_competitions, String status, int speed, String center) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.length = length;
        this.capacity = capacity;
        this.n_competitions = n_competitions;
        this.status = status;
        this.speed = speed;
        this.center = center;
    }

}
