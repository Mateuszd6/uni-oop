// Mateusz Dudziński PO 2018L, Task 3.

public abstract class Vegetable {
    // Time (in minutes), from the beggining of the simulation, when the
    // vegetable has been planted.
    protected int plant_time;

    // The cost of planting the vegetable.
    private int plant_cost;

    public int getPlantCost() {
        return plant_cost;
    }

    // Evaluate the cost of the vegetable.
    public abstract float evaluate();

    public abstract String toString();

    public Vegetable(int plant_time, int plant_cost) {
        this.plant_time = plant_time;
        this.plant_cost = plant_cost;
    }
}
