import java.util.ArrayList;
import java.util.LinkedList;

public class Center {
    public String name;
    public ArrayList<String> types;

    public Center(String center_name, String type){
        types = new ArrayList<>();
        this.name = center_name;
        types.add(type);
    }

    public void new_boat (String type){
        types.add(type);
    }

}
