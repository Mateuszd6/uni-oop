// Mateusz Dudziński PO 2018L, Task 3.

public class Potato extends Vegetable {

    // Until 10 s after planing value is 0, then 5.
    @Override
    public float evaluate() {
        int current_time = Simulation.getTime();

        // For first 10 seconds the value is 0.
        if (current_time - plant_time <= 10)
            return 0.0f;
        else
            return 5.0f;
    }

    @Override
    public String toString() {
        return "Potato";
    }

    public Potato(int plant_time) {
        super(plant_time, 2);
    }
}
