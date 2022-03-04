package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws IOException {

        /**
         * Preparation: Handling the input json files
         *
         */
        Parser parser = new Parser();
        //read Dist.json
        Map<int[], Double> distMap_intArray = parser.parseDistJson();

        //read G.json
        Map<Integer, List<String>> gMap = parser.parseGJson();

        //read Coord.json
        Map<Integer, List<Integer>> coordMap = parser.parseCoordJson();

        //read Cost.json
        Map<int[], Integer> costMap_intArray = parser.parseCostJson();


        /**
         * Task 1
         */
        int V = coordMap.size(); // total number of nodes
        System.out.println("-----------------------Task1---------------------------------------");
        GFG.solveTask1(V, distMap_intArray, costMap_intArray);

        /**
         * Task 2
         */
        Task2 task2 = new Task2(distMap_intArray,costMap_intArray);
        long startTime2 = System.nanoTime();
        node2 ans2 = task2.ucs();
        long endTime2 = System.nanoTime();
        long duration2 = (endTime2 - startTime2) / 1000000;

        System.out.println("-----------------------Task2---------------------------------------");
        if(ans2 != null){
            ans2.printPath();
            System.out.println("Shortest distance: "+  ans2.distCost);
            System.out.println("Energy consumed: " + ans2.energyCost);
            System.out.println("Time used to explore: " + duration2 + " ms");

        }

        /**
         * Task 3
         */
        /**
         * Task 3 Static Weighting
         * f(n) = g(n) + w * h(n)
         * w = 1.00, 1.01, 1.02 ...... 5.98,5.99
         */
        System.out.println("-----------------------Task3 Static Weighting---------------------------------------");
        double weight = 1;
        for(int i = 0; i < 500; i++){
            Task3_1 task3_1 = new Task3_1(distMap_intArray,costMap_intArray,coordMap,weight + i * 0.01);
            long startTime3_1 = System.nanoTime();
            node2 ans3_1 = task3_1.ucs();
            long endTime3_1 = System.nanoTime();
            long duration3_1 = (endTime3_1 - startTime3_1) / 1000000;

            if(ans3_1 != null){
                System.out.println("===================" + i +"th run========================================");
                ans3_1.printPath();
                System.out.println("Shortest distance: "+  ans3_1.distCost);
                System.out.println("Energy consumed: " + ans3_1.energyCost);
                System.out.println("Time used to explore: " + duration3_1 + " ms");

            }
        }


        /**
         * Task 3 Dynamic Weighting
         * f(n) = f(n) = g(n) < h(n)
         *      ? g(n) + h(n)
         *      : (g(n) + (2*w - 1) * h(n)) / w
         */
        double weight2 = 1;
        System.out.println("-----------------------Task3 Dynamic Weighting---------------------------------------");
        for(int i = 0; i < 100; i++) {
            Task3_2 task3_2 = new Task3_2(distMap_intArray,costMap_intArray,coordMap,weight2 + i * 0.01);
            long startTime3_2 = System.nanoTime();
            node2 ans3_2 = task3_2.ucs();
            long endTime3_2 = System.nanoTime();
            long duration3_2 = (endTime3_2 - startTime3_2) / 1000000;

            if(ans3_2 != null){
                System.out.println("===================" + i +"th run========================================");
                ans3_2.printPath();
                System.out.println("Shortest distance: "+  ans3_2.distCost);
                System.out.println("Energy consumed: " + ans3_2.energyCost);
                System.out.println("Time used to explore: " + duration3_2 + " ms");

            }
        }

    }

}





