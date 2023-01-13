import java.util.ArrayList;
import java.util.LinkedList;


public class Main {

    public static void main(String[] args) {

        int option;

        ArrayList<Vessel> vessels;
        ArrayList<Sailor> sailors;
        vessels = Menu.DatasetSelectionVessels();
        sailors = Menu.DatasetSelectionNavigators();


        do {

            Menu.show();
            option = Menu.askForInteger("Please choose an option: ", 1, 3);
            Menu.runSelectedMenu(option, vessels, sailors);

        } while (option != 3);

    }

}