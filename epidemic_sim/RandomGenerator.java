import java.util.Random;

// A wrapper for random functions that we only care for.
public class RandomGenerator {
    private Random generator;

    public RandomGenerator(int seed) {
        generator = new Random(seed);
    }

    // Get random integer in range [min;max). Assumes min <= max.
    public int getRandomIntInRange(int min, int max) {
        assert min <= max;
        return generator.nextInt(max - min) + min;
    }

    // Given a percentage of chances for an event to occur, returns true if an
    // event has occured and false if not. This assumes that [chances] are in
    // range of [0-1].
    public boolean getEventOccurrence(float chances) {
        assert (0.0f <= chances && chances <= 1.0f);

        // In order to avoid the case, when chances are 0 and nextFloat is 0.0f
        if (chances == 0.0f)
            return false;

        float random = generator.nextFloat();
        return random <= chances;
    }
}
