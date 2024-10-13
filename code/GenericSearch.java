package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.*;

public class GenericSearch {
    

    private List<Action> constructSolution(Node node) {
        
        List<Action> result = new ArrayList<>();
        while (node != null){
            if(node.getAction() != null){
                Action action = node.getAction();
                result.add(action);
                // System.out.println(problem.stepCost(node.getState(), action));
                // if(node.getParent() != null)
                // pathCost += pathCost + 1;
            }
            node = node.getParent();
            
        }
        Collections.reverse(result);
        return result;
    }

    public String breadthFirstSearch(Node initialState, boolean visualize) {

        Queue<Node> bfsQueue = new LinkedList<>();
        Set<ArrayList<ArrayList<String>>> visitedStates = new HashSet<>(); // To track visited states
        int nodesExpanded = 0;
    
        bfsQueue.add(initialState);
        visitedStates.add(initialState.state);
    
        while (!bfsQueue.isEmpty()) {
            Node currentNode = bfsQueue.poll();
            if (currentNode == null)
                break;
    
            nodesExpanded++;
    
            if (visualize) {
                System.out.println("Current Node: " + currentNode.state);
                if (currentNode.parent != null)
                    System.out.println("Parent Node: " + currentNode.parent.state);
            }
    
            if (currentNode.isGoal()) {
                return constructSolution(currentNode).toString().replaceAll("\\[", "").replaceAll("\\]", "") 
                       + ";" + currentNode.pathCost + ";" + nodesExpanded;
            }
    
            List<Action> actions = currentNode.getActions();
    
            for (Action action : actions) {
                Node child = currentNode.pour(action.getSourceBottleId(), action.getDestinationBottleId());
    
                if (child != null && !visitedStates.contains(child.state)) { // Check if the state has been visited
                    bfsQueue.add(child);
                    visitedStates.add(child.state); // Mark this state as visited
                }
            }
        }
    
        return "NOSOLUTION";
    }
    
    public String depthFirstSearch(Node initialState, boolean visualize) {
        return "X";
    }

    public String uniformCostSearch(Node initialState, boolean visualize) {
        return "X";
    }

    public String iterativeDeepening(Node initialState, boolean visualize) {
        return "X";
    }

    public String aStar1Search(Node initialState, boolean visualize) {
        return "X";
    }

    public String aStar2Search(Node initialState, boolean visualize) {
        return "X";
    }

    public String greedy1Search(Node initialState, boolean visualize) {
        return "X";
    }

    public String greedy2Search(Node initialState, boolean visualize) {
        return "X";
    }

}
