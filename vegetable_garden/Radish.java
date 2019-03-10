// Mateusz Dudziński PO 2018L, Task 3.

public class Radish extends Vegetable {

    // 5 s or less after planting value is 0, then lerps in 10 seconds to 25,
    // then after 5 seconds value becomes 0.
    @Override
    public float evaluate() {
        int current_time = Simulation.getTime();

        // For first 5 seconds the value is 0.
        if (current_time - plant_time <= 5)
            return 0.0f;

        // Value lerps to 25 at frame 15.
        else if (current_time - plant_time <= 15)
            // This is the only place in the task when are are forced to use floats.
            return 25.0f * (current_time - plant_time - 5) / 10;

        // Radish is worth 25 for 4 more seconds.
        else if (current_time - plant_time <= 19)
            return 25.0f;

        // And after it it becomes 0.
        else
            return 0.0f;

    }
    
    @Override
    public String toString() {
        return "Radish";
    }

    public Radish(int plant_time) {
        super(plant_time, 8);
    }
}
