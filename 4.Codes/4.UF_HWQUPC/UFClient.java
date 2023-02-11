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
    static String filePath = "3.plotData/UFPlotData.csv";
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Please input N: ");
//        int n = sc.nextInt();
        int max = 100;
        List<String> y = new ArrayList<>();
        List<String> x = new ArrayList<>();
        for (int n = 1; n < max; n++) {
            Integer numOfCounnections = count(n);
            x.add(String.valueOf(n));
            y.add(numOfCounnections.toString());
            System.out.println("Number of connections when n equals to " + n + " is: " + numOfCounnections);
        }
        writeDataLineByLine(filePath, x.toArray(new String[0]), y.toArray(new String[0]));
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
}
