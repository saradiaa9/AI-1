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
        Stack<Node> dfsStack = new Stack<>();
        Set<ArrayList<ArrayList<String>>> visitedStates = new HashSet<>(); // To track visited states
        int nodesExpanded = 0;
    
        dfsStack.push(initialState);
        visitedStates.add(initialState.state);
    
        while (!dfsStack.isEmpty()) {
            Node currentNode = dfsStack.pop();
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
            Collections.reverse(actions);

            for (Action action : actions) {
                Node child = currentNode.pour(action.getSourceBottleId(), action.getDestinationBottleId());
                if(child != null && !visitedStates.contains(child.state)){
                    dfsStack.push(child);
                    visitedStates.add(child.state);
                }
            }
        }
        return "NOSOLUTION";
    
            
    }

    public String uniformCostSearch(Node initialState, boolean visualize) {
        PriorityQueue<Node> ucsQueue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.pathCost - n2.pathCost;
            }
        });
        Set<ArrayList<ArrayList<String>>> visitedStates = new HashSet<>(); // To track visited states
        int nodesExpanded = 0;
    
        ucsQueue.add(initialState);
        visitedStates.add(initialState.state);
    
        while (!ucsQueue.isEmpty()) {
            Node currentNode = ucsQueue.poll();
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
                    ucsQueue.add(child);
                    visitedStates.add(child.state); // Mark this state as visited
                }
            }
        }
    
        return "NOSOLUTION";
    }

    public String iterativeDeepening(Node initialState, boolean visualize) {
        int depth = 0;
        while (true) {
            String result = depthLimitedSearch(initialState, depth, visualize);
            if (!result.equals("CUTOFF")) {
                return result;
            }
            depth++;
        }
    }

    public String depthLimitedSearch(Node initialState, int depth, boolean visualize) {
        Stack<Node> dfsStack = new Stack<>();
        Set<ArrayList<ArrayList<String>>> visitedStates = new HashSet<>(); // To track visited states
        int nodesExpanded = 0;
    
        dfsStack.push(initialState);
        visitedStates.add(initialState.state);
    
        while (!dfsStack.isEmpty()) {
            Node currentNode = dfsStack.pop();
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
    
            if (currentNode.depth < depth) {
                List<Action> actions = currentNode.getActions();
                Collections.reverse(actions);
    
                for (Action action : actions) {
                    Node child = currentNode.pour(action.getSourceBottleId(), action.getDestinationBottleId());
                    if(child != null && !visitedStates.contains(child.state)){
                        dfsStack.push(child);
                        visitedStates.add(child.state);
                    }
                }
            }
        }
        return "CUTOFF";
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
