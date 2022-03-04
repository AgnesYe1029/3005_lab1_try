package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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