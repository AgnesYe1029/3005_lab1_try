package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        /**
         * Preparation: Handling the input json files
         *
         */

        //read Dist.json
        URL distPath = Main.class.getResource("Dist.json");
        File distFile = new File(distPath.getFile());
        Reader distReader = new BufferedReader(new FileReader(distFile));
        //Dist.json file is stored into a hashmap.
        Map<String, Double> distMap = new Gson().fromJson(distReader,
                new TypeToken<HashMap<String, Double>>() {}.getType());
        //convert the distmap keys from string to intarray;
        HashMap<int[], Double> distMap_intArray = new HashMap<int[], Double>();
        for (Map.Entry<String, Double> set :
                distMap.entrySet()) {

            String temp = set.getKey().replaceAll("^\"|\"$", "");;
            String[] strArray = temp.split(",");
            int[] intArray = new int[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                intArray[i] = Integer.parseInt(strArray[i]);
            }
            distMap_intArray.put(intArray, set.getValue());
        } //now that the distmap_intarray has int list keys. the keys follow [1, 2].

        //read G.json
        URL gPath = Main.class.getResource("G.json");
        File gFile = new File(gPath.getFile());
        Reader gReader = new BufferedReader(new FileReader(gFile));
        //G.json file is stored into a hashmap of key type string and value type a list.
        Map<Integer, List<String>> gMap = new Gson().fromJson(gReader,
                new TypeToken<HashMap<Integer, List<String>>>() {}.getType());

        //read Coord.json
        URL coordPath = Main.class.getResource("Coord.json");
        File coordFile = new File(coordPath.getFile());
        Reader coordReader = new BufferedReader(new FileReader(coordFile));
        //Coord.json file is stored into a hashmap of key type string and value type a list.
        Map<Integer, List<Integer>> coordMap = new Gson().fromJson(coordReader,
                new TypeToken<HashMap<Integer, List<Integer>>>() {}.getType());

        //read Cost.json
        URL costPath = Main.class.getResource("Cost.json");
        File costFile = new File(costPath.getFile());
        Reader costReader = new BufferedReader(new FileReader(costFile));
        //Cost.json file is stored into a hashmap.
        Map<String, Integer> costMap = new Gson().fromJson(costReader,
                new TypeToken<HashMap<String, Integer>>() {}.getType());
        //convert the costmap keys from string to intarray;
        HashMap<int[], Integer> costMap_intArray = new HashMap<int[], Integer>();
        for (Map.Entry<String, Integer> set :
                costMap.entrySet()) {

            String temp = set.getKey().replaceAll("^\"|\"$", "");;
            String[] strArray = temp.split(",");
            int[] intArray = new int[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                intArray[i] = Integer.parseInt(strArray[i]);
            }
            costMap_intArray.put(intArray, set.getValue());
        } //now that the costmap_intarray has int list keys. the keys follow [1, 2].

        //Example - make sure the inputs are correctly handled.
//        System.out.println(distMap.get("1,2"));
//        System.out.println(gMap.get(1));
//        System.out.println(coordMap.get(1));
//        System.out.println(coordMap.size());
//        for (Map.Entry<int[], Double> set :
//                costMap_intArray.entrySet()) {
//            if(set.getKey()[0]==107502 || set.getKey()[1] == 107502)
//                System.out.println(Arrays.toString(set.getKey()) + " = " + set.getValue());
//        }
//        int[] temparr = new int[2];
//        temparr[0] = 107503;
//        temparr[0] = 107502;
//
//        System.out.println(costMap_intArray.containsKey(temparr));

//        for (Map.Entry<String, Double> set :
//                costMap.entrySet()) {
////            System.out.println("for debugging the costMap_local");
////            System.out.println(set.getKey());
//            if (set.getKey().equals("1273,1277")) {
//                System.out.println("for debugging the costMap_local"); //1->1363->1358->1357->1356->1276->1273->1277->1269->1267->1268->1284->1283->1282->1255
//                System.out.println(set.getKey() + "=" + set.getValue());
//            }
//        }


        /**
         * Task 1
         */
        int V = coordMap.size(); // total number of nodes
        //GFG.solveTask1(V, distMap_intArray, costMap_intArray);

        /**
         * Task 2
         */
        Task2 task2 = new Task2(distMap_intArray,costMap_intArray);
        long startTime = System.nanoTime();
        node ans = task2.ucs();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        if(ans != null){
            ans.printPath();
            System.out.println("Shortest distance: "+  ans.distCost);
            System.out.println("Energy consumed: " + ans.energyCost);
            System.out.println("Time used to explore: " + duration + " ms");

        }

    }

}





