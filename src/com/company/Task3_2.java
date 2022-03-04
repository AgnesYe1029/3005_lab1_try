package com.company;

import java.util.*;

public class Task3_2 {
    static Map<Integer, Map<Integer, Double>> distMap;
    static Map<Integer, Map<Integer, Integer>> energyMap;
    static Map<Integer, List<Integer>> coordMap;
    static PriorityQueue<node2> pq;
    final static int root = 1;
    final static int goal = 50;
    final static double energyBudget = 287932.0;
    static double weight;

    public Task3_2(Map<int[],Double> distMap, Map<int[],Integer> energyMap) {
        this.distMap = constructMap(distMap);
        this.energyMap = constructMap(energyMap);
        this.pq =  new PriorityQueue<>((a, b) -> (int) ((a.distCost - b.distCost))) ;
        this.weight = 1.0;
    }
    public Task3_2(Map<int[],Double> distMap, Map<int[],Integer> energyMap, Map<Integer, List<Integer>> coordMap, double weight) {
        this.distMap = constructMap(distMap);
        this.energyMap = constructMap(energyMap);
        this.coordMap = coordMap;
        this.weight = weight;
        this.pq =  new PriorityQueue<>((a, b) -> (int) (AStarCal(a.distCost, coordMap.get(a.id), goal, weight) - AStarCal(b.distCost, coordMap.get(b.id), goal, weight))) ;

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
    public static double AStarCal(double distCost,List<Integer> nodeCoord ,int goal, double weight){
        List<Integer> goalCoord = coordMap.get(goal);
        double x = Math.abs(nodeCoord.get(0) - goalCoord.get(0));
        double y = Math.abs(nodeCoord.get(1) - goalCoord.get(1));
        double euclideanDist = Math.sqrt(x * x + y * y);
        return distCost < euclideanDist ? distCost + euclideanDist
                : (distCost + (2 * weight - 1) * euclideanDist) / weight;
    }
}
