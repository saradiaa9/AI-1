package code;

import java.util.ArrayList;
import java.util.Arrays;

public class WaterSortSearch {
    private static GenericSearch search = new GenericSearch(); // Initialize here

    // Constructor
    public WaterSortSearch() {
        // No need to initialize search here since it's already static
    }

    // Parse the provided string to create the initial Node state
    static Node parseInitialState(String initialStateStr) {
        // Split the input string by semicolon
        String[] parts = initialStateStr.split(";");

        // Extract number of bottles and bottle capacity
        int numberOfBottles = Integer.parseInt(parts[0].trim());
        int bottleCapacity = Integer.parseInt(parts[1].trim());

        // Initialize the state with the specified number of bottles
        ArrayList<ArrayList<String>> state = new ArrayList<>(numberOfBottles);

        // Parse each bottle's contents
        for (int i = 2; i < parts.length; i++) {
            String[] colors = parts[i].trim().split(","); // Split by comma
            ArrayList<String> bottleContents = new ArrayList<>(Arrays.asList(colors));
            
            // Fill empty slots to the bottle capacity
            while (bottleContents.size() < bottleCapacity) {
                bottleContents.add("e"); // Adding "e" for empty slots
            }
            state.add(bottleContents);
        }

        // Return a new Node with the parsed state
        return new Node(state);
    }

    // Solve method that takes initial state and strategy as parameters
    public static String solve(String initialStateStr, String strategy, boolean visualize) {
        Node initialState = parseInitialState(initialStateStr); // Parse the initial state string

        String result = "";

        switch (strategy) {
            case "BF":
                result = search.breadthFirstSearch(initialState, visualize);
                break;
            case "DF":
                result = search.depthFirstSearch(initialState, visualize);
                break;
            case "UC":
                result = search.uniformCostSearch(initialState, visualize);
                break;
            case "ID":
                result = search.iterativeDeepening(initialState, visualize);
                break;
            case "GR1":
                result = search.greedy1Search(initialState, visualize);
                break;
            case "GR2":
                result = search.greedy2Search(initialState, visualize);
                break;
            case "AS1":
                result = search.aStar1Search(initialState, visualize);
                break;
            case "AS2":
                result = search.aStar2Search(initialState, visualize);
                break;
            default:
                throw new IllegalArgumentException("Unknown strategy: " + strategy);
        }

        return result; // Return the result of the search
    }
}
