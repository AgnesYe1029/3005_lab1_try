package com.company;

import java.util.*;

public class Task2 {
    static Map<Integer, Map<Integer, Double>> distMap;
    static Map<Integer, Map<Integer, Integer>> energyMap;
    static PriorityQueue<node2> pq;
    final static int root = 1;
    final static int goal = 50;
    final static double energyBudget = 287932.0;

    public Task2(Map<int[],Double> distMap, Map<int[],Integer> energyMap) {
        this.distMap = constructMap(distMap);
        this.energyMap = constructMap(energyMap);
        this.pq =  new PriorityQueue<>((a, b) -> (int) ((a.distCost - b.distCost))) ;
    }

    public static node2 ucs(){
        node2 rootNode = new node2(root);
        pq.offer(rootNode);
        //maintains a map whose key is the node id and the value is all possible cost values to this node
        //Because there may be multiple nodes connected to this node in the ucs exploration with different
        //dist cost and energy cost
        Map<Integer, List<EdgeCost>> visited = new HashMap<>();
        visited.put(rootNode.id, new ArrayList<>());
        visited.get(rootNode.id).add(new EdgeCost(0d, 0d));

        while(!pq.isEmpty()){
            node2 current_node = pq.poll();
            //The first time we come to goal node, it is the shortest path we want
            if(current_node.id == goal){
                return current_node;
            }
            //Explore all neighbour nodes of the current node
            for (int neighbourId : distMap.get(current_node.id).keySet()) {
                double newDistCost = current_node.distCost + distMap.get(current_node.id).get(neighbourId);
                double newEnergyCost = current_node.energyCost + energyMap.get(current_node.id).get(neighbourId);
                EdgeCost newEdgeCost = new EdgeCost(newDistCost, newEnergyCost);
                if (newEnergyCost <= energyBudget
                && isBetter(newEdgeCost, visited.getOrDefault(neighbourId, new ArrayList<>())))
                //Check if energy cost is satisfied + check if the new cost is better than those records
                // we already have in the visited map
                {
                    if(!visited.containsKey(neighbourId)){
                        visited.put(neighbourId, new ArrayList<>());
                    }
                    visited.get(neighbourId).add(newEdgeCost);
                    node2 nextNode = new node2(neighbourId,newDistCost,newEnergyCost,current_node);
                    pq.offer(nextNode);
                }
            }

        }
        return null;
    }
    //method to construct distMap and energyMap
    //Notice: starting index: 1
    public static <T> Map<Integer, Map<Integer, T>> constructMap(Map<int[],T> map){
        Map<Integer, Map<Integer, T>> weightMap = new HashMap<>();
        for (Map.Entry<int[],T> set : map.entrySet()) {
            int node_a = set.getKey()[0];
            int node_b = set.getKey()[1];
            T cost = set.getValue();
            if (!weightMap.containsKey(node_a)) {
                weightMap.put(node_a, new HashMap<>());
            }
            weightMap.get(node_a).put(node_b, cost);
            if (!weightMap.containsKey(node_b)) {
                weightMap.put(node_b, new HashMap<>());
            }
            weightMap.get(node_b).put(node_a, cost);
        }
        return weightMap;
    }
    //Method to check if new cost is better
    public static boolean isBetter(EdgeCost newEdgeCost, List<EdgeCost> exploredEdgeCosts) {
        for (EdgeCost e : exploredEdgeCosts) {
            if (!newEdgeCost.compareTo(e)) {
                return false;
            }
        }
        return true;
    }
}

//A class containing info about both dist cost and energy cost
class EdgeCost {
    double distCost;
    double energyCost;
    public node2 node;

    public EdgeCost(double distCost, double energyCost) {
        this.distCost = distCost;
        this.energyCost = energyCost;
        this.node = null;
    }

    public boolean compareTo(EdgeCost other) {
        return this.distCost < other.distCost || this.energyCost < other.energyCost;
    }
}

//A class containing node info
class node2{
    int id;
    Map<node2,EdgeCost> neighbours;
    double distCost;
    double energyCost;
    node2 parent;

    public node2(int id) {
        this.id = id;
        this.distCost = 0;
        this.energyCost = 0;
        this.neighbours = new HashMap<>();
        this.parent = null;
    }

    public node2(int id, double distCost, double energyCost,node2 parent) {
        this.id = id;
        this.distCost = distCost;
        this.energyCost = energyCost;
        this.neighbours = new HashMap<>();
        this.parent = parent;
    }
    public void printPath(){
        Stack<node2> s = new Stack<>();
        node2 temp = this;
        while(temp != null){
            s.push(temp);
            temp = temp.parent;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Shortest path: \n");
        while(!s.empty() && s.size() != 1){
            sb.append(s.pop().id);
            sb.append("->");
        }
        sb.append(s.pop().id);
        System.out.println(sb);
    }
}