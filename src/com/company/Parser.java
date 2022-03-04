package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    public HashMap<int[], Double> parseDistJson() throws FileNotFoundException {
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
        return distMap_intArray;
    }
    public Map<Integer, List<String>> parseGJson() throws FileNotFoundException {
        URL gPath = Main.class.getResource("G.json");
        File gFile = new File(gPath.getFile());
        Reader gReader = new BufferedReader(new FileReader(gFile));
        //G.json file is stored into a hashmap of key type string and value type a list.
        Map<Integer, List<String>> gMap = new Gson().fromJson(gReader,
                new TypeToken<HashMap<Integer, List<String>>>() {}.getType());
        return gMap;
    }
    public Map<Integer, List<Integer>> parseCoordJson() throws FileNotFoundException {
        URL coordPath = Main.class.getResource("Coord.json");
        File coordFile = new File(coordPath.getFile());
        Reader coordReader = new BufferedReader(new FileReader(coordFile));
        //Coord.json file is stored into a hashmap of key type string and value type a list.
        Map<Integer, List<Integer>> coordMap = new Gson().fromJson(coordReader,
                new TypeToken<HashMap<Integer, List<Integer>>>() {}.getType());
        return coordMap;
    }
    public HashMap<int[], Integer> parseCostJson() throws FileNotFoundException {
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
        }
        return costMap_intArray;
    }
}
