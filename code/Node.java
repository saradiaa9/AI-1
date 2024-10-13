package code;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Node {
    public Node parent;
    public ArrayList<ArrayList<String>> state;
    public String[] acceptedColors = { "r", "g", "b", "y", "o" };
    public String empty = "e";
    public Action action;
    public int maxSize;
    public int pathCost = 0;

    public Node(Node parent, ArrayList<ArrayList<String>> state, Action action, int maxSize, int pathCost) {
        this.parent = parent;
        this.state = state;
        this.action = action;
        this.maxSize = maxSize;
        this.pathCost = pathCost;
    }

    public Node(ArrayList<ArrayList<String>> state, int maxSize) {
        this(null, state, null, maxSize, 0);
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public Node getParent() {
        return parent;
    }

    // public boolean isGoal() {
    // ArrayList<Boolean> goals = new ArrayList<Boolean>();
    // for (ArrayList<String> bottle : state) {
    // ArrayList<String> bottleWithoutE = removeE(bottle);
    // if (!bottleWithoutE.isEmpty()) {
    // String top = bottleWithoutE.get(0);
    // for (String color : bottleWithoutE) {
    // if (!color.equals(top)) {
    // return false;
    // }
    // }
    // }
    // }
    // return true;
    // }

    public boolean isGoal() {

        for (ArrayList<String> bottle : state) {
            ArrayList<String> bottleWithoutE = removeE(bottle);
            if (!bottleWithoutE.isEmpty() && bottleWithoutE.size() < maxSize) {
                return false;
            }
            if (!bottleWithoutE.isEmpty() && bottleWithoutE.size() == maxSize) {
                String top = bottleWithoutE.get(0);
                for (String color : bottleWithoutE) {
                    if (!color.equals(top)) {
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

    public int stepCost(Action action) {
        // Define costs based on the action's source and destination
        if (action.getSourceBottleId() == 0 || action.getDestinationBottleId() == 1) {
            return 3; // Cost for specific action
        }
        // Add more conditions for different costs
        return 1; // Default cost
    }

    public String getTop(int bottleIndex) {
        ArrayList<String> bottle = state.get(bottleIndex);
        ArrayList<String> bottleWithoutE = removeE(bottle);
        if (bottleWithoutE.isEmpty()) {
            return empty;
        }
        return bottleWithoutE.get(0);

    }

    public int getIndex(ArrayList<ArrayList<String>> bottles) {
        for (int i = 0; i < bottles.size(); i++) {
            if (bottles.get(i).equals(state)) {
                return i;
            }
        }
        return -1;
    }
  
    public boolean isFull(ArrayList<String> bottle) {
        return bottle.size() == maxSize;

    }

    public boolean canPour(int from, int to) {

        if (from == to) {
            return false;
        }
        

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
    public Node pour(int from, int to) {
        if (!canPour(from, to)) {
            System.out.println("Cannot pour from bottle " + from + " to bottle " + to);
            return null;
        }
    
        // Clone only the affected bottles
        ArrayList<ArrayList<String>> newState = new ArrayList<>(state); 
        ArrayList<String> sourceBottle = new ArrayList<>(newState.get(from)); // Copy the source bottle
        ArrayList<String> targetBottle = new ArrayList<>(newState.get(to));   // Copy the target bottle
    
        ArrayList<String> cleanedSource = removeE(sourceBottle);
        ArrayList<String> cleanedTarget = removeE(targetBottle);
    
        if (isGoal() && isFull(cleanedSource) && cleanedTarget.isEmpty()) {
            return null;
        }
    
        String topFrom = cleanedSource.get(0); 
        int numberOfTop = 1;
    
        for (int i = 1; i < cleanedSource.size(); i++) {
            if (cleanedSource.get(i).equals(topFrom)) {
                numberOfTop++;
            } else {
                break;
            }
        }
    
        int possibleColorsToAdd = maxSize - cleanedTarget.size();
        int numbersToAdd = Math.min(possibleColorsToAdd, numberOfTop);
    
        for (int i = 0; i < numbersToAdd; i++) {
            cleanedSource.remove(0);
            cleanedTarget.add(0, topFrom);
        }
    
        while (cleanedSource.size() < maxSize) {
            cleanedSource.add(empty);
        }
        while (cleanedTarget.size() < maxSize) {
            cleanedTarget.add(empty);
        }
    
        newState.set(from, cleanedSource); 
        newState.set(to, cleanedTarget);
    
        return new Node(this, newState, new Action(from, to), maxSize, numbersToAdd + pathCost);
    }
    
    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>();

        for (int i = 0; i < state.size(); i++) {
            for (int j = 0; j < state.size(); j++) {
                if (i != j) {
                    Action action = new Action(i, j);
                    if (canPour(i, j)) {
                        actions.add(action);
                    }
                }
            }
        }

        return actions;
    }

}