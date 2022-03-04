package com.company;

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