package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GenericSearch {
    protected int pathCost = 0;

    private List<Action> constructSolution(Node node) {
        
        List<Action> result = new ArrayList<>();
        while (node != null){
            if(node.getAction() != null){
                Action action = node.getAction();
                result.add(action);
                // System.out.println(problem.stepCost(node.getState(), action));
                if(node.getParent().getParent() != null)
                this.pathCost += node.stepCost(action);
            }
            node = node.getParent();
            
        }
        Collections.reverse(result);
        return result;
    }

    public String breadthFirstSearch(Node initialState, boolean visualize) {

        Queue<Node> bfsQueue = new LinkedList<>();
        int nodesExpanded = 0;

        bfsQueue.add(initialState);

        while (!bfsQueue.isEmpty()) {
            Node currentNode = bfsQueue.poll();
            if (currentNode == null)
                break;
            nodesExpanded++;

            if (currentNode.isGoal(currentNode.state)) {

                
                    // System.out.println(constructSolution(currentNode).toString().replaceAll("\\[", "").replaceAll("\\]", "") + ";" + pathCost + ";" + nodesExpanded);
                return (constructSolution(currentNode).toString().replaceAll("\\[", "").replaceAll("\\]", "") + ";" + pathCost + ";" + nodesExpanded);
            }

            List<Action> actions = currentNode.getActions();

            for (Action action : actions) {

                Node child = currentNode.pour(action.getSourceBottleId(), action.getDestinationBottleId());

                if (child != null) {
                    bfsQueue.add(child);
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
