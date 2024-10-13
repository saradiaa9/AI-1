package code;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public Node parent;
    public ArrayList<ArrayList<String>> state;
    public String[] acceptedColors = { "r", "g", "b", "y", "o" };
    public String empty = "e";
    public Action action;
    public int maxSize;

    public Node(Node parent, ArrayList<ArrayList<String>> state, Action action, int maxSize) {
        this.parent = parent;
        this.state = state;
        this.action = action;
        this.maxSize = maxSize;
    }

    public Node(ArrayList<ArrayList<String>> state, int maxSize) {
        this(null, state, null, maxSize);
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

    public boolean isGoal(ArrayList<ArrayList<String>> goal) {
        for (ArrayList<String> bottle : state) {
            ArrayList<String> bottleWithoutE = removeE(bottle);
            if (!bottleWithoutE.isEmpty()) {
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

    public int stepCost( Action action) {
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
            System.out.println("Cannot pour from bottle " + from + " to bottle " + to);
            return null; // Exit if pouring is not allowed
        }

        // Get a copy of the current state
        ArrayList<String> sourceBottle = state.get(from);
        ArrayList<String> targetBottle = state.get(to);

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

        for (int i = 0; i < numbersToAdd; i++) {
            cleanedSource.remove(i);
            cleanedTarget.add(topFrom);
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

        Node newNode = new Node(this, state, new Action(from, to), maxSize);

        newNode.state.set(from, cleanedSource);
        newNode.state.set(to, cleanedTarget);

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

}