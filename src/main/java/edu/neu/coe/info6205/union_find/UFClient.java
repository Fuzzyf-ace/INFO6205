package edu.neu.coe.info6205.union_find;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UFClient {
    static String filePath = "3.plotData/";
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Please input N: ");
//        int n = sc.nextInt();
        int max = (int) Math.pow(2, 18);
        int numExperiments = 100;
        List<String> x = new ArrayList<>();
        List<String> y = new ArrayList<>();
//        List<List<String>> z = new ArrayList<>();
//        z.add(new ArrayList<>());
//        for (int i = 0; i < numExperiments; i++) {
//            z.add(new ArrayList<>());
//        }
//        for (int i = 0; i < numExperiments; i++) {
//            int numOfPairs = 0;
//            for (int n = 1; n <= max; n++) {
//                numOfPairs = pairCount(n);
//                z.get(i).add(String.valueOf(numOfPairs));
//            }
//        }
        List<String> z = new ArrayList<>();

        for (int n = 1; n <= max; n=2*n) {
            int numOfPairs = 0;
            int numOfCounnections = count(n);
            for (int i = 0; i < numExperiments; i++) {
                numOfPairs += pairCount(n);
            }
            numOfPairs /= numExperiments;
            x.add(String.valueOf(n));
            y.add(String.valueOf(numOfCounnections));
            z.add(String.valueOf(numOfPairs));
            System.out.println("Number of connections when n equals to " + n + " is: " + numOfCounnections + ", average number of pairs is " + numOfPairs);
        }

        writeDataLineByLine(filePath + "UFConnectionsData.csv", x.toArray(new String[0]), y.toArray(new String[0]));
        writeDataLineByLine(filePath + "UFPairsData.csv", x.toArray(new String[0]), z.toArray(new String[0]));

//        writeDataLineByLine(filePath + "UFPairsData.csv", x.toArray(new String[0]), z, z.size());

    }
    public static int count(int n) {
        UF_HWQUPC uf_hwqupc = new UF_HWQUPC(n);
        Random rd = new Random();
        int numOfConnections = 0;
        while (uf_hwqupc.components() != 1) {
            int i = rd.nextInt(n);
            int j = rd.nextInt(n);
            while (i == j) {
                j = rd.nextInt(n);
            }
            if (!uf_hwqupc.isConnected(i, j)) {
                uf_hwqupc.connect(i, j);
                numOfConnections++;
            }
        }
        return numOfConnections;
    }

    public static int pairCount(int n) {
        UF_HWQUPC uf_hwqupc = new UF_HWQUPC(n);
        Random rd = new Random();
        int numOfPairs = 0;
        while (uf_hwqupc.components() != 1) {
            int i = rd.nextInt(n);
            int j = rd.nextInt(n);
            while (i == j) {
                j = rd.nextInt(n);
            }
            if (!uf_hwqupc.isConnected(i, j)) {
                uf_hwqupc.connect(i, j);
            }
            numOfPairs++;
        }
        return numOfPairs;
    }
    public static void writeDataLineByLine(String filePath, String[] x, String[] y) {
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(x);
            writer.writeNext(y);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void writeDataLineByLine(String filePath, String[] x, List<List<String>> y, int sizeOfIndependent) {
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(x);
            for (int i = 0; i < sizeOfIndependent; i++) {
                writer.writeNext(y.get(i).toArray(new String[0]));
            }

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
