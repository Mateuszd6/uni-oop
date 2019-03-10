// Mateusz Dudziński PO 2018L, Task 3.

import java.util.ArrayList;

public abstract class Gardener {

    // List of the vegetables collected by the gardener.
    private ArrayList<Vegetable> harvested_vegetables;

    // Amount of cash that Gardener gains, and amount that he invests. Plant
    // cost happens to be always an int, but cash does not (because of the
    // linear scaling of the radish).
    private float income;
    private int costs;

    // Used for initializing simulation for some gardeners.
    protected void initSimulation(Garden garden) {}
    
    // Strategy-specific update function.
    protected abstract void update(Garden garden);

    public Gardener() {
        this.income = 0;
        this.costs = 0;
        this.harvested_vegetables = new ArrayList<Vegetable>();
    }

    // Plant randomly generated vegetable at slot [slot_id],
    // created in current time.
    public void plantRandomVegetable(Garden garden, int slot_id) {
    	assert !garden.vegetableAtSlotExists(slot_id);
    	
        int current_time = Simulation.getTime();
        int random_number = Simulation.getRandomNumber(3);
        Vegetable random_vegetable = null;
        switch(random_number) {
            case 0:
                random_vegetable = new Potato(current_time);
                break;

            case 1:
                random_vegetable = new Tomato(current_time);
                break;

            case 2:
                random_vegetable = new Radish(current_time);
                break;

            default:
                // will NOT execute because random_number is in range [0;2].
        }

        assert random_vegetable != null;

        // Remove cost cash.
        int vegetable_cost = random_vegetable.getPlantCost();

        // TODO: Verbosity level?
        logMessage(String.format("Planting new %s at slot: %d (cost: %d).\n",
                                 random_vegetable.toString(),
                                 slot_id, vegetable_cost));

        costs += vegetable_cost;
        garden.addVegetableAtSlot(random_vegetable, slot_id);

        // TODO: logging!
    }

    public void harvestVegetable(Garden garden, int slot_id) {
    	assert garden.vegetableAtSlotExists(slot_id);
    	
        Vegetable harvested_vegetabe = garden.harvestFromSlot(slot_id);

        harvested_vegetables.add(harvested_vegetabe);


        // Add money for harvesting the vegetabe.
        float vegetable_value = harvested_vegetabe.evaluate();
        income += vegetable_value;

        logMessage(String.format("I've harvested %s, from slot %d" + 
        					     " (with income: %.2f).\n",
                                 harvested_vegetabe.toString(), 
                                 slot_id, vegetable_value));
    }

    public void logMessage(String msg) {
        System.out.println(msg);
    }

    public void printSummary() {
        logMessage(String.format(
                       "Summary:\nTotal income: %.2f zl\n" + 
                       "Total costs: %d zl\nOutcome: %.2f\n",
                       income, costs, income - costs));
    }

    // Run the simulation.
    public void simulate(Garden g, int time) {
    	initSimulation(g);
    	for (int i = 0; i < time; ++i) {
            // Call updatestrategy each 'minute'.
            update(g);
            Simulation.sleepOneMinute();
            Simulation.incrementTime();
        }

        printSummary();
    }
}
