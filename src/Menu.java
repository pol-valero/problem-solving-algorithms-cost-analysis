
import java.lang.reflect.Field;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Menu {

    public static void runSelectedOption(int option, LinkedList<Vessel> vessels, LinkedList<Sailor> sailors) {

        long initialTime;
        long executionTime;         //Here we will store the execution time of an algorithm

        switch (option) {

            case 1:
                initialTime = System.nanoTime();
                //SortingAlgorithms.quickSortAge(vessels);                                                //We sort the vessels list by age with quickSort
                executionTime = System.nanoTime() - initialTime;

                System.out.println("\nThe vessels have been sorted by age using QuickSort");
                printSortedObjectList(vessels, false);
                System.out.println("\nSorting algorithm execution time: " + (double) executionTime/1000000 + " ms");     //Print the execution time of the algorithm
                break;

            case 2:

                initialTime = System.nanoTime();
                //SortingAlgorithms.mergeSortName(vessels);                                               //We sort the vessels list by name with mergeSort
                executionTime = System.nanoTime() - initialTime;

                System.out.println("\nThe vessels have been sorted by name using MergeSort");
                //printSortedObjectList(vessels, true);
                System.out.println("\nSorting algorithm execution time: " + (double) executionTime/1000000 + " ms");     //Print the execution time of the algorithm
                break;

            case 3:
                initialTime = System.nanoTime();
                //SortingAlgorithms.bucketSortCapabilities(vessels);                                      //We sort the vessels list by capabilities with bucketSort
                executionTime = System.nanoTime() - initialTime;

                System.out.println("\nThe vessels have been sorted by name using BucketSort");
                //printSortedObjectList(vessels,false);
                System.out.println("\nSorting algorithm execution time: " + (double) executionTime/1000000 + " ms");     //Print the execution time of the algorithm
                break;

            case 4:
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
    public static LinkedList<Vessel> DatasetSelectionVessels() {

        LinkedList<Vessel> vessels;
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

        return vessels;
    }

    public static LinkedList<Sailor> DatasetSelectionNavigators() {

        LinkedList<Sailor> sailors;
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
                        "   1. Sort vessels by age\n" +
                        "   2. Sort vessels by name\n" +
                        "   3. Sort vessels by capabilities\n" +
                        "   4. Exit\n\n--------------------------------------------------------------------\n");
    }


    private static void printSortedObjectList(LinkedList<Vessel> vessels, Boolean ascendingOrder) {
        int i;

        if (ascendingOrder) {
            for (i = 0; i < vessels.size(); i++) {
                printObjectFields(vessels, i);
            }
        } else {
            for (i = vessels.size() - 1; i >= 0; i--) {
                printObjectFields(vessels, i);
            }
        }
    }

    private static void printObjectFields(LinkedList<?> objects, int i) {
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
