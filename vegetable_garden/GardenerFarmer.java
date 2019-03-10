// Mateusz Dudziński PO 2018L, Task 3.

/**
 * Garener that each minute go through all vegetables and collected these, which
 * value has decreased. Plant new, random one in their place.
 */
public class GardenerFarmer extends Gardener {

    // Holds info about values of the vegetables 'minute' ago.
    private float[] previous_values;

    public GardenerFarmer() {
        super();
    }
    
    // At the beginning of the simulation we inicliaize [prevous_values] array
    // to have a size of the garden, which is not a mebmer of Gardener class!!
    @Override
    protected void initSimulation(Garden garden) {
    	previous_values = new float[garden.getSize()];
    }

    @Override
    protected void update(Garden garden) {
        for (int slot_id = 0; slot_id < garden.getSize(); ++slot_id) {
            if (!garden.vegetableAtSlotExists(slot_id)) {
            	// If there is no vegetable at slot, we just make a new one.
                plantRandomVegetable(garden, slot_id);
                previous_values[slot_id] = 0.0f;
            }
            else {
            	float vegetable_cost = garden.evaluateVegetableAt(slot_id);
                if (previous_values[slot_id] > vegetable_cost) {
                	harvestVegetable(garden, slot_id);
                    plantRandomVegetable(garden, slot_id);

                    // Because we've removed the vegetabe we set previous cost to 0.
                    previous_values[slot_id] = 0.0f;
                }
                else {
                    // Just update the previous value in the array.
                    previous_values[slot_id] = vegetable_cost;
                }
            	
            }        
        }
    }
}
