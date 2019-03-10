// Mateusz Dudziński PO 2018L, Task 3.

import java.util.Random;

public class Simulation {

    // How long (in ms) takes 'one minute' of the simulation. 60000 will make it
    // real-time.
    public static final int delta_time = 1 * 1;

    // Current time (in 'minutes') in the simulation.
    private static int time = 0;

    public static int getTime() {
        return time;
    }
    
    public static void incrementTime() {
    	time += 1;
    }

    // Random number generator used by the simulation.
    private static Random random_generator;

    public static void sleepOneMinute() {
        try {
            Thread.sleep(delta_time);
        }
        // TODO: No idea how to get here, C-c does not work for me.
        catch (InterruptedException e) {
            System.out.println("Thread has been interrupted, exitting...");
            System.exit(130);
        }
    }


    // Returns the random inteeger in range 0 (inclusive), and max (exclusive).
    public static int getRandomNumber(int max) {
        return Math.abs(random_generator.nextInt() % max);
    }

    public static void main(String[] args) {
        // Inicialize random generator with the current time as a seed.
        random_generator = new Random(System.currentTimeMillis());

        System.out.println("Simulation started! (Gardener strategy: farmer).\n");
        Gardener tested_gardener = new GardenerFarmer();
        tested_gardener.simulate(new Garden(10), 40);

        System.out.println("Simulation started! (Gardener strategy: PGR).\n");
        tested_gardener = new GardenderPGREmployee();
        tested_gardener.simulate(new Garden(20), 60);
    }
}
