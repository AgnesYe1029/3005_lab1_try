package com.company;

import java.util.*;

/**Task 1
 * You will need to solve a relaxed version of the NYC instance where we do not have the energy
 * constraint. You can use any algorithm we discussed in the lectures. Note that this is equivalent to
 * solving the shortest path problem.
 *
 */

// Java Program to Implement Dijkstra's Algorithm
// Using Priority Queue
// Main class DPQ
public class Task1 {

    // Member variables of this class
    private Double dist[];
    private static long energy_cost;
    private static int[] prev; //previous path before reaching the destination
    private Set<Integer> settled;
    private PriorityQueue<Node> pq;
    // Number of vertices
    private int V;
    List<List<Node>> adj;
    // energy cost array
    List<List<Node>> cost_adj;


    // constructor of this class
    public Task1(int V) {
        this.V = V;
        this.energy_cost = 0;
        dist = new Double[V];
        prev = new int[V];
        settled = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());
    }

    // Method 1
    // Dijkstra's Algorithm
    public void dijkstra(List<List<Node>> adj, List<List<Node>> cost_adj, int src) {
        this.adj = adj;
        this.cost_adj = cost_adj;

        for (int i = 0; i < V; i++) {
            dist[i] = Double.MAX_VALUE;
            prev[i] = -1; //initialize all the predecessors of all nodes to be -1
        }

        // Add source node to the priority queue
        pq.add(new Node(src, 0.0));

        // Distance to the source is 0
        dist[src] = 0.0;

        while (settled.size() != V) {

            // Terminating condition check when
            // the priority queue is empty, return
            if (pq.isEmpty())
                return;

            // Removing the minimum distance node
            // from the priority queue
            int u = pq.remove().node;

            // Adding the node whose distance is
            // finalized
            if (settled.contains(u))

                // Continue keyword skips execution for
                // following check
                continue;

            // We don't have to call e_Neighbors(u)
            // if u is already present in the settled set.
            settled.add(u);

            e_Neighbours(u);
        }
    }

    // Method 2
    // To process all the neighbours
    // of the passed node
    private void e_Neighbours(int u) {

        Double edgeDistance = -1.0;
        Double newDistance = -1.0;

        // All the neighbors of v
        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);

            // If current node hasn't already been processed
            if (!settled.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;

                // If new distance is cheaper in cost
                if (newDistance < dist[v.node]) {
                    dist[v.node] = newDistance;
                    prev[v.node] = u; //remember the predecessor
                    for (int j = 0; j < cost_adj.get(u).size(); j++) {
                        if (cost_adj.get(u).get(j).node == v.node) {
                            double new_cost = cost_adj.get(u).get(j).cost;
                            energy_cost += Math.round(new_cost);
                            break;
                        }
                    }//adding up the cost

                }

                // Add the current node to the queue
                pq.add(new Node(v.node, dist[v.node]));
            }
        }
    }

    //
    public static void solveTask1(int numOfNodes, Map<int[], Double> distMap_int, Map<int[], Integer> costMap_int) {
        int V = numOfNodes;
        int source = 0;

        // Adjacency list representation of the
        // connected edges by declaring List class object
        // Declaring object of type List<Node>
        List<List<Node>> adj
                = new ArrayList<List<Node>>();

        // Initialize list for every node
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            adj.add(item);
        }

        // Inputs for the GFG(dpq) graph
        // Iterating HashMap through for loop
        for (Map.Entry<int[], Double> set :
                distMap_int.entrySet()) {
            int nodex = set.getKey()[0] - 1;//node need to be converted.
            int nodey = set.getKey()[1] - 1;
            Double dist = set.getValue();
            adj.get(nodex).add(new Node(nodey, dist));
            adj.get(nodey).add(new Node(nodex, dist));
        }

        // Adjacency list representation of the energy cost
        List<List<Node>> cost_adj
                = new ArrayList<List<Node>>();

        // Initialize list for every node
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            cost_adj.add(item);
        }

        // Inputs for the cost energy adj list
        for (Map.Entry<int[], Integer> set :
                costMap_int.entrySet()) {
            int nodea = set.getKey()[0] - 1;//node need to be converted.
            int nodeb = set.getKey()[1] - 1;
            double energy = set.getValue();
            cost_adj.get(nodea).add(new Node(nodeb, energy));
            cost_adj.get(nodeb).add(new Node(nodea, energy));
        }

        // Calculating the single source shortest path
        Task1 dpq = new Task1(V);
        long startTime = System.nanoTime();
        dpq.dijkstra(adj, cost_adj, source);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;


        // Print shortest path
        System.out.print("Shortest path: ");
        int j = 49;
        ArrayList<Integer> finalPath = new ArrayList<Integer>();
        while (j >= 0) {
            finalPath.add(0, j);
            j = prev[j];
        }
        int val = 0;
        while (finalPath.size() > val) {
            System.out.print(finalPath.get(val) + 1);
            if (val != finalPath.size() - 1)
                System.out.print("->");
            else System.out.print("\n");
            val++;
        }

        //print shortest distance
        System.out.println("Shortest distance: " + dpq.dist[49]);

        //print energy cost
        System.out.println("Energy consumed: " + energy_cost);

        //print time used
        System.out.println("Time used to explore: " + duration + " ms");

    }
}

// Class 2
// Helper class implementing Comparator interface
// Representing a node in the graph
class Node implements Comparator<Node> {

    // Member variables of this class
    public int node;
    public Double cost;

    // Constructors of this class

    // Constructor 1
    public Node() {}

    // Constructor 2
    public Node(int node, Double cost)
    {

        // This keyword refers to current instance itself
        this.node = node;
        this.cost = cost;
    }

    // Method 1
    @Override public int compare(Node node1, Node node2)
    {

        if (node1.cost < node2.cost)
            return -1;

        if (node1.cost > node2.cost)
            return 1;

        return 0;
    }
}
