package code;

import java.util.*;

public class GenericSearch {
    
    private List<Action> constructSolution(Node node) {
        List<Action> result = new ArrayList<>();
        while (node != null){
            if(node.getAction() != null){
                Action action = node.getAction();
                result.add(action);
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
            if (!result.equals("NOSOLUTION")) {
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
        return "NOSOLUTION";
    }

    public String aStarWithadmissibleHeuristic1(Node initialState, boolean visualize) {
        PriorityQueue<Node> aStar = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return (n1.pathCost + heuristic1(n1)) - (n2.pathCost + heuristic1(n2));
            }
        });
        Set<ArrayList<ArrayList<String>>> visitedStates = new HashSet<>(); // To track visited states
        int nodesExpanded = 0;

        aStar.add(initialState);
        visitedStates.add(initialState.state);

        while (!aStar.isEmpty()) {
            Node currentNode = aStar.poll();
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
                    aStar.add(child);
                    visitedStates.add(child.state); // Mark this state as visited
                }
            }
        }

        return "NOSOLUTION";
    }

    public String aStarWithadmissibleHeuristic2(Node initialState, boolean visualize) {
        PriorityQueue<Node> aStar = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return (n1.pathCost + heuristic2(n1)) - (n2.pathCost + heuristic2(n2));
            }
        });
        Set<ArrayList<ArrayList<String>>> visitedStates = new HashSet<>(); // To track visited states
        int nodesExpanded = 0;

        aStar.add(initialState);
        visitedStates.add(initialState.state);

        while (!aStar.isEmpty()) {
            Node currentNode = aStar.poll();
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
                    aStar.add(child);
                    visitedStates.add(child.state); // Mark this state as visited
                }
            }
        }

        return "NOSOLUTION";
    }

    public String greedyWithHeuristic1(Node initialState, boolean visualize) {
        PriorityQueue<Node> greedy= new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return heuristic1(n1) - heuristic1(n2);
            }
        });
        Set<ArrayList<ArrayList<String>>> visitedStates = new HashSet<>(); // To track visited states
        int nodesExpanded = 0;

        greedy.add(initialState);
        visitedStates.add(initialState.state);

        while (!greedy.isEmpty()) {
            Node currentNode = greedy.poll();
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
                    greedy.add(child);
                    visitedStates.add(child.state); // Mark this state as visited
                }
            }
        }

        return "NOSOLUTION";
    }

    public String greedyWithHeuristic2(Node initialState, boolean visualize) {
        PriorityQueue<Node> greedy= new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return heuristic2(n1) - heuristic2(n2);
            }
        });
        Set<ArrayList<ArrayList<String>>> visitedStates = new HashSet<>(); // To track visited states
        int nodesExpanded = 0;
        
        greedy.add(initialState);
        visitedStates.add(initialState.state);

        while (!greedy.isEmpty()) {
            Node currentNode = greedy.poll();
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
                    greedy.add(child);
                    visitedStates.add(child.state); // Mark this state as visited
                }
            }
        }

        return "NOSOLUTION";
    }

   //bashof el azayez lw msh fadya w msh full hzawed h w lw msh fadya bs full hashof awel lon w azawed h l kol lon mo5talef
    public int heuristic1(Node initialState){
        int h = 0;
        for (ArrayList<String> bottle : initialState.state) {
            ArrayList<String> bottleWithoutE = initialState.removeE(bottle);
            // if (!bottleWithoutE.isEmpty() && bottleWithoutE.size() < initialState.maxSize) {
            //     h += 1;
            // }
            if (!bottleWithoutE.isEmpty() && bottleWithoutE.size() == initialState.maxSize) {
                String top = bottleWithoutE.get(0);
                for (String color : bottleWithoutE) {
                    if (!color.equals(top)) {
                        h += 1;
                    }
                }
            }
        }
        return h;
    }
    //ba5od el state el feha azayez mtrateba aktar
    public int heuristic2(Node initialState) {
        int h = 0;
    
        // Loop through each bottle in the state
        for (ArrayList<String> bottle : initialState.state) {
            ArrayList<String> bottleWithoutE = initialState.removeE(bottle);
    
            // Count empty bottles
            if (bottleWithoutE.isEmpty()) {
                h += 1;
            } 
            // For non-empty bottles
            else if (bottleWithoutE.size() < initialState.maxSize) {
                h += 1;
            } 
            // For full bottles, check if they are sorted
            else if (bottleWithoutE.size() == initialState.maxSize) {
                if (!initialState.isSorted(bottle)) {
                    h += 1; // Add 1 to the heuristic if the bottle isn't sorted
                }
            }
        }
    
        // Ensure most filled sorted bottles are considered at the start (additional logic could go here)
        
        return h;
    }
    
}
