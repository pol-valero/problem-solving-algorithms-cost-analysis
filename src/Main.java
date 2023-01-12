import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        int option;

        ArrayList<Vessel> vessels;
        ArrayList<Sailor> sailors;
        vessels = Menu.DatasetSelectionVessels();
        sailors = Menu.DatasetSelectionNavigators();

        do {

            Menu.show();
            option = Menu.askForInteger("Please choose an option: ", 1, 4);
            Menu.runSelectedOption(option, vessels, sailors);

        } while (option != 4);

    }

}