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
    public int depth;

    public Node(Node parent, ArrayList<ArrayList<String>> state, Action action, int maxSize, int pathCost, int depth) {
        this.parent = parent;
        this.state = state;
        this.action = action;
        this.maxSize = maxSize;
        this.pathCost = pathCost;
        this.depth = depth;
    }

    public Node(ArrayList<ArrayList<String>> state, int maxSize) {
        this(null, state, null, maxSize, 0, 0);
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
        // Check if pouring from 'from' bottle to 'to' bottle is allowed
        if (!canPour(from, to)) {

            return null; // Exit if pouring is not allowed
        }

        ArrayList<ArrayList<String>> newState = new ArrayList<>();

        for (ArrayList<String> bottle : state) {
            ArrayList<String> newBottle = new ArrayList<>();
            for (String color : bottle) {

                newBottle.add(new String(color));
            }
            newState.add(newBottle);
        }

        // Get a copy of the current state
        ArrayList<String> sourceBottle = newState.get(from);
        ArrayList<String> targetBottle = newState.get(to);

        // Remove "e" from both bottles
        ArrayList<String> cleanedSource = removeE(sourceBottle);
        ArrayList<String> cleanedTarget = removeE(targetBottle);

        int numberOfTop = 0;

        // Get the top color from the source bottle
        String topFrom = cleanedSource.get(0); // Remove the top color from source

        numberOfTop++;
        for (int i = 1; i < cleanedSource.size(); i++) {
            if (cleanedSource.get(i).equals(topFrom)) {
                numberOfTop++;
            } else {
                break;
            }
        }

        int possibleColorsToAdd = maxSize - cleanedTarget.size();

        int numbersToAdd = Math.min(possibleColorsToAdd, numberOfTop);
        if (isGoal() && isFull(cleanedSource) && cleanedTarget.isEmpty()) {
            return null;

        }
        for (int i = 0; i < numbersToAdd; i++) {
            cleanedSource.remove(0);
            cleanedTarget.add(0, topFrom);
        }

        // Fill the remaining slots with "e" to restore bottle sizes
        while (cleanedSource.size() < maxSize) {
            cleanedSource.add(0, empty);
        }
        while (cleanedTarget.size() < maxSize) {
            cleanedTarget.add(0, empty);
        }

        // // Update the state with the new bottles
        // state.set(from, cleanedSource);
        // state.set(to, cleanedTarget);

        newState.set(from, cleanedSource);
        newState.set(to, cleanedTarget);

        Node newNode = new Node(this, newState, new Action(from, to), maxSize, numbersToAdd + pathCost, depth + 1);

        return newNode;
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

    public boolean isSorted(ArrayList<String> bottle) {

        String top = getTop(state.indexOf(bottle));

        for (String color : bottle) {
            if (!color.equals(top)) {
                return false;
            }
        }

        return true;
    }
    
        public  void visualize() {
            System.out.println("Current State of Bottles:");
            // Print the contents of each bottle
            for (int i = 0; i < state.size(); i++) {
                System.out.print("Bottle " + i + ": ");
                ArrayList<String> bottle = state.get(i);
                for (String color : bottle) {
                    System.out.print(color + " ");
                }
                System.out.println();
            }
            System.out.println();
        
            // Visualize the pouring actions as a stack
            if (action != null) {
                System.out.println("Pouring from Bottle " + action.getSourceBottleId() +
                                   " to Bottle " + action.getDestinationBottleId() + ":");
                
                // Get the source and destination bottles
                ArrayList<String> sourceBottle = state.get(action.getSourceBottleId());
                ArrayList<String> destinationBottle = state.get(action.getDestinationBottleId());
        
                // Display the pouring process
                String colorToPour = sourceBottle.get(0); // Assume the top color to pour
                System.out.println("Pouring color: " + colorToPour);
        
                // Simulate the pour by modifying the state for visualization
                // Create temporary copies of the bottles to show the state change
                ArrayList<String> tempSource = new ArrayList<>(sourceBottle);
                ArrayList<String> tempDestination = new ArrayList<>(destinationBottle);
        
                // Remove the color from the source bottle
                tempSource.remove(0);
                // Add the color to the destination bottle
                tempDestination.add(0, colorToPour);
        
                // Print the updated states
                System.out.println("Updated State After Pour:");
                System.out.print("Source Bottle After Pour: ");
                for (String color : tempSource) {
                    System.out.print(color + " ");
                }
                System.out.println();
        
                System.out.print("Destination Bottle After Pour: ");
                for (String color : tempDestination) {
                    System.out.print(color + " ");
                }
                System.out.println();
            } else {
                System.out.println("No action to visualize.");
            }
        }
    }
        

