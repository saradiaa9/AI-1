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
    public int getIndex (ArrayList<ArrayList<String>> bottles){
        for (int i = 0; i < bottles.size(); i++){
            if (bottles.get(i).equals(state)){
                return i;
            }
        }
        return -1;
    }
    
    public boolean canPour(int from, int to) {
        String topFrom = getTop(from);
        String topTo = getTop(to);
        
        // Check if the source bottle is empty
        if (topFrom.equals(empty)) {
            return false;
        }
        
        // Check if the target bottle is empty or has the same top color as the source
        if (topTo.equals(empty) || topTo.equals(topFrom)) {
            // Check if the target bottle has enough space to accept the liquid
            int emptySlotsInTarget = 0;
            for (String color : state.get(to)) {
                if (color.equals(empty)) {
                    emptySlotsInTarget++;
                }
            }
            // Pour is only possible if the target bottle has space for the topFrom color
            return emptySlotsInTarget > 0;
        }
        
        return false;
    }
    
    public void pour(int from, int to) {
        // Get a copy of the current state
        ArrayList<String> sourceBottle = state.get(from);
        ArrayList<String> targetBottle = state.get(to);
        
        // Remove "e" from both bottles
        ArrayList<String> cleanedSource = removeE(sourceBottle);
        ArrayList<String> cleanedTarget = removeE(targetBottle);
        
        // Get the top color from the source bottle
        String topFrom = cleanedSource.remove(0);  // Remove the top color from source
    
        // Add the top color to the target bottle
        cleanedTarget.add(0, topFrom);
    
        // Fill the remaining slots with "e" to restore bottle sizes
        while (cleanedSource.size() < sourceBottle.size()) {
            cleanedSource.add(empty);
        }
        while (cleanedTarget.size() < targetBottle.size()) {
            cleanedTarget.add(empty);
        }
    
        // Update the state with the new bottles
        state.set(from, cleanedSource);
        state.set(to, cleanedTarget);
    }
}          