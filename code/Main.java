// package code;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// public class Main {
    
//     public static void main(String[] args) {
//         // Create some initial bottles
//         ArrayList<String> bottle1 = new ArrayList<>(Arrays.asList("r", "r", "e", "e"));
//         ArrayList<String> bottle2 = new ArrayList<>(Arrays.asList("g", "e", "e", "e"));
//         ArrayList<String> bottle3 = new ArrayList<>(Arrays.asList("r", "r", "r", "g"));
        
//         // Create the state (set of bottles)
//         ArrayList<ArrayList<String>> initialState = new ArrayList<>();
//         initialState.add(bottle1);
//         initialState.add(bottle2);
//         initialState.add(bottle3);
        
//         // Initialize Node with this state
//         Node node = new Node(initialState, 4);
        
//         // Test: Print initial state
//         System.out.println("Initial state:");
//         printState(node.state);

//         // Test: Get the top of each bottle
//         System.out.println("Top of bottle 0: " + node.getTop(0)); // Expected: "r"
//         System.out.println("Top of bottle 1: " + node.getTop(1)); // Expected: "g"
//         System.out.println("Top of bottle 2: " + node.getTop(2)); // Expected: "e"

//         // Test: Can pour from bottle 0 to bottle 1?
//         System.out.println("Can pour from bottle 0 to bottle 1: " + node.canPour(0, 1)); // Expected: true

//         // Test: Pour from bottle 0 to bottle 2
//         System.out.println("Pouring from bottle 0 to bottle 2...");
   
//         Node newNode = node.pour(2, 0);
        
//         // Test: Print new state after pour
//         System.out.println("State after pouring:");
//         printState(newNode.state);
//         String init = "3;" +
//             "4;" +
//             "r,y,r,y;" +
//             "y,r,y,r;" +
//             "e,e,e,e;";
        
//         Node initialNode = WaterSortSearch.parseInitialState(init);
        
        
//         // Output the parsed state to verify correctness
//         System.out.println("Parsed State:");
//         for (ArrayList<String> bottle : initialNode.state) {
//             System.out.println(bottle);
//         }

//         List<String> sara = new ArrayList<>();
//         sara.add("r");
//         sara.add("r");
//         sara.add("r");
//         sara.add("r");
//         System.out.println(sara.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
//     }


    
    

//     // Helper function to print the state of the bottles
//     public static void printState(ArrayList<ArrayList<String>> state) {
//         for (ArrayList<String> bottle : state) {
//             System.out.println(bottle);
//         }
//     }
    
// }
