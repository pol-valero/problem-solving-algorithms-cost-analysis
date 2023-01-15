
import java.lang.reflect.Field;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {

    private static void highVelocityNavigationMenu(ArrayList<Vessel> vessels, ArrayList<Sailor> sailors) {
        int option;

        HighVelocityProblem highVelocityProblem = new HighVelocityProblem();

        //Change options as needed. More approaches can be added

        do {

        System.out.println("\nHow do you want to solve the problem? ");
        System.out.print("\n\t1) Bruteforce approach");
        System.out.print("\n\t2) Backtracking approach");
        System.out.print("\n\t3) Backtracking with 'PBMSC' and 'Marcatge' approach");
        System.out.print("\n\t4) Greedy approach");
        System.out.print("\n\t5) Go back\n\n");

        option = Menu.askForInteger("Please choose an option: ", 1, 5);

            switch (option) {

                case 1:
                    //highVelocityProblem.bruteforce(vessels, sailors);
                    break;

                case 2:
                    //Backtracking
                    highVelocityProblem.BackTracking_Problem(vessels,sailors);
                    break;

                case 3:
                    //Backtracking with PBMSC and Marcatge
                    break;

                case 4:
                    //Greedy approach
                    break;
            }

        } while (option != 5);

    }

    private static void entireFleetMenu(ArrayList<Vessel> vessels) {
        int option;

        EntireFleetProblem entireFleetProblem = new EntireFleetProblem();

        ArrayList<Center> centers;
        centers = DatasetLoader.loadCenter(vessels);
        printSortedObjectList(centers,true);  //--- For Debbug
        //Change options as needed. More approaches can be added

        do {

            System.out.println("\nHow do you want to solve the problem? ");
            System.out.print("\n\t1) Bruteforce approach");
            System.out.print("\n\t2) Backtracking approach");
            System.out.print("\n\t3) Backtracking with 'PBMSC' and 'Marcatge' approach");
            System.out.print("\n\t4) Branch and bound approach");
            System.out.print("\n\t5) Go back\n\n");

            option = Menu.askForInteger("Please choose an option: ", 1, 5);

            switch (option) {

                case 1:
                    //Bruteforce
                    break;

                case 2:
                    //Backtracking
                    break;

                case 3:
                    //Backtracking with PBMSC and Marcatge
                    EntireFleetProblem.BackTracking_Problem(centers);
                    break;

                case 4:
                    //Branch and bound approach
                    EntireFleetProblem.Greedy_Problem(centers);
                    break;
            }

        } while (option != 5);

    }

    public static void runSelectedMenu(int option, ArrayList<Vessel> vessels, ArrayList<Sailor> sailors) {

        switch (option) {

            case 1:
                highVelocityNavigationMenu(vessels, sailors);
                break;

            case 2:
                entireFleetMenu(vessels);
                break;

            case 3:
                System.out.print("\u001B[31m");
                System.out.print("\n───────────────────────────────────────────────────────────\n" +
                        "─██████████████───████████──████████─██████████████─██████─\n" +
                        "─██░░░░░░░░░░██───██░░░░██──██░░░░██─██░░░░░░░░░░██─██░░██─\n" +
                        "─██░░██████░░██───████░░██──██░░████─██░░██████████─██░░██─\n" +
                        "─██░░██──██░░██─────██░░░░██░░░░██───██░░██─────────██░░██─\n" +
                        "─██░░██████░░████───████░░░░░░████───██░░██████████─██░░██─\n" +
                        "─██░░░░░░░░░░░░██─────████░░████─────██░░░░░░░░░░██─██░░██─\n" +
                        "─██░░████████░░██───────██░░██───────██░░██████████─██████─\n" +
                        "─██░░██────██░░██───────██░░██───────██░░██────────────────\n" +
                        "─██░░████████░░██───────██░░██───────██░░██████████─██████─\n" +
                        "─██░░░░░░░░░░░░██───────██░░██───────██░░░░░░░░░░██─██░░██─\n" +
                        "─████████████████───────██████───────██████████████─██████─\n" +
                        "───────────────────────────────────────────────────────────\n");
                System.out.print("\u001B[0m");
                break;

        }
    }

    //Asks for an option number, and checks if a wrong input is entered
    static int askForInteger(String message, int min, int max) {

        Scanner s = new Scanner(System.in);

        int option = min - 1;

        do {
            System.out.print(message);
            try {
                option = s.nextInt();
                if (option < min || option > max) {
                    System.out.printf("\nPlease enter a number between %d and %d.%n\n", min, max);
                }
            } catch (InputMismatchException e) {
                System.out.println("\nPlease enter a number.\n");
            } finally {
                s.nextLine();
            }
        } while (option < min || option > max);

        return option;
    }

    //Asks the user which datasets he wants to load and loads the chosen dataset
    public static ArrayList<Vessel> DatasetSelectionVessels() {

        ArrayList<Vessel> vessels;
        int option;

        System.out.println("\nWelcome to CatTheHobie - The Sequel!");

        System.out.println("\nDataset selection");

        System.out.print("\n\t1) boatsXS.txt");
        System.out.print("\n\t2) boatsS.txt");
        System.out.print("\n\t3) boatsM.txt");
        System.out.println("\n\t4) boatsL.txt\n");

        option = Menu.askForInteger("Please choose an option: ", 1, 4);


        switch (option) {
            case 1:
                vessels = DatasetLoader.loadVessels("boatsXS.txt");
                break;

            case 2:
                vessels = DatasetLoader.loadVessels("boatsS.txt");
                break;

            case 3:
                vessels = DatasetLoader.loadVessels("boatsM.txt");
                break;

            case 4:
                vessels = DatasetLoader.loadVessels("boatsL.txt");
                break;

            default:
                vessels = DatasetLoader.loadVessels("boatsXS.txt");
                break;
        }

        //HighVelocityProblem.findMinVesselWeight(vessels);

        return vessels;
    }

    public static ArrayList<Sailor> DatasetSelectionNavigators() {

        ArrayList<Sailor> sailors;
        int option;

        System.out.println("\nDataset selection");

        System.out.print("\n\t1) sailorsXS.txt");
        System.out.print("\n\t2) sailorsS.txt");
        System.out.print("\n\t3) sailorsM.txt");
        System.out.println("\n\t4) sailorsL.txt\n");

        option = Menu.askForInteger("Please choose an option: ", 1, 4);


        switch (option) {
            case 1:
                sailors = DatasetLoader.loadNavigator("sailorsXS.txt");
                break;

            case 2:
                sailors = DatasetLoader.loadNavigator("sailorsS");
                break;

            case 3:
                sailors = DatasetLoader.loadNavigator("sailorsM.txt");
                break;

            case 4:
                sailors = DatasetLoader.loadNavigator("sailorsL.txt");
                break;

            default:
                sailors = DatasetLoader.loadNavigator("sailorsXS.txt");
                break;
        }

        return sailors;
    }

    public static void show() {

        System.out.println(
                "\n--------------------------------------------------------------------\n" +
                        "\nWhat do you want to do?\n\n" +
                        "   1. Solve 'High velocity navigation' problem\n" +
                        "   2. Solve 'Entire fleet' problem\n" +
                        "   3. Exit\n\n--------------------------------------------------------------------\n");
    }


    private static void printSortedObjectList(ArrayList<?> objects, Boolean ascendingOrder) {
        int i;

        if (ascendingOrder) {
            for (i = 0; i < objects.size(); i++) {
                printObjectFields(objects, i);
            }
        } else {
            for (i = objects.size() - 1; i >= 0; i--) {
                printObjectFields(objects, i);
            }
        }
    }

    private static void printObjectFields(ArrayList<?> objects, int i) {
        System.out.print("\n\n");
        for (Field field : objects.get(i).getClass().getFields()) {
            String name = field.getName();
            Object value;
            try {
                value = field.get(objects.get(i));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (!name.equals("abilities")) {
                System.out.printf("%s: %s\n", name, value);
            } else {
                System.out.printf("%s: ", name);
                int[] fieldArrayValues = (int[]) value;
                System.out.printf(" Windsurf(%d/10)", fieldArrayValues[0]);
                System.out.printf("  Optimist(%d/10)", fieldArrayValues[1]);
                System.out.printf("  Laser(%d/10)", fieldArrayValues[2]);
                System.out.printf("  PatíCatalà(%d/10)", fieldArrayValues[3]);
                System.out.printf("  HobieDragoon(%d/10)", fieldArrayValues[4]);
                System.out.printf("  HobieCat(%d/10)\n", fieldArrayValues[5]);
            }
        }
    }

}
