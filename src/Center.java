import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Center implements Comparable<Center>{
    public String name;
    public ArrayList<String> types;
    public final String[] all_types = {"Windsurf", "Optimist", "Laser", "Patí Català", "HobieDragoon", "HobieCat"};
    public static boolean[] found_types;


    public Center(String center_name, String type){
        types = new ArrayList<>();
        this.name = center_name;
        types.add(type);
        found_types = new boolean[6];
        Arrays.fill(this.found_types, false);
    }

    public void new_boat (String type){
        types.add(type);
    }
    public int setFound_types (){
        int found = 0;
        for (int i = 0; i < all_types.length; i++) {
            if (!found_types[i] && this.types.contains(all_types[i])) {
                found_types[i] = true;
                found++;
            }
        }
        return found;
    }
    public int compareTo(Center that) {
        int foundActual = 0;
        int foundOther = 0;
        for (int i = 0; i < found_types.length; i++) {
            if (!found_types[i] && this.types.contains(all_types[i])) {
                foundActual++;
            }
            if (!found_types[i] && that.types.contains(all_types[i])) {
                foundOther++;
            }
        }
        return  foundOther - foundActual;
    }

}
