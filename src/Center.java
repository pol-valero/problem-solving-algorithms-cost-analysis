import java.util.ArrayList;
import java.util.LinkedList;

public class Center {
    public String name;
    public int num_boats;
    public ArrayList<String> types;

    public Center(String center_name, String type){
        types = new ArrayList<>();
        this.name = center_name;
        types.add(type);
        num_boats = 1;
    }

    public void new_boat (String type){
        num_boats++;
        types.add(type);
    }

}
