import java.nio.file.Files;
import java.nio.file.Path;


import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class DatasetLoader {

    private static String separator = ";";

    private static Vessel csvLineToVessel (String csvLine) {
        String[] field = csvLine.split(separator);
        Vessel vessel = new Vessel(Integer.parseInt(field[0]), field[1], field[2], Float.parseFloat(field[3]), Float.parseFloat(field[4]),
                Integer.parseInt(field[5]), Integer.parseInt(field[6]), field[7], Integer.parseInt(field[8]), field[9]);

        return vessel;
    }
    private static Sailor csvLineToNavigator (String csvLine) {
        int[] abilities = new int[6];
        String[] field = csvLine.split(separator);

        //Variable temporal con el array de todas las habilidades
        for (int i = 0; i < 6; i++) {
            abilities[i] = Integer.parseInt(field[i+3]);
        }

        //Crear el nuevo objeto de navegador con todas sus caracteristicas.
        Sailor sailor = new Sailor(Integer.parseInt(field[0]), field[1], Float.parseFloat(field[2]),
                abilities, Integer.parseInt(field[9]));

        return sailor;
    }
    public static ArrayList<Vessel> loadVessels(String datasetName) {

        Path path = Path.of("datasets/" + datasetName);

        boolean firstLine = true;

        ArrayList<Vessel> vesselsList = new ArrayList<>();

        try {
            List<String> csvLines = Files.readAllLines(path);

            for (String csvLine : csvLines) {
                if(!firstLine) {
                    vesselsList.add(csvLineToVessel(csvLine));
                } else {
                    firstLine = false;
                }
            }

            return vesselsList;
        } catch (IOException e) {
            return vesselsList;
        }
    }
    public static ArrayList<Sailor> loadNavigator(String datasetName) {

        Path path = Path.of("datasets/" + datasetName);

        boolean firstLine = true;

        ArrayList<Sailor> sailorList = new ArrayList<>();

        try {
            List<String> csvLines = Files.readAllLines(path);

            for (String csvLine : csvLines) {
                if(!firstLine) {
                    sailorList.add(csvLineToNavigator(csvLine));
                } else {
                    firstLine = false;
                }
            }

            return sailorList;
        } catch (IOException e) {
            return sailorList;
        }
    }

}