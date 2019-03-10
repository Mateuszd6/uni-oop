// Mateusz Dudziński PO 2018L, Task 3.

/**
 * Garender that every fixed number of seconds collects all vegetables and
 * plants new, randomly selected onces, in their place.
 */
public class GardenderPGREmployee extends Gardener {

    private static final int collect_interval = 12;
    private int minutes_since_last_collect;

    public GardenderPGREmployee() {
        super();
        minutes_since_last_collect = 0;
    }

    @Override
    protected void update(Garden garden) {
        minutes_since_last_collect++;

        // Chceck if it is a time to collect the vegetables, if so collect all.
        if (minutes_since_last_collect >= collect_interval) {
            for(int slot_id = 0; slot_id < garden.getSize(); ++slot_id) {
                if (garden.vegetableAtSlotExists(slot_id))
                	harvestVegetable(garden, slot_id);
                
                plantRandomVegetable(garden, slot_id);
            }

            minutes_since_last_collect = 0;
        }
    }
}
