package code;

public class Action {
    private int sourceBottleId;
    private int destinationBottleId;
    private int cost = 1;

    public Action(int sourceBottleId, int destinationBottleId) {
        this.sourceBottleId = sourceBottleId;
        this.destinationBottleId = destinationBottleId;
    }

    public int getSourceBottleId() {
        return sourceBottleId;
    }

    public int getDestinationBottleId() {
        return destinationBottleId;
    }

    @Override
    public String toString() {
        return "pour_" + sourceBottleId + "_" + destinationBottleId;
    }

    public int getCost() {
        return cost;
    }
}
