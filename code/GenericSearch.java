package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        int nodesExpanded = 0;

        bfsQueue.add(initialState);

        while (!bfsQueue.isEmpty()) {
            Node currentNode = bfsQueue.poll();
            if (currentNode == null)
                break;
            nodesExpanded++;
            System.out.println(currentNode.state.toString());
            if(currentNode.parent != null)
                System.out.println(currentNode.parent.state.toString());

            if (currentNode.isGoal()) {
                // System.out.println("parent Node :" + currentNode.parent.parent.toString());
                
                //  System.out.println(constructSolution(currentNode).toString().replaceAll("\\[", "").replaceAll("\\]", "") + ";" + currentNode.pathCost + ";" + nodesExpanded);
                return (constructSolution(currentNode).toString().replaceAll("\\[", "").replaceAll("\\]", "") + ";" + currentNode.pathCost + ";" + nodesExpanded);
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
