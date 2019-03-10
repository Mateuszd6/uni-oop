// Mateusz Dudziński PO 2018L, Task 3.

public class Tomato extends Vegetable {

    // 10 s after planting value is 0, then lerps to 30 in 5 s, then lerps to 0
    // in another 5 s.
    @Override
    public float evaluate() {

        int current_time = Simulation.getTime();

        // For first 10 seconds the value is 0.
        if (current_time - plant_time <= 10)
            return 0.0f;

        // Lerps to 30 (30 is reach for only one frame, and it is 15 seconds
        // after planting.)
        else if (current_time - plant_time <= 15)
            return 30 * (current_time - plant_time - 10) / 5;

        // Lerps back to 0. 0 is reached 20 minutes or more after planting.
        else if (current_time - plant_time <= 20)
            return 30 * (5 - (current_time - plant_time - 15)) / 5;
        else
            return 0.0f;
    }

    @Override
    public String toString() {
        return "Tomato";
    }

    public Tomato(int plant_time) {
        super(plant_time, 10);
    }
}
