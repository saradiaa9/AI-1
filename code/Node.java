package code;

import java.util.ArrayList;

public class Node {
    public Node parent;
    public ArrayList<ArrayList<String>> state;
    public String[] acceptedColors = {"r", "g", "b", "y", "o"};
    public String empty = "e";
    public int pathCost;
    
    public Node(Node parent, ArrayList<ArrayList<String>> state, int pathCost) {
        this.parent = parent;
        this.state = state;
        this.pathCost = pathCost;
    }

    public Node(ArrayList<ArrayList<String>> state) {
        this(null, state, 0);
    }

    public boolean isGoal(ArrayList<ArrayList<String>> goal) {
        for (ArrayList<String> bottle : state){
            ArrayList<String> bottleWithoutE = removeE(bottle);
            if (!bottleWithoutE.isEmpty()){
                String top = bottleWithoutE.get(0);
                for (String color : bottleWithoutE){
                    if (!color.equals(top)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<String> removeE(ArrayList<String> bottle) {
        ArrayList<String> newBottle = new ArrayList<String>();
        for (String color : bottle) {
            if (!color.equals(empty)) {
                newBottle.add(color);
            }
        }
        return newBottle;
    }

    public String getTop (int bottleIndex){
        ArrayList<String> bottle = state.get(bottleIndex);
        ArrayList<String> bottleWithoutE = removeE(bottle);
        if (bottleWithoutE.isEmpty()){
            return empty;
        }
        return bottleWithoutE.get(0);
        
    }
}
